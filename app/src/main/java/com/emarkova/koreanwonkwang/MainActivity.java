package com.emarkova.koreanwonkwang;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.emarkova.koreanwonkwang.data.database.DBManager;
import com.emarkova.koreanwonkwang.helpers.DataLoader;
import com.emarkova.koreanwonkwang.presentation.activities.ActivityLessonList;
import com.emarkova.koreanwonkwang.presentation.fragments.FragmentSignIn;
import com.emarkova.koreanwonkwang.presentation.model.UserInformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private EditText ETemail;
    private EditText ETpassword;

    private DefaultPreferences defaultPreferences;
    private static final String DEFAULT_PREF = "DEFAULT_PREF";
    private DBManager manager; //убрать в конце

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        ETemail = (EditText) findViewById(R.id.et_email);
        ETpassword = (EditText) findViewById(R.id.et_password);

        manager = new DBManager(this);//убрать в конце
        //загрузчик
        defaultPreferences = new DefaultPreferences(MainActivity.this);
        //defaultPreferences.nullUser();

 /*       defaultPreferences.setBDDefined(false);
        manager.deleteLessonTable();
        manager.deleteExerciseTable();
        manager.deleteVocabularyTable();
*/
        if(!defaultPreferences.checkDBDefined(getSharedPreferences(DEFAULT_PREF, Context.MODE_PRIVATE))) {
            //загрузка данных
            (new Thread(()->{
                DataLoader dataLoader = new DataLoader(this);
                dataLoader.loadLessons();
                dataLoader.loadExercise();
                dataLoader.createVocabularyTable();
                defaultPreferences.setBDDefined(true);
            })).start();
        }

/*        Intent intent1 = new Intent(getApplicationContext(), ActivityLessonList.class);
        startActivity(intent1);*/

        while (!defaultPreferences.checkDBDefined(getSharedPreferences(DEFAULT_PREF, Context.MODE_PRIVATE))){}
        if (defaultPreferences.checkUserDefined(getSharedPreferences(DEFAULT_PREF, Context.MODE_PRIVATE))) {
            Intent intent = new Intent(getApplicationContext(), ActivityLessonList.class);
            startActivity(intent);
        }
        else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            FragmentSignIn fragmentSignIn = new FragmentSignIn();
            transaction.replace(R.id.frameSignIn, fragmentSignIn);
            transaction.commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true);
    }

    public void onSignIn(View view) {
        signin(ETemail.getText().toString(),ETpassword.getText().toString());
    }

    public void onRegistration(View view) {
        registration(ETemail.getText().toString(),ETpassword.getText().toString());
    }

    private void signin(String email , String password) {
        if (email.equals(""))
            Toast.makeText(getApplicationContext(), "Введите почту", Toast.LENGTH_SHORT).show();
        else if (password.equals(""))
            Toast.makeText(getApplicationContext(), "Введите пароль", Toast.LENGTH_SHORT).show();
        else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Aвторизация успешна", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        String userID = user.getUid();
                        defaultPreferences.setUserPref(new UserInformation());
                        Intent intent = new Intent(getApplicationContext(), ActivityLessonList.class);
                        startActivity(intent);

                    } else
                        Toast.makeText(getApplicationContext(), "Aвторизация провалена", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void registration (String email , String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "Регистрация успешна", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userID = user.getUid();
                    UserInformation userInformation = new UserInformation();
                    userInformation.setUserId(userID);
                    userInformation.setUserName("");
                    userInformation.setUserLevel("1");
                    myRef.child("users").child(userID).setValue(userInformation);
                }
                else
                    Toast.makeText(getApplicationContext(), "Регистрация провалена", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
