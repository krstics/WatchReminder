package com.krstics.watchreminder.Helpers;

public class Constants {
    public static final class TvDB{
        public static final String BASE_URL_TVDB = "http://thetvdb.com/api/";
        public static final String BANNERS_URL = "http://thetvdb.com/banners/";
        public static final String API_KEY = "07C679B9A55DD6A4";
        public static final String TYPE = "/series/";
        public static final String ALL = "/all";
    }

    public static final class AddedShowsDB {

        public static final String id = "id";
        public static final String firstAired = "FirstAired";
        public static final String showName = "SeriesName";
        public static final String overview = "Overview";
        public static final String poster = "Poster";
        public static final String status = "Status";
        public static final String network = "Network";
        public static final String airsDayOfWeek = "AirsDayOfWeek";
        public static final String airsTime = "AirsTime";

        public static final String DB_NAME = "Shows";
        public static final int DB_VERSION = 1;
        public static final String ADDED_SHOWS_TB_NAME = "AddedShows";

        //queries
        public static final String DROP_ADDED_SHOWS_QUERY = "DROP TABLE IF EXIST " + ADDED_SHOWS_TB_NAME;
        public static final String GET_ALL_SHOWS_QUERY = "SELECT * FROM " + ADDED_SHOWS_TB_NAME;
        public static final String REMOVE_ALL_FROM_TB_QUERY = "DELETE * FROM " + ADDED_SHOWS_TB_NAME;
        public static final String CREATE_TB_QUERY = "CREATE TABLE " + ADDED_SHOWS_TB_NAME + "" +
                "(" + id + " TEXT primary key not null," +
                      firstAired + " TEXT not null," +
                      showName + " TEXT not null," +
                      overview + " TEXT not null," +
                      poster + " BLOB," +
                      status + " TEXT," +
                      network + " TEXT," +
                      airsDayOfWeek + " TEXT," +
                      airsTime + " TEXT)";
    }

}
