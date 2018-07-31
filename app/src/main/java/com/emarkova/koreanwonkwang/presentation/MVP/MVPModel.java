package com.emarkova.koreanwonkwang.presentation.MVP;

import com.emarkova.koreanwonkwang.presentation.model.Exercise;

import java.util.List;

public interface MVPModel {
    List<Exercise> getListOfExercise(String title, String type);
    List<Exercise> getTest(String title);
    void setTestResult(double result, String title);
    void openNewLesson(String title);
}
