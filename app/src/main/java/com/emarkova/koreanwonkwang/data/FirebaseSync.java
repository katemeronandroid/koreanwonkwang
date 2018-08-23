package com.emarkova.koreanwonkwang.data;

import android.util.Log;

import com.emarkova.koreanwonkwang.CustomApplication;
import com.emarkova.koreanwonkwang.DefaultPreferences;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseSync {

    private final FirebaseDatabase mFirebaseDatabase;
    private final DatabaseReference myRef;

    public FirebaseSync() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
    }

    public void syncFirebaseLevel(String level) {
        Log.d("Logs", "here" + level);
        DefaultPreferences preferences = CustomApplication.getPreferences();
        myRef.child("users").child(preferences.getUserPref().getUserId()).child("userLevel").setValue(level);
        myRef.child("users").child(preferences.getUserPref().getUserId()).child("results").child(String.valueOf(Integer.parseInt(level)-1)).setValue("0.0");
    }

    public void syncFirebaseLevelResult(String level, String result) {
        String formattedDouble = String.format("%.2f", Double.valueOf(result)).replace(",",".");
        DefaultPreferences preferences = CustomApplication.getPreferences();
        myRef.child("users").child(preferences.getUserPref().getUserId()).child("results").child(String.valueOf(Integer.parseInt(level)-1)).setValue(formattedDouble);
    }

}
