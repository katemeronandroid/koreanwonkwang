package com.emarkova.koreanwonkwang.presentation.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.emarkova.koreanwonkwang.TranslateIntentService;
import com.emarkova.koreanwonkwang.R;
import com.emarkova.koreanwonkwang.presentation.MVP.MVPVocabularyView;
import com.emarkova.koreanwonkwang.presentation.MVP.VocabularyPresenterImp;
import com.emarkova.koreanwonkwang.presentation.model.Word;

import java.util.List;


public class ActivityTranslate extends AppCompatActivity implements MVPVocabularyView {
    private static final int TIMEOUT = 1000;
    private static final String KEY_WORD = "text";
    private static final String KEY_LANG = "lang";
    private static final String KEY_ACTION = "emarkova.GET_TRANSLATION";
    private VocabularyPresenterImp presenter;
    private LocalBroadcastManager localBroadcastManager;
    private CustomBroadcastReceiver mReceiver;
    private IntentFilter mFilter;
    private EditText editText;
    private TextView textView;
    private String currentLang = "ko";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        mReceiver = new CustomBroadcastReceiver();
        mFilter = new IntentFilter(KEY_ACTION);
        presenter = new VocabularyPresenterImp();
        presenter.connectToView(this);
        initToolbar();
        textView = findViewById(R.id.resultWord);
        editText = findViewById(R.id.inputWord);
        translateTextListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        localBroadcastManager.registerReceiver(mReceiver, mFilter);
    }

    public void changeText(View view) {
        String tmp = editText.getText().toString();
        editText.setText(textView.getText());
        textView.setText(tmp);
        switch (currentLang) {
            case "ko":
                currentLang = "ru";
                break;
            case "ru":
                currentLang = "ko";
                break;
            default:
                throw new IllegalArgumentException("Unknown language!");
        }
    }

    public void saveWord(View view) {
        if(editText.getText().length() != 0) {
            if(currentLang.equals("ko"))
                presenter.newWord(editText.getText().toString(), textView.getText().toString());
            if(currentLang.equals("ru"))
                presenter.newWord(textView.getText().toString(), editText.getText().toString());
            Toast.makeText(this, R.string.add_new_word_success, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        localBroadcastManager.unregisterReceiver(mReceiver);
    }

    @Override
    public void setWordsList(List<Word> list) {

    }

    private class CustomBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            textView.setText(intent.getStringExtra(KEY_WORD));
            currentLang = intent.getStringExtra(KEY_LANG);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle(R.string.translator);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    private void translateTextListeners() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() != 0) {
                    Intent intent = new Intent(ActivityTranslate.this, TranslateIntentService.class);
                    intent.putExtra(KEY_WORD, editText.getText().toString());
                    (getApplicationContext()).startService(intent);
                }
                else {
                    try {
                        Thread.sleep(TIMEOUT);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent broadcastIntent = new Intent(KEY_ACTION);
                    broadcastIntent.putExtra(KEY_WORD, "");
                    broadcastIntent.putExtra(KEY_LANG, currentLang);
                    sendBroadcast(broadcastIntent);
                }
            }
        });
    }
}
