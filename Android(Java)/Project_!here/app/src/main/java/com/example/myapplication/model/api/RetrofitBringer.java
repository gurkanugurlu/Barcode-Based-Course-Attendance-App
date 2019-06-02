package com.example.myapplication.model.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBringer {

    public static Retrofit bringRetrofit(){
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://192.168.43.75:57063/").addConverterFactory(GsonConverterFactory.create()).build();
        return  retrofit;
    }
}
