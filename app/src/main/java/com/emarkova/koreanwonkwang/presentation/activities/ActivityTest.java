package com.emarkova.koreanwonkwang.presentation.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.emarkova.koreanwonkwang.presentation.MVP.MVPPresenter;
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
    private FragmentTransaction transactionManager;
    private FragmentManager fragmentManager;
    private int fragmentCounter;
    private Button buttonCheck;
    private Toolbar toolbar;
    private String title;
    private boolean checked = false;
    private int rightAnswers = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        Intent intent = getIntent();
        title = intent.getStringExtra("number");
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        MVPPresenter presenter = new MVPPresenterImp();
        presenter.connectToView(this);
        presenter.getTest(title);
    }

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
        toolbar.setTitle(ConstantString.LESSON + title);
    }

    private void clearStack(){
        int count = fragmentManager.getBackStackEntryCount();
        while(count >= 0){
            fragmentManager.popBackStack();
            count--;
        }
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder ad = new AlertDialog.Builder(ActivityTest.this);
        ad.setTitle(ConstantString.ALERT_TITLE);
        ad.setMessage(ConstantString.ALERT_EXIT);
        ad.setPositiveButton(ConstantString.YES, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clearStack();
                Intent intent = new Intent(ActivityTest.this, ActivityLesson.class);
                intent.putExtra("number", title);
                startActivity(intent);
            }
        });
        ad.setNegativeButton(ConstantString.NO, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alert = ad.create();
        alert.show();
    }

    @Override
    public void setExerciseList(List<Exercise> data) {
        final ArrayList<Exercise> exercises = new ArrayList<>();
        exercises.addAll(data);
        Collections.copy(exercises, data);
        fragmentCounter = exercises.size();
        if(exercises.size() != 0) {
            fragmentManager = getSupportFragmentManager();
            transactionManager = fragmentManager.beginTransaction();
            setExercise(exercises.get(0));
            buttonCheck = findViewById(R.id.buttonCheck);
            buttonCheck.setText(ConstantString.CHECK);
            buttonCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    transactionManager = getSupportFragmentManager().beginTransaction();
                    final Exercise exercise = exercises.get(exercises.size() - fragmentCounter);
                    EditText editText = (EditText)findViewById(R.id.editTextAnswer);
                    if(!checked) {
                        final AlertDialog.Builder ad = new AlertDialog.Builder(ActivityTest.this);
                        ad.setTitle(ConstantString.ANSWER);
                        ad.setMessage(checkAnswer(exercise.getAnswer(), editText.getText().toString()));
                        ad.setPositiveButton(ConstantString.YES, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                checked = true;
                                dialogInterface.cancel();
                                buttonCheck.setText(ConstantString.NEXT);
                            }
                        });
                        AlertDialog alert = ad.create();
                        alert.show();
                    }
                    else {
                        if(fragmentCounter > 1) {
                            Exercise newExercise = exercises.get(exercises.size() - fragmentCounter + 1);
                            setExercise(newExercise);
                            checked = false;
                            buttonCheck.setText(ConstantString.CHECK);
                            fragmentCounter--;
                        }
                        else {
                            setTestResult(rightAnswers);
                            Intent intent = new Intent(ActivityTest.this, ActivityLesson.class);
                            intent.putExtra("number", title);
                            startActivity(intent);
                        }
                    }
                }
            });
        }
    }

    private void setTestResult(int rightAnswers) {
        double result = (new Double(rightAnswers)/ConstantString.LESSON_SIZE)*ConstantString.PERCENT;
        MVPPresenterImp presenter = new MVPPresenterImp();
        presenter.setTestResult(result, title);
    }

    private void setExercise(Exercise exercise) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("exercise", exercise);
        if (exercise.getType().equals(ConstantString.WORD_TYPE) || exercise.getType().equals(ConstantString.GRAMMAR_TYPE)) {
            FragmentExercise exerciseCommon = new FragmentExercise();
            exerciseCommon.setArguments(bundle);
            transactionManager.replace(R.id.frameExercise,exerciseCommon);
            transactionManager.commit();
        }
        else if(exercise.getType().equals(ConstantString.AUDIO_TYPE)) {
            FragmentAudio exerciseAudio = new FragmentAudio();
            exerciseAudio.setArguments(bundle);
            transactionManager.replace(R.id.frameExercise,exerciseAudio);
            transactionManager.commit();
        }
    }
}
