package com.emarkova.koreanwonkwang;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class DefaultPreferences {
    private static final String NAME_PREF = "DEFAULT_PREF";
    private static final String DB_DEFINED = "DB_DEF";
    private Context context;

    public DefaultPreferences(Context context) {
        this.context = context;
    }

    public boolean checkDBDefined(SharedPreferences preferences){
        boolean result = false;
        if(preferences.contains(DB_DEFINED)&&preferences.getBoolean(DB_DEFINED, false))
            result = true;
        return result;
    }

    public void setBDDefined(boolean param) {
        SharedPreferences preferences = context.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(DB_DEFINED, param);
        editor.commit();
        Log.d("Logs", "set " + param);
    }

}
