package com.emarkova.koreanwonkwang.domain.mappers;

import com.emarkova.koreanwonkwang.data.model.DataLesson;
import com.emarkova.koreanwonkwang.data.model.DataWord;
import com.emarkova.koreanwonkwang.domain.model.DomainWord;

import java.util.ArrayList;
import java.util.List;

public class DataDomainWordMapper {

    public static DomainWord mapToDomain(DataWord dataWord) {
        DomainWord result = new DomainWord();
        result.setId(dataWord.getId());
        result.setKoWord(dataWord.getKoWord());
        result.setRuWord(dataWord.getRuWord());
        return result;
    }

    public static DataWord mapToData(DomainWord domainWord) {
        DataWord result = new DataWord();
        result.setId(domainWord.getId());
        result.setKoWord(domainWord.getKoWord());
        result.setRuWord(domainWord.getRuWord());
        return result;
    }

    public static List<DomainWord> mapListToDomain(List<DataWord> list) {
        List<DomainWord> result = new ArrayList<>();
        for (DataWord dataWord:list) {
            result.add(mapToDomain(dataWord));
        }
        return result;
    }
}
