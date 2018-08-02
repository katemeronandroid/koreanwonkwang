package com.emarkova.koreanwonkwang.domain.usecases;

import com.emarkova.koreanwonkwang.CustomApplication;
import com.emarkova.koreanwonkwang.data.database.DBRepository;

public class DeleteWord {
    DBRepository repository;

    public DeleteWord() {
        repository = CustomApplication.getDBManager();
    }

    public void deleteWord(String id) {
        repository.deleteWord(id);
    }
}
