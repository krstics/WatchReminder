package com.krstics.watchreminder.Services;

import com.krstics.watchreminder.Data.Item;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PreviousTimeService {
    @GET("Updates.php")
    Call<Item> getPreviousServerTime(@Query("type") String type);
}
