package com.example.todolist;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class mydbhelper3 extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Contacts2DB";//static used to pre allocate memory and final is used to avoide reassigning value
    private  static final int DATABASE_VERSION =1 ;
    private static final String TABLE_CONTACT ="Contacts";
    private static final  String KEY_ID ="ID";
    private static  final String KEY_NAME ="Name";
    private static final  String KEY_TIME ="Phone_no";
    private static final  String KEY_Date ="date";
    private  Context context;
    public mydbhelper3(@Nullable Context context) {
        
        super( context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // CREATE TABLE  contacts (id INTEGER PRIMARY KEY AUTOINCREMENT , name TEXT , phone_no TEXT);

        sqLiteDatabase.execSQL(" create table "+ TABLE_CONTACT +"(" + KEY_ID + " integer primary key autoincrement,  " + KEY_Date + " text, " + KEY_NAME + " text,"+KEY_TIME +" text "+")" );
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_CONTACT);
        onCreate(sqLiteDatabase);
    }
   void addContacts (String Name , String date,String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues  cv = new ContentValues();
        cv.put (KEY_NAME,Name);
        cv.put(KEY_TIME,date);
        cv.put(KEY_Date,time);
        long result = db.insert(TABLE_CONTACT,null,cv);
        if(result == -1){
            Toast.makeText(context, "failed to insert data", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(context, "sucess", Toast.LENGTH_SHORT).show();
//        db.close();

    }
    public ArrayList<Contactmodel> getcontect(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery(" SELECT * FROM "+TABLE_CONTACT,null);
        ArrayList<Contactmodel> arrcontacts = new ArrayList<>();

        while(cursor.moveToNext()){
            Contactmodel model = new Contactmodel();
            model.id= cursor.getInt(0);
            model.date=cursor.getString(1);
            model.name=cursor.getString(2);
            model.time= cursor.getString(3);
            arrcontacts.add(model);
        }
        return arrcontacts;
    }
    public void UpdateContact(Contactmodel contactmodel){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME,contactmodel.name);
        cv.put(KEY_Date,contactmodel.date);
        cv.put(KEY_TIME,contactmodel.time);
        db.update(TABLE_CONTACT,cv,KEY_ID + " = " + contactmodel.id,null);
    }
    public void DeleteContact(Contactmodel contactmodel){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACT,KEY_ID +" = ?",new String[]{String.valueOf(contactmodel.id)});


    }
}
