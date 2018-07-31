package com.emarkova.koreanwonkwang.domain.mappers;

import com.emarkova.koreanwonkwang.data.model.DataLesson;
import com.emarkova.koreanwonkwang.domain.model.DomainLesson;

import java.util.ArrayList;
import java.util.List;

public class DataDomainLessonMapper{

    public static DomainLesson mapToDomain(DataLesson dataLesson) {
        DomainLesson result = new DomainLesson();
        result.setNumber(dataLesson.getNumber());
        result.setDesc(dataLesson.getDesc());
        result.setOpen(dataLesson.getOpen());
        result.setPer(dataLesson.getPer());
        return result;
    }

    public static DataLesson mapToData(DomainLesson domainLesson) {
        DataLesson result = new DataLesson();
        result.setNumber(domainLesson.getNumber());
        result.setDesc(domainLesson.getDesc());
        result.setOpen(domainLesson.getOpen());
        result.setPer(domainLesson.getPer());
        return result;
    }

    public static List<DomainLesson> mapListToDomain(List<DataLesson> list) {
        List<DomainLesson> result = new ArrayList();
        for (DataLesson dataLesson:list) {
            result.add(mapToDomain(dataLesson));
        }
        return result;
    }
}
