package com.emarkova.koreanwonkwang.presentation.MVP;

import com.emarkova.koreanwonkwang.presentation.model.Word;

public interface VocabularyPresenter {
    void connectToView(MVPVocabularyView view);
    void getVocabularyList();
    void deleteWord(String id);
    void newWord(String koWord, String ruWord);
    void updateWord(Word word);
}
