package com.emarkova.koreanwonkwang.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Orginise work with Retrofit.
 */
public class RetrofitHelper {
    private static final String BASE_URL = "https://translate.yandex.net";

    /**
     * Return the Translate server
     * @return TranslateServer
     */
    public TranslateServer getServer() {
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        return  retrofit.create(TranslateServer.class);
    }

}
