package com.emarkova.koreanwonkwang;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.emarkova.koreanwonkwang.data.api.APIHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {
    private static final long TIMEOUT = 1000;
    private static final String KEY_LANG = "lang";
    private static final String KEY_ACTION = "emarkova.GET_TRANSLATION";
    private static final String KEY_WORD = "text";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String text = intent.getStringExtra(KEY_WORD);
        APIHelper apiHelper = new APIHelper(text, null);
        String translation = apiHelper.getTranslation(text);
        String lang = apiHelper.currentLanguage();
        Intent broadcastIntent = new Intent(KEY_ACTION);
        broadcastIntent.putExtra(KEY_WORD, translation);
        broadcastIntent.putExtra(KEY_LANG, lang);
        sendBroadcast(broadcastIntent);

    }
}
