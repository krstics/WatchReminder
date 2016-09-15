package com.krstics.watchreminder.Helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.krstics.watchreminder.DB.ShowsDB;
import com.krstics.watchreminder.Loaders.EpisodeLoad;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

public class Utils {
    public static final String TAG = Utils.class.getSimpleName();

    public static Bitmap getBitmapImage(String link) {
        Bitmap bitmap = null;

        if(link != null) {
            try {
                bitmap = new DownloadBitmapTask().execute(link).get();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return bitmap;
    }

    public static class DownloadBitmapTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            String link = strings[0];
            InputStream stream = null;

            try {
                stream = new URL(Constants.TvDB.BANNERS_URL + link).openStream();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

            if(stream == null)
                return null;
            else
                return BitmapFactory.decodeStream(stream);
        }

    }

    public static byte[] convertBitmapToByteArray(Bitmap bitmap){
        if(bitmap == null)
            return null;

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Bitmap convertByteArrayToBitmap(byte[] array){
        if(array == null)
            return null;
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }

/*    public static class DownloadEpisodes extends AsyncTask<String, Void, Void> {
        private ShowsDB mDb;

        public DownloadEpisodes(ShowsDB db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(String... params) {
            String seriesId = params[0];
            EpisodeLoad episodeLoad = new EpisodeLoad(mDb);
            episodeLoad.loadAllSeriesRecords(seriesId);

            return null;
        }
    }*/
}
