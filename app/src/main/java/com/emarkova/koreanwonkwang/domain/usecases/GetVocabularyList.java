package com.emarkova.koreanwonkwang.domain.usecases;

import com.emarkova.koreanwonkwang.CustomApplication;
import com.emarkova.koreanwonkwang.data.database.DBRepository;
import com.emarkova.koreanwonkwang.domain.mappers.DataDomainWordMapper;
import com.emarkova.koreanwonkwang.domain.mappers.DomainViewWordMapper;
import com.emarkova.koreanwonkwang.presentation.model.Word;

import java.util.List;

public class GetVocabularyList {
    DBRepository repository;

    public GetVocabularyList() {
        repository = CustomApplication.getDBManager();
    }

    public List<Word> getVocabularyList() {
        return DomainViewWordMapper.mapListToView(DataDomainWordMapper.mapListToDomain(repository.getVocabulary()));
    }
}
