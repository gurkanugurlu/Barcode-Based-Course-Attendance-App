package com.example.myapplication.model;

import java.io.Serializable;

public class Student implements Serializable {
    private Integer student_id;
    private String student_name;
    private String student_surname;
    private String student_password;

    public Student() {

    }

    public Student(Integer student_id, String student_name, String student_surname, String student_password) {
        this.student_id = student_id;
        this.student_name = student_name;
        this.student_surname = student_surname;
        this.student_password = student_password;
    }

    public Student(Integer student_id, String student_password) {
        this.student_id = student_id;
        this.student_password = student_password;
    }

    public Integer getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_surname() {
        return student_surname;
    }

    public void setStudent_surname(String student_surname) {
        this.student_surname = student_surname;
    }

    public String getStudent_password() {
        return student_password;
    }

    public void setStudent_password(String student_password) {
        this.student_password = student_password;
    }
}
