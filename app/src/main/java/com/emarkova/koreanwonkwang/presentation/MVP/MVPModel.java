package com.emarkova.koreanwonkwang.presentation.MVP;

import com.emarkova.koreanwonkwang.presentation.model.Exercise;

import java.util.List;

/**
 * Data model of lesson and exercises.
 */
public interface MVPModel {

    /**
     * Return list of exercises.
     * @param title lesson
     * @param type type of exercises
     * @return list of exercises from Data base.
     */
    List<Exercise> getListOfExercise(String title, String type);

    /**
     * Return list of exercises for test.
     * @param title lesson
     * @return lest pf exercises for test.
     */
    List<Exercise> getTest(String title);

    /**
     * Set test result to daba base.
     * @param result user result
     * @param title lesson
     */
    void setTestResult(double result, String title);

    /**
     * Open new lesson.
     * @param title lesson
     */
    void openNewLesson(String title);

    /**
     * Open lessons from 1 to level.
     * @param level user level
     * @param results user results
     */
    void openLessons(int level, List<String> results);
}
