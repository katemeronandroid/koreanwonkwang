package com.emarkova.koreanwonkwang;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.emarkova.koreanwonkwang.domain.usecases.GetLessonList;
import com.emarkova.koreanwonkwang.presentation.activities.ActivityLesson;
import com.emarkova.koreanwonkwang.data.database.DBManager;
import com.emarkova.koreanwonkwang.helpers.DataLoader;
import com.emarkova.koreanwonkwang.presentation.activities.ActivitySettings;
import com.emarkova.koreanwonkwang.presentation.activities.ActivityTranslate;
import com.emarkova.koreanwonkwang.presentation.helpers.RecyclerClickListiner;
import com.emarkova.koreanwonkwang.presentation.model.Lesson;
import com.emarkova.koreanwonkwang.presentation.recyclerview.BaseAdapter;
import com.emarkova.koreanwonkwang.presentation.helpers.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int SPACE = 10;
    private static final String DEFAULT_PREF = "DEFAULT_PREF";
    private DefaultPreferences defaultPreferences;
    private RecyclerView lessonRecyclerView;
    private RecyclerView.Adapter lessonAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DBManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = new DBManager(MainActivity.this);
        defaultPreferences = new DefaultPreferences(MainActivity.this);
        //defaultPreferences.setBDDefined(false);
        //manager.deleteLessonTable();
        //manager.deleteExerciseTable();
        if(!defaultPreferences.checkDBDefined(getSharedPreferences(DEFAULT_PREF, Context.MODE_PRIVATE))) {
            //загрузка данных
            DataLoader dataLoader = new DataLoader(MainActivity.this);
            dataLoader.loadLessons();
            dataLoader.loadExercise();
            defaultPreferences.setBDDefined(true);
        }
        final List<Lesson> lessonList = (new GetLessonList()).getLessonList();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitleTextColor(0xFFFFFFFF);


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Intent intentSet = new Intent(MainActivity.this, ActivitySettings.class);
                startActivity(intentSet);
                break;
            case R.id.action_translate:
                Intent intentTran = new Intent(MainActivity.this, ActivityTranslate.class);
                startActivity(intentTran);
                break;
            case R.id.action_exit:
                moveTaskToBack(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
