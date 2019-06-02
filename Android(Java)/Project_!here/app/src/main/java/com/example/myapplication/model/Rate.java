package com.example.myapplication.model;

public class Rate {
    private Integer rate_star;

    public Rate() {
    }

    public Rate(Integer star) {
        this.rate_star = star;
    }
    public Integer getStar() {
        return rate_star;
    }

    public void setStar(Integer star) {
        this.rate_star = star;
    }
}
