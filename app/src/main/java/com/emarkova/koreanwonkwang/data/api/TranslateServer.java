package com.emarkova.koreanwonkwang.data.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TranslateServer {

    @POST("/api/v1.5/tr.json/translate")
    Call<Object> translate(@Query("key") String key, @Query("text") String text, @Query("lang") String lang);

    @POST("/api/v1.5/tr.json/detect")
    Call<Object> getLang(@Query("key") String key, @Query("text") String text);
}
