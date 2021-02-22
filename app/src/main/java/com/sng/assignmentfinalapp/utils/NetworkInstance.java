package com.sng.assignmentfinalapp.utils;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class NetworkInstance {
    private static Retrofit retrofit;
    static final String BASE_URL="https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/";
    public static Retrofit getRetrofit()
    {
        if(retrofit==null)
            //retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(SimpleXmlConverterFactory.createNonStrict()).build();
           retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).build();
        return retrofit;
    }
}
