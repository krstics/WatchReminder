package com.krstics.watchreminder.RestManager;

import com.krstics.watchreminder.Helpers.Constants;
import com.krstics.watchreminder.Services.AllSeriesRecord;
import com.krstics.watchreminder.Services.BaseSeriesRecord;
import com.krstics.watchreminder.Services.EpisodeByAirDate;
import com.krstics.watchreminder.Services.PreviousTimeService;
import com.krstics.watchreminder.Services.TVDBSearchService;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RestManager {
    private TVDBSearchService searchService;
    private BaseSeriesRecord baseSeriesRecord;
    private AllSeriesRecord allSeriesRecord;
    private EpisodeByAirDate episodeByAirDate;
    private PreviousTimeService previousTimeService;

    public TVDBSearchService getSearchService(){
        if(searchService == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.TvDB.BASE_URL_TVDB)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();

            searchService = retrofit.create(TVDBSearchService.class);
        }
        return searchService;
    }

    public BaseSeriesRecord getBaseSeriesRecord(){
        if(baseSeriesRecord == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.TvDB.BASE_URL_TVDB)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();

            baseSeriesRecord = retrofit.create(BaseSeriesRecord.class);
        }
        return baseSeriesRecord;
    }

    public AllSeriesRecord getAllSeriesRecord(){
        if(allSeriesRecord == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.TvDB.BASE_URL_TVDB)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();

            allSeriesRecord = retrofit.create(AllSeriesRecord.class);
        }
        return allSeriesRecord;
    }

    public EpisodeByAirDate getEpisodeByAirDaterest(){
        if(episodeByAirDate == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.TvDB.BASE_URL_TVDB)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();

            episodeByAirDate = retrofit.create(EpisodeByAirDate.class);
        }
        return episodeByAirDate;
    }

    public PreviousTimeService getPreviousTimeService(){
        if(previousTimeService == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.TvDB.BASE_URL_TVDB)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();

            previousTimeService = retrofit.create(PreviousTimeService.class);
        }

        return previousTimeService;
    }
}
