package com.soussidev.viewmodel_livedata_fragment.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Soussi on 23/03/2018.
 */

public class Constant {

    public static final String BASE_URL = "http://10.0.2.2:8080/";
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    public static final String TAG = "Viewholder";
    public static final String NAME_PATH ="IPTVTunisia/";

    public enum GET_OPERATION{
        getChannel
    }

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        OkHttpClient client = new OkHttpClient.Builder()

                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
