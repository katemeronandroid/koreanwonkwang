package com.emarkova.koreanwonkwang.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.emarkova.koreanwonkwang.data.model.DataLesson;

import java.util.ArrayList;

public class DBHelperLesson extends SQLiteOpenHelper {
    private static final String LESTABNAME = "LESSON";
    private static final String DB_NAME = "korean_database";
    private static final int VERSION_DB = 1;

    DBHelperLesson(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    DBHelperLesson(Context context) {

        this(context, DB_NAME, null, VERSION_DB);
    }

    public void createTables(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+ LESTABNAME + "(num text PRIMARY KEY, open text NOT NULL, per text, descr text)");
    }

    public void deleteTables(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LESTABNAME);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertLesson(String num, String open, String per, String desc) {
        SQLiteDatabase database = this.getWritableDatabase();
        try {
            database.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("num", num);
            values.put("open", open);
            values.put("per", per);
            values.put("descr", desc);
            database.insert(LESTABNAME, null, values);
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

    public ArrayList<DataLesson> getLessonList() {
        ArrayList<DataLesson> result = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        try{
            database.beginTransaction();
            String [] columns = new String[] { "num", "open", "per", "descr" };
            Cursor cursor = database.query(LESTABNAME, columns, null, null, null, null, null);
            result = parceLessonList(cursor);
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
            database = null;
        }
        return result;
    }

    private ArrayList<DataLesson> parceLessonList(Cursor cursor) {
        ArrayList<DataLesson> result = new ArrayList<>();
        if(cursor.moveToFirst()) {
            do {
                String tmpNum = cursor.getString(cursor.getColumnIndex("num"));
                String tmpOpen = cursor.getString(cursor.getColumnIndex("open"));
                String tmpPer = cursor.getString(cursor.getColumnIndex("per"));
                String tmpDesc = cursor.getString(cursor.getColumnIndex("descr"));
                result.add(new DataLesson(tmpNum, tmpOpen, tmpPer, tmpDesc));
            }while (cursor.moveToNext());
        }
        return result;
    }

    public void updateLessonResult(String les, double result) {
        SQLiteDatabase database = this.getWritableDatabase();
        try{
            database.beginTransaction();
            String query = "SELECT * FROM " + LESTABNAME + " WHERE num='"+ les + "'";
            Cursor cursor = database.rawQuery(query, null);
            if(cursor.moveToFirst()) {
                if(Double.valueOf(cursor.getString(cursor.getColumnIndex("per"))) < result )
                {
                    String formattedDouble = String.format("%.2f", result);
                    ContentValues values = new ContentValues();
                    values.put("per", String.valueOf(formattedDouble).replace(",", "."));
                    database.update(LESTABNAME, values, "num = ?", new String[] { les });
                }
            }
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
    }

    public void setLessonResult(String les, String result) {
        SQLiteDatabase database = this.getWritableDatabase();
        try{
            database.beginTransaction();
            String query = "SELECT * FROM " + LESTABNAME + " WHERE num='"+ les + "'";
            Cursor cursor = database.rawQuery(query, null);
            if(cursor.moveToFirst()) {
                ContentValues values = new ContentValues();
                values.put("per", result.replace(",", "."));
                database.update(LESTABNAME, values, "num = ?", new String[] { les });
            }
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
    }

    public void openLesson(String les) {
        SQLiteDatabase database = this.getWritableDatabase();
        try{
            database.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("open", String.valueOf(1));
            database.update(LESTABNAME, values, "num = ?", new String[] { les });
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
    }

    public void closeLessons() {
        SQLiteDatabase database = this.getWritableDatabase();
        try{
            database.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("open", String.valueOf(0));
            database.update(LESTABNAME, values, null, null);
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
    }

    public void setNullResultLessons() {
        SQLiteDatabase database = this.getWritableDatabase();
        try{
            database.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("per", String.valueOf(0.0));
            database.update(LESTABNAME, values, null, null);
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
    }
}
