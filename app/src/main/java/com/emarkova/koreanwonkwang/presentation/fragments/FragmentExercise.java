package com.emarkova.koreanwonkwang.presentation.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emarkova.koreanwonkwang.R;
import com.emarkova.koreanwonkwang.presentation.model.Exercise;
import com.emarkova.koreanwonkwang.databinding.FragmentExerciseBinding;

public class FragmentExercise extends Fragment {
    private static final String EXERCISE_KEY = "exercise";
    private TextView word;
    private FragmentExerciseBinding binding;
    private boolean testMode = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_exercise, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        word = view.findViewById(R.id.textWord);
        if(testMode)
            word.setVisibility(View.INVISIBLE);
        Bundle bundle = getArguments();
        if(bundle != null) {
            Exercise exercise = bundle.getParcelable(EXERCISE_KEY);
            binding.setExercise(exercise);
        }
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }
}
