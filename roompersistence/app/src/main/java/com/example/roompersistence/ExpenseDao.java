package com.example.roompersistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class ExpenseDao {
    @Query(" select * from expense")
    List<Expense> getAllExpense(){
    return null;
    }

    @Insert
    void addTx(Expense expense){}

    @Update
    void updateTx(Expense expense){}

    @Delete
    void deleteTx(Expense expense){}

}
