package com.boss.modelclass;

public class myUsers {
    String uid;
    String username;
    String email;
    String password;
    String profileImage;

    public myUsers(String uid, String profileImage, String email, String username, String password) {
        this.uid = uid;
        this.profileImage = profileImage;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public myUsers(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public myUsers() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
