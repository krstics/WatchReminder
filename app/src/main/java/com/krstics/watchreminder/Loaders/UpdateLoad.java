package com.krstics.watchreminder.Loaders;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.krstics.watchreminder.DB.ShowsDB;
import com.krstics.watchreminder.Data.Item;
import com.krstics.watchreminder.RestManager.RestManager;

import java.util.List;

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
               if(response.isSuccessful()) {
                   Item item = response.body();
                   ShowsDB db = new ShowsDB(context);
                   db.insertPreviousTime(item.getTime());
               }
               else{
                   Log.e(TAG, response.errorBody().toString());
               }
            }
            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void updateDataInDB(final Context context) {

        final ShowsDB db = new ShowsDB(context);

        String time = db.getPreviousUpdateTime();

        final Call<Item> call = restManager.getUpdatesService().getUpdates("all", time);

       new Thread(new Runnable() {
           @Override
           public void run() {
               call.enqueue(new Callback<Item>() {
                   @Override
                   public void onResponse(Call<Item> call, Response<Item> response) {
                       if(response.isSuccessful()) {
                           Item item = response.body();

                           ShowsDB db = new ShowsDB(context);
                           db.updatePreviousTime(item.getTime());

                           if(item.getSeries() != null) {
                               List<String> seriesIds = db.getShowsIDs();

                               if (seriesIds != null) {
                                   SeriesLoad seriesLoad = new SeriesLoad();

                                   for (String series : seriesIds) {
                                       for (String seriesToUpdate : item.getSeries()){
                                           if(series.compareTo(seriesToUpdate) == 0){
                                               seriesLoad.getBaseSeriesRecord(seriesToUpdate, context);
                                           }
                                       }
                                   }
                               }
                           }

                           if(item.getEpisodes() != null) {
                               List<String> episodeIds = db.getEpisodeIDs();

                               if (episodeIds != null) {
                                   EpisodeLoad episodeLoad = new EpisodeLoad();

                                   for (String episodes: episodeIds) {
                                       for (String episodeToUpdate : item.getEpisodes()){
                                           if(episodes.compareTo(episodeToUpdate) == 0){
                                               episodeLoad.loadEpisodeById(episodeToUpdate, context);
                                           }
                                       }
                                   }
                               }
                           }
                       }
                       else{
                           Log.e(TAG, response.errorBody().toString());
                       }
                   }

                   @Override
                   public void onFailure(Call<Item> call, Throwable t) {
                       Log.e(TAG, t.getMessage());
                   }
               });
           }
       }).start();
    }
}
