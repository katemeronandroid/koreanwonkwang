package com.emarkova.koreanwonkwang;

import android.app.Instrumentation;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.emarkova.koreanwonkwang.data.database.DBManager;
import com.emarkova.koreanwonkwang.domain.usecases.GetLessonList;
import com.emarkova.koreanwonkwang.helpers.ConstantString;
import com.emarkova.koreanwonkwang.presentation.MVP.VocabularyPresenter;
import com.emarkova.koreanwonkwang.presentation.MVP.VocabularyPresenterImp;
import com.emarkova.koreanwonkwang.presentation.activities.ActivityAchievement;
import com.emarkova.koreanwonkwang.presentation.activities.ActivityExercise;
import com.emarkova.koreanwonkwang.presentation.activities.ActivityLesson;
import com.emarkova.koreanwonkwang.helpers.DataLoader;
import com.emarkova.koreanwonkwang.presentation.activities.ActivitySettings;
import com.emarkova.koreanwonkwang.presentation.activities.ActivityTranslate;
import com.emarkova.koreanwonkwang.presentation.activities.ActivityVocabulary;
import com.emarkova.koreanwonkwang.presentation.helpers.RecyclerClickListiner;
import com.emarkova.koreanwonkwang.presentation.model.Lesson;
import com.emarkova.koreanwonkwang.presentation.recyclerview.BaseAdapter;
import com.emarkova.koreanwonkwang.presentation.helpers.SpaceItemDecoration;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int SPACE = 10;
    private static final String DEFAULT_PREF = "DEFAULT_PREF";
    private DefaultPreferences defaultPreferences;
    private RecyclerView lessonRecyclerView;
    private RecyclerView.Adapter lessonAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Toolbar toolbar;
    private Drawer.Result drawer;
    private DBManager manager; //убрать в конце

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initDrawer();
        manager = new DBManager(this);//убрать в конце

        //загрузчик
        DefaultPreferences defaultPreferences = new DefaultPreferences(MainActivity.this);

        /*defaultPreferences.setBDDefined(false);
        manager.deleteLessonTable();
        manager.deleteExerciseTable();
        manager.deleteVocabularyTable();*/

        if(!defaultPreferences.checkDBDefined(getSharedPreferences(DEFAULT_PREF, Context.MODE_PRIVATE))) {
            Log.d("Logs", String.valueOf(defaultPreferences.checkDBDefined(getSharedPreferences(DEFAULT_PREF, Context.MODE_PRIVATE))));
            //загрузка данных
            (new Thread(()->{
                DataLoader dataLoader = new DataLoader(MainActivity.this);
                dataLoader.loadLessons();
                dataLoader.loadExercise();
                dataLoader.createVocabularyTable();
                defaultPreferences.setBDDefined(true);
            })).start();
        }

        //initUser();
/*        while (!defaultPreferences.checkDBDefined(getSharedPreferences(DEFAULT_PREF, Context.MODE_PRIVATE))){}

        initRecyclerView();*/

        while (!defaultPreferences.checkDBDefined(getSharedPreferences(DEFAULT_PREF, Context.MODE_PRIVATE))){}
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
                Intent intentTran = new Intent(MainActivity.this, ActivityTranslate.class);
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
                            final AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                            ad.setTitle(R.string.about_app);
                            ad.setMessage("Информация об устройстве");
                            ad.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                            AlertDialog alert = ad.create();
                            alert.show();
                        }
                        else {
                            Intent intent;
                            switch (drawerItem.getIdentifier()){
                                case 1:
                                    intent = new Intent(MainActivity.this, ActivityAchievement.class);
                                    startActivity(intent);
                                    break;
                                case 2:
                                    intent = new Intent(MainActivity.this, ActivityVocabulary.class);
                                    startActivity(intent);
                                    break;
                                case 3:
                                    intent = new Intent(MainActivity.this, ActivitySettings.class);
                                    startActivity(intent);
                                    break;
                            }
                        }
                    }
                })
                .build();
    }

    private AccountHeader.Result createAccountHeader(){
        IProfile profile = new ProfileDrawerItem()
                .withName("Kate")
                .withEmail("katemeron@mail.ru")
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
                new DividerDrawerItem(),
                new SecondaryDrawerItem().withName(R.string.settings).withIcon(FontAwesome.Icon.faw_cog).withIdentifier(3),
                new SecondaryDrawerItem().withName(R.string.about_app).withIcon(FontAwesome.Icon.faw_question).withIdentifier(4)};
    }

    private void initRecyclerView() {
        final List<Lesson> lessonList = (new GetLessonList()).getLessonList();
        lessonRecyclerView = (RecyclerView)findViewById(R.id.lessonRecyclerList);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lessonRecyclerView.setLayoutManager(layoutManager);
        lessonRecyclerView.addItemDecoration(new SpaceItemDecoration(SPACE));
        lessonAdapter = new BaseAdapter(lessonList);
        lessonRecyclerView.setAdapter(lessonAdapter);
        lessonRecyclerView.addOnItemTouchListener(new RecyclerClickListiner(this) {
            @Override
            public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
                if(lessonList.get(position).isOpen())
                {
                    Intent intent = new Intent(MainActivity.this, ActivityLesson.class);
                    intent.putExtra("number", String.valueOf(position + 1));
                    startActivity(intent);
                }
            }
        });
    }

    private  void initUser() {
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View layout = inflater.inflate(R.layout.dialog_user_info, null);
        final EditText userName = (EditText)layout.findViewById(R.id.userNameDialog);
        final EditText userEmail = (EditText)layout.findViewById(R.id.userEmailDialog);
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setView(layout);
        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
        Log.d("Logs", "here");
    }
}
