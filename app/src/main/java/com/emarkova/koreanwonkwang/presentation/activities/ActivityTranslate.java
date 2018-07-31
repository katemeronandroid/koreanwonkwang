package com.emarkova.koreanwonkwang.presentation.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.emarkova.koreanwonkwang.MyIntentService;
import com.emarkova.koreanwonkwang.R;
import com.emarkova.koreanwonkwang.data.api.TranslateServer;

public class ActivityTranslate extends AppCompatActivity {
    private static final String KEY_WORD = "text";
    private static final String KEY_ACTION = "emarkova.GET_TRANSLATION";
    private Toolbar toolbar;
    private CustomBroadcastReciever mReciever;
    private IntentFilter mFilter;
    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
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


        mReciever = new CustomBroadcastReciever();
        mFilter = new IntentFilter(KEY_ACTION);

        //final EditText
        editText = (EditText) findViewById(R.id.inputWord);
        editText.setMaxLines(1);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (keyEvent != null) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        Intent intent = new Intent(ActivityTranslate.this, MyIntentService.class);
                        intent.putExtra(KEY_WORD, editText.getText().toString());
                        (getApplicationContext()).startService(intent);
                        return true;
                    }
                }
                else if (actionId == EditorInfo.IME_ACTION_DONE){
                    Intent intent = new Intent(ActivityTranslate.this, MyIntentService.class);
                    intent.putExtra(KEY_WORD, editText.getText().toString());
                    (getApplicationContext()).startService(intent);
                    return true;
                }
                    return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReciever, mFilter);
    }

    private class CustomBroadcastReciever extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("Logs", "response");
            ((TextView)findViewById(R.id.resultWord)).setText(intent.getStringExtra(KEY_WORD));
        }
    }
}
