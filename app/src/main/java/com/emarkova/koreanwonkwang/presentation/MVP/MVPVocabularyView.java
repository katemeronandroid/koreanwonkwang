package com.emarkova.koreanwonkwang.presentation.MVP;

import com.emarkova.koreanwonkwang.presentation.model.Word;

import java.util.List;

public interface MVPVocabularyView {

    /**
     * Set list of words into view
     * @param list List of words
     */
    void setWordsList(List<Word> list);
}
