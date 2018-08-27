package com.emarkova.koreanwonkwang.helpers;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.emarkova.koreanwonkwang.CustomApplication;
import com.emarkova.koreanwonkwang.data.database.DBManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * Class to upload the local Data base after application installation.
 */
public class DataLoader {
    private static final String PATH_LESSON = "lessons.txt";
    private static final String PATH_EXERCISE = "exercises.txt";
    private final AssetManager assetManager;
    private final DBManager manager;

    public DataLoader(Context context) {
        assetManager = context.getAssets();
        manager = CustomApplication.getDBManager();
    }

    /**
     * Load table of lessons.
     */
    public void loadLessons() {
        manager.createLessonTable();
        try {
            InputStream is = assetManager.open(PATH_LESSON);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "CP1251"));
            String line = "";
            while ((line = br.readLine()) != null) {
                String [] parce = line.split("\\|");
                if(parce.length == 4) {
                    manager.uploadLesson(parce[0],parce[1], parce[2], parce[3]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load table of exercises.
     */
    public void loadExercise() {
        manager.createExerciseTable();
        try {
            InputStream is = assetManager.open(PATH_EXERCISE);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "CP1251"));
            String line = "";
            while ((line = br.readLine()) != null) {
                String [] parce = line.split("\\|");
                List<String> list = Arrays.asList(parce);
                manager.uploadExercise(list);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create vocabulary table.
     */
    public void createVocabularyTable() {
        manager.createVocabularyTable();
    }

}
