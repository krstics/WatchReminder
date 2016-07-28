package com.krstics.watchreminder.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.krstics.watchreminder.Data.Episode;
import com.krstics.watchreminder.Data.ShowListData;
import com.krstics.watchreminder.Helpers.Constants;
import com.krstics.watchreminder.Helpers.Utils;
import com.krstics.watchreminder.Listeners.ShowFetchListener;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ShowsDB extends SQLiteOpenHelper{

    public static final String TAG = ShowsDB.class.getSimpleName();

    public ShowsDB(Context context) {
        super(context, Constants.AddedShowsDB.DB_NAME, null, Constants.AddedShowsDB.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(Constants.AddedShowsDB.CREATE_TB_QUERY);
            db.execSQL(Constants.AddedEpisodesTABLE.CREATE_EPISODES_TB_QUERY);
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

            this.onCreate(db);
        }
        catch (SQLException ex){
            Log.e(TAG, ex.getMessage());
        }
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

    public void insertEpisodes(final List<Episode> episodeList){

        new Thread(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = getWritableDatabase();
                ContentValues values = new ContentValues();
                int size = episodeList.size();

                for (int i = 0; i < size; i++) {
                    values.clear();

                    Log.e(TAG, episodeList.get(i).getId());
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
            }
        }).start();
    }

    public void removeShow(final String seriesId){
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            db.delete(Constants.AddedShowsDB.ADDED_SHOWS_TB_NAME, Constants.AddedShowsDB.id + "=" + "'" + seriesId + "'", null);
            db.delete(Constants.AddedEpisodesTABLE.EPISODES_TB_NAME, Constants.AddedEpisodesTABLE.seriesId + "=" + "'" + seriesId + "'", null);
        }
        catch (SQLException ex){
            Log.e(TAG, ex.getMessage());
        }
    }

    public void removeAllShows(){
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.delete(Constants.AddedShowsDB.ADDED_SHOWS_TB_NAME, null, null);
        }
        catch (SQLException ex){
            Log.e(TAG, ex.getMessage());
        }
    }

    public void fetchShows(ShowFetchListener listener){
        ShowFetcher fetcher = new ShowFetcher(listener, this.getWritableDatabase());
        fetcher.start();
    }

    public class ShowFetcher extends Thread{
        private final ShowFetchListener mListener;
        private final SQLiteDatabase mDb;

        public ShowFetcher(ShowFetchListener listener, SQLiteDatabase db){
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

        public void publishShow(final ShowListData show){
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onDeliverShow(show);
                }
            });
        }
    }
}
