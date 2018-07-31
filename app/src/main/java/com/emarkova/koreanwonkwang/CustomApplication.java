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

    public static DBManager getDBManager() {
        DBManager manager = DBManager.getInstance(mApplication.getApplicationContext());
        return manager;
    }

}
