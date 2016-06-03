package com.krstics.watchreminder.Data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Series")
public class Series {

    @Element(name = "FirstAired")
    private String FirstAired;

    @Element(name = "id")
    private String id;

    @Element(name = "zap2it_id")
    private String zap2it_id;

    @Element(name = "Network")
    private String Network;

    @Element(name = "SeriesName")
    private String SeriesName;

    @Element(name = "seriesid")
    private String seriesid;

    @Element(name = "IMDB_ID", required = false)
    private String IMDB_ID;

    @Element(name = "language")
    private String language;

    @Element(name = "Overview")
    private String Overview;

    @Element(name = "banner", required = false)
    private String banner;

    public String getFirstAired ()
    {
        return FirstAired;
    }

    public void setFirstAired (String FirstAired)
    {
        this.FirstAired = FirstAired;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getZap2it_id ()
    {
        return zap2it_id;
    }

    public void setZap2it_id (String zap2it_id)
    {
        this.zap2it_id = zap2it_id;
    }

    public String getNetwork ()
    {
        return Network;
    }

    public void setNetwork (String Network)
    {
        this.Network = Network;
    }

    public String getSeriesName ()
    {
        return SeriesName;
    }

    public void setSeriesName (String SeriesName)
    {
        this.SeriesName = SeriesName;
    }

    public String getSeriesid ()
    {
        return seriesid;
    }

    public void setSeriesid (String seriesid)
    {
        this.seriesid = seriesid;
    }

    public String getIMDB_ID ()
    {
        return IMDB_ID;
    }

    public void setIMDB_ID (String IMDB_ID)
    {
        this.IMDB_ID = IMDB_ID;
    }

    public String getLanguage ()
    {
        return language;
    }

    public void setLanguage (String language)
    {
        this.language = language;
    }

    public String getOverview ()
    {
        return Overview;
    }

    public void setOverview (String Overview)
    {
        this.Overview = Overview;
    }

    public String getBanner ()
    {
        return banner;
    }

    public void setBanner (String banner)
    {
        this.banner = banner;
    }
}
