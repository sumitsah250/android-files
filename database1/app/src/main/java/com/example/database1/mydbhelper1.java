package com.example.database1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.security.PrivateKey;

public class mydbhelper1 extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ContactDB";//static used to pre allocate memory and final is used to avoide reassigning value
    private  static final int DATABASE_VERSION =1 ;
    private static String TABLE_CONTACT ="contacts";
    private static  String KEY_ID ="id";
    private static  String KEY_NAME ="name";
    private static  String KEY_NUMBER ="phone_no";
    public mydbhelper1(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // CREATE TABLE  contacts (id INTEGER PRIMARY KEY AUTOINCREMENT , name TEXT , phone_no TEXT);

        sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_CONTACT +"(" + KEY_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + "TEXT,"+KEY_NUMBER +"TEXT"+")" );

        SQLiteDatabase database = this.getWritableDatabase();
        database.close();


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_CONTACT);
        onCreate(sqLiteDatabase);

    }
    public void addContacts (String name , String phoneno){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues  values = new ContentValues();
        values.put (KEY_NAME,name);
        values.put(KEY_NUMBER,phoneno);
        db.insert(TABLE_CONTACT,null,values);
        db.close();
    }
}
