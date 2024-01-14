package com.example.roompersistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = Expense.class,exportSchema = false,version = 1)
public abstract class databaseHelper extends RoomDatabase {
    private static final String  DB_NAME = "expensedb";
    private static databaseHelper instance;
    public static synchronized databaseHelper getDb(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context,databaseHelper.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance ;
    }
    public abstract ExpenseDao expenseDao();
}
