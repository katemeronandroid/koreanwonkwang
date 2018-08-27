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

public class ActivityTest extends AppCompatActivity implements MVPView{
    private static final String LESSON_KEY = "number";
    private static final String EXERCISE_KEY = "exercise";
    private final MVPPresenterImp presenter = new MVPPresenterImp();
    private FragmentTransaction transactionManager;
    private int fragmentCounter;
    private Button buttonCheck;
    private String title;
    private boolean checked = false;
    private int rightAnswers = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        Intent intent = getIntent();
        title = intent.getStringExtra(LESSON_KEY);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        presenter.connectToView(this);
        presenter.getTest(title);
    }

    /**
     * Check if the input answer is correct.
     * @param answer correct answer
     * @param input user answer
     * @return boolean
     */
    private String checkAnswer(String answer, String input) {
        if (input != null)
            if(input.equalsIgnoreCase(answer)) {
                rightAnswers++;
                return ConstantString.CORRECT;
            }
        return answer;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(ConstantString.LESSON + title);
    }

    /**
     * Clean stack of fragments.
     */
    private void clearStack(){
        int count = getSupportFragmentManager().getBackStackEntryCount();
        while(count >= 0){
            getSupportFragmentManager().popBackStack();
            count--;
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(ActivityTest.this)
        .setTitle(R.string.alert)
        .setMessage(R.string.alert_exit)
        .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
            clearStack();
            Intent intent = new Intent(ActivityTest.this, ActivityLesson.class);
            intent.putExtra(LESSON_KEY, title);
            startActivity(intent);
        })
        .setNegativeButton(R.string.no, (dialogInterface, i) -> dialogInterface.cancel())
        .show();
    }

    @Override
    public void setExerciseList(List<Exercise> data) {
        final ArrayList<Exercise> exercises = new ArrayList<>();
        exercises.addAll(data);
        Collections.copy(exercises, data);
        fragmentCounter = exercises.size();
        if(exercises.size() != 0) {
            setExercise(exercises.get(0));
            buttonCheck = findViewById(R.id.buttonCheck);
            buttonCheck.setText(R.string.check);
            buttonCheck.setOnClickListener(view -> {

                final Exercise exercise = exercises.get(exercises.size() - fragmentCounter);
                EditText editText = findViewById(R.id.editTextAnswer);
                if(!checked) {
                    new AlertDialog.Builder(ActivityTest.this)
                    .setTitle(R.string.answer)
                    .setMessage(checkAnswer(exercise.getAnswer(), editText.getText().toString()))
                    .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                        checked = true;
                        dialogInterface.cancel();
                        buttonCheck.setText(R.string.answer);
                    }).show();
                }
                else {
                    if(fragmentCounter > 1) {
                        Exercise newExercise = exercises.get(exercises.size() - fragmentCounter + 1);
                        setExercise(newExercise);
                        checked = false;
                        buttonCheck.setText(R.string.check);
                        fragmentCounter--;
                    }
                    else {
                        setTestResult(rightAnswers);
                        Intent intent = new Intent(ActivityTest.this, ActivityLesson.class);
                        intent.putExtra(LESSON_KEY, title);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    /**
     * Set test results to Data base and user preferences.
     * @param rightAnswers
     */
    private void setTestResult(int rightAnswers) {
        double result = ((double) rightAnswers /ConstantString.LESSON_SIZE)*ConstantString.PERCENT;
        presenter.setTestResult(result, title);
    }

    /**
     * Choose and set proper fragment depends on exercise's type.
     * @param exercise to set
     */
    private void setExercise(Exercise exercise) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXERCISE_KEY, exercise);
        transactionManager = getSupportFragmentManager().beginTransaction();
        if (exercise.getType().equals(ConstantString.WORD_TYPE) || exercise.getType().equals(ConstantString.GRAMMAR_TYPE)) {
            FragmentExercise exerciseCommon = new FragmentExercise();
            exerciseCommon.setTestMode(true);
            exerciseCommon.setArguments(bundle);
            transactionManager.replace(R.id.frameExercise,exerciseCommon);
        }
        else if(exercise.getType().equals(ConstantString.AUDIO_TYPE)) {
            FragmentAudio exerciseAudio = new FragmentAudio();
            exerciseAudio.setTestMode(true);
            exerciseAudio.setArguments(bundle);
            transactionManager.replace(R.id.frameExercise,exerciseAudio);
        }
        transactionManager.commit();
    }
}
