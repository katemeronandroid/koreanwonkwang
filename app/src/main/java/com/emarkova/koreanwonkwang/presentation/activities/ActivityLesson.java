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
    private FragmentManager fragmentManager;
    private FragmentTransaction transactionManager;
    private Toolbar toolbar;
    private String title;
    private Button buttonWords;
    private Button buttonGrammar;
    private Button buttonAudio;
    private Button buttonTest;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        title = intent.getStringExtra("number");
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        fragmentManager = getSupportFragmentManager();
        buttonWords = (Button)findViewById(R.id.buttonWords);
        buttonWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentWords = new Intent(ActivityLesson.this, ActivityExercise.class);
                intentWords.putExtra(LESSON_KEY, title);
                intentWords.putExtra(TYPE_KEY, ConstantString.WORD_TYPE);
                startActivity(intentWords);
            }
        });
        buttonGrammar = (Button)findViewById(R.id.buttonGrammar);
        buttonGrammar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGrammar = new Intent(ActivityLesson.this, ActivityExercise.class);
                intentGrammar.putExtra(LESSON_KEY, title);
                intentGrammar.putExtra(TYPE_KEY,ConstantString.GRAMMAR_TYPE);
                startActivity(intentGrammar);
            }
        });
        buttonAudio = (Button)findViewById(R.id.buttonListen);
        buttonAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intertAudio = new Intent(ActivityLesson.this, ActivityExercise.class);
                intertAudio.putExtra(LESSON_KEY, title);
                intertAudio.putExtra(TYPE_KEY, ConstantString.AUDIO_TYPE);
                startActivity(intertAudio);
            }
        });
        buttonTest = (Button)findViewById(R.id.buttonTest);
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTest = new Intent(ActivityLesson.this, ActivityTest.class);
                intentTest.putExtra(LESSON_KEY, title);
                startActivity(intentTest);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle(R.string.lesson + " " + title);
        toolbar.setTitleTextColor(0xFFFFFFFF);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ActivityLesson.this, MainActivity.class);
        startActivity(intent);
    }

    private void clearStack(){
        int count = fragmentManager.getBackStackEntryCount(); //getBackStackEntryCount();
        while(count > 0){
            fragmentManager.popBackStack();
            count--;
        }
    }
}
