package com.emarkova.koreanwonkwang.presentation.mvp;

import com.emarkova.koreanwonkwang.presentation.model.Exercise;

import java.util.List;

public interface MVPView {
    void setExerciseList(List<Exercise> data);
}
