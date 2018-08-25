package com.emarkova.koreanwonkwang.presentation.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.emarkova.koreanwonkwang.R;
import com.emarkova.koreanwonkwang.presentation.mvp.MVPVocabularyView;
import com.emarkova.koreanwonkwang.presentation.mvp.VocabularyPresenter;
import com.emarkova.koreanwonkwang.presentation.mvp.VocabularyPresenterImp;
import com.emarkova.koreanwonkwang.presentation.model.Word;
import com.emarkova.koreanwonkwang.presentation.recyclerview.VocabularyAdapter;

import java.util.List;

public class ActivityVocabulary extends AppCompatActivity implements MVPVocabularyView {
    private RecyclerView vocabularyRecyclerView;
    private RecyclerView.Adapter vocabularyAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Toolbar toolbar;
    private static FloatingActionButton floatingButton;

    @Override
    public void onBackPressed() {
        if(VocabularyAdapter.editMode) {
            VocabularyAdapter.editMode = false;
            setFABVisibility(View.VISIBLE);
            vocabularyAdapter.notifyDataSetChanged();
        }
        else
            super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);
        floatingButton = findViewById(R.id.fab);
        vocabularyRecyclerView = (RecyclerView)findViewById(R.id.wordRecyclerList);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        vocabularyRecyclerView.setLayoutManager(layoutManager);
        VocabularyPresenter presenter = new VocabularyPresenterImp();
        presenter.connectToView(ActivityVocabulary.this);
        presenter.getVocabularyList();
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
        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        View layout = inflater.inflate(R.layout.dialog_add_word, null);
        final EditText koWord = (EditText)layout.findViewById(R.id.koreanInputDialog);
        final EditText ruWord = (EditText)layout.findViewById(R.id.russianInputDialog);
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setView(layout);
        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                VocabularyPresenter presenter = new VocabularyPresenterImp();
                presenter.connectToView(ActivityVocabulary.this);
                presenter.newWord(koWord.getText().toString(), ruWord.getText().toString());
                dialogInterface.cancel();
                presenter.getVocabularyList();
            }
        });
        builder.create().show();
    }

    public static void setFABVisibility(int mode) {
        floatingButton.setVisibility(mode);
    }

    @Override
    public void setWordsList(List<Word> list) {
        vocabularyAdapter = new VocabularyAdapter(list);
        vocabularyRecyclerView.setAdapter(vocabularyAdapter);
        if(list.size() == 0)
            setFABVisibility(View.VISIBLE);
    }
}
