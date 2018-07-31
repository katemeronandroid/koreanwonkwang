package com.emarkova.koreanwonkwang.data.database;

import com.emarkova.koreanwonkwang.data.model.DataExercise;
import com.emarkova.koreanwonkwang.data.model.DataLesson;
import com.emarkova.koreanwonkwang.presentation.model.Exercise;
import com.emarkova.koreanwonkwang.presentation.model.Lesson;

import java.util.ArrayList;
import java.util.List;

public interface DBRepository {
    public void uploadLesson(String num, String open, String per, String desc);
    public void uploadExercise(List<String> params);
    public List<DataLesson> getLessonList();
    public List<DataExercise> getExerciseList(String les, String type);
    public List<DataExercise> getTest(String les);
    public void setTestResult(double result, String title);
    public void openLesson(String title);
    public void deleteLessonTable();
    public void createLessonTable();
    public void createExerciseTable();
    public void deleteExerciseTable();

}
