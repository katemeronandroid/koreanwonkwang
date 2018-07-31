package com.emarkova.koreanwonkwang.data.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class APIHelper {
    private static RetrofitHelper retrofit = null;
    final private String KEY = "trnsl.1.1.20180726T061141Z.a14801d917a62a95.c00d63f843382ca8309b1309fd246f75526b20a1";
    private static final String KOREAN = "ko";
    private static final String RUSSIAN = "ru";
    private Object translateResponse = null;

    public APIHelper(String text, String lang) {
        this.retrofit = new RetrofitHelper();

    }

    public String getTranslation(String textSend) {
        String language = getLanguage(textSend);
        String fromTo = "";
        switch (language) {
            default:
                break;
            case KOREAN:
                fromTo = KOREAN + "-" + RUSSIAN;
                break;
            case RUSSIAN:
                fromTo = RUSSIAN + "-" + KOREAN;
                break;
        }
        if(!fromTo.equals(""))
        {
            try {
                Response<Object> response = retrofit.getServer().translate(KEY,textSend,fromTo).execute();
                translateResponse = response.body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JsonObject jObject = new JsonParser().parse(translateResponse.toString()
                                                        .replace("]","\"")
                                                        .replace("[", "\"")).getAsJsonObject();
            String translation = jObject.get("text").toString().replace("\"", "");
            return translation;
        }
        else
            return textSend;

    }

    public String getLanguage(String textSend) {
        try {
            Response<Object> response = retrofit.getServer().getLang(KEY,textSend).execute();
            translateResponse = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().create();
        Map map = gson.fromJson(getTranslateResponse().toString(), Map.class);
        String language = map.get("lang").toString();
        language = language.replace("]", "").replace("[", "");
        return language;
    }

    public Object getTranslateResponse() {
        return translateResponse;
    }

}
