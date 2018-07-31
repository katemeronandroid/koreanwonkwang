package com.emarkova.koreanwonkwang.presentation.MVP;

import android.util.Log;

import com.emarkova.koreanwonkwang.CustomApplication;
import com.emarkova.koreanwonkwang.helpers.ConstantString;

public class MVPPresenterImp implements MVPPresenter {
    private MVPModel mvpModel;
    private MVPView mvpView;

    public MVPPresenterImp() {
        this.mvpModel = new MVPModelImp(CustomApplication.getDBManager());
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
        if(result > ConstantString.TEST_LEVEL) {
            Log.d("Logs", "here");
            mvpModel.openNewLesson(String.valueOf(Integer.valueOf(title) + 1));
        }
    }
}
