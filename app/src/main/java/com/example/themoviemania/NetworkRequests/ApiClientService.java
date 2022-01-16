package com.example.themoviemania.NetworkRequests;

import com.example.themoviemania.Utils.Credentials;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientService {

    private static Retrofit retrofit;

    public static Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(Credentials.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static MovieApiMethods getApi(){
        MovieApiMethods movieApiMethods = getClient().create(MovieApiMethods.class);
        return movieApiMethods;
    }
}
