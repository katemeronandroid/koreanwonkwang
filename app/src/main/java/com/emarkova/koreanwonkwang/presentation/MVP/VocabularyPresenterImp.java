package com.emarkova.koreanwonkwang.presentation.MVP;

import com.emarkova.koreanwonkwang.domain.usecases.DeleteWord;
import com.emarkova.koreanwonkwang.domain.usecases.GetVocabularyList;
import com.emarkova.koreanwonkwang.domain.usecases.SetNewWord;
import com.emarkova.koreanwonkwang.domain.usecases.UpdateWord;
import com.emarkova.koreanwonkwang.presentation.model.Word;

public class VocabularyPresenterImp implements VocabularyPresenter{
    private MVPVocabularyView vocabularyView;
    @Override
    public void connectToView(MVPVocabularyView view) {
        this.vocabularyView = view;
    }

    @Override
    public void getVocabularyList() {
        vocabularyView.setWordsList((new GetVocabularyList()).getVocabularyList());
    }

    @Override
    public void deleteWord(String id) {
        (new DeleteWord()).deleteWord(id);
    }

    @Override
    public void newWord(String koWord, String ruWord) {
        (new SetNewWord()).setNewWord(koWord, ruWord);
    }

    @Override
    public void updateWord(Word word) {
        (new UpdateWord()).updateWord(word);
    }

}
