package com.example.hands_on_realm.model;

import androidx.annotation.NonNull;

import io.realm.RealmObject;

public class student extends RealmObject {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public student() {
    }

    public student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @NonNull
    @Override
    public String toString() {
        return "student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
