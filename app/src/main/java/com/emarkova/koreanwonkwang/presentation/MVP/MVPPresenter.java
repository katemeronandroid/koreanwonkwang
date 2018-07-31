package com.emarkova.koreanwonkwang.presentation.MVP;

public interface MVPPresenter {

    void connectToView(MVPView view);
    void getExercise(String title, String type);
    void getTest(String title);
    void setTestResult(double result, String title);

}
