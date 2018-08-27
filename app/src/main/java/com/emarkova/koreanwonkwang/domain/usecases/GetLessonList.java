package com.emarkova.koreanwonkwang.domain.usecases;

import android.util.Log;

import com.emarkova.koreanwonkwang.CustomApplication;
import com.emarkova.koreanwonkwang.data.database.DBRepository;
import com.emarkova.koreanwonkwang.domain.mappers.DataDomainLessonMapper;
import com.emarkova.koreanwonkwang.domain.mappers.DomainViewLessonMapper;
import com.emarkova.koreanwonkwang.presentation.model.Lesson;

import java.util.ArrayList;
import java.util.List;

public class GetLessonList {
    private final DBRepository repository;

    public GetLessonList() {repository = CustomApplication.getDBManager();}

    public List<Lesson> getLessonList() {
        return DomainViewLessonMapper.mapListToView(DataDomainLessonMapper.mapListToDomain(repository.getLessonList()));
    }

    public List<String> getLessonsResult() {
        List<String> result = new ArrayList<>();
        List<Lesson> lessonList = getLessonList();
        for(int i = 0; i< lessonList.size(); i++) {
            result.add(String.valueOf(lessonList.get(i).getPer()));
            if(Double.valueOf(lessonList.get(i).getPer()).equals(0.0)) {
                break;
            }
        }
        return result;
    }

}
