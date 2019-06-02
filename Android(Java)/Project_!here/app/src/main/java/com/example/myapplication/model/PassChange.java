package com.example.myapplication.model;

public class PassChange {
    private Integer id;
    private String password;

    public PassChange() {
    }

    public PassChange(Integer id, String password) {
        this.id = id;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


