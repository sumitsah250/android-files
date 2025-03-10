package com.boss.pustakbazzar;

import java.util.Date;

public class Book {
    private String id, title, author, price, imageUrl, userId;

    public Book() {id= String.valueOf(new Date().getTime());
    } // Required for Firebase

    public Book(String id, String title, String author, String price, String imageUrl, String userId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.imageUrl = imageUrl;
        this.userId = userId;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public String getUserId() { return userId; }
}
