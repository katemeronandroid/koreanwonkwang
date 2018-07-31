package com.emarkova.koreanwonkwang.presentation.MVP;

import android.util.Log;

import com.emarkova.koreanwonkwang.data.database.DBManager;
import com.emarkova.koreanwonkwang.domain.usecases.GetExerciseList;
import com.emarkova.koreanwonkwang.domain.usecases.GetLessonList;
import com.emarkova.koreanwonkwang.domain.usecases.SetTestResult;
import com.emarkova.koreanwonkwang.presentation.model.Exercise;

import java.util.List;

public class MVPModelImp implements MVPModel {

    public MVPModelImp(DBManager manager) {
    }

    @Override
    public List<Exercise> getListOfExercise(String title, String type) {
        return (new GetExerciseList()).getExerciseList(title, type);
    }

    @Override
    public List<Exercise> getTest(String title) {
        return (new GetExerciseList()).getTest(title);
    }

    @Override
    public void setTestResult(double result, String title) {
        (new SetTestResult()).setTestResultToDB(result, title);
    }

    @Override
    public void openNewLesson(String title) {
        (new SetTestResult()).setOpenNextLessonToDB(title);
    }
}
