package com.emarkova.koreanwonkwang.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.emarkova.koreanwonkwang.data.model.DataExercise;
import com.emarkova.koreanwonkwang.helpers.ConstantString;
import com.emarkova.koreanwonkwang.presentation.model.Exercise;
import com.emarkova.koreanwonkwang.presentation.model.Lesson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class DBHelperExercise extends SQLiteOpenHelper {
    private static final String LESTABNAME = "LESSON";
    private static final String EXTABNAME = "EXERCISE";
    private static final String DB_NAME = "weather_database";
    private static final int VERSION_DB = 1;

    public DBHelperExercise(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelperExercise(Context context) {
        this(context, DB_NAME, null, VERSION_DB);
    }

    public void createTables(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+ EXTABNAME + "(id integer PRIMARY KEY, les_num text NOT NULL, type text, word text, descr text, ques text, ans text, test text, audio text, FOREIGN KEY(les_num) REFERENCES "+ LESTABNAME+"(num))");

    }

    public void deleteTables(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EXTABNAME);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //createTables(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void insertExercise(List<String> params) {
        SQLiteDatabase database = this.getWritableDatabase();
        try {
            database.beginTransaction();
            ContentValues values = new ContentValues();
           //"(id  les_num type word descr ques ans
            values.put("les_num", params.get(0));
            values.put("type", params.get(1));
            values.put("word", params.get(2));
            values.put("descr", params.get(3));
            values.put("ques", params.get(4));
            values.put("ans", params.get(5));
            values.put("test", params.get(6));
            values.put("audio", params.get(7));
            database.insert(EXTABNAME, null, values);
            database.setTransactionSuccessful();
        }
        catch (SQLiteException e) {
            Log.v("SQLExeption", e.getMessage());
        }
        finally {
            if(database.inTransaction())
                database.endTransaction();
            database.close();
        }
    }

    public ArrayList<DataExercise> getExerciseList(String les, String type) {
        ArrayList<DataExercise> result = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        try{
            database.beginTransaction();
            //String [] columns = new String[] { "les_num", "type", "word", "descr", "ques", "ans" };//les_num type word descr ques ans
            //Cursor cursor = database.query(EXTABNAME, columns, null, null, null, null, null);
            String query = "SELECT * FROM " + EXTABNAME + " WHERE les_num='"+ les + "' AND type ='" + type +"' AND test = '0'";
            Cursor cursor = database.rawQuery(query, null);
            result = parceExerciseList(cursor);
            cursor.close();
            database.setTransactionSuccessful();
        }
        catch (SQLiteException e) {
            Log.v("SQLiteException", e.getMessage());
        }
        finally {
            if(database.inTransaction())
                database.endTransaction();
            database.close();
        }
        return result;
    }

    private ArrayList<DataExercise> parceExerciseList(Cursor cursor) {
        int counter = 0;
        ArrayList<DataExercise> result = new ArrayList<>();
        if(cursor.moveToFirst()) {
            do {
                if(counter >= ConstantString.LESSON_SIZE)
                    break;
                String tmpLes = cursor.getString(cursor.getColumnIndex("les_num"));
                String tmpType = cursor.getString(cursor.getColumnIndex("type"));
                String tmpWord = cursor.getString(cursor.getColumnIndex("word"));
                String tmpDesc = cursor.getString(cursor.getColumnIndex("descr"));
                String tmpQues = cursor.getString(cursor.getColumnIndex("ques"));
                String tmpAns = cursor.getString(cursor.getColumnIndex("ans"));
                String tmpTest = cursor.getString(cursor.getColumnIndex("test"));
                String tmpAudio = cursor.getString(cursor.getColumnIndex("audio"));
                result.add(new DataExercise(tmpLes, tmpType, tmpWord, tmpDesc, tmpQues, tmpAns, tmpTest, tmpAudio));
            } while (cursor.moveToNext());
        }
        return result;
    }

    public ArrayList<DataExercise> getTestList(String les) {
        ArrayList<DataExercise> result = new ArrayList<>();
        ArrayList<DataExercise> allExercises = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        try{
            database.beginTransaction();
            //String [] columns = new String[] { "les_num", "type", "word", "descr", "ques", "ans" };//les_num type word descr ques ans
            String query = "SELECT * FROM " + EXTABNAME + " WHERE les_num='"+ les + "' AND test = '1'";
            Cursor cursor = database.rawQuery(query, null);
            allExercises = parceExerciseList(cursor);
            cursor.close();
            database.setTransactionSuccessful();
        }
        catch (SQLiteException e) {
            Log.v("SQLiteException", e.getMessage());
        }
        finally {
            if(database.inTransaction())
                database.endTransaction();
            database.close();
        }
        result = getRandomTest(allExercises);
        return result;
    }

    private ArrayList<DataExercise> getRandomTest(ArrayList<DataExercise> allExercises) {
        ArrayList<DataExercise> result = new ArrayList<>();
        HashSet<Integer> randomSet = new HashSet<>();
        Random random = new Random();
        while (result.size() < ConstantString.LESSON_SIZE) {
            Integer newInt = random.nextInt(allExercises.size() - 1);
            if(!randomSet.contains(newInt)) {
                randomSet.add(newInt);
                result.add(allExercises.get(newInt));
            }
        }
        return result;
    }

}
