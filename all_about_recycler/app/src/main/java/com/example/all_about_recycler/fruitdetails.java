package com.example.all_about_recycler;

import android.media.Image;

public class fruitdetails {
    int image;
    String title,details;

    public fruitdetails() {
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public fruitdetails(int image, String title, String details) {
        this.image = image;
        this.title = title;
        this.details = details;
    }
}
