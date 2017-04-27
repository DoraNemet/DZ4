package com.ferit.dfundak.dz4;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
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


public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private static final String PATH = "rss/vijesti/";
    private static final String URL_BUG = "http://www.bug.hr";
    private Spinner spnCategory;
    private ListView lvFeed;
    private ArrayList<FeedItem> queryItemList;
    private ArrayList<String> category = new ArrayList<>();
    Intent browserIntent;
    Button refreshBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestAll();

        lvFeed = (ListView) findViewById(R.id.lvFeed);

        this.spnCategory = (Spinner) findViewById(R.id.spnCategory);
        ArrayAdapter<CharSequence> inputDAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, category);
        inputDAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategory.setAdapter(inputDAdapter);
        spnCategory.setOnItemSelectedListener(this);

        //Image loader setup
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        refreshBtn = (Button) findViewById(R.id.refreshButton);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestData();
            }
        });
    }

    private void requestAll() {
        category.add("Sve vijesti");
        Call<SearchResults> call = getService().getAll(PATH);

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
            if (!category.contains(result.getItems().get(i).getCategory())) {
                category.add(result.getItems().get(i).getCategory());
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        requestData();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    //retrofit
    private API getService() {
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(SimpleXmlConverterFactory.create())
                .baseUrl(URL_BUG)
                .build();

        return retrofit.create(API.class);
    }

    //request data
    public void requestData() {
        Call<SearchResults> call = getService().getDetails(PATH);
        call.enqueue(new Callback<SearchResults>() {
            @Override
            public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
                queryItemList = (ArrayList<FeedItem>) response.body().getChannel().getItems();
                updateArticles();
            }

            @Override
            public void onFailure(Call<SearchResults> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }
        });
    }

    private void updateArticles() {
        if (queryItemList == null) {
            noResults();
        } else {
            filterResult();
            ItemAdapter adapter = new ItemAdapter(queryItemList, spnCategory.getSelectedItem().toString());
            lvFeed.setAdapter(adapter);
            lvFeed.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    String selected = ((TextView) view.findViewById(R.id.tv_url)).getText().toString();
                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(selected));
                    startActivity(browserIntent);
                }
            });
        }
    }

    private void filterResult() {
        String selectedCategory = spnCategory.getSelectedItem().toString();
        ArrayList<FeedItem> dArray = new ArrayList<>();
        dArray.addAll(queryItemList);
        int deleted = 0;
        for (int i = 0; i < dArray.size(); i++) {
            if (!dArray.get(i).getCategory().equals(selectedCategory) && !selectedCategory.equals("Sve vijesti")) {
                queryItemList.remove(i - deleted);
                deleted++;
            }
        }
    }

    private void noResults() {
        Toast.makeText(getApplicationContext(), "No articles to show", Toast.LENGTH_LONG).show();
    }
}
