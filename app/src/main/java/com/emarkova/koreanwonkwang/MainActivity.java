package com.emarkova.koreanwonkwang;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.emarkova.koreanwonkwang.data.database.DBManager;
import com.emarkova.koreanwonkwang.helpers.DataLoader;
import com.emarkova.koreanwonkwang.presentation.activities.ActivityLessonList;
import com.emarkova.koreanwonkwang.presentation.fragments.FragmentSignIn;
import com.emarkova.koreanwonkwang.presentation.model.UserInformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
    private DefaultPreferences defaultPreferences;
    private static final String DEFAULT_PREF = "DEFAULT_PREF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        defaultPreferences = ((CustomApplication) getApplicationContext()).getPreferences();
        if(!defaultPreferences.checkDBDefined(getSharedPreferences(DEFAULT_PREF, Context.MODE_PRIVATE))) {
            //загрузка данных
            (new Thread(()->{
                DataLoader dataLoader = new DataLoader(this);
                dataLoader.loadLessons();
                dataLoader.loadExercise();
                dataLoader.createVocabularyTable();
                defaultPreferences.setBDDefined(true);
                notifyUserDefined();
            })).start();
        }

        if (defaultPreferences.checkDBDefined(getSharedPreferences(DEFAULT_PREF, Context.MODE_PRIVATE))){
            notifyUserDefined();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true);
    }

    private void notifyUserDefined() {
        if (defaultPreferences.checkUserDefined(getSharedPreferences(DEFAULT_PREF, Context.MODE_PRIVATE))) {
            Intent intent = new Intent(getApplicationContext(), ActivityLessonList.class);
            startActivity(intent);
        }
        else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            FragmentSignIn fragmentSignIn = new FragmentSignIn();
            transaction.replace(R.id.frameSignIn, fragmentSignIn);
            transaction.commit();
        }
    }

}
