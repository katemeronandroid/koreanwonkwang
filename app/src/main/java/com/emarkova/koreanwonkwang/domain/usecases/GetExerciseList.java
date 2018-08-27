package com.emarkova.koreanwonkwang.domain.usecases;

import com.emarkova.koreanwonkwang.CustomApplication;
import com.emarkova.koreanwonkwang.data.database.DBRepository;
import com.emarkova.koreanwonkwang.domain.mappers.DataDomainExericiseMapper;
import com.emarkova.koreanwonkwang.domain.mappers.DomainViewExerciseMapper;
import com.emarkova.koreanwonkwang.presentation.model.Exercise;

import java.util.List;

public class GetExerciseList {
    private final DBRepository repository;

    public GetExerciseList() {repository = CustomApplication.getDBManager();}

    public List<Exercise> getExerciseList(String title, String type) {
        return DomainViewExerciseMapper.mapListToView(DataDomainExericiseMapper.mapListToDomain(repository.getExerciseList(title, type)));
    }

    public List<Exercise> getTest(String title){
        return DomainViewExerciseMapper.mapListToView(DataDomainExericiseMapper.mapListToDomain(repository.getTest(title)));
    }

}
