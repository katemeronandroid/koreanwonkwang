package com.emarkova.koreanwonkwang.domain.usecases;

import com.emarkova.koreanwonkwang.CustomApplication;
import com.emarkova.koreanwonkwang.data.database.DBManager;
import com.emarkova.koreanwonkwang.data.database.DBRepository;

public class SetTestResult {
    private final DBRepository repository;

    public SetTestResult(){
        repository = CustomApplication.getDBManager();
    }

    public void setTestResultToDB(double result, String title) {
        repository.setTestResult(result, title);
    }

    public void setOpenNextLessonToDB(String title) {
        repository.openLesson(title);
    }
}
