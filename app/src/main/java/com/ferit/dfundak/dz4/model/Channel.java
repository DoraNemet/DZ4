package com.ferit.dfundak.dz4.model;

/**
 * Created by Dora on 26/04/2017.
 */

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict = false, name = "channel")

public class Channel {

    @ElementList(name = "item", inline = true, required = false) private List<FeedItem> mFeed;

    public List<FeedItem> getItems() { return mFeed; }
}