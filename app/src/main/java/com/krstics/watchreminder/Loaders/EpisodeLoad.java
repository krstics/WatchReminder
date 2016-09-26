package com.krstics.watchreminder.Loaders;

import android.content.Context;
import android.util.Log;

import com.krstics.watchreminder.DB.ShowsDB;
import com.krstics.watchreminder.Data.Data;
import com.krstics.watchreminder.Data.Episode;
import com.krstics.watchreminder.Data.Series;
import com.krstics.watchreminder.Helpers.Constants;
import com.krstics.watchreminder.RestManager.RestManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
            public void onResponse(Call<Data> call, final Response<Data> response) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(response.isSuccessful()){
                            Data result = response.body();
                            List<Series> seriesList = result.getSeries();

                            if(seriesList != null){
                                List<Episode> episodeList = result.getEpisode();

                                if(episodeList != null){

                                    List<Episode> episodesToInsert = new ArrayList<>();
                                    int year = Calendar.getInstance().get(Calendar.YEAR);

                                    for(int i = 0; i < episodeList.size(); ++i){

                                        int episodeYear = Integer.getInteger(episodeList.get(i).getFirstAired().substring(0,3));

                                        Log.e(TAG, Integer.toString(episodeYear));

                                        if(year == episodeYear)
                                            episodesToInsert.add(episodeList.get(i));

                                        if(!episodesToInsert.isEmpty())
                                            mDB.insertEpisodes(episodesToInsert);
                                    }
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
                }).start();

            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }

    public void loadEpisodeByAirDate(List<String> showIDs) {
        Call<Data> episodeCall;

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Log.e(TAG, Integer.toString(showIDs.size()));

        for(int i = 0; i < showIDs.size(); ++i){
            Log.e(TAG, showIDs.get(i));
            episodeCall = restManager.getEpisodeByAirDaterest().getEpisodeByAirDate(Constants.TvDB.API_KEY, showIDs.get(i), date);
            episodeCall.enqueue(new Callback<Data>() {
                @Override
                public void onResponse(Call<Data> call, Response<Data> response) {
                    if(response.isSuccessful()){
                        Data result = response.body();
                        List<Episode> episodes = result.getEpisode();

                        if(episodes != null){
                            mDB.insertEpisodes(episodes);
                        }
                        else {
                            Log.e(TAG, "episodes is null");
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
}
