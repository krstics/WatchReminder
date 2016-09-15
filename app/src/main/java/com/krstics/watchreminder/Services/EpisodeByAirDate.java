package com.krstics.watchreminder.Services;

import com.krstics.watchreminder.Data.Data;
import com.krstics.watchreminder.Helpers.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EpisodeByAirDate {
    @GET(Constants.TvDB.EPISODE_BY_AIR_DATE + "?" + "apikey=" + Constants.TvDB.API_KEY + "&seriesid=" + "{seriesId}" + "&airdate=" + "{airsDate}")
    Call<Data> getEpisodeByAirDate(@Path("seriesId") String seriesID, @Path("airsDate") String airDate);
}
