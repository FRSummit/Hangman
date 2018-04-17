package com.sammi.frsummit.hangman;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by F R SUMMIT on 1/23/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    final static String DB_NAME = "words.db";
    final static int DB_VERSION = 2;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
        onCreate(this.getWritableDatabase());
        System.out.println("Constructor got called");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("onCreate got called");
        String QUERY;
        QUERY = "DROP TABLE IF EXISTS word";
        db.execSQL(QUERY);
        QUERY = "CREATE TABLE word (_id integer primary key autoincrement, value varchar(50))";
        System.out.println("Executing " + QUERY);
        db.execSQL(QUERY);
    }

    public void addWord(String word) {
        String QUERY = "INSERT INTO word VALUES (null, '" + word + "');";
        System.out.println("Executing " + QUERY);
        this.getWritableDatabase().execSQL(QUERY);
    }

    public ArrayList<String> getAllWords() {
        ArrayList<String> words = new ArrayList<>();
        String QUERY = "SELECT * from word";

        Cursor cursor = this.getWritableDatabase().rawQuery(QUERY, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            words.add(cursor.getString(1));
            cursor.moveToNext();
        }
        return words;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
