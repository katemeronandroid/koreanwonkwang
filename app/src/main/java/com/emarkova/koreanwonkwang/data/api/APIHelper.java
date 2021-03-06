package com.emarkova.koreanwonkwang.data.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.Map;
import retrofit2.Response;

/**
 * Organise working with Yandex Translate.
 */
public class APIHelper {
    private static RetrofitHelper retrofit = null;
    private static final String KEY = "trnsl.1.1.20180726T061141Z.a14801d917a62a95.c00d63f843382ca8309b1309fd246f75526b20a1";
    private static final String KOREAN = "ko";
    private static final String RUSSIAN = "ru";
    private Object translateResponse = null;
    private String language;

    public APIHelper(String text, String lang) {
        retrofit = new RetrofitHelper();

    }

    /**
     * Get the translation
     * @param textSend string for translation
     * @return translated string
     */
    public String getTranslation(String textSend) {
        language = getLanguage(textSend);
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

    /**
     * Define the current language.
     * @param textSend string for translation
     * @return language code as string
     */
    private String getLanguage(String textSend) {
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

    /**
     * Return current language
     * @return language code as string
     */
    public String currentLanguage() {
        return this.language;
    }

    /**
     * Return the translated response as Object
     * @return response as Object
     */
    private Object getTranslateResponse() {
        return translateResponse;
    }

}
