package com.example.myapplication.model;

import java.io.Serializable;

public class StudentPackage implements Serializable {
    private Student student;
    private String  content;
    public  StudentPackage(){

    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
