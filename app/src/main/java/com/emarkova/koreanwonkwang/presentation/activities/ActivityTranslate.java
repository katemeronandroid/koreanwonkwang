package com.emarkova.koreanwonkwang.presentation.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.emarkova.koreanwonkwang.MyIntentService;
import com.emarkova.koreanwonkwang.R;
import com.emarkova.koreanwonkwang.domain.usecases.SetNewWord;

public class ActivityTranslate extends AppCompatActivity {
    private static final String KEY_WORD = "text";
    private static final String KEY_LANG = "lang";
    private static final String KEY_ACTION = "emarkova.GET_TRANSLATION";
    private Toolbar toolbar;
    private CustomBroadcastReceiver mReceiver;
    private IntentFilter mFilter;
    private EditText editText;
    private TextView textView;
    private String currentLang = "ko";
    private boolean changeDetection;
    Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        mReceiver = new CustomBroadcastReceiver();
        mFilter = new IntentFilter(KEY_ACTION);
        initToolbar();
        textView = (TextView)findViewById(R.id.resultWord);
        editText = (EditText) findViewById(R.id.inputWord);
        translateTextListeners();
        handler = new Handler() {
          public void handlerMessage(Message message) {
              Bundle bundle = message.getData();
              textView.setText(bundle.getString(KEY_WORD));
              currentLang = bundle.getString(KEY_LANG);
          }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mFilter);
    }

    public void changeText(View view) {
        String tmp = editText.getText().toString();
        editText.setText(textView.getText());
        textView.setText(tmp);
        if(currentLang.equals("ko"))
            currentLang = "ru";
        else if (currentLang.equals("ru"))
            currentLang = "ko";
        else
            currentLang = "";
    }

    public void saveWord(View view) {
        if(currentLang != "" && editText.getText().length() != 0) {
            if(currentLang.equals("ko"))
                (new SetNewWord()).setNewWord(editText.getText().toString(), textView.getText().toString());
            if(currentLang.equals("ru"))
                (new SetNewWord()).setNewWord(textView.getText().toString(), editText.getText().toString());
            Toast.makeText(this, R.string.add_new_word_success, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    private class CustomBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            textView.setText(intent.getStringExtra(KEY_WORD));
            currentLang = intent.getStringExtra(KEY_LANG);
        }
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitle(R.string.translator);
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
                    Intent intent = new Intent(ActivityTranslate.this, MyIntentService.class);
                    intent.putExtra(KEY_WORD, editText.getText().toString());
                    (getApplicationContext()).startService(intent);
                }
                else {
                    try {
                        Thread.sleep(1000);
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
