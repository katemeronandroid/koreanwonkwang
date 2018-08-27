package com.emarkova.koreanwonkwang.presentation.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.emarkova.koreanwonkwang.CustomApplication;
import com.emarkova.koreanwonkwang.DefaultPreferences;
import com.emarkova.koreanwonkwang.MainActivity;
import com.emarkova.koreanwonkwang.R;
import com.emarkova.koreanwonkwang.domain.FirebaseSync;
import com.emarkova.koreanwonkwang.domain.usecases.GetLessonList;
import com.emarkova.koreanwonkwang.presentation.MVP.MVPPresenterImp;
import com.emarkova.koreanwonkwang.presentation.MVP.MVPView;
import com.emarkova.koreanwonkwang.presentation.helpers.RecyclerClickListiner;
import com.emarkova.koreanwonkwang.presentation.helpers.SpaceItemDecoration;
import com.emarkova.koreanwonkwang.presentation.model.Exercise;
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

import java.util.Arrays;
import java.util.List;

public class ActivityLessonList extends AppCompatActivity implements MVPView {
    private static final String LESSON_KEY = "number";
    private static final int SPACE = 10;
    private DefaultPreferences defaultPreferences;
    private final MVPPresenterImp presenter = new MVPPresenterImp();
    private RecyclerView lessonRecyclerView;
    private RecyclerView.Adapter lessonAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Toolbar toolbar;
    private Drawer.Result drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessonlist);
        defaultPreferences = ((CustomApplication) getApplicationContext()).getPreferences();
        initToolbar();
        initDrawer();
        initRecyclerView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.connectToView(this);
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
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setSubtitleTextColor(0xFFFFFFFF);
    }
    private void initDrawer() {
        drawer = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(createAccountHeader())
                .addDrawerItems(initDrawerItems())
                .withOnDrawerItemClickListener((parent, view, position, id, drawerItem) -> {
                    if(drawerItem.getIdentifier() == 4) {
                        //alert
                        new AlertDialog.Builder(ActivityLessonList.this)
                        .setTitle(R.string.about_app)
                        .setMessage(getApplicationContext().getResources().getString(R.string.info_about_app))
                        .setPositiveButton(R.string.OK, (dialogInterface, i) -> dialogInterface.cancel()).show();
                    }
                    else if(drawerItem.getIdentifier() == 1) {
                        presenter.syncFirebaseResults();
                    }
                    else {
                        Intent intent;
                        switch (drawerItem.getIdentifier()){
                            case 2:
                                intent = new Intent(getApplicationContext(), ActivityVocabulary.class);
                                startActivity(intent);
                                break;
                            case 3:
                                (new FirebaseSync(getApplicationContext())).syncFirebaseUserStatus();
                                defaultPreferences.signoutUser();
                                intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                break;
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
        return new IDrawerItem[]{new PrimaryDrawerItem().withName(R.string.sync).withIcon(FontAwesome.Icon.faw_exchange).withIdentifier(1),
                new PrimaryDrawerItem().withName(R.string.my_vocabulary).withIcon(FontAwesome.Icon.faw_book).withIdentifier(2),
                new SecondaryDrawerItem().withName(R.string.singout).withIcon(FontAwesome.Icon.faw_sign_out).withIdentifier(3),
                new DividerDrawerItem(),
                new SecondaryDrawerItem().withName(R.string.about_app).withIcon(FontAwesome.Icon.faw_question).withIdentifier(4)};
    }

    private void initRecyclerView() {
        lessonRecyclerView = findViewById(R.id.lessonRecyclerList);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lessonRecyclerView.setLayoutManager(layoutManager);
        lessonRecyclerView.addItemDecoration(new SpaceItemDecoration(SPACE));
        final List<Lesson> lessonList = presenter.getLessons();
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

    @Override
    public void setExerciseList(List<Exercise> data) {

    }
}
