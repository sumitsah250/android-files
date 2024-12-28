package com.boss.models;

import android.provider.Contacts;

import java.util.ArrayList;

public class CuretedApiResponse
{
    public int page;
    public int per_page;
    public ArrayList<Photo> photos;
    public String next_page;

    public int getPage() {
        return page;
    }

    public int getPer_page() {
        return per_page;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public String getNext_page() {
        return next_page;
    }
}
