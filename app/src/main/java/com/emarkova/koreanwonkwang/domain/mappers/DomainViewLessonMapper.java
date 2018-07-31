package com.emarkova.koreanwonkwang.domain.mappers;

import com.emarkova.koreanwonkwang.data.model.DataLesson;
import com.emarkova.koreanwonkwang.domain.model.DomainLesson;
import com.emarkova.koreanwonkwang.presentation.model.Lesson;

import java.util.ArrayList;
import java.util.List;

public class DomainViewLessonMapper {

    public static DomainLesson mapToDomain(Lesson lesson) {
        DomainLesson result = new DomainLesson();
        result.setNumber(lesson.getNumber());
        result.setDesc(lesson.getDesc());
        result.setOpen(lesson.getOpen());
        result.setPer(lesson.getPer());
        return result;
    }

    public  static Lesson mapToView(DomainLesson domainLesson) {
        Lesson result = new Lesson();
        result.setNumber(domainLesson.getNumber());
        result.setDesc(domainLesson.getDesc());
        result.setOpen(domainLesson.getOpen());
        result.setPer(domainLesson.getPer());
        return result;
    }

    public static List<Lesson> mapListToView(List<DomainLesson> list) {
        List<Lesson> result = new ArrayList();
        for (DomainLesson dataLesson:list) {
            result.add(mapToView(dataLesson));
        }
        return result;
    }
}
