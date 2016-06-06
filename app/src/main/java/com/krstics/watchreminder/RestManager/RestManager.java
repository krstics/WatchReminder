package com.krstics.watchreminder.RestManager;

import com.krstics.watchreminder.Helpers.Constants;
import com.krstics.watchreminder.Services.BaseSeriesRecord;
import com.krstics.watchreminder.Services.TVDBSearchService;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RestManager {
    private TVDBSearchService searchService;
    private BaseSeriesRecord baseSeriesRecord;

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
}
