package com.emarkova.koreanwonkwang.presentation.MVP;

import com.emarkova.koreanwonkwang.presentation.model.Exercise;

import java.util.List;

public interface MVPView {

    /**
     * Set list of exercises into view.
     * @param data List of exercises
     */
    void setExerciseList(List<Exercise> data);
}
