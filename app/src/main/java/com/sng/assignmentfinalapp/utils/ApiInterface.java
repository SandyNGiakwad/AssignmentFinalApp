package com.sng.assignmentfinalapp.utils;


import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {


    Retrofit retrofit=null;

    @GET("{feed_type}.atom")
    Call<String> getXmlFeed(@Path("feed_type") String feed_type);


}
