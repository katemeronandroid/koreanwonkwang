package com.emarkova.koreanwonkwang.presentation.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.emarkova.koreanwonkwang.DefaultPreferences;
import com.emarkova.koreanwonkwang.MainActivity;
import com.emarkova.koreanwonkwang.R;
import com.emarkova.koreanwonkwang.data.database.DBManager;
import com.emarkova.koreanwonkwang.domain.usecases.GetLessonList;
import com.emarkova.koreanwonkwang.helpers.DataLoader;
import com.emarkova.koreanwonkwang.presentation.MVP.MVPPresenter;
import com.emarkova.koreanwonkwang.presentation.MVP.MVPPresenterImp;
import com.emarkova.koreanwonkwang.presentation.helpers.RecyclerClickListiner;
import com.emarkova.koreanwonkwang.presentation.helpers.SpaceItemDecoration;
import com.emarkova.koreanwonkwang.presentation.model.Lesson;
import com.emarkova.koreanwonkwang.presentation.model.UserInformation;
import com.emarkova.koreanwonkwang.presentation.recyclerview.BaseAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivityLessonList extends AppCompatActivity {
    private static final String LESSON_KEY = "number";
    private static final int SPACE = 10;
    private static final String DEFAULT_PREF = "DEFAULT_PREF";
    private DefaultPreferences defaultPreferences;
    private RecyclerView lessonRecyclerView;
    private RecyclerView.Adapter lessonAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Toolbar toolbar;
    private Drawer.Result drawer;
    private DBManager manager; //убрать в конце
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessonlist);
        defaultPreferences = new DefaultPreferences(this);
        initToolbar();
        initDrawer();
        initRecyclerView();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_translate:
                Intent intentTran = new Intent(getApplicationContext(), ActivityTranslate.class);
                startActivity(intentTran);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        if(drawer != null && drawer.isDrawerOpen()){
            drawer.closeDrawer();
        }
        else{
            moveTaskToBack(true);
        }
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setSubtitleTextColor(0xFFFFFFFF);
    }
    private void initDrawer() {
        drawer = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                //.withDisplayBelowToolbar(true)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(createAccountHeader())
                .addDrawerItems(initDrawerItems())
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if(drawerItem.getIdentifier() == 4) {
                            //alert
                            final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityLessonList.this);
                            builder.setTitle(R.string.about_app);
                            builder.setMessage("Информация об приложении");
                            builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                        else {
                            Intent intent;
                            switch (drawerItem.getIdentifier()){
                                case 1:
                                    intent = new Intent(getApplicationContext(), ActivityAchievement.class);
                                    startActivity(intent);
                                    break;
                                case 2:
                                    intent = new Intent(getApplicationContext(), ActivityVocabulary.class);
                                    startActivity(intent);
                                    break;
                                case 3:
                                    defaultPreferences.signoutUser();
                                    intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    break;
                            }
                        }
                    }
                })
                .build();
    }

    private AccountHeader.Result createAccountHeader(){
        UserInformation userInformation = defaultPreferences.getUserPref();
        IProfile profile = new ProfileDrawerItem()
                .withName(userInformation.getUserName())
                .withEmail(userInformation.getUserEmail())
                .withIcon(getResources().getDrawable(R.drawable.account_icon));
        AccountHeader.Result accountHeader = new AccountHeader()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(profile)
                .build();
        return accountHeader;
    }

    private IDrawerItem[] initDrawerItems() {
        return new IDrawerItem[]{new PrimaryDrawerItem().withName(R.string.my_achievements).withIcon(FontAwesome.Icon.faw_diamond).withIdentifier(1),
                new PrimaryDrawerItem().withName(R.string.my_vocabulary).withIcon(FontAwesome.Icon.faw_book).withIdentifier(2),
                new SecondaryDrawerItem().withName(R.string.singout).withIcon(FontAwesome.Icon.faw_sign_out).withIdentifier(3),
                new DividerDrawerItem(),
                new SecondaryDrawerItem().withName(R.string.about_app).withIcon(FontAwesome.Icon.faw_question).withIdentifier(4)};
    }

    private void initRecyclerView() {
        lessonRecyclerView = (RecyclerView)findViewById(R.id.lessonRecyclerList);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lessonRecyclerView.setLayoutManager(layoutManager);
        lessonRecyclerView.addItemDecoration(new SpaceItemDecoration(SPACE));
        final List<Lesson> lessonList = (new GetLessonList()).getLessonList();
        lessonAdapter = new BaseAdapter(lessonList);
        lessonRecyclerView.setAdapter(lessonAdapter);
        lessonRecyclerView.addOnItemTouchListener(new RecyclerClickListiner(this) {
            @Override
            public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
                if(lessonList.get(position).isOpen())
                {
                    Intent intent = new Intent(getApplicationContext(), ActivityLesson.class);
                    intent.putExtra(LESSON_KEY, String.valueOf(position + 1));
                    startActivity(intent);
                }
            }
        });
    }

    private void openLessons(){
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = getIntent().getParcelableExtra("user");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("Logs", "datashot LIST");
                String userID = user.getUid();
                defaultPreferences.setUserName(dataSnapshot.child("users").child(userID).child("userName").getValue().toString());
                defaultPreferences.setUserLevel(dataSnapshot.child("users").child(userID).child("userLevel").getValue().toString());
                String results = dataSnapshot.child("users").child(userID).child("results").getValue().toString()
                        .replace("[", "").replace("]","").replace(" ","");
                UserInformation userInformation = new UserInformation();
                userInformation.setResults(Arrays.asList(results.split(",")));
                MVPPresenter presenter = new MVPPresenterImp();
                presenter.openLessons(Integer.parseInt(defaultPreferences.getUserPref().getUserLevel()), userInformation.getResults());
                initDrawer();
                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
