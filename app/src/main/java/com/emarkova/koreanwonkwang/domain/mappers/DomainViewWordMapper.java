package com.emarkova.koreanwonkwang.domain.mappers;

import android.view.View;

import com.emarkova.koreanwonkwang.domain.model.DomainWord;
import com.emarkova.koreanwonkwang.presentation.model.Word;

import java.util.ArrayList;
import java.util.List;

public class DomainViewWordMapper {

    public static Word mapToView(DomainWord domainWord) {
        Word result = new Word();
        result.setId(domainWord.getId());
        result.setKoWord(domainWord.getKoWord());
        result.setRuWord(domainWord.getRuWord());
        return result;
    }

    public static DomainWord mapToDomain(Word word) {
        DomainWord result = new DomainWord();
        result.setId(word.getId());
        result.setKoWord(word.getKoWord());
        result.setRuWord(word.getRuWord());
        return result;
    }

    public static List<Word> mapListToView(List<DomainWord> list) {
        List<Word> result = new ArrayList<>();
        for(DomainWord domainWord: list) {
            result.add(mapToView(domainWord));
        }
        return result;
    }
}
