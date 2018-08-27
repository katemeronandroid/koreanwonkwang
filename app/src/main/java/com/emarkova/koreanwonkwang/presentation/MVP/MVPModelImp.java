package com.emarkova.koreanwonkwang.presentation.MVP;

import com.emarkova.koreanwonkwang.domain.usecases.GetExerciseList;
import com.emarkova.koreanwonkwang.domain.usecases.OpenLessons;
import com.emarkova.koreanwonkwang.domain.usecases.SetTestResult;
import com.emarkova.koreanwonkwang.presentation.model.Exercise;

import java.util.List;

/**
 * Data model implementation.
 */
public class MVPModelImp implements MVPModel {
    private final GetExerciseList modelGetExerciseList = new GetExerciseList();
    private final SetTestResult modelSetTestResult = new SetTestResult();
    private final OpenLessons modelOpenLessons = new OpenLessons();

    @Override
    public List<Exercise> getListOfExercise(String title, String type) {
        return modelGetExerciseList.getExerciseList(title, type);
    }

    @Override
    public List<Exercise> getTest(String title) {
        return modelGetExerciseList.getTest(title);
    }

    @Override
    public void setTestResult(double result, String title) {
        modelSetTestResult.setTestResultToDB(result, title);
    }

    @Override
    public void openNewLesson(String title) {
        modelSetTestResult.setOpenNextLessonToDB(title);
    }

    @Override
    public void openLessons(int level, List<String> results) {
        modelOpenLessons.openLessons(level, results);
    }
}
