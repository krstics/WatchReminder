package com.krstics.watchreminder.Data;

import android.graphics.Bitmap;

import com.krstics.watchreminder.Helpers.Utils;

public class EpisodeListData implements Comparable<EpisodeListData>{
    private String episodeId;
    private String episodeName;
    private String episodeNumber;
    private String seasonNumber;
    private String airsDate;
    private String episodeImdbId;
    private String overview;
    private Bitmap episodeBanner;
    private Bitmap showBanner;
    private String airsDayOfWeek;
    private String airsTime;
    private String network;
    private String showName;
    private String showId;
    private String showImdbId;

    public String getShowImdbId() {
        return showImdbId;
    }

    public void setShowImdbId(String showImdbId) {
        this.showImdbId = showImdbId;
    }

    public String getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(String episodeId) {
        this.episodeId = episodeId;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public void setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
    }

    public String getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(String episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(String seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String getAirsDate() {
        return airsDate;
    }

    public void setAirsDate(String airsDate) {
        this.airsDate = airsDate;
    }

    public String getEpisodeImdbId() {
        return episodeImdbId;
    }

    public void setEpisodeImdbId(String imdbId) {
        this.episodeImdbId = imdbId;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Bitmap getEpisodeBanner() {
        return episodeBanner;
    }

    public void setEpisodeBanner(Bitmap episodeBanner) {
        this.episodeBanner = episodeBanner;
    }

    public Bitmap getShowBanner() {
        return showBanner;
    }

    public void setShowBanner(Bitmap showBanner) {
        this.showBanner = showBanner;
    }

    public String getAirsDayOfWeek() {
        return airsDayOfWeek;
    }

    public void setAirsDayOfWeek(String airsDayOfWeek) {
        this.airsDayOfWeek = airsDayOfWeek;
    }

    public String getAirsTime() {
        return airsTime;
    }

    public void setAirsTime(String airsTime) {
        this.airsTime = airsTime;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public void addDataFromEpisode(final Episode data){
        setEpisodeId(data.getId());
        setEpisodeName(data.getEpisodeName());
        setEpisodeNumber(data.getEpisodeNumber());
        setSeasonNumber(data.getSeasonNumber());
        setAirsDate(data.getFirstAired());
        setEpisodeImdbId(data.getIMDB_ID());
        setOverview(data.getOverview());
        setEpisodeBanner(Utils.getBitmapImage(data.getFilename()));
    }

    public void addDatafromSeries(final Series data){
        setShowBanner(Utils.getBitmapImage(data.getBanner()));
        setAirsDayOfWeek(data.getAirs_DayOfWeek());
        setAirsTime(data.getAirs_Time());
        setNetwork(data.getNetwork());
        setShowName(data.getSeriesName());
        setShowId(data.getId());
        setShowImdbId(data.getIMDB_ID());
    }

    @Override
    public int compareTo(EpisodeListData episodeListData) {
        String x1 = getShowName();
        String x2 = episodeListData.getShowName();
        int sComp = x1.compareTo(x2);

        if (sComp != 0) {
            return sComp;
        } else {
            String y1 = getAirsDate();
            String y2 = episodeListData.getAirsDate();
            return y1.compareTo(y2);
        }
    }
}
