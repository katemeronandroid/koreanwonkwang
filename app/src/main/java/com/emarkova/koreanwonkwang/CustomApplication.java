package com.emarkova.koreanwonkwang;

import android.app.Application;
import android.content.Context;

import com.emarkova.koreanwonkwang.data.database.DBManager;

public class CustomApplication extends Application {
    private static CustomApplication mApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    /**
     * Get Data layer repository implementation.
     * @return DBManager
     */
    public static DBManager getDBManager() {
        DBManager manager = DBManager.getInstance(mApplication.getApplicationContext());
        return manager;
    }

    /**
     * Get Default preferences
     * @return DefaultPreferences
     */
    public DefaultPreferences getPreferences() {
        DefaultPreferences preferences = new DefaultPreferences(mApplication);
        return preferences;
    }
}
