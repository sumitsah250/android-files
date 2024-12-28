package com.boss.models;

import java.io.Serializable;

public class Photo implements Serializable {
    public int id;
    public int width;
    public int height;
    public String url;
    public String photographer;
    public String photographer_url;
    public Long photographer_id;
    public String avg_color;
    public Src src;

    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public Long getPhotographer_id() {
        return photographer_id;
    }

    public int getHeight() {
        return height;
    }

    public String getUrl() {
        return url;
    }

    public String getPhotographer() {
        return photographer;
    }

    public String getPhotographer_url() {
        return photographer_url;
    }

    public String getAvg_color() {
        return avg_color;
    }

    public Src getSrc() {
        return src;
    }

    public boolean isLiked() {
        return liked;
    }

    public String getAlt() {
        return alt;
    }

    public boolean liked;
    public String alt;
}
