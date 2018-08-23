package com.emarkova.koreanwonkwang.presentation.MVP;

import android.util.Log;

import com.emarkova.koreanwonkwang.CustomApplication;
import com.emarkova.koreanwonkwang.DefaultPreferences;
import com.emarkova.koreanwonkwang.data.FirebaseSync;
import com.emarkova.koreanwonkwang.helpers.ConstantString;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MVPPresenterImp implements MVPPresenter {
    private MVPModel mvpModel;
    private MVPView mvpView;
    DefaultPreferences preferences;

    public MVPPresenterImp() {
        this.mvpModel = new MVPModelImp();
        preferences = CustomApplication.getPreferences();
    }

    @Override
    public void connectToView(MVPView view) {
        this.mvpView = view;
    }

    @Override
    public void getExercise(String title, String type) {
        mvpView.setExerciseList(mvpModel.getListOfExercise(title, type));
    }

    @Override
    public void getTest(String title) {
        mvpView.setExerciseList(mvpModel.getTest(title));
    }

    @Override
    public void setTestResult(double result, String title) {
        mvpModel.setTestResult(result, title);
        syncFirebaseLevelResult(result, String.valueOf(Integer.valueOf(title)));
        if(result > ConstantString.TEST_LEVEL) {
            if(Integer.valueOf(title) < Integer.valueOf(preferences.getUserPref().getUserLevel())) {
                //просто обновляем
                Log.d("Logs", "here");
            }
            else {
                Log.d("Logs", "why here");
                mvpModel.openNewLesson(String.valueOf(Integer.valueOf(title) + 1));
                syncFirebaseLevel(String.valueOf(Integer.valueOf(title) + 1));
                syncPreferences(String.valueOf(Integer.valueOf(title) + 1));
            }
        }
    }

    private void syncFirebaseLevelResult(double result, String title) {
        (new FirebaseSync()).syncFirebaseLevelResult(title, String.valueOf(result));
    }

    private void syncPreferences(String level) {
        DefaultPreferences preferences = CustomApplication.getPreferences();
        preferences.setUserLevel(level);
    }

    private void syncFirebaseLevel(String level) {
        (new FirebaseSync()).syncFirebaseLevel(level);
    }

    public void openLessons(int level, List<String> results){
        mvpModel.openLessons(level, results);
    }

    @Override
    public void openLessons() {
        List<String> result = new ArrayList<>(1);
        result.add("0.0");
        mvpModel.openLessons(1, result);
    }
}
