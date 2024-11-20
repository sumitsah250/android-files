package com.example.hands_on_realm.model;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.Closeable;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainViewModel extends AndroidViewModel {
    Realm realm;
    public MutableLiveData<RealmResults<Student>> studentdata= new MutableLiveData<>();


    public MainViewModel(@NonNull Application application) {
        super( application);
        Realm.init(application);
        setUpDatabase();
        Toast.makeText(application, ""+Constants.number, Toast.LENGTH_SHORT).show();
    }
    void setUpDatabase(){
        realm = Realm.getDefaultInstance();
    }
    public void addstudent(Student student){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(student);
        realm.commitTransaction();
    }
    public void getstudent(){
        RealmResults<Student> newtransection=null;
        newtransection = realm.where(Student.class).findAll();
        studentdata.setValue(newtransection);
    }
    public  void deletedata(Student student){
        realm.beginTransaction();
        student.deleteFromRealm();
        realm.commitTransaction();
        getstudent();
    }



}
