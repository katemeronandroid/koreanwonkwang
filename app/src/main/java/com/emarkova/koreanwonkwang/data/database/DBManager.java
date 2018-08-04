package com.emarkova.koreanwonkwang.data.database;

import android.content.Context;

import com.emarkova.koreanwonkwang.data.model.DataExercise;
import com.emarkova.koreanwonkwang.data.model.DataLesson;
import com.emarkova.koreanwonkwang.data.model.DataWord;

import java.util.ArrayList;
import java.util.List;

public class DBManager implements DBRepository {
    DBHelperLesson lessonHelper;
    DBHelperExercise exerciseHelper;
    DBHelperVocabulary vocabularyHelper;

    public DBManager(Context context) {
        this.lessonHelper = new DBHelperLesson(context);
        this.exerciseHelper = new DBHelperExercise(context);
        this.vocabularyHelper = new DBHelperVocabulary(context);
    }

    public void uploadLesson(String num, String open, String per, String desc) {
        lessonHelper.insertLesson(num, open, per, desc);
    }

    public void uploadExercise(List<String> params) {
        exerciseHelper.insertExercise(params);
    }

    public ArrayList<DataLesson> getLessonList() {
        return lessonHelper.getLessonList();
    }

    public ArrayList<DataExercise> getExerciseList(String les, String type) {
        return exerciseHelper.getExerciseList(les, type);
    }

    public ArrayList<DataExercise> getTest(String les) {
        return exerciseHelper.getTestList(les);
    }

    public void setTestResult(double result, String title) {
        lessonHelper.updateLessonResult(title, result);
    }

    public void openLesson(String title) {
        lessonHelper.openLesson(title);
    }

    public void deleteLessonTable() {
        lessonHelper.deleteTables(lessonHelper.getReadableDatabase());
    }

    public void createLessonTable() {
        lessonHelper.createTables(lessonHelper.getReadableDatabase());
    }

    public void createExerciseTable() {
        exerciseHelper.createTables(exerciseHelper.getWritableDatabase());
    }

    public void deleteExerciseTable() {
        exerciseHelper.deleteTables(exerciseHelper.getWritableDatabase());
    }

    //vocabulary

    public void createVocabularyTable() {
        vocabularyHelper.createTable(vocabularyHelper.getWritableDatabase());
    }

    public void deleteVocabularyTable() {
        vocabularyHelper.deleteTable(vocabularyHelper.getWritableDatabase());
    }

    public List<DataWord> getVocabulary() {
        return vocabularyHelper.getListOfWords();
    }

    public void setNewWord(String koWord, String ruWord) {
        vocabularyHelper.insertWord(koWord, ruWord);
    }

    public void updateWord(DataWord dataWord) {
        vocabularyHelper.updateWord(dataWord);
    }

    @Override
    public void deleteWord(String id) {
        vocabularyHelper.deleteWord(id);
    }

    public static DBManager getInstance(Context context){
        return new DBManager(context);
    }
}
