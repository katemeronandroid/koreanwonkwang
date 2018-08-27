package com.emarkova.koreanwonkwang;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.emarkova.koreanwonkwang.presentation.model.UserInformation;

public class DefaultPreferences {
    private static final String NAME_PREF = "DEFAULT_PREF";
    private static final String DB_DEFINED = "DB_DEF";
    private static final String USER_PREF = "USER_PREF";
    private static final String USER_ID = "USER_ID";
    private static final String USER_NAME = "USER_NAME";
    private static final String USER_MAIL = "USER_MAIL";
    private static final String USER_LEVEL = "USER_LEVEL";
    private final SharedPreferences preferences;

    public DefaultPreferences(Context context) {
        preferences = context.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE);;
    }

    public boolean checkDBDefined(SharedPreferences preferences){
        boolean result = false;
        if(preferences.contains(DB_DEFINED)&&preferences.getBoolean(DB_DEFINED, false))
            result = true;
        return result;
    }

    public void setBDDefined(boolean param) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(DB_DEFINED, param);
        editor.commit();
    }

    public boolean checkUserDefined(SharedPreferences preferences) {
        boolean result = false;
        if(preferences.contains(USER_PREF)&&preferences.getBoolean(USER_PREF, false))
            result = true;
        return result;
    }

    public void setUserPref(UserInformation userInformation) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_ID, userInformation.getUserId());
        editor.putString(USER_NAME, userInformation.getUserName());
        editor.putString(USER_MAIL, userInformation.getUserEmail());
        editor.putString(USER_LEVEL, userInformation.getUserLevel());
        editor.putBoolean(USER_PREF, true);
        editor.commit();
    }

    public void setUserName(String name) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_NAME, name);
        editor.commit();
    }

    public void setUserLevel(String level) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_LEVEL, level);
        editor.commit();
    }

    public UserInformation getUserPref() {
        UserInformation result = new UserInformation();
        result.setUserId(preferences.getString(USER_ID, null));
        result.setUserName(preferences.getString(USER_NAME, null));
        result.setUserEmail(preferences.getString(USER_MAIL, null));
        result.setUserLevel(preferences.getString(USER_LEVEL, null));
        return result;
    }

    public void signoutUser() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_ID, null);
        editor.putString(USER_NAME, null);
        editor.putString(USER_MAIL, null);
        editor.putString(USER_LEVEL, null);
        editor.putBoolean(USER_PREF, false);
        editor.commit();
    }

    public void nullUser() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(USER_PREF, false);
        editor.commit();
    }

}
