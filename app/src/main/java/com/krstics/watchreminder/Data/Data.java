package com.krstics.watchreminder.Data;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "data")
public class Data {

    @ElementList(inline = true, required = false)
    private List<Series> Series;

    public List<Series> getSeries ()
    {
        return Series;
    }

    public void setSeries (List<Series> Series)
    {
        this.Series = Series;
    }
}