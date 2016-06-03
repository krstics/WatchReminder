package com.krstics.watchreminder.RestManager;

import com.krstics.watchreminder.Helpers.Constants;
import com.krstics.watchreminder.Services.TVDBSearchService;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RestManager {
    private TVDBSearchService searchService;

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
}
