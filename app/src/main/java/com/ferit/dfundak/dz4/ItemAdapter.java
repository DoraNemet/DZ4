package com.ferit.dfundak.dz4;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ferit.dfundak.dz4.model.FeedItem;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import static com.ferit.dfundak.dz4.R.mipmap.ic_launcher_round;

/**
 * Created by Dora on 26/04/2017.
 */

public class ItemAdapter extends BaseAdapter {

    private ArrayList<FeedItem> mFeed;
    private String selectedCategory;

    public ItemAdapter(ArrayList<FeedItem> queryItemList, String selectedItem) {
        this.mFeed = queryItemList;
        this.selectedCategory = selectedItem;
    }

    @Override
    public int getCount() {
        return mFeed.size();
    }

    @Override
    public Object getItem(int i) {
        return mFeed.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, View rootView, ViewGroup parent) {
        final FeedItem model = mFeed.get(position);
        ArticleViewHolder articleViewHolder;

        if (rootView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            rootView = layoutInflater.inflate(R.layout.item_row, parent, false);
            articleViewHolder = new ArticleViewHolder(rootView);
            rootView.setTag(articleViewHolder);
        } else {
            articleViewHolder = (ArticleViewHolder) rootView.getTag();
        }

        articleViewHolder.tvTitle.setText(model.getTitle());
        articleViewHolder.tvDescription.setText(model.getDescription());
        articleViewHolder.tvLink.setText(model.getLink());
        articleViewHolder.tvDate.setText(model.getPublished());

        if (model.getChannel().getUrl() != null) {
            String picture_url = model.getChannel().getUrl();
            if (picture_url != null) {
                ImageLoader.getInstance().displayImage(picture_url, articleViewHolder.ivImage);
            }
        } else {
            articleViewHolder.ivImage.setImageResource(ic_launcher_round);
        }
        return rootView;
    }

    static class ArticleViewHolder {
        public ImageView ivImage;
        public TextView tvTitle, tvDate, tvDescription, tvLink;

        public ArticleViewHolder(View tasksView) {

            this.ivImage = (ImageView) tasksView.findViewById(R.id.iv_image);
            this.tvTitle = (TextView) tasksView.findViewById(R.id.tv_title);
            this.tvDate = (TextView) tasksView.findViewById(R.id.tv_date);
            this.tvDescription = (TextView) tasksView.findViewById(R.id.tv_description);
            this.tvLink = (TextView) tasksView.findViewById(R.id.tv_url);
        }
    }
}
