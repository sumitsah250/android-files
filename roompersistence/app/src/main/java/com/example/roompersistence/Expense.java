package com.example.roompersistence;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "expense")
public class Expense {
    @PrimaryKey(autoGenerate = true)
    private int id;
   @ColumnInfo(name ="title")
    private String title;
   @ColumnInfo( name = "amount")
    private String amount;

   Expense(int id,String title,String amount){
       this.id=id;
       this.amount=amount;
       this.title=title;
   }
   @Ignore
   Expense(String title,String amount){
       this.amount=amount;
       this.title=title;
   }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
