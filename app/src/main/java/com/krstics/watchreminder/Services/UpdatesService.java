package com.krstics.watchreminder.Services;

import com.krstics.watchreminder.Data.Item;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UpdatesService {
    @GET("Updates.php")
    Call<Item> getUpdates(@Query("type") String type, @Query("time") String time);
}
