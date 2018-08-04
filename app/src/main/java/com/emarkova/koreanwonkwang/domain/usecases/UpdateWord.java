package com.emarkova.koreanwonkwang.domain.usecases;

import com.emarkova.koreanwonkwang.CustomApplication;
import com.emarkova.koreanwonkwang.data.database.DBRepository;
import com.emarkova.koreanwonkwang.domain.mappers.DataDomainWordMapper;
import com.emarkova.koreanwonkwang.domain.mappers.DomainViewWordMapper;
import com.emarkova.koreanwonkwang.presentation.model.Word;

public class UpdateWord {
    DBRepository repository;

    public UpdateWord() {
        repository = CustomApplication.getDBManager();
    }

    public void updateWord(Word word) {
        repository.updateWord(DataDomainWordMapper.mapToData(DomainViewWordMapper.mapToDomain(word)));
    }
}
