package com.krstics.watchreminder.Data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Series")
public class Series {

    @Element(name = "FirstAired", required = false)
    private String FirstAired;

    @Element(name = "id", required = false)
    private String id;

    @Element(name = "zap2it_id", required = false)
    private String zap2it_id;

    @Element(name = "Network", required = false)
    private String Network;

    @Element(name = "SeriesName", required = false)
    private String SeriesName;

    @Element(name = "AliasNames", required = false)
    private String AliasNames;

    @Element(name = "seriesid", required = false)
    private String seriesid;

    @Element(name = "IMDB_ID", required = false)
    private String IMDB_ID;

    @Element(name = "language", required = false)
    private String language;

    @Element(name = "Overview", required = false)
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

    public String getAliasNames() {
        return AliasNames;
    }

    public void setAliasNames(String aliasNames) {
        AliasNames = aliasNames;
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
