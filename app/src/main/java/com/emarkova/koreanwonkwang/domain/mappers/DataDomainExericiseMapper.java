package com.emarkova.koreanwonkwang.domain.mappers;

import com.emarkova.koreanwonkwang.data.model.DataExercise;
import com.emarkova.koreanwonkwang.domain.model.DomainExercise;
import com.emarkova.koreanwonkwang.presentation.model.Exercise;

import java.util.ArrayList;
import java.util.List;

public class DataDomainExericiseMapper {

    public static DomainExercise mapToDomain(DataExercise dataExercise) {
        DomainExercise result = new DomainExercise();
        result.setLessonNumber(dataExercise.getLessonNumber());
        result.setType(dataExercise.getType());
        result.setWord(dataExercise.getWord());
        result.setDescription(dataExercise.getDescription());
        result.setQuestion(dataExercise.getQuestion());
        result.setAnswer(dataExercise.getAnswer());
        result.setAudio(dataExercise.getAudio());
        result.setTest(dataExercise.getTest());
        return result;
    }

    public static DataExercise mapToData(DomainExercise domainExercise) {
        DataExercise result = new DataExercise();
        result.setLessonNumber(domainExercise.getLessonNumber());
        result.setType(domainExercise.getType());
        result.setWord(domainExercise.getWord());
        result.setDescription(domainExercise.getDescription());
        result.setQuestion(domainExercise.getQuestion());
        result.setAnswer(domainExercise.getAnswer());
        result.setAudio(domainExercise.getAudio());
        result.setTest(domainExercise.getTest());
        return result;
    }

    public static List<DomainExercise> mapListToDomain(List<DataExercise> list) {
        List<DomainExercise> resullt = new ArrayList<>();
        for (DataExercise exercise : list) {
            resullt.add(mapToDomain(exercise));
        }
        return resullt;
    }
}
