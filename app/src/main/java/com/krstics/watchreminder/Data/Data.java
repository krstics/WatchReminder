package com.krstics.watchreminder.Data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "data")
public class Data {

    @ElementList(inline = true, required = false)
    private List<Series> Series;

    @ElementList(inline = true, required = false)
    private List<Episode> Episode;

    @Element(name = "Error", required = false)
    private String Error;

    public List<Series> getSeries ()
    {
        return Series;
    }

    public void setSeries (List<Series> Series)
    {
        this.Series = Series;
    }

    public List<com.krstics.watchreminder.Data.Episode> getEpisode() {
        return Episode;
    }

    public void setEpisode(List<com.krstics.watchreminder.Data.Episode> episode) {
        Episode = episode;
    }
}