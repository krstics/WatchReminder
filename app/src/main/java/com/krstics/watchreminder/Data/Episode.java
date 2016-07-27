package com.krstics.watchreminder.Data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Episode")
public class Episode {

    @Element(name = "ProductionCode", required = false)
    private String ProductionCode;

    @Element(name = "Combined_episodenumber", required = false)
    private String Combined_episodenumber;

    @Element(name = "seriesid", required = false)
    private String seriesid;

    @Element(name = "DVD_chapter", required = false)
    private String DVD_chapter;

    @Element(name = "RatingCount", required = false)
    private String RatingCount;

    @Element(name = "id", required = true)
    private String id;

    @Element(name = "thumb_width", required = false)
    private String thumb_width;

    @Element(name = "EpisodeName", required = false)
    private String EpisodeName;

    @Element(name = "IMDB_ID", required = false)
    private String IMDB_ID;

    @Element(name = "Overview", required = false)
    private String Overview;

    @Element(name = "Director", required = false)
    private String Director;

    @Element(name = "EpisodeNumber", required = false)
    private String EpisodeNumber;

    @Element(name = "FirstAired", required = false)
    private String FirstAired;

    @Element(name = "Combined_season", required = false)
    private String Combined_season;

    @Element(name = "thumb_added", required = false)
    private String thumb_added;

    @Element(name = "EpImgFlag", required = false)
    private String EpImgFlag;

    @Element(name = "GuestStars", required = false)
    private String GuestStars;

    @Element(name = "thumb_height", required = false)
    private String thumb_height;

    @Element(name = "seasonid", required = false)
    private String seasonid;

    @Element(name = "DVD_episodenumber", required = false)
    private String DVD_episodenumber;

    @Element(name = "SeasonNumber", required = false)
    private String SeasonNumber;

    @Element(name = "lastupdated", required = false)
    private String lastupdated;

    @Element(name = "airsbefore_episode", required = false)
    private String airsbefore_episode;

    @Element(name = "DVD_season", required = false)
    private String DVD_season;

    @Element(name = "Writer", required = false)
    private String Writer;

    @Element(name = "Rating", required = false)
    private String Rating;

    @Element(name = "airsbefore_season", required = false)
    private String airsbefore_season;

    @Element(name = "DVD_discid", required = false)
    private String DVD_discid;

    @Element(name = "filename", required = false)
    private String filename;

    @Element(name = "Language", required = false)
    private String Language;

    @Element(name = "absolute_number", required = false)
    private String absolute_number;

    @Element(name = "airsafter_season", required = false)
    private String airsafter_season;

    public String getProductionCode() {
        return ProductionCode;
    }

    public void setProductionCode(String productionCode) {
        ProductionCode = productionCode;
    }

    public String getCombined_episodenumber() {
        return Combined_episodenumber;
    }

    public void setCombined_episodenumber(String combined_episodenumber) {
        Combined_episodenumber = combined_episodenumber;
    }

    public String getSeriesid() {
        return seriesid;
    }

    public void setSeriesid(String seriesid) {
        this.seriesid = seriesid;
    }

    public String getDVD_chapter() {
        return DVD_chapter;
    }

    public void setDVD_chapter(String DVD_chapter) {
        this.DVD_chapter = DVD_chapter;
    }

    public String getRatingCount() {
        return RatingCount;
    }

    public void setRatingCount(String ratingCount) {
        RatingCount = ratingCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumb_width() {
        return thumb_width;
    }

    public void setThumb_width(String thumb_width) {
        this.thumb_width = thumb_width;
    }

    public String getEpisodeName() {
        return EpisodeName;
    }

    public void setEpisodeName(String episodeName) {
        EpisodeName = episodeName;
    }

    public String getIMDB_ID() {
        return IMDB_ID;
    }

    public void setIMDB_ID(String IMDB_ID) {
        this.IMDB_ID = IMDB_ID;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public String getEpisodeNumber() {
        return EpisodeNumber;
    }

    public void setEpisodeNumber(String episodeNumber) {
        EpisodeNumber = episodeNumber;
    }

    public String getFirstAired() {
        return FirstAired;
    }

    public void setFirstAired(String firstAired) {
        FirstAired = firstAired;
    }

    public String getCombined_season() {
        return Combined_season;
    }

    public void setCombined_season(String combined_season) {
        Combined_season = combined_season;
    }

    public String getThumb_added() {
        return thumb_added;
    }

    public void setThumb_added(String thumb_added) {
        this.thumb_added = thumb_added;
    }

    public String getEpImgFlag() {
        return EpImgFlag;
    }

    public void setEpImgFlag(String epImgFlag) {
        EpImgFlag = epImgFlag;
    }

    public String getGuestStars() {
        return GuestStars;
    }

    public void setGuestStars(String guestStars) {
        GuestStars = guestStars;
    }

    public String getThumb_height() {
        return thumb_height;
    }

    public void setThumb_height(String thumb_height) {
        this.thumb_height = thumb_height;
    }

    public String getSeasonid() {
        return seasonid;
    }

    public void setSeasonid(String seasonid) {
        this.seasonid = seasonid;
    }

    public String getDVD_episodenumber() {
        return DVD_episodenumber;
    }

    public void setDVD_episodenumber(String DVD_episodenumber) {
        this.DVD_episodenumber = DVD_episodenumber;
    }

    public String getSeasonNumber() {
        return SeasonNumber;
    }

    public void setSeasonNumber(String seasonNumber) {
        SeasonNumber = seasonNumber;
    }

    public String getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(String lastupdated) {
        this.lastupdated = lastupdated;
    }

    public String getAirsbefore_episode() {
        return airsbefore_episode;
    }

    public void setAirsbefore_episode(String airsbefore_episode) {
        this.airsbefore_episode = airsbefore_episode;
    }

    public String getDVD_season() {
        return DVD_season;
    }

    public void setDVD_season(String DVD_season) {
        this.DVD_season = DVD_season;
    }

    public String getWriter() {
        return Writer;
    }

    public void setWriter(String writer) {
        Writer = writer;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getAirsbefore_season() {
        return airsbefore_season;
    }

    public void setAirsbefore_season(String airsbefore_season) {
        this.airsbefore_season = airsbefore_season;
    }

    public String getDVD_discid() {
        return DVD_discid;
    }

    public void setDVD_discid(String DVD_discid) {
        this.DVD_discid = DVD_discid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getAbsolute_number() {
        return absolute_number;
    }

    public void setAbsolute_number(String absolute_number) {
        this.absolute_number = absolute_number;
    }

    public String getAirsafter_season() {
        return airsafter_season;
    }

    public void setAirsafter_season(String airsafter_season) {
        this.airsafter_season = airsafter_season;
    }
}
