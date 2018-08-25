package com.emarkova.koreanwonkwang.presentation.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.emarkova.koreanwonkwang.DefaultPreferences;
import com.emarkova.koreanwonkwang.R;
import com.emarkova.koreanwonkwang.presentation.mvp.MVPPresenter;
import com.emarkova.koreanwonkwang.presentation.mvp.MVPPresenterImp;
import com.emarkova.koreanwonkwang.presentation.activities.ActivityLessonList;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentRegistration extends Fragment {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private static final String KEY_ID = "id";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        EditText name = view.findViewById(R.id.et_username);
        Bundle bundle = getArguments();
        String userID = bundle.getString(KEY_ID);
        Button buttonSave = view.findViewById(R.id.buttonSaveUser);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DefaultPreferences preferences = new DefaultPreferences(getContext());
                preferences.setUserName(name.getText().toString());
                myRef.child("users").child(userID).child("userName").setValue(name.getText().toString());
                MVPPresenter presenter = new MVPPresenterImp();
                presenter.openLessons();
                Intent intent = new Intent(getContext(), ActivityLessonList.class);
                startActivity(intent);
                Log.d("Logs", "tutututu");
            }
        });
    }
}
