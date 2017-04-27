package com.ferit.dfundak.dz4.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Dora on 26/04/2017.
 */

@Root(name = "item", strict = false)
public class FeedItem {

    @Element(name = "title", required = false) private String mTitle;
    @Element(name = "link", required = false) private String mLink;
    @Element(name = "pubDate", required = false) private String mPublished;
    @Element(name = "description", required = false) private String mDescription;
    @Element(name = "category", required = false) private String mCategory;
    @Element(name = "enclosure", required = false) Enclosure mChannel;


    public Enclosure getChannel() {
        return mChannel;
    }

    public void setChannel(Enclosure mChannel) {
        this.mChannel = mChannel;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        this.mLink = link;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getPublished() {
        return mPublished;
    }

    public void setPublished(String published) {
        this.mPublished = published;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        this.mCategory = category;
    }
}