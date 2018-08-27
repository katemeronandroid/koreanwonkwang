package com.emarkova.koreanwonkwang.domain.usecases;

import android.util.Log;

import com.emarkova.koreanwonkwang.CustomApplication;
import com.emarkova.koreanwonkwang.data.database.DBRepository;
import com.emarkova.koreanwonkwang.data.model.DataWord;

public class SetNewWord {
    private final DBRepository repository;

    public SetNewWord() {
        repository = CustomApplication.getDBManager();
    }

    public void setNewWord(String koWord, String ruWord) {
        repository.setNewWord(koWord, ruWord);
    }
}
