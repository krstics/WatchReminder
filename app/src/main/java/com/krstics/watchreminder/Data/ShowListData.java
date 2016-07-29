package com.krstics.watchreminder.Data;


import android.graphics.Bitmap;

import com.krstics.watchreminder.Helpers.Utils;

public class ShowListData {

    private String FirstAired;
    private String SeriesName;
    private String seriesid;
    private String Overview;
    private String banner;
    private Bitmap bitmap;
    private String Status;
    private String Network;
    private String airsDayOfWeek;
    private String airsTime;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getFirstAired() {
        return FirstAired;
    }

    public void setFirstAired(String firstAired) {
        FirstAired = firstAired;
    }

    public String getSeriesName() {
        return SeriesName;
    }

    public void setSeriesName(String seriesName) {
        SeriesName = seriesName;
    }

    public String getSeriesid() {
        return seriesid;
    }

    public void setSeriesid(String seriesid) {
        this.seriesid = seriesid;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getNetwork() {
        return Network;
    }

    public void setNetwork(String network) {
        Network = network;
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

    public void addDataFromSeries(Series data){

        this.setSeriesName(data.getSeriesName());
        this.setFirstAired(data.getFirstAired());
        this.setOverview(data.getOverview());
        this.setSeriesid(data.getId());
        this.setBitmap(Utils.getBitmapImage(data.getBanner()));
        this.setStatus(data.getStatus());
        this.setNetwork(data.getNetwork());
        this.setAirsDayOfWeek(data.getAirs_DayOfWeek());
        this.setAirsTime(data.getAirs_Time());

    }
}