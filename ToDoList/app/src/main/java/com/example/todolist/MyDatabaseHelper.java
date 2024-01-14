package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DAATABASE_NAME=  "sumit_db";
    private static final int DATABASE_VERSION =1;
    private static final String TABLE_NAME = "my_library";
    private static final String COLUMN_ID = "id";
    private static final String  COLUMN_TITLE ="book_title";
    private static final String  COLUMN_PAGES  = "book_pages";
    private static final String  COLUMN_AUTHOR = "book_author";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DAATABASE_NAME, null, DATABASE_VERSION);
        this.context =context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
                   db.execSQL(" create table " + TABLE_NAME + "(" + COLUMN_ID +" integer primary key autoincrement, " + COLUMN_TITLE + " text," + COLUMN_AUTHOR +" text "+")" );


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS "+ TABLE_NAME );
        onCreate(db);

    }
   public void addBook(String Title, String Author, String Pages){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE,Title);
        cv.put(COLUMN_AUTHOR,Author);
        cv.put(COLUMN_PAGES,Pages);
        long result = db.insert(TABLE_NAME,null,cv);
        if(result == -1){
            Toast.makeText(context, "failed to insert data", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "sucess", Toast.LENGTH_SHORT).show();
        }
        db.close();

    }

}

