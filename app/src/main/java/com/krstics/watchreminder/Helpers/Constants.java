package com.krstics.watchreminder.Helpers;

public class Constants {
    public static final class TvDB{
        public static final String BASE_URL_TVDB = "http://thetvdb.com/api/";
        public static final String EPISODE_BY_AIR_DATE = "GetEpisodeByAirDate.php";
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
        public static final String GET_AIR_DAY_OF_WEEK = "SELECT * FROM " + ADDED_SHOWS_TB_NAME + " WHERE " + id + " = ";
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

    public static final class AddedEpisodesTABLE{
        public static final String episodeId = "EpisodeId";
        public static final String episodeName = "EpisodeName";
        public static final String episodeNumber = "EpisodeNumber";
        public static final String seasonNumber = "SeasonNumber";
        public static final String airsDate = "AirsDate";
        public static final String episodeImdbId = "EpisodeIMDBID";
        public static final String overview = "Overview";
        public static final String episodeBanner = "episodeBanner";
        public static final String seriesId = "SeriesId";

        public static final String EPISODES_TB_NAME = "Episodes";

        //Queries
        public static final String DROP_EPISODES_TB_QUERY = "DROP TABLE IF EXISTS " + EPISODES_TB_NAME;
        public static final String GET_ALL_EPISODES_FOR_SHOW_QUERY = "SELECT * FROM " + EPISODES_TB_NAME + " WHERE " + seriesId + " = ";
        public static final String GET_ALL_NOT_WATCHED_EPISODES = "SELECT * FROM " + EPISODES_TB_NAME + " JOIN " + AddedShowsDB.ADDED_SHOWS_TB_NAME +
                " ON " + EPISODES_TB_NAME + "." + seriesId + " = " + AddedShowsDB.ADDED_SHOWS_TB_NAME + "." + AddedShowsDB.id +
                " WHERE " + airsDate + " <= ";
        public static final String GET_NEXT_4_WEEKS_PREMIERS = "SELECT * FROM " + EPISODES_TB_NAME + " JOIN " + AddedShowsDB.ADDED_SHOWS_TB_NAME +
                " ON " + EPISODES_TB_NAME + "." + seriesId + " = " + AddedShowsDB.ADDED_SHOWS_TB_NAME + "." + AddedShowsDB.id +
                " WHERE " + airsDate + " > ";
        public static final String GET_TODAY_PREMIERING_EPISODES = "SELECT * FROM " + EPISODES_TB_NAME + " JOIN " + AddedShowsDB.ADDED_SHOWS_TB_NAME +
                " ON " + EPISODES_TB_NAME + "." + seriesId + " = " + AddedShowsDB.ADDED_SHOWS_TB_NAME + "." + AddedShowsDB.id +
                " WHERE " + airsDate + " = ";
        public static final String REMOVE_ALL_FROM_TB_QUERY = "DELETE * FROM " + EPISODES_TB_NAME;
        public static final String CREATE_EPISODES_TB_QUERY = "CREATE TABLE " + EPISODES_TB_NAME + "" +
                "(" + episodeId + " TEXT primary key not null," +
                episodeName + " TEXT," +
                episodeNumber + " TEXT," +
                overview + " TEXT," +
                episodeBanner + " BLOB," +
                seasonNumber + " TEXT," +
                airsDate + " TEXT," +
                episodeImdbId + " TEXT," +
                seriesId + " TEXT not null," +
                "FOREIGN KEY (" + seriesId + ") REFERENCES " + AddedShowsDB.ADDED_SHOWS_TB_NAME + "(" + AddedShowsDB.id + "))";
    }

}
