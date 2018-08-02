package com.emarkova.koreanwonkwang.presentation.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.emarkova.koreanwonkwang.R;
import com.emarkova.koreanwonkwang.domain.usecases.GetVocabularyList;
import com.emarkova.koreanwonkwang.helpers.ConstantString;
import com.emarkova.koreanwonkwang.presentation.model.Word;
import com.emarkova.koreanwonkwang.presentation.recyclerview.VocabularyAdapter;

import java.util.List;

public class ActivityVocabulary extends AppCompatActivity {
    private RecyclerView vocabularyRecyclerView;
    private RecyclerView.Adapter vocabularyAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Toolbar toolbar;


    @Override
    public void onBackPressed() {
        if(VocabularyAdapter.editMode) {
            VocabularyAdapter.editMode = false;
            vocabularyAdapter.notifyDataSetChanged();
        }
        else
            super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);
        final List<Word> wordList = (new GetVocabularyList()).getVocabularyList();
        Log.d("Logs", String.valueOf(wordList.size()));
        vocabularyRecyclerView = (RecyclerView)findViewById(R.id.wordRecyclerList);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        vocabularyRecyclerView.setLayoutManager(layoutManager);
        vocabularyAdapter = new VocabularyAdapter(wordList);
        vocabularyRecyclerView.setAdapter(vocabularyAdapter);
        initToolbar();
    }

    private void initToolbar() {
        toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        toolbar.setTitle(R.string.my_vocabulary);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void newWord(View view) {
    }
}
