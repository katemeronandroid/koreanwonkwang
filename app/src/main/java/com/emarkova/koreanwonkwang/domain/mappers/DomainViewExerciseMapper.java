package com.emarkova.koreanwonkwang.domain.mappers;

import com.emarkova.koreanwonkwang.presentation.model.Exercise;
import com.emarkova.koreanwonkwang.domain.model.DomainExercise;

import java.util.ArrayList;
import java.util.List;

public class DomainViewExerciseMapper {

    public static DomainExercise mapToDomain(Exercise exercise) {
        DomainExercise result = new DomainExercise();
        result.setLessonNumber(exercise.getLessonNumber());
        result.setType(exercise.getType());
        result.setWord(exercise.getWord());
        result.setDescription(exercise.getDescription());
        result.setQuestion(exercise.getQuestion());
        result.setAnswer(exercise.getAnswer());
        result.setAudio(exercise.getAudio());
        result.setTest(exercise.getTest());
        return result;
    }

    public static Exercise mapToView(DomainExercise domainExercise) {
        Exercise result = new Exercise();
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

    public static List<Exercise> mapListToView(List<DomainExercise> list) {
        List<Exercise> resullt = new ArrayList<>();
        for (DomainExercise exercise : list) {
            resullt.add(mapToView(exercise));
        }
        return resullt;
    }
}
