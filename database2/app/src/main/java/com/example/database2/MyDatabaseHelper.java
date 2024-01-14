package com.example.database2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DAATABASE_NAME=  "sumit.db";
    private static final int DATABASE_VERSION =1;
    private static final String TABLE_NAME = "my_library";
    private static final String COLUMN_ID = "_id";
    private static final String  COLUMN_TITLE ="book_title";
    private static final String  COLUMN_AUTHOR = "book_author";
    private static final String  COLUMN_PAGES="bool_pages";
    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DAATABASE_NAME, null, DATABASE_VERSION);
        this.context =context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                " CREATE TABLE " + TABLE_NAME +
                        "(" + COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_AUTHOR +" INTEGER );";
        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS "+ TABLE_NAME );

    }
}
