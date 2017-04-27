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
 * Created by Dora on 27/04/2017.
 */

public class ItemAdapter extends BaseAdapter {

    private ArrayList<FeedItem> mFeed;

    public ItemAdapter(ArrayList<FeedItem> queryModelList) {
        this.mFeed = queryModelList;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        final FeedItem model = mFeed.get(position);
        ArticleViewHolder articleViewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_row, parent, false);
            articleViewHolder = new ArticleViewHolder(convertView);
            convertView.setTag(articleViewHolder);

        } else {
            articleViewHolder = (ArticleViewHolder) convertView.getTag();
        }

        articleViewHolder.tvTitle.setText(model.getmTitle());
        articleViewHolder.tvDescription.setText(model.getmDescription());
        articleViewHolder.tvLink.setText(model.getmLink());
        articleViewHolder.tvDate.setText(model.getmPublished());

        if (model.getmChannel().getUrl() != null) {

            String picture_url = model.getmChannel().getUrl();

            if (picture_url != null) {
                ImageLoader.getInstance().displayImage(picture_url, articleViewHolder.ivImage);
            }
        } else {
            articleViewHolder.ivImage.setImageResource(ic_launcher_round);
        }
        return convertView;
    }

    static class ArticleViewHolder {
        public ImageView ivImage;
        public TextView tvTitle, tvDate, tvDescription, tvLink;

        public ArticleViewHolder(View tasksView) {

            this.ivImage = (ImageView) tasksView.findViewById(R.id.ivImage);
            this.tvTitle = (TextView) tasksView.findViewById(R.id.txtTitle);
            this.tvDate = (TextView) tasksView.findViewById(R.id.txtPublished);
            this.tvDescription = (TextView) tasksView.findViewById(R.id.txtDescription);
            this.tvLink = (TextView) tasksView.findViewById(R.id.txhtml);
        }
    }
}
