package com.emarkova.koreanwonkwang.presentation.MVP;

import java.util.List;

public interface MVPPresenter {

    void connectToView(MVPView view);
    void getExercise(String title, String type);
    void getTest(String title);
    void setTestResult(double result, String title);
    public void openLessons(int level, List<String> results);
}
