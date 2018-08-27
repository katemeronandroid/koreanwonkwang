package com.emarkova.koreanwonkwang.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.emarkova.koreanwonkwang.presentation.MVP.MVPPresenterImp;
import com.emarkova.koreanwonkwang.presentation.MVP.MVPView;
import com.emarkova.koreanwonkwang.R;
import com.emarkova.koreanwonkwang.presentation.fragments.FragmentAudio;
import com.emarkova.koreanwonkwang.presentation.fragments.FragmentExercise;
import com.emarkova.koreanwonkwang.helpers.ConstantString;
import com.emarkova.koreanwonkwang.presentation.model.Exercise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActivityExercise extends AppCompatActivity implements MVPView {
    private static final String LESSON_KEY = "number";
    private static final String EXERCISE_KEY = "exercise";
    private static final String TYPE_KEY = "type";
    private final MVPPresenterImp presenter = new MVPPresenterImp();
    private int fragmentCounter;
    private Button buttonCheck;
    private String type;
    private String title;
    private boolean checked = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        Intent intent = getIntent();
        title = intent.getStringExtra(LESSON_KEY);
        type = intent.getStringExtra(TYPE_KEY);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        presenter.connectToView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.getExercise(title, type);
    }

    private boolean checkAnswer(String answer, String input) {
        return input != null && input.equalsIgnoreCase(answer);
    }

    private int setColor(String answer, String input) {
        if (input != null)
            if(input.equalsIgnoreCase(answer)) {
                return R.color.colorRight;
            }
        return R.color.colorWrong;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(ConstantString.LESSON + title);
    }

    private void clearStack(){
        int count = getSupportFragmentManager().getBackStackEntryCount();
        while(count >= 0){
            getSupportFragmentManager().popBackStack();
            count--;
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(ActivityExercise.this)
        .setTitle(R.string.alert)
        .setMessage(R.string.alert_exit)
        .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
            clearStack();
            Intent intent = new Intent(ActivityExercise.this, ActivityLesson.class);
            intent.putExtra(LESSON_KEY, title);
            startActivity(intent);
        })
        .setNegativeButton(R.string.no, (dialogInterface, i) -> dialogInterface.cancel()).show();
    }

    @Override
    public void setExerciseList(List<Exercise> data) {
        final ArrayList<Exercise> exercises = new ArrayList<>(data);
        Collections.copy(exercises, data);
        fragmentCounter = exercises.size();
        if (exercises.size() != 0) {
            setExercise(exercises.get(0));
            buttonCheck = findViewById(R.id.buttonCheck);
            buttonCheck.setText(R.string.check);
            buttonCheck.setOnClickListener(view -> {
                final Exercise exercise = exercises.get(exercises.size() - fragmentCounter);
                EditText editText = findViewById(R.id.editTextAnswer);
                if (!checked) {
                    AlertDialog.Builder ad;
                    if(checkAnswer(exercise.getAnswer(), editText.getText().toString())){
                        ad = new AlertDialog.Builder(ActivityExercise.this, R.style.AlertCorrect);
                        ad.setMessage(R.string.correct);
                    }
                    else {
                        ad = new AlertDialog.Builder(ActivityExercise.this, R.style.AlertWrong);
                        ad.setMessage(exercise.getAnswer());
                    }
                    ad.setTitle(R.string.answer);
                    ad.setPositiveButton(R.string.OK, (dialogInterface, i) -> {
                        checked = true;
                        dialogInterface.cancel();
                        buttonCheck.setText(R.string.next);
                    });
                    final AlertDialog alert = ad.create();
                    alert.show();
                } else {
                    if (fragmentCounter > 1) {
                        Exercise newExercise = exercises.get(exercises.size() - fragmentCounter + 1);
                        setExercise(newExercise);
                        checked = false;
                        buttonCheck.setText(R.string.check);
                        fragmentCounter--;
                    } else {
                        Intent intent = new Intent(ActivityExercise.this, ActivityLesson.class);
                        intent.putExtra(LESSON_KEY, title);
                        startActivity(intent);
                    }
                }
            });
        }
    }
    private void setExercise(Exercise exercise) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXERCISE_KEY, exercise);
        FragmentTransaction transactionManager = getSupportFragmentManager().beginTransaction();
        if (exercise.getType().equals(ConstantString.WORD_TYPE) || exercise.getType().equals(ConstantString.GRAMMAR_TYPE)) {
            FragmentExercise exerciseCommon = new FragmentExercise();
            exerciseCommon.setArguments(bundle);
            transactionManager.replace(R.id.frameExercise,exerciseCommon);
        }
        else if(exercise.getType().equals(ConstantString.AUDIO_TYPE)) {
            FragmentAudio exerciseAudio = new FragmentAudio();
            exerciseAudio.setArguments(bundle);
            transactionManager.replace(R.id.frameExercise,exerciseAudio);
        }
        transactionManager.commit();
    }
}
