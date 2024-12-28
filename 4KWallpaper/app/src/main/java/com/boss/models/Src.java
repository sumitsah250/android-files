package com.boss.models;

import java.io.Serializable;

public class Src implements Serializable {
    public String original;
    public String large2x;
    public String large;
    public String medium;
    public String small;

    public Src(String original, String large2x, String large, String medium, String small, String portrait, String landscape, String tiny) {
        this.original = original;
        this.large2x = large2x;
        this.large = large;
        this.medium = medium;
        this.small = small;
        this.portrait = portrait;
        this.landscape = landscape;
        this.tiny = tiny;
    }

    public String portrait;
    public String landscape;
    public String tiny;
}
