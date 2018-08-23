package com.emarkova.koreanwonkwang.presentation.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.emarkova.koreanwonkwang.CustomApplication;
import com.emarkova.koreanwonkwang.DefaultPreferences;
import com.emarkova.koreanwonkwang.R;
import com.emarkova.koreanwonkwang.data.database.DBManager;
import com.emarkova.koreanwonkwang.helpers.DataLoader;
import com.emarkova.koreanwonkwang.presentation.MVP.MVPPresenter;
import com.emarkova.koreanwonkwang.presentation.MVP.MVPPresenterImp;
import com.emarkova.koreanwonkwang.presentation.activities.ActivityLessonList;
import com.emarkova.koreanwonkwang.presentation.model.UserInformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class FragmentSignIn extends Fragment {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authListener;
    private EditText ETemail;
    private EditText ETpassword;
    private UserInformation userInformation;
    private FragmentManager fragmentManager;

    private DefaultPreferences defaultPreferences;
    private static final String DEFAULT_PREF = "DEFAULT_PREF";
    private static final String USER_PREF = "USER_PREF";
    private static final String KEY_ID = "id";
    private DBManager manager; //убрать в конце

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signin, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        ETemail = (EditText) view.findViewById(R.id.et_email);
        ETpassword = (EditText) view.findViewById(R.id.et_password);
        userInformation = new UserInformation();

        (view.findViewById(R.id.btn_sign_in)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signin(ETemail.getText().toString(),ETpassword.getText().toString());
            }
        });
        (view.findViewById(R.id.btn_registration)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registration(ETemail.getText().toString(),ETpassword.getText().toString());
            }
        });
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FirebaseUser user = mAuth.getCurrentUser();
                String userID = user.getUid();
                userInformation.setUserName(dataSnapshot.child("users").child(userID).child("userName").getValue().toString());
                userInformation.setUserLevel(dataSnapshot.child("users").child(userID).child("userLevel").getValue().toString());
                Log.d("Logs", dataSnapshot.child("users").child(userID).child("results").getValue().toString());
                String results = dataSnapshot.child("users").child(userID).child("results").getValue().toString()
                                .replace("[", "").replace("]","").replace(" ","");
                userInformation.setResults(Arrays.asList(results.split(",")));
                MVPPresenter presenter = new MVPPresenterImp();
                presenter.openLessons(Integer.parseInt(userInformation.getUserLevel()), userInformation.getResults());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        manager = new DBManager(getContext());//убрать в конце

        //загрузчик
        defaultPreferences = CustomApplication.getPreferences();

   /*   defaultPreferences.setBDDefined(false);
        manager.deleteLessonTable();
        manager.deleteExerciseTable();
        manager.deleteVocabularyTable();
*/
        if(!defaultPreferences.checkDBDefined( getActivity().getSharedPreferences(DEFAULT_PREF, Context.MODE_PRIVATE))) {
            //загрузка данных
            (new Thread(()->{
                DataLoader dataLoader = new DataLoader(getContext());
                dataLoader.loadLessons();
                dataLoader.loadExercise();
                dataLoader.createVocabularyTable();
                defaultPreferences.setBDDefined(true);
            })).start();
        }
        while (!defaultPreferences.checkDBDefined(getActivity().getSharedPreferences(DEFAULT_PREF, Context.MODE_PRIVATE))){}
        if (defaultPreferences.checkUserDefined(getActivity().getSharedPreferences(USER_PREF, Context.MODE_PRIVATE))) {
            Intent intent = new Intent(getContext(), ActivityLessonList.class);
            startActivity(intent);
        }
    }

    private void signin(String email , String password) {
        if (email.equals(""))
            Toast.makeText(getContext(), "Введите почту", Toast.LENGTH_SHORT).show();
        else if (password.equals(""))
            Toast.makeText(getContext(), "Введите пароль", Toast.LENGTH_SHORT).show();
        else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Aвторизация одобрена", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        String userID = user.getUid();
                        userInformation.setUserId(user.getUid());
                        userInformation.setUserEmail(email);
                        defaultPreferences.setUserPref(userInformation);
                        //Открыть уроки
//                        MVPPresenter presenter = new MVPPresenterImp();
//                        presenter.openLessons(Integer.parseInt(userInformation.getUserLevel()));
                        //
                        Intent intent = new Intent(getContext(), ActivityLessonList.class);
                        startActivity(intent);
                        clearStack();
                    } else
                        Toast.makeText(getContext(), "В авторизации отказано", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void registration (String email , String password) {
        if (email.equals(""))
            Toast.makeText(getContext(), "Введите почту", Toast.LENGTH_SHORT).show();
        else if (password.equals(""))
            Toast.makeText(getContext(), "Введите пароль", Toast.LENGTH_SHORT).show();
        else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Регистрация успешна", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        String userID = user.getUid();
                        UserInformation userInformation = new UserInformation();
                        userInformation.setUserId(userID);
                        userInformation.setUserName("");
                        userInformation.setUserEmail(email);
                        userInformation.setUserLevel("1");
                        ArrayList<String> list = new ArrayList<>();
                        list.add("0.0");
                        userInformation.setResults(list);
                        myRef.child("users").child(userID).setValue(userInformation);
                        defaultPreferences.setUserPref(userInformation);

                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        FragmentRegistration fragmentRegistration = new FragmentRegistration();
                        Bundle bundle = new Bundle();
                        bundle.putString(KEY_ID, userID);
                        fragmentRegistration.setArguments(bundle);
                        transaction.replace(R.id.frameSignIn, fragmentRegistration);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    } else
                        Toast.makeText(getContext(), "Регистрация провалена", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void clearStack(){
        int count = getActivity().getSupportFragmentManager().getBackStackEntryCount();
        while(count >= 0){
            getActivity().getSupportFragmentManager().popBackStack();
            count--;
        }
    }
}
