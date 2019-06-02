package com.example.myapplication.model.api;

import com.example.myapplication.model.HistorySender;
import com.example.myapplication.model.Lesson;
import com.example.myapplication.model.LessonSender;
import com.example.myapplication.model.PassChange;
import com.example.myapplication.model.Rate;
import com.example.myapplication.model.Student;
import com.example.myapplication.model.StudentPackage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {
    @GET("api/deneme")
    Call<Student> getStudent();
    @POST("api/deneme/student")
    Call<Student> getStudents(@Body Student student);

    @GET("api/deneme/lesson/{id}")
    Call<List<LessonSender>> getStudentsList(@Path("id") String id);

    @POST("api/deneme/history/{id}")
    Call<List<HistorySender>> getHistory(@Path("id")String id);

    @POST("api/deneme/barcode")
    Call<StudentPackage> readBarcode(@Body StudentPackage studentPackage);

    @PUT("api/deneme")
    Call<PassChange> changePassword(@Body PassChange passChange);

    @POST("api/deneme/rate")
    Call<Rate> rate(@Body Rate rate);
}
