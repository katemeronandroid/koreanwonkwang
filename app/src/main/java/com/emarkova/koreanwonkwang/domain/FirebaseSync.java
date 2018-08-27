package com.emarkova.koreanwonkwang.domain;

import android.content.Context;
import android.util.Log;

import com.emarkova.koreanwonkwang.CustomApplication;
import com.emarkova.koreanwonkwang.DefaultPreferences;
import com.emarkova.koreanwonkwang.domain.usecases.GetLessonList;
import com.emarkova.koreanwonkwang.presentation.model.UserInformation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseSync {
    private final FirebaseDatabase mFirebaseDatabase;
    private final DatabaseReference myRef;
    private final DefaultPreferences preferences;
    private static final String USERS_KEY = "users";

    public FirebaseSync(Context context) {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        preferences = ((CustomApplication) context.getApplicationContext()).getPreferences();
    }

    public void syncFirebaseLevel(String level) {
        Log.d("Logs", "here" + level);
        myRef.child(USERS_KEY).child(preferences.getUserPref().getUserId()).child("userLevel").setValue(level);
        myRef.child(USERS_KEY).child(preferences.getUserPref().getUserId()).child("results").child(String.valueOf(Integer.parseInt(level)-1)).setValue("0.0");
    }

    public void syncFirebaseLevelResult(String level, String result) {
        String formattedDouble = String.format("%.2f", Double.valueOf(result)).replace(",",".");
        myRef.child(USERS_KEY).child(preferences.getUserPref().getUserId()).child("results").child(String.valueOf(Integer.parseInt(level)-1)).setValue(formattedDouble);
    }

    public void syncFirebaseUserStatus() {
        (new GetLessonList()).getLessonsResult();
        UserInformation user = preferences.getUserPref();
        user.setResults((new GetLessonList()).getLessonsResult());
        myRef.child(USERS_KEY).child(user.getUserId()).setValue(user);
        /*Log.d("Logs", "ID" + user.getUserId());
        Log.d("Logs", "Name " + user.getUserName());
        Log.d("Logs", "Level " + user.getUserLevel());*/
    }

}
