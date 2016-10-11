package com.krstics.watchreminder.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.krstics.watchreminder.Data.Episode;
import com.krstics.watchreminder.Data.EpisodeListData;
import com.krstics.watchreminder.Data.Series;
import com.krstics.watchreminder.Data.ShowListData;
import com.krstics.watchreminder.Helpers.Constants;
import com.krstics.watchreminder.Helpers.Utils;
import com.krstics.watchreminder.Listeners.EpisodeFetchListener;
import com.krstics.watchreminder.Listeners.Next4WeeksPremiersFetchListener;
import com.krstics.watchreminder.Listeners.NotWatchedEpisodesFetchListener;
import com.krstics.watchreminder.Listeners.ShowFetchListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShowsDB extends SQLiteOpenHelper{

    private static final String TAG = ShowsDB.class.getSimpleName();
    Context context;

    public ShowsDB(Context c) {
        super(c, Constants.AddedShowsDB.DB_NAME, null, Constants.AddedShowsDB.DB_VERSION);
        context = c;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(Constants.AddedShowsDB.CREATE_TB_QUERY);
            db.execSQL(Constants.AddedEpisodesTABLE.CREATE_EPISODES_TB_QUERY);
            db.execSQL(Constants.UpdateTimeTable.CREATE_QUERY);
        }
        catch (SQLException ex){
            Log.e(TAG, ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(Constants.AddedEpisodesTABLE.DROP_EPISODES_TB_QUERY);
            db.execSQL(Constants.AddedShowsDB.DROP_ADDED_SHOWS_QUERY);
            db.execSQL(Constants.UpdateTimeTable.DROP_QUERY);

            this.onCreate(db);
        }
        catch (SQLException ex){
            Log.e(TAG, ex.getMessage());
        }
    }

    public void insertPreviousTime(String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Log.e(TAG, "insert" + time);

        values.put(Constants.UpdateTimeTable.previousServerTime, time);

        try{
            db.insert(Constants.UpdateTimeTable.UPDATE_TIME_TABLE_NAME, null, values);
        }
        catch (SQLException ex){
            Log.e(TAG, ex.getMessage());
        }
    }

    public void updatePreviousTime(final String time){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = getWritableDatabase();

                Log.e(TAG, "update" + time);

                ContentValues values = new ContentValues();

                values.put(Constants.UpdateTimeTable.previousServerTime, time);

                try{
                    db.update(Constants.UpdateTimeTable.UPDATE_TIME_TABLE_NAME, values, null, null);
                }
                catch (SQLException ex){
                    Log.e(TAG, ex.getMessage());
                }
            }
        }).start();
    }

    public String getPreviousUpdateTime() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM " + Constants.UpdateTimeTable.UPDATE_TIME_TABLE_NAME, null);
        String time = "";

        if(cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                time = cursor.getString(cursor.getColumnIndex(Constants.UpdateTimeTable.previousServerTime));
            }
        }
        cursor.close();

        return time;
    }

    public int insertShows(ShowListData show){

        int exists = checkIfExists(show.getSeriesid());

        if(exists == 1)
            return exists;

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Constants.AddedShowsDB.id, show.getSeriesid());
        values.put(Constants.AddedShowsDB.firstAired, show.getFirstAired());
        values.put(Constants.AddedShowsDB.showName, show.getSeriesName());
        values.put(Constants.AddedShowsDB.overview, show.getOverview());
        values.put(Constants.AddedShowsDB.poster, Utils.convertBitmapToByteArray(show.getBitmap()));
        values.put(Constants.AddedShowsDB.status, show.getStatus());
        values.put(Constants.AddedShowsDB.network, show.getNetwork());
        values.put(Constants.AddedShowsDB.airsDayOfWeek, show.getAirsDayOfWeek());
        values.put(Constants.AddedShowsDB.airsTime, show.getAirsTime());

        try{
            db.insert(Constants.AddedShowsDB.ADDED_SHOWS_TB_NAME, null, values);
        }
        catch (SQLException ex){
            Log.e(TAG, ex.getMessage());
        }
        return 0;
    }

    public void updateSeries(final Series series){
        final SQLiteDatabase db = this.getWritableDatabase();

        new Thread(new Runnable() {
            @Override
            public void run() {


                ContentValues values = new ContentValues();

                Log.e(TAG, "updateSeries" + series.getId());

                values.put(Constants.AddedShowsDB.id, series.getId());
                values.put(Constants.AddedShowsDB.firstAired, series.getFirstAired());
                values.put(Constants.AddedShowsDB.showName, series.getSeriesName());
                values.put(Constants.AddedShowsDB.overview, series.getOverview());
                values.put(Constants.AddedShowsDB.poster, Utils.convertBitmapToByteArray(Utils.getBitmapImage(series.getBanner())));
                values.put(Constants.AddedShowsDB.status, series.getStatus());
                values.put(Constants.AddedShowsDB.network, series.getNetwork());
                values.put(Constants.AddedShowsDB.airsDayOfWeek, series.getAirs_DayOfWeek());
                values.put(Constants.AddedShowsDB.airsTime, series.getAirs_Time());

                try {
                    db.update(Constants.AddedShowsDB.ADDED_SHOWS_TB_NAME, values, "id = " + series.getId(), null);
                    Toast.makeText(context, "Show " + series.getSeriesName() + " updated successfully!", Toast.LENGTH_SHORT).show();
                }
                catch (SQLException ex){
                    Log.e(TAG, ex.getMessage());
                }
            }
        }).start();
    }

    private int checkIfExists(String seriesid) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.AddedShowsDB.ADDED_SHOWS_TB_NAME +
                " WHERE " + Constants.AddedShowsDB.id + " = " + "'" + seriesid + "'", null);

        if(cursor.getCount() > 0) {
            db.close();
            cursor.close();
            Log.e(TAG, "record exists");
            return 1;
            //record exist
        }
        else {
            db.close();
            Log.e(TAG, "record not exists");
            return 0;
        }
    }
    public boolean checkIfEpisodeExists(String seriesId){
        SQLiteDatabase db = getReadableDatabase();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Cursor cursor= db.rawQuery("SELECT * FROM Episodes WHERE SeriesId=" + "'" + seriesId + "'" + "AND AirsDate=" + "'" + date + "'", null);

        if(cursor.getCount() > 0) {
            cursor.close();
            return true;
        }

        cursor.close();
        return false;
    }

    public boolean checkIfEpisodeExists(String seriesId, String date){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM Episodes WHERE SeriesId=" + "'" + seriesId + "'" + "AND AirsDate=" + "'" + date.substring(0, 10) + "'", null);

        if(cursor.getCount() > 0) {
            cursor.close();
            return true;
        }

        cursor.close();
        return false;
    }

    public void insertEpisodes(final List<Episode> episodeList){
        Log.e(TAG, "In insert episodes");

        new Thread(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = getWritableDatabase();

                ContentValues values = new ContentValues();
                int size = episodeList.size();

                for (int i = 0; i < size; i++) {

                    if (episodeList.get(i).getId() != null) {
                        Cursor cursor = db.rawQuery("SELECT * FROM Episodes WHERE EpisodeId=" + "'" + episodeList.get(i).getId() + "'", null);
                        if (cursor.getCount() == 0) {

                            Log.e(TAG, "Insert episode");

                            values.clear();

                            values.put(Constants.AddedEpisodesTABLE.episodeId, episodeList.get(i).getId());
                            values.put(Constants.AddedEpisodesTABLE.seriesId, episodeList.get(i).getSeriesid());
                            values.put(Constants.AddedEpisodesTABLE.episodeName, episodeList.get(i).getEpisodeName());
                            values.put(Constants.AddedEpisodesTABLE.episodeNumber, episodeList.get(i).getEpisodeNumber());
                            values.put(Constants.AddedEpisodesTABLE.seasonNumber, episodeList.get(i).getSeasonNumber());
                            values.put(Constants.AddedEpisodesTABLE.airsDate, episodeList.get(i).getFirstAired());
                            values.put(Constants.AddedEpisodesTABLE.episodeImdbId, episodeList.get(i).getIMDB_ID());
                            values.put(Constants.AddedEpisodesTABLE.overview, episodeList.get(i).getOverview());
                            values.put(Constants.AddedEpisodesTABLE.episodeBanner, Utils.convertBitmapToByteArray(Utils.getBitmapImage(episodeList.get(i).getFilename())));

                            try {
                                db.insert(Constants.AddedEpisodesTABLE.EPISODES_TB_NAME, null, values);
                            } catch (SQLException ex) {
                                Log.e(TAG, ex.getMessage());
                            }
                        }
                        cursor.close();
                    }
                }

            }
        }).start();
    }

    public void updateEpisode(final Episode episode){
        final SQLiteDatabase db = this.getWritableDatabase();

        new Thread(new Runnable() {
            @Override
            public void run() {
                ContentValues values = new ContentValues();

                Log.e(TAG, "updateEpisode" + episode.getId());

                values.put(Constants.AddedEpisodesTABLE.episodeId, episode.getId());
                values.put(Constants.AddedEpisodesTABLE.seriesId, episode.getSeriesid());
                values.put(Constants.AddedEpisodesTABLE.episodeName, episode.getEpisodeName());
                values.put(Constants.AddedEpisodesTABLE.episodeNumber, episode.getEpisodeNumber());
                values.put(Constants.AddedEpisodesTABLE.seasonNumber, episode.getSeasonNumber());
                values.put(Constants.AddedEpisodesTABLE.airsDate, episode.getFirstAired());
                values.put(Constants.AddedEpisodesTABLE.episodeImdbId, episode.getIMDB_ID());
                values.put(Constants.AddedEpisodesTABLE.overview, episode.getOverview());
                values.put(Constants.AddedEpisodesTABLE.episodeBanner, Utils.convertBitmapToByteArray(Utils.getBitmapImage(episode.getFilename())));

                try{
                    db.update(Constants.AddedEpisodesTABLE.EPISODES_TB_NAME, values, "EpisodeId = " + episode.getId(), null);
                    Toast.makeText(context, "Episode " + episode.getEpisodeName() + " updated successfully!", Toast.LENGTH_SHORT).show();
                }
                catch (SQLException ex){
                    Log.e(TAG, ex.getMessage());
                }
            }
        }).start();
    }

    public void removeShow(final String seriesId){
        final SQLiteDatabase db = this.getWritableDatabase();
        new Thread(new Runnable() {
            @Override
            public void run() {

                try{
                    db.delete(Constants.AddedShowsDB.ADDED_SHOWS_TB_NAME, Constants.AddedShowsDB.id + "=" + "'" + seriesId + "'", null);
                    db.delete(Constants.AddedEpisodesTABLE.EPISODES_TB_NAME, Constants.AddedEpisodesTABLE.seriesId + "=" + "'" + seriesId + "'", null);
                }
                catch (SQLException ex){
                    Log.e(TAG, ex.getMessage());
                }
            }
        }).start();
    }

    public void removeEpisode(final String episodeId){
        final SQLiteDatabase db = this.getWritableDatabase();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    db.delete(Constants.AddedEpisodesTABLE.EPISODES_TB_NAME, Constants.AddedEpisodesTABLE.episodeId + "=" + "'" + episodeId + "'", null);
                }
                catch (SQLException ex){
                    Log.e(TAG, ex.getMessage());
                }
            }
        }).start();
    }

    public void removeAllShows(){
        final SQLiteDatabase db = this.getWritableDatabase();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    db.delete(Constants.AddedShowsDB.ADDED_SHOWS_TB_NAME, null, null);
                }
                catch (SQLException ex){
                    Log.e(TAG, ex.getMessage());
                }
            }
        }).start();
    }

    public List<String> getShowsIDs() {
        List<String> showIDs = new ArrayList<>();
        SQLiteDatabase mDb = getReadableDatabase();

        Cursor cursor= mDb.rawQuery(Constants.AddedShowsDB.GET_ALL_SHOWS_QUERY, null);

        if(cursor.getCount() > 0){
            if(cursor.moveToFirst()){
                do {
                    showIDs.add(cursor.getString(cursor.getColumnIndex(Constants.AddedShowsDB.id)));
                }
                while (cursor.moveToNext());
            }
        }

        cursor.close();
        return showIDs;
    }

    public List<String> getEpisodeIDs(){
        List<String> episodeIDs = new ArrayList<>();
        SQLiteDatabase mDb = getReadableDatabase();

        Cursor cursor= mDb.rawQuery(Constants.AddedEpisodesTABLE.GET_ALL_EPISODES, null);

        if(cursor.getCount() > 0){
            if(cursor.moveToFirst()){
                do {
                    episodeIDs.add(cursor.getString(cursor.getColumnIndex(Constants.AddedShowsDB.id)));
                }
                while (cursor.moveToNext());
            }
        }

        cursor.close();
        return episodeIDs;
    }

    public String getAirDayOfWeek(String seriesId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(Constants.AddedShowsDB.GET_AIR_DAY_OF_WEEK + "'" + seriesId + "'", null);

        String airDay = "";

        if(cursor.getCount() > 0){
            if(cursor.moveToFirst()){
                airDay = cursor.getString(cursor.getColumnIndex(Constants.AddedShowsDB.airsDayOfWeek));
            }
        }

        cursor.close();

        return airDay;
    }

    public boolean shouldLoadByAirDate(String id, String date) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM Episodes WHERE SeriesId=" + "'" + id + "'" + "AND AirsDate = " + "'" + date + "'", null);

        if(cursor.getCount() > 0) {
            cursor.close();
            return false;
        }

        cursor.close();
        return true;
    }

    public boolean checkIfEpisodeExistsForNext4Weeks(String seriesId) {
        SQLiteDatabase db = getReadableDatabase();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Cursor cursor= db.rawQuery("SELECT * FROM Episodes WHERE SeriesId=" + "'" + seriesId + "'" + "AND AirsDate > " + "'" + date + "'", null);

        if(cursor.getCount() >= 4) {
            cursor.close();
            return true;
        }

        cursor.close();
        return false;
    }

    public void fetchShows(ShowFetchListener listener){
        ShowFetcher fetcher = new ShowFetcher(listener, this.getWritableDatabase());
        fetcher.start();
    }

    private class ShowFetcher extends Thread{
        private final ShowFetchListener mListener;
        private final SQLiteDatabase mDb;

        ShowFetcher(ShowFetchListener listener, SQLiteDatabase db){
            mListener = listener;
            mDb = db;
        }

        @Override
        public void run() {
            Cursor cursor= mDb.rawQuery(Constants.AddedShowsDB.GET_ALL_SHOWS_QUERY, null);
            final List<ShowListData> showList = new ArrayList<>();

            if(cursor.getCount() > 0){
                if(cursor.moveToFirst()){
                    do {
                        ShowListData show = new ShowListData();

                        show.setSeriesid(cursor.getString(cursor.getColumnIndex(Constants.AddedShowsDB.id)));
                        show.setFirstAired(cursor.getString(cursor.getColumnIndex(Constants.AddedShowsDB.firstAired)));
                        show.setSeriesName(cursor.getString(cursor.getColumnIndex(Constants.AddedShowsDB.showName)));
                        show.setOverview(cursor.getString(cursor.getColumnIndex(Constants.AddedShowsDB.overview)));
                        show.setStatus(cursor.getString(cursor.getColumnIndex(Constants.AddedShowsDB.status)));
                        show.setBitmap(Utils.convertByteArrayToBitmap(cursor.getBlob(cursor.getColumnIndex(Constants.AddedShowsDB.poster))));
                        show.setNetwork(cursor.getString(cursor.getColumnIndex(Constants.AddedShowsDB.network)));
                        show.setAirsDayOfWeek(cursor.getString(cursor.getColumnIndex(Constants.AddedShowsDB.airsDayOfWeek)));
                        show.setAirsTime(cursor.getString(cursor.getColumnIndex(Constants.AddedShowsDB.airsTime)));

                        showList.add(show);
                        publishShow(show);
                    }
                    while (cursor.moveToNext());
                }
            }
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onDeliverAllShows(showList);
                }
            });

            cursor.close();
        }

        void publishShow(final ShowListData show){
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onDeliverShow(show);
                }
            });
        }
    }

    public void fetchEpisodes(EpisodeFetchListener listener){
        EpisodeFetcher fetcher = new EpisodeFetcher(listener, this.getWritableDatabase());
        fetcher.start();
    }

    private class EpisodeFetcher extends Thread{
        private final EpisodeFetchListener listener;
        private final SQLiteDatabase db;

        EpisodeFetcher(EpisodeFetchListener listener, SQLiteDatabase db){
            this.listener = listener;
            this.db = db;
        }

        @Override
        public void run(){
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            Log.e(TAG, Constants.AddedEpisodesTABLE.GET_TODAY_PREMIERING_EPISODES + date);

            Cursor cursor = db.rawQuery(Constants.AddedEpisodesTABLE.GET_TODAY_PREMIERING_EPISODES + "'" + date + "'", null);
            final List<EpisodeListData> episodes = new ArrayList<>();

            if(cursor.getCount() > 0){
                if(cursor.moveToFirst()){
                    do{
                        EpisodeListData episode = new EpisodeListData();

                        episode.setEpisodeId(cursor.getString(cursor.getColumnIndex(Constants.AddedEpisodesTABLE.episodeId)));
                        episode.setShowId(cursor.getString(cursor.getColumnIndex(Constants.AddedEpisodesTABLE.seriesId)));
                        episode.setAirsDate(cursor.getString(cursor.getColumnIndex(Constants.AddedEpisodesTABLE.airsDate)));
                        episode.setAirsTime(cursor.getString(cursor.getColumnIndex(Constants.AddedShowsDB.airsTime)));
                        episode.setEpisodeName(cursor.getString(cursor.getColumnIndex(Constants.AddedEpisodesTABLE.episodeName)));
                        episode.setShowName(cursor.getString(cursor.getColumnIndex(Constants.AddedShowsDB.showName)));
                        episode.setEpisodeNumber(cursor.getString(cursor.getColumnIndex(Constants.AddedEpisodesTABLE.episodeNumber)));
                        episode.setSeasonNumber(cursor.getString(cursor.getColumnIndex(Constants.AddedEpisodesTABLE.seasonNumber)));
                        episode.setOverview(cursor.getString(cursor.getColumnIndex(Constants.AddedEpisodesTABLE.overview)));
                        episode.setEpisodeBanner(Utils.convertByteArrayToBitmap(cursor.getBlob(cursor.getColumnIndex(Constants.AddedEpisodesTABLE.episodeBanner))));
                        episode.setShowBanner(Utils.convertByteArrayToBitmap(cursor.getBlob(cursor.getColumnIndex(Constants.AddedShowsDB.poster))));

                        episodes.add(episode);
                        publishEpisode(episode);
                    }
                    while (cursor.moveToNext());
                }
            }
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(
                    new Runnable() {
                @Override
                public void run() {
                    listener.onDeliverAllEpisodes(episodes);
                }
            });

            cursor.close();
        }

        void publishEpisode(final EpisodeListData episode){
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onDeliverEpisode(episode);
                }
            });
        }
    }

    public void fetchNotWatchedEpisodes(NotWatchedEpisodesFetchListener listener){
        NotWatchedEpisodesFetcher fetcher = new NotWatchedEpisodesFetcher(listener, this.getWritableDatabase());
        fetcher.start();
    }

    private class NotWatchedEpisodesFetcher extends Thread{
        private final NotWatchedEpisodesFetchListener listener;
        private final SQLiteDatabase db;

        NotWatchedEpisodesFetcher(NotWatchedEpisodesFetchListener listener, SQLiteDatabase db){
            this.listener = listener;
            this.db = db;
        }

        @Override
        public void run(){
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            Cursor cursor = db.rawQuery(Constants.AddedEpisodesTABLE.GET_ALL_NOT_WATCHED_EPISODES + "'" + date + "'", null);
            final List<EpisodeListData> episodes = new ArrayList<>();

            if(cursor.getCount() > 0){
                if(cursor.moveToFirst()){
                    do{
                        EpisodeListData episode = new EpisodeListData();

                        episode.setEpisodeId(cursor.getString(cursor.getColumnIndex(Constants.AddedEpisodesTABLE.episodeId)));
                        episode.setShowId(cursor.getString(cursor.getColumnIndex(Constants.AddedEpisodesTABLE.seriesId)));
                        episode.setAirsDate(cursor.getString(cursor.getColumnIndex(Constants.AddedEpisodesTABLE.airsDate)));
                        episode.setAirsTime(cursor.getString(cursor.getColumnIndex(Constants.AddedShowsDB.airsTime)));
                        episode.setEpisodeName(cursor.getString(cursor.getColumnIndex(Constants.AddedEpisodesTABLE.episodeName)));
                        episode.setShowName(cursor.getString(cursor.getColumnIndex(Constants.AddedShowsDB.showName)));
                        episode.setEpisodeNumber(cursor.getString(cursor.getColumnIndex(Constants.AddedEpisodesTABLE.episodeNumber)));
                        episode.setSeasonNumber(cursor.getString(cursor.getColumnIndex(Constants.AddedEpisodesTABLE.seasonNumber)));
                        episode.setOverview(cursor.getString(cursor.getColumnIndex(Constants.AddedEpisodesTABLE.overview)));
                        episode.setEpisodeBanner(Utils.convertByteArrayToBitmap(cursor.getBlob(cursor.getColumnIndex(Constants.AddedEpisodesTABLE.episodeBanner))));
                        episode.setShowBanner(Utils.convertByteArrayToBitmap(cursor.getBlob(cursor.getColumnIndex(Constants.AddedShowsDB.poster))));

                        episodes.add(episode);
                        publishNotWatchedEpisode(episode);
                    }
                    while (cursor.moveToNext());
                }
            }
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onDeliverAllNotWatchedEpisodes(episodes);
                }
            });

            cursor.close();
        }

        void publishNotWatchedEpisode(final EpisodeListData episode){
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onDeliverNotWathcedEpisode(episode);
                }
            });
        }
    }




    public void fetchNext4WeeksPremiers(Next4WeeksPremiersFetchListener listener){
        Next4WeeksPremiersFetcher fetcher = new Next4WeeksPremiersFetcher(listener, this.getWritableDatabase());
        fetcher.start();
    }

    private class Next4WeeksPremiersFetcher extends Thread{
        private final Next4WeeksPremiersFetchListener listener;
        private final SQLiteDatabase db;

        Next4WeeksPremiersFetcher(Next4WeeksPremiersFetchListener listener, SQLiteDatabase db){
            this.listener = listener;
            this.db = db;
        }

        @Override
        public void run(){
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            Cursor cursor = db.rawQuery(Constants.AddedEpisodesTABLE.GET_NEXT_4_WEEKS_PREMIERS + "'" + date + "'" +
                                        " ORDER BY " + Constants.AddedShowsDB.showName + ", " + Constants.AddedEpisodesTABLE.airsDate, null);
            final List<EpisodeListData> episodes = new ArrayList<>();

            if(cursor.getCount() > 0){
                if(cursor.moveToFirst()){
                    do{
                        EpisodeListData episode = new EpisodeListData();

                        episode.setEpisodeId(cursor.getString(cursor.getColumnIndex(Constants.AddedEpisodesTABLE.episodeId)));
                        episode.setShowId(cursor.getString(cursor.getColumnIndex(Constants.AddedEpisodesTABLE.seriesId)));
                        episode.setAirsDate(cursor.getString(cursor.getColumnIndex(Constants.AddedEpisodesTABLE.airsDate)));
                        episode.setAirsTime(cursor.getString(cursor.getColumnIndex(Constants.AddedShowsDB.airsTime)));
                        episode.setEpisodeName(cursor.getString(cursor.getColumnIndex(Constants.AddedEpisodesTABLE.episodeName)));
                        episode.setShowName(cursor.getString(cursor.getColumnIndex(Constants.AddedShowsDB.showName)));
                        episode.setEpisodeNumber(cursor.getString(cursor.getColumnIndex(Constants.AddedEpisodesTABLE.episodeNumber)));
                        episode.setSeasonNumber(cursor.getString(cursor.getColumnIndex(Constants.AddedEpisodesTABLE.seasonNumber)));
                        episode.setOverview(cursor.getString(cursor.getColumnIndex(Constants.AddedEpisodesTABLE.overview)));
                        episode.setEpisodeBanner(Utils.convertByteArrayToBitmap(cursor.getBlob(cursor.getColumnIndex(Constants.AddedEpisodesTABLE.episodeBanner))));
                        episode.setShowBanner(Utils.convertByteArrayToBitmap(cursor.getBlob(cursor.getColumnIndex(Constants.AddedShowsDB.poster))));

                        episodes.add(episode);
                        publishNotWatchedEpisode(episode);
                    }
                    while (cursor.moveToNext());
                }
            }
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onDeliverAllEpisodes(episodes);
                }
            });

            cursor.close();
        }

        void publishNotWatchedEpisode(final EpisodeListData episode){
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onDeliverEpisode(episode);
                }
            });
        }
    }
}
