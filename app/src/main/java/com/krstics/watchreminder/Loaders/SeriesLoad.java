package com.krstics.watchreminder.Loaders;

import android.content.Context;
import android.util.Log;

import com.krstics.watchreminder.DB.ShowsDB;
import com.krstics.watchreminder.Data.Data;
import com.krstics.watchreminder.Data.Series;
import com.krstics.watchreminder.RestManager.RestManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeriesLoad {
    private static final String TAG = SeriesLoad.class.getSimpleName();
    private RestManager restManager;

    public SeriesLoad(){
        restManager = new RestManager();
    }

    public void getBaseSeriesRecord(final String id, final Context context){

        new Thread(new Runnable() {
            @Override
            public void run() {
                final Call<Data> baseSeriesRecordCall = restManager.getBaseSeriesRecord().getSeriesBaseRecords(id);

                baseSeriesRecordCall.enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {
                        if(response.isSuccessful()){
                            Data result = response.body();

                            if(result.getSeries() != null) {
                                ShowsDB db = new ShowsDB(context);
                                for(Series series : result.getSeries()) {
                                    db.updateSeries(series);
                                }
                            }
                        }
                        else {
                            Log.e(TAG, response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
                });
            }
        }).start();
    }
}
