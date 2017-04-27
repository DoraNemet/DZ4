package com.ferit.dfundak.dz4.model;

import org.simpleframework.xml.Attribute;

/**
 * Created by Dora on 27/04/2017.
 */

public class Enclosure {

    @Attribute(name = "url") private String mUrl;
    @Attribute(name = "type") private String mType;

    public String getType() {return mType;}
    public String getUrl() { return mUrl; }
    public void setType(String type) {
        this.mType = mType;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }
}
