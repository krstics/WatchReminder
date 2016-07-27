package com.krstics.watchreminder.Loaders;

import android.util.Log;

import com.krstics.watchreminder.DB.ShowsDB;
import com.krstics.watchreminder.Data.Data;
import com.krstics.watchreminder.Data.Episode;
import com.krstics.watchreminder.Data.Series;
import com.krstics.watchreminder.RestManager.RestManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EpisodeLoad {
    private static final String TAG = EpisodeLoad.class.getSimpleName();
    private RestManager restManager;
    private ShowsDB mDB;

    public EpisodeLoad(ShowsDB db){
        restManager = new RestManager();
        mDB = db;
    }

    public void loadAllSeriesRecords(final String id){
        final Call<Data> allCall = restManager.getAllSeriesRecord().getSeriesAllRecords(id);
        allCall.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if(response.isSuccessful()){
                    Data result = response.body();
                    List<Series> seriesList = result.getSeries();

                    if(seriesList != null){
                        List<Episode> episodeList = result.getEpisode();

                        if(episodeList != null){
                            mDB.insertEpisodes(episodeList);
                        }
                        else {
                            Log.e(TAG, "episodeList is null");
                        }
                    }
                    else {
                        Log.e(TAG, "seriesList is null");
                    }
                }
                else{
                    Log.e(TAG, Integer.toString(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }
}
