package com.krstics.watchreminder.Loaders;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.krstics.watchreminder.Adapters.ShowListAdapter;
import com.krstics.watchreminder.Data.Data;
import com.krstics.watchreminder.Data.Series;
import com.krstics.watchreminder.Data.ShowListData;
import com.krstics.watchreminder.Fragments.FragmentOne;
import com.krstics.watchreminder.RestManager.RestManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchLoad {
    private static final String TAG = SearchLoad.class.getSimpleName();
    private ShowListAdapter showListAdapter;
    private RestManager restManager;
    private ArrayAdapter<String> arrayAdapterACTV;
    private Context context;

    public SearchLoad(Context context, ShowListAdapter adapter, FragmentOne fragmentOne){
        showListAdapter = adapter;
        this.context = context;
        restManager = new RestManager();
    }

    public void callSearch(final String name){

        final Call<Data> searchCall = restManager.getSearchService().getSearchResult(name);
        searchCall.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if(response.isSuccessful()){
                    Data searchResult = response.body();
                    List<Series> seriesList = searchResult.getSeries();

                    if(seriesList != null){
                        int seriesListSize = seriesList.size();
                        if(seriesListSize > 0){
                            showListAdapter.deleteAllShows();
                            for (int i = 0; i < seriesListSize; i++) {
                                final Call<Data> baseSeriesRecordCall = restManager.getBaseSeriesRecord().getSeriesBaseRecords(seriesList.get(i).getId());
                                baseSeriesRecordCall.enqueue(new Callback<Data>() {
                                    @Override
                                    public void onResponse(Call<Data> call, Response<Data> response) {
                                        if(response.isSuccessful()){
                                            Data searchResult = response.body();

                                            if(!searchResult.getSeries().isEmpty()) {
                                                int seriesListSize = searchResult.getSeries().size();
                                                if (seriesListSize > 0) {
                                                    for(int i = 0; i < seriesListSize; i++) {
                                                        ShowListData show = new ShowListData();
                                                        show.addDataFromSeries(searchResult.getSeries().get(i));
                                                        showListAdapter.addShow(show);
                                                    }
                                                }
                                            }
                                        }
                                        else
                                            Toast.makeText(context, "Nothing found in base series record, try again.", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<Data> call, Throwable t) {

                                    }
                                });
                            }
                        }
                    }
                    else
                        Toast.makeText(context, "Nothing found, try again.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.e(TAG, Integer.toString(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.getMessage());
            }
        });
    }
}
