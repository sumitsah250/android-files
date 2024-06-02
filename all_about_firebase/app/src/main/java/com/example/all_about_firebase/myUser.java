package com.example.all_about_firebase;

public class myUser {
    private String email;

    public myUser(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public myUser() {
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

    private String password;
}
