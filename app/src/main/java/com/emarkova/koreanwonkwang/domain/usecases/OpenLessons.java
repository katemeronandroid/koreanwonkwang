package com.emarkova.koreanwonkwang.domain.usecases;

import com.emarkova.koreanwonkwang.CustomApplication;
import com.emarkova.koreanwonkwang.data.database.DBRepository;

import java.util.List;

public class OpenLessons {
    private final DBRepository repository;

    public OpenLessons() {
        repository = CustomApplication.getDBManager();
    }

    public void openLessons(int level, List<String> results){
        repository.closeLessons();
        repository.setNullResultLessons();
        for(int i = 1; i <= level; i++) {
            repository.openLesson(String.valueOf(i));
            repository.setLessonResult(String.valueOf(i), results.get(i-1));
        }
    }
}
