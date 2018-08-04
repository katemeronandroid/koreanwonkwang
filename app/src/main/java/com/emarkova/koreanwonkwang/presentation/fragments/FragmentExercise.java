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
    private TextView word;
    private TextView question;
    private FragmentExerciseBinding binding;
    private boolean testMode = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_exercise, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        word = (TextView)view.findViewById(R.id.textWord);
        if(testMode)
            word.setVisibility(View.INVISIBLE);
        question = (TextView)view.findViewById(R.id.textQuestion);
        Bundle bundle = getArguments();
        if(bundle != null) {
            Exercise exercise = bundle.getParcelable("exercise");
            binding.setExercise(exercise);
            //question.setText(exercise.getQuestion());
        }
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }
}
