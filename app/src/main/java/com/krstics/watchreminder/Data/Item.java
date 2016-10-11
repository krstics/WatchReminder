package com.krstics.watchreminder.Data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "Items")
public class Item {

    @Element(name = "Time", required = true)
    private String Time;

    @Element(name = "Series", required = false)
    List<String> series;

    @Element(name = "Episode", required = false)
    List<String> episodes;

    public List<String> getSeries() {
        return series;
    }

    public void setSeries(List<String> series) {
        this.series = series;
    }

    public List<String> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<String> episodes) {
        this.episodes = episodes;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
