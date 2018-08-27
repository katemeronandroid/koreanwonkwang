package com.emarkova.koreanwonkwang.presentation.MVP;

import android.content.Context;
import android.util.Log;

import com.emarkova.koreanwonkwang.CustomApplication;
import com.emarkova.koreanwonkwang.DefaultPreferences;
import com.emarkova.koreanwonkwang.domain.FirebaseSync;
import com.emarkova.koreanwonkwang.domain.usecases.GetLessonList;
import com.emarkova.koreanwonkwang.helpers.ConstantString;
import com.emarkova.koreanwonkwang.presentation.activities.ActivityTest;
import com.emarkova.koreanwonkwang.presentation.model.Lesson;

import java.util.ArrayList;
import java.util.List;

public class MVPPresenterImp {
    private MVPModel mvpModel;
    private MVPView mvpView;
    private DefaultPreferences preferences;

    public MVPPresenterImp() {
        this.mvpModel = new MVPModelImp();
    }

    public void connectToView(MVPView view) {
        this.mvpView = view;
        this.preferences = ((CustomApplication) ((Context) view).getApplicationContext()).getPreferences();
    }

    public void getExercise(String title, String type) {
        mvpView.setExerciseList(mvpModel.getListOfExercise(title, type));
    }

    public void getTest(String title) {
        mvpView.setExerciseList(mvpModel.getTest(title));
    }

    public void setTestResult(double result, String title) {
        mvpModel.setTestResult(result, title);
        if(result > ConstantString.TEST_LEVEL) {
            if(Integer.valueOf(title) >= Integer.valueOf(preferences.getUserPref().getUserLevel())) {
                Log.d("Logs", "why here");
                mvpModel.openNewLesson(String.valueOf(Integer.valueOf(title) + 1));
                //syncFirebaseLevel(String.valueOf(Integer.valueOf(title) + 1));
                syncPreferences(String.valueOf(Integer.valueOf(title) + 1));
            }
        }
    }

    private void syncFirebaseLevelResult(double result, String title) {
        (new FirebaseSync(((Context) mvpView))).syncFirebaseLevelResult(title, String.valueOf(result));
    }

    private void syncPreferences(String level) {
        preferences.setUserLevel(level);
    }

    private void syncFirebaseLevel(String level) {
        (new FirebaseSync((Context) mvpView)).syncFirebaseLevel(level);
    }

    public void openLessons(int level, List<String> results){
        mvpModel.openLessons(level, results);
    }

    public void openLessons() {
        List<String> result = new ArrayList<>(1);
        result.add("0.0");
        mvpModel.openLessons(1, result);
    }

    public void syncFirebaseResults() {
        (new FirebaseSync((Context) mvpView)).syncFirebaseUserStatus();
    }

    public List<Lesson> getLessons() {
        return (new GetLessonList()).getLessonList();
    }

}
