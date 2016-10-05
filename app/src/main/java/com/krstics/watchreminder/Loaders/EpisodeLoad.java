package com.krstics.watchreminder.Loaders;

import android.content.Context;
import android.util.Log;

import com.krstics.watchreminder.DB.ShowsDB;
import com.krstics.watchreminder.Data.Data;
import com.krstics.watchreminder.Data.Episode;
import com.krstics.watchreminder.Data.Series;
import com.krstics.watchreminder.Helpers.Constants;
import com.krstics.watchreminder.RestManager.RestManager;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
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

    public EpisodeLoad(){
        restManager = new RestManager();
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

    public void loadEpisodeByAirDate(final Context context) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                List<String> showIDs;
                List<String> showIDsToLoad = new ArrayList<>();
                final ShowsDB showsDB = new ShowsDB(context);
                showIDs = showsDB.getShowsIDs();

                for (int i = 0; i < showIDs.size(); ++i) {
                    if (!showsDB.checkIfEpisodeExists(showIDs.get(i))) {
                        showIDsToLoad.add(showIDs.get(i));
                    }
                }

                if (showIDsToLoad.size() > 0) {


                    Call<Data> episodeCall;

                    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                    Log.e(TAG, Integer.toString(showIDs.size()));

                    for (int i = 0; i < showIDs.size(); ++i) {
                        Log.e(TAG, showIDs.get(i));
                        episodeCall = restManager.getEpisodeByAirDaterest().getEpisodeByAirDate(Constants.TvDB.API_KEY, showIDs.get(i), date);
                        episodeCall.enqueue(new Callback<Data>() {
                            @Override
                            public void onResponse(Call<Data> call, Response<Data> response) {
                                if (response.isSuccessful()) {
                                    Data result = response.body();
                                    List<Episode> episodes = result.getEpisode();

                                    if (episodes != null) {
                                        showsDB.insertEpisodes(episodes);
                                    } else {
                                        Log.e(TAG, "episodes is null");
                                    }
                                } else {
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
        }).start();
    }

    public void loadEpisodeByAirDateForNext4Weeks(final Context context){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> showIDs;
                List<String> showIDsToLoad = new ArrayList<>();
                final ShowsDB showsDB = new ShowsDB(context);
                showIDs = showsDB.getShowsIDs();

                for (int i = 0; i < showIDs.size(); ++i) {
                    if (!showsDB.checkIfEpisodeExistsForNext4Weeks(showIDs.get(i))) {
                        showIDsToLoad.add(showIDs.get(i));
                    }
                }
                Call<Data> episodeCall;

                for (int j = 0; j < showIDsToLoad.size(); ++j) {
                    List<String> dates = getDates(showIDsToLoad.get(j), context);
                    dates = checkIfExistsInDB(showIDsToLoad.get(j), dates, context);

                    if(!dates.isEmpty()) {

                        for (int i = 0; i < dates.size(); ++i) {
                            Log.e(TAG, showIDsToLoad.get(j) + " " + dates.get(i).substring(0, 10));
                            episodeCall = restManager.getEpisodeByAirDaterest().getEpisodeByAirDate(Constants.TvDB.API_KEY, showIDsToLoad.get(j), dates.get(i).substring(0, 10));
                            episodeCall.enqueue(new Callback<Data>() {
                                @Override
                                public void onResponse(Call<Data> call, Response<Data> response) {
                                    if (response.isSuccessful()) {
                                        Data result = response.body();
                                        List<Episode> episodes = result.getEpisode();

                                        if (episodes != null) {
                                            showsDB.insertEpisodes(episodes);
                                        } else {
                                            Log.e(TAG, "episodes is null");
                                        }
                                    } else {
                                        Log.e(TAG, Integer.toString(response.code()));
                                    }
                                }

                                @Override
                                public void onFailure(Call<Data> call, Throwable t) {
                                    Log.e(TAG, t.getMessage());
                                }
                            });
                        }
                    }
                }
            }
        }).start();
    }

    private List<String> checkIfExistsInDB(String seriesId, List<String> dates, Context context) {
        List<String> datesToReturn = new ArrayList<>();
        ShowsDB showsDB = new ShowsDB(context);
        for(int i = 0; i < dates.size(); ++i){
            if(!showsDB.checkIfEpisodeExists(seriesId, dates.get(i)))
                datesToReturn.add(dates.get(i));
        }

        return datesToReturn;
    }

    private List<String> getDates(String showIDsToLoad, Context context){

        List<String> dates = new ArrayList<>();

            ShowsDB showsDB = new ShowsDB(context);
            String dayOfWeek = showsDB.getAirDayOfWeek(showIDsToLoad);

            String start = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(start));
            } catch (ParseException e) {
                Log.e(TAG, e.toString());
            }
            c.add(Calendar.DAY_OF_YEAR, 1);
            start = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
            c.add(Calendar.DAY_OF_YEAR, 31);
            String end = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
            DateTime startDate = DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime(start);
            DateTime endDate = DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime(end);
            int day = 0;
            switch (dayOfWeek) {
                case "Monday":
                    day = DateTimeConstants.MONDAY;
                    break;
                case "Tuesday":
                    day = DateTimeConstants.TUESDAY;
                    break;
                case "Wednesday":
                    day = DateTimeConstants.WEDNESDAY;
                    break;
                case "Thursday":
                    day = DateTimeConstants.THURSDAY;
                    break;
                case "Friday":
                    day = DateTimeConstants.FRIDAY;
                    break;
                case "Saturday":
                    day = DateTimeConstants.SATURDAY;
                    break;
                case "Sunday":
                    day = DateTimeConstants.SUNDAY;
                    break;
            }

            while (startDate.isBefore(endDate)){
                if ( startDate.getDayOfWeek() == day){
                    dates.add(startDate.toString());
                }
                startDate = startDate.plusDays(1);
            }
        return dates;
    }
}
