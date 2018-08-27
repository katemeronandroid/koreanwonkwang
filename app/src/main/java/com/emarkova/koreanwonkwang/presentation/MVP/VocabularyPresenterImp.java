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

    /**
     * Connect to current view.
     * @param view current view
     */
    public void connectToView(MVPVocabularyView view) {
        this.vocabularyView = view;
    }

    /**
     * Get ans set list of words into view.
     */
    public void getVocabularyList() {
        vocabularyView.setWordsList(getVocabularyList.getVocabularyList());
    }

    /**
     * Delete word from Data base.
     * @param id word id
     */
    public void deleteWord(String id) {
        deleteWord.deleteWord(id);
    }

    /**
     * Insert new word into Data base.
     * @param koWord korean word
     * @param ruWord russian word
     */
    public void newWord(String koWord, String ruWord) {
        setNewWord.setNewWord(koWord, ruWord);
    }

    /**
     * Update word into Data base
     * @param word current word
     */
    public void updateWord(Word word) {
        updateWord.updateWord(word);
    }

}
