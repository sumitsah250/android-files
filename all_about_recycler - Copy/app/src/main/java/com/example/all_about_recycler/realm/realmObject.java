package com.example.all_about_recycler.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class realmObject extends RealmObject {
    @PrimaryKey
    private  Long id;
    private String name;
    private String Description;

    public realmObject() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
