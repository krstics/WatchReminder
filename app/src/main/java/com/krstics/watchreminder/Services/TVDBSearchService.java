package com.krstics.watchreminder.Services;

import com.krstics.watchreminder.Data.Data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TVDBSearchService {
    @GET("GetSeries.php")
    Call<Data> getSearchResult(@Query("seriesname") String name);
}
