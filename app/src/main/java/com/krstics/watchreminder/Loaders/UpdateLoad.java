package com.krstics.watchreminder.Loaders;

import android.content.Context;
import android.util.Log;

import com.krstics.watchreminder.DB.ShowsDB;
import com.krstics.watchreminder.Data.Item;
import com.krstics.watchreminder.RestManager.RestManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateLoad {
    private static final String TAG = UpdateLoad.class.getSimpleName();
    private RestManager restManager;

    public UpdateLoad(){
        restManager = new RestManager();
    }

    public void getPreviousTime(final Context context) {
        final Call<Item> call = restManager.getPreviousTimeService().getPreviousServerTime("none");
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, final Response<Item> response) {
                Item item = response.body();
                ShowsDB db = new ShowsDB(context);
                db.insertPreviousTime(item.getTime());
            }
            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}
