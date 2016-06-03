package com.krstics.watchreminder.Helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Utils {
    public static final String TAG = Utils.class.getSimpleName();

    public static Bitmap getBitmapImage(String link) {
        Bitmap bitmap = null;

        try {
            bitmap = new DownloadBitmapTask().execute(link).get();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return bitmap;
    }

    public static class DownloadBitmapTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            String link = strings[0];
            InputStream stream = null;

            try {
                stream = new URL(link).openStream();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }

            return BitmapFactory.decodeStream(stream);
        }

    }

    public static byte[] convertBitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Bitmap convertByteArrayToBitmap(byte[] array){
        if(array == null)
            return null;
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }
}
