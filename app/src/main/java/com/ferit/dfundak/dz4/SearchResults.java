package com.ferit.dfundak.dz4;

import com.ferit.dfundak.dz4.model.Channel;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Dora on 27/04/2017.
 */

@Root(strict = false, name = "rss")

public class SearchResults {

    @Attribute(name = "version", required = false) private String mVersion;
    @Element(name = "channel") private Channel mChannel;

    public Channel getChannel() { return mChannel; }
}
