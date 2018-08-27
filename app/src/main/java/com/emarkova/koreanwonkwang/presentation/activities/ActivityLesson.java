package com.emarkova.koreanwonkwang.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.emarkova.koreanwonkwang.MainActivity;
import com.emarkova.koreanwonkwang.R;
import com.emarkova.koreanwonkwang.helpers.ConstantString;

public class ActivityLesson extends AppCompatActivity {
    private static final String LESSON_KEY = "number";
    private static final String TYPE_KEY = "type";
    private String title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        title = intent.getStringExtra(LESSON_KEY);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        Button buttonWords = findViewById(R.id.buttonWords);
        buttonWords.setOnClickListener(view -> {
            Intent intentWords = new Intent(ActivityLesson.this, ActivityExercise.class);
            intentWords.putExtra(LESSON_KEY, title);
            intentWords.putExtra(TYPE_KEY, ConstantString.WORD_TYPE);
            startActivity(intentWords);
        });
        Button buttonGrammar =  findViewById(R.id.buttonGrammar);
        buttonGrammar.setOnClickListener(view -> {
            Intent intentGrammar = new Intent(ActivityLesson.this, ActivityExercise.class);
            intentGrammar.putExtra(LESSON_KEY, title);
            intentGrammar.putExtra(TYPE_KEY,ConstantString.GRAMMAR_TYPE);
            startActivity(intentGrammar);
        });
        Button buttonAudio = findViewById(R.id.buttonListen);
        buttonAudio.setOnClickListener(view -> {
            Intent intertAudio = new Intent(ActivityLesson.this, ActivityExercise.class);
            intertAudio.putExtra(LESSON_KEY, title);
            intertAudio.putExtra(TYPE_KEY, ConstantString.AUDIO_TYPE);
            startActivity(intertAudio);
        });
        Button buttonTest = findViewById(R.id.buttonTest);
        buttonTest.setOnClickListener(view -> {
            Intent intentTest = new Intent(ActivityLesson.this, ActivityTest.class);
            intentTest.putExtra(LESSON_KEY, title);
            startActivity(intentTest);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(ConstantString.LESSON + title);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
