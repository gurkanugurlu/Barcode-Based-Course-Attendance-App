package com.example.myapplication.model;

import java.sql.Date;
import java.sql.Time;

public class Lesson {
    private Integer lesson_id;
    private String lesson_name;
    private String lesson_onset_time;
    private String lesson_expiration_time;

    public Lesson(){

    }

    public Lesson(Integer lesson_id, String lesson_name, String lesson_onset_time, String lesson_expiration_time) {
        this.lesson_id = lesson_id;
        this.lesson_name = lesson_name;
        this.lesson_onset_time = lesson_onset_time;
        this.lesson_expiration_time = lesson_expiration_time;
    }

    public String getLesson_name() {
        return lesson_name;
    }

    public void setLesson_name(String lesson_name) {
        this.lesson_name = lesson_name;
    }

    public String getLesson_onset_time() {
        return lesson_onset_time;
    }

    public void setLesson_onset_time(String lesson_onset_time) {
        this.lesson_onset_time = lesson_onset_time;
    }

    public String getLesson_expiration_time() {
        return lesson_expiration_time;
    }

    public void setLesson_expiration_time(String lesson_expiration_time) {
        this.lesson_expiration_time = lesson_expiration_time;
    }

    public Integer getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(Integer lesson_id) {
        this.lesson_id = lesson_id;
    }

    public String getLessonName() {
        return lesson_name;
    }

    public void setLessonName(String lesson_name) {
        this.lesson_name = lesson_name;
    }
}
