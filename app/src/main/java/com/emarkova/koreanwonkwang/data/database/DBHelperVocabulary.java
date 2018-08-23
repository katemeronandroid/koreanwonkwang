package com.emarkova.koreanwonkwang.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.emarkova.koreanwonkwang.data.model.DataWord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DBHelperVocabulary extends SQLiteOpenHelper {
    private static final String TABNAME = "VOCABULARY";
    private static final String DB_NAME = "weather_database";
    private static final int VERSION_DB = 1;

    public DBHelperVocabulary(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelperVocabulary(Context context) {

        this(context, DB_NAME, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void createTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABNAME + "(id datetime default current_timestamp PRIMARY KEY, ko_word text NOT NULL, ru_word text NOT NULL)");
    }

    public void deleteTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABNAME);
    }

    public void insertWord(String ko, String ru) {
        SQLiteDatabase database = this.getWritableDatabase();
        try {
            database.beginTransaction();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String date = sdf.format(new Date());
            ContentValues values = new ContentValues();
            //values.put("id", date);
            values.put("ko_word", ko);
            values.put("ru_word", ru);
            database.insert(TABNAME, null, values);
            database.setTransactionSuccessful();
        }
        catch (SQLiteException e) {
            Log.v("SQLException", e.getMessage());
        }
        finally {
            if(database.inTransaction())
                database.endTransaction();
            database.close();
        }
    }

    public List<DataWord> getListOfWords() {
        List<DataWord> result = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        try{
            database.beginTransaction();
            String[] columns = new String[] {"id", "ko_word", "ru_word"};
            Cursor cursor = database.query(TABNAME, columns, null, null, null, null, null);
            result = parceWordList(cursor);
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

    private List<DataWord> parceWordList(Cursor cursor) {
        List<DataWord> result = new ArrayList<>();
        if(cursor.moveToFirst()) {
            do{
                DataWord dataWord = new DataWord();
                dataWord.setId(cursor.getString(cursor.getColumnIndex("id")));
                dataWord.setKoWord(cursor.getString(cursor.getColumnIndex("ko_word")));
                dataWord.setRuWord(cursor.getString(cursor.getColumnIndex("ru_word")));
                result.add(dataWord);
            } while (cursor.moveToNext());
        }
        return result;
    }

    public void deleteWord(String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        try {
            database.beginTransaction();
            database.delete(TABNAME, "id = ?", new String[]{id});
            database.setTransactionSuccessful();
        }
        catch (SQLiteException e) {
            Log.v("SQLiteExeption", e.getMessage());
        }
        finally {
            if(database.inTransaction())
                database.endTransaction();
            database.close();
        }
    }
     public void updateWord(DataWord dataWord) {
        SQLiteDatabase database = this.getWritableDatabase();
        try {
            database.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("ko_word", dataWord.getKoWord());
            values.put("ru_word", dataWord.getRuWord());
            database.update(TABNAME, values, "id = ?", new String[]{dataWord.getId()});
            database.setTransactionSuccessful();
        }
        catch (SQLiteException e) {
            Log.v("SQLiteExeption", e.getMessage());
        }
        finally {
            if(database.inTransaction())
                database.endTransaction();
            database.close();
        }
     }
}
