package com.emarkova.koreanwonkwang.domain.usecases;

import com.emarkova.koreanwonkwang.CustomApplication;
import com.emarkova.koreanwonkwang.data.database.DBRepository;
import com.emarkova.koreanwonkwang.domain.mappers.DataDomainLessonMapper;
import com.emarkova.koreanwonkwang.domain.mappers.DomainViewLessonMapper;
import com.emarkova.koreanwonkwang.presentation.model.Lesson;

import java.util.List;

public class GetLessonList {
    DBRepository repository;

    public GetLessonList() {repository = CustomApplication.getDBManager();}

    public List<Lesson> getLessonList() {
        return DomainViewLessonMapper.mapListToView(DataDomainLessonMapper.mapListToDomain(repository.getLessonList()));
    }

}
