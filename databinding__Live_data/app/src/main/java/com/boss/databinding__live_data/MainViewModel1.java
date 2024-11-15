package com.boss.databinding__live_data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel1 extends ViewModel {

    MutableLiveData<Integer> score=new MutableLiveData<>();
    public MainViewModel1(){
        score.setValue(0);
    }
    public MainViewModel1(MutableLiveData<Integer> score) {
        this.score = score;
    }
    public void increment(){
        score.setValue(score.getValue()+1);
    }
    public void decrement(){
        score.setValue(score.getValue()-1);
    }
}
