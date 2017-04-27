package com.ferit.dfundak.dz4.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Dora on 27/04/2017.
 */

@Root(name = "item", strict = false)
public class FeedItem {

    @Element(name = "title", required = false) private String mTitle;
    @Element(name = "link", required = false) private String mLink;
    @Element(name = "description", required = false) private String mDescription;
    @Element(name = "pubDate", required = false) private String mPublished;
    @Element(name = "enclosure", required = false) Enclosure mChannel;
    @Element(name = "category", required = false) private String mCategory;


    public Enclosure getmChannel() {
        return mChannel;
    }

    public void setmChannel(Enclosure mChannel) {
        this.mChannel = mChannel;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmLink() {
        return mLink;
    }

    public void setmLink(String mLink) {
        this.mLink = mLink;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmPublished() {
        return mPublished;
    }

    public void setmPublished(String mPublished) {
        this.mPublished = mPublished;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }
}