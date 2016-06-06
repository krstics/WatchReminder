package com.krstics.watchreminder.Services;

import com.krstics.watchreminder.Data.Data;
import com.krstics.watchreminder.Helpers.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BaseSeriesRecord {
    @GET(Constants.TvDB.API_KEY + Constants.TvDB.TYPE + "{seriesId}")
    Call<Data> getSeriesBaseRecords(@Path("seriesId") String seriesID);
}
