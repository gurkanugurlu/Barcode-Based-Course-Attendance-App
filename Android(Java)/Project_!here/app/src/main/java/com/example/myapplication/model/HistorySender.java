package com.example.myapplication.model;

public class HistorySender {
    private Integer id;
    private String lesson_name;
    private String session_onset_time;
    private String session_expiration_time;

    public HistorySender() {

    }


    public HistorySender(Integer id, String lesson_name, String session_onset_time, String session_expiration_time) {
        this.id = id;
        this.lesson_name = lesson_name;
        this.session_onset_time = session_onset_time;
        this.session_expiration_time = session_expiration_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLesson_name() {
        return lesson_name;
    }

    public void setLesson_name(String lesson_name) {
        this.lesson_name = lesson_name;
    }

    public String getSession_onset_time() {
        return session_onset_time;
    }

    public void setSession_onset_time(String session_onset_time) {
        this.session_onset_time = session_onset_time;
    }

    public String getSession_expiration_time() {
        return session_expiration_time;
    }

    public void setSession_expiration_time(String session_expiration_time) {
        this.session_expiration_time = session_expiration_time;
    }
}
