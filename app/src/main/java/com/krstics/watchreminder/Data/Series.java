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

    //Id at tv.com
    @Element(name = "SeriesID", required = false)
    private String SeriesID;

    @Element(name = "added", required = false)
    private String added;

    @Element(name = "tms_wanted_old", required = false)
    private String tms_wanted_old;

    @Element(name = "Runtime", required = false)
    private String Runtime;

    @Element(name = "ContentRating", required = false)
    private String ContentRating;

    @Element(name = "lastupdated", required = false)
    private String lastupdated;

    @Element(name = "RatingCount", required = false)
    private String RatingCount;

    @Element(name = "fanart", required = false)
    private String fanart;

    @Element(name = "Status", required = false)
    private String Status;

    @Element(name = "Actors", required = false)
    private String Actors;

    @Element(name = "NetworkID", required = false)
    private String NetworkID;

    @Element(name = "Rating", required = false)
    private String Rating;

    @Element(name = "Airs_DayOfWeek", required = false)
    private String Airs_DayOfWeek;

    @Element(name = "Genre", required = false)
    private String Genre;

    @Element(name = "Airs_Time", required = false)
    private String Airs_Time;

    @Element(name = "poster", required = false)
    private String poster;

    @Element(name = "Language", required = false)
    private String Language;

    @Element(name = "addedBy", required = false)
    private String addedBy;

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

    public String getlanguage ()
    {
        return language;
    }

    public void setlanguage (String language)
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

    public String getSeriesID() {
        return SeriesID;
    }

    public void setSeriesID(String seriesID) {
        SeriesID = seriesID;
    }

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public String getTms_wanted_old() {
        return tms_wanted_old;
    }

    public void setTms_wanted_old(String tms_wanted_old) {
        this.tms_wanted_old = tms_wanted_old;
    }

    public String getRuntime() {
        return Runtime;
    }

    public void setRuntime(String runtime) {
        Runtime = runtime;
    }

    public String getContentRating() {
        return ContentRating;
    }

    public void setContentRating(String contentRating) {
        ContentRating = contentRating;
    }

    public String getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(String lastupdated) {
        this.lastupdated = lastupdated;
    }

    public String getRatingCount() {
        return RatingCount;
    }

    public void setRatingCount(String ratingCount) {
        RatingCount = ratingCount;
    }

    public String getFanart() {
        return fanart;
    }

    public void setFanart(String fanart) {
        this.fanart = fanart;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getActors() {
        return Actors;
    }

    public void setActors(String actors) {
        Actors = actors;
    }

    public String getNetworkID() {
        return NetworkID;
    }

    public void setNetworkID(String networkID) {
        NetworkID = networkID;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getAirs_DayOfWeek() {
        return Airs_DayOfWeek;
    }

    public void setAirs_DayOfWeek(String airs_DayOfWeek) {
        Airs_DayOfWeek = airs_DayOfWeek;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getAirs_Time() {
        return Airs_Time;
    }

    public void setAirs_Time(String airs_Time) {
        Airs_Time = airs_Time;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getLanguage ()
    {
        return Language;
    }

    public void setLanguage (String language)
    {
        this.Language = language;
    }

}
