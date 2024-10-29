package com.boss.databinding__live_data;

import android.util.Log;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel{
    int count=0;
    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i("MainViewModel","view model is destroyed");
    }
    public MainViewModel() {
        Log.i("MainViewModel","view model is created");
    }


}

