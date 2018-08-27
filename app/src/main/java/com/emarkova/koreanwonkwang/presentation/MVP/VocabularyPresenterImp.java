package com.emarkova.koreanwonkwang.presentation.MVP;

import com.emarkova.koreanwonkwang.domain.usecases.DeleteWord;
import com.emarkova.koreanwonkwang.domain.usecases.GetVocabularyList;
import com.emarkova.koreanwonkwang.domain.usecases.SetNewWord;
import com.emarkova.koreanwonkwang.domain.usecases.UpdateWord;
import com.emarkova.koreanwonkwang.presentation.model.Word;

public class VocabularyPresenterImp {
    private MVPVocabularyView vocabularyView;
    private final GetVocabularyList getVocabularyList = new GetVocabularyList();
    private final DeleteWord deleteWord = new DeleteWord();
    private final SetNewWord setNewWord = new SetNewWord();
    private final UpdateWord updateWord = new UpdateWord();
    public void connectToView(MVPVocabularyView view) {
        this.vocabularyView = view;
    }

    public void getVocabularyList() {
        vocabularyView.setWordsList(getVocabularyList.getVocabularyList());
    }

    public void deleteWord(String id) {
        deleteWord.deleteWord(id);
    }

    public void newWord(String koWord, String ruWord) {
        setNewWord.setNewWord(koWord, ruWord);
    }

    public void updateWord(Word word) {
        updateWord.updateWord(word);
    }

}
