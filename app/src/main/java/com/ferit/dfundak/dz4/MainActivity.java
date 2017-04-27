package com.ferit.dfundak.dz4;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ferit.dfundak.dz4.model.Channel;
import com.ferit.dfundak.dz4.model.FeedItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


public class MainActivity extends Activity {

    private static final String PATH = "rss/vijesti/";
    private static final String ENDPOINT = "http://www.bug.hr";

    private ListView lvFeed;
    private ArrayList<FeedItem> queryModelList;
    private ArrayList<String> category = new ArrayList<>();
    Intent browserIntent;
    Button refreshBtn;

    private RefreshServiceConnection mRefreshServiceConnection;
    private Refresh mRefreshService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRefreshServiceConnection = new RefreshServiceConnection();
        requestRaw();
        setupUI();
    }

    private void setupUI() {

        lvFeed = (ListView) findViewById(R.id.lvFeed);
        Intent intent = new Intent(getApplicationContext(), Refresh.class);
        this.bindService(intent, this.mRefreshServiceConnection, Context.BIND_AUTO_CREATE);

        refreshBtn = (Button) findViewById(R.id.refreshButton);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestData();
            }
        });

//Image loader setup
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        //requestData();
    }


    private API getService() {
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(SimpleXmlConverterFactory.create())
                .baseUrl(ENDPOINT)
                .build();

        return retrofit.create(API.class);
    }

    public void requestData() {

        Call<SearchResults> call = getService().getDetails(PATH);

        call.enqueue(new Callback<SearchResults>() {
            @Override
            public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
                queryModelList = (ArrayList<FeedItem>) response.body().getChannel().getItems();
                updateDisplay();
            }

            @Override
            public void onFailure(Call<SearchResults> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }
        });
    }

    private void requestRaw() {
        category.add("Sve vijesti");
        Call<SearchResults> call = getService().getRaw(PATH);

        call.enqueue(new Callback<SearchResults>() {
            @Override
            public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
                setCategory(response.body().getChannel());
            }

            @Override
            public void onFailure(Call<SearchResults> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }
        });
    }

    public void setCategory(Channel result) {
        for (int i = 0; i < result.getItems().size(); i++) {
            if (!category.contains(result.getItems().get(i).getmCategory())) {
                category.add(result.getItems().get(i).getmCategory());
            }
        }
        for (int i = 0; i < category.size(); i++) {
            Log.i("Category#", category.get(i));
        }
    }

    private void updateDisplay() {
        if (queryModelList == null) {
            NoResult();
        } else {
            ItemAdapter adapter = new ItemAdapter(queryModelList);
            lvFeed.setAdapter(adapter);
            lvFeed.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    String selected = ((TextView) view.findViewById(R.id.txhtml)).getText().toString();
                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(selected));
                    startActivity(browserIntent);
                }
            });
        }
    }

    private void NoResult() {
        Toast.makeText(getApplicationContext(), "No articles to show", Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(getApplicationContext(), Refresh.class);
        this.bindService(intent, this.mRefreshServiceConnection, Context.BIND_AUTO_CREATE);
        Log.d("Service", "Bind service");
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unbindService(this.mRefreshServiceConnection);
        Log.d("Service", "Unbind service");
    }

    class RefreshServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mRefreshService = ((Refresh.RefreshFeed) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRefreshService = null;
        }
    }
}
