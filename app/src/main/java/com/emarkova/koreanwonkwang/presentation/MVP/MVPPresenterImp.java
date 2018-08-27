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

/**
 * MVP presenter.
 */
public class MVPPresenterImp {
    private MVPModel mvpModel;
    private MVPView mvpView;
    private DefaultPreferences preferences;
    private final GetLessonList getterLessonList = new GetLessonList();

    public MVPPresenterImp() {
        this.mvpModel = new MVPModelImp();
    }

    /**
     * Connect to MVP View.
     * @param view current view
     */
    public void connectToView(MVPView view) {
        this.mvpView = view;
        this.preferences = ((CustomApplication) ((Context) view).getApplicationContext()).getPreferences();
    }

    /**
     * Get exercises from model and set them to view.
     * @param title lesson
     * @param type type of exercises
     */
    public void getExercise(String title, String type) {
        mvpView.setExerciseList(mvpModel.getListOfExercise(title, type));
    }

    /**
     * Get exercises for test from model and set them to view.
     * @param title lesson
     */
    public void getTest(String title) {
        mvpView.setExerciseList(mvpModel.getTest(title));
    }

    /**
     * Set test results to Data layer.
     * @param result user result
     * @param title lesson
     */
    public void setTestResult(double result, String title) {
        mvpModel.setTestResult(result, title);
        if(result > ConstantString.TEST_LEVEL) {
            if(Integer.valueOf(title) >= Integer.valueOf(preferences.getUserPref().getUserLevel())) {
                mvpModel.openNewLesson(String.valueOf(Integer.valueOf(title) + 1));
                syncPreferences(String.valueOf(Integer.valueOf(title) + 1));
            }
        }
    }

    /**
     * Update user level in preferences.
     * @param level new user level
     */
    private void syncPreferences(String level) {
        preferences.setUserLevel(level);
    }

    /**
     * Open lesson from 1 to user level with results
     * @param level user level
     * @param results user results
     */
    public void openLessons(int level, List<String> results){
        mvpModel.openLessons(level, results);
    }

    /**
     * Open first lesson for a beginner.
     */
    public void openLessons() {
        List<String> result = new ArrayList<>(1);
        result.add("0.0");
        mvpModel.openLessons(1, result);
    }

    /**
     * Synchronise user information with Firebase.
     */
    public void syncFirebaseResults() {
        (new FirebaseSync((Context) mvpView)).syncFirebaseUserStatus();
    }

    /**
     * Get list of all lessons.
     * @return List of Lessons
     */
    public List<Lesson> getLessons() {
        return getterLessonList.getLessonList();
    }

}
