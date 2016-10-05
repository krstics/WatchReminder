package com.krstics.watchreminder.Services;

import com.krstics.watchreminder.Data.Data;
import com.krstics.watchreminder.Helpers.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EpisodeByAirDate {
    @GET(Constants.TvDB.EPISODE_BY_AIR_DATE)
    Call<Data> getEpisodeByAirDate(@Query("apikey") String apikey, @Query("seriesid") String seriesID, @Query("airdate") String airDate);
}
