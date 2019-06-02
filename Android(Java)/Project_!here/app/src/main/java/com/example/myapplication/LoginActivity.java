package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myapplication.model.Student;
import com.example.myapplication.model.api.JsonPlaceHolderApi;
import com.example.myapplication.model.api.RetrofitBringer;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button buttonLogin;
    CheckBox checkBoxAcc;
    TextInputLayout textInputLayoutUsername, textInputLayoutPassword;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private Student myStudent;
    private SharedPreferences sharedPreferences;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
        initComponents();
        setLastTextInputs();
        registerEventHandler();
        setAccountLogin();
        progressBar.setVisibility(View.INVISIBLE);


    }

    private void setAccountLogin() {
        boolean state = sharedPreferences.getBoolean("loginState", false);
        if (state) {
            Student studentLogin = new Student();
            studentLogin.setStudent_id(sharedPreferences.getInt("studentLoginId", 0));
            studentLogin.setStudent_name(sharedPreferences.getString("studentLoginName", ""));
            studentLogin.setStudent_surname(sharedPreferences.getString("studentLoginSurname", ""));
            studentLogin.setStudent_password(sharedPreferences.getString("StudentLoginPass", ""));
            Intent intentLogin = new Intent(LoginActivity.this, MainActivity.class);
            intentLogin.putExtra("student", studentLogin);
            startActivity(intentLogin);
            finish();
        }
    }

    private void setLastTextInputs() {
        Integer username = sharedPreferences.getInt("lastUserId", 0);
        if(username==0){
            textInputLayoutUsername.getEditText().setText("");
        }
        textInputLayoutUsername.getEditText().setText(username.toString());
    }


    private void registerEventHandler() {

        buttonLoginEventHandler();
    }

    private void buttonLoginEventHandler() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                final int student_id = Integer.parseInt(textInputLayoutUsername.getEditText().getText().toString());
                String password = textInputLayoutPassword.getEditText().getText().toString();
                Student student = new Student(student_id, password);
                removeKeyboards();

                Call<Student> call = jsonPlaceHolderApi.getStudents(student);
                call.enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> call, Response<Student> response) {
                        if (response.isSuccessful()) {
                            Student student1 = response.body();
                            myStudent = student1;
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("student", myStudent);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            Integer lastUserId = student1.getStudent_id();
                            editor.putInt("lastUserId", lastUserId);
                            if (checkBoxAcc.isChecked()) {
                                editor.putBoolean("loginState", true);
                                ///TODO SON KULLLANICININ STUDENT NESNESINI SAKLAMA
                                editor.putInt("studentLoginId", student1.getStudent_id());
                                editor.putString("studentLoginName", student1.getStudent_name());
                                editor.putString("studentLoginSurname", student1.getStudent_surname());
                                editor.putString("studentLoginPass", student1.getStudent_password());


                            } else {
                                editor.putBoolean("loginState", false);
                            }
                            editor.apply();
                            startActivity(intent);
                            finish();
                        } else {
                            int a = response.code();
                            String donus="";
                            if(a==400){
                                donus="Kullanici bulunamadi";
                            }
                            textInputLayoutPassword.setError(donus);
                            progressBar.setVisibility(View.GONE);

                        }
                    }

                    @Override
                    public void onFailure(Call<Student> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });


            }

            private void removeKeyboards() {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(textInputLayoutUsername.getEditText().getWindowToken(), 0);
                imm.hideSoftInputFromWindow(textInputLayoutPassword.getEditText().getWindowToken(), 0);
            }
        });

    }


    private void initComponents() {

        jsonPlaceHolderApi = RetrofitBringer.bringRetrofit().create(JsonPlaceHolderApi.class);
        buttonLogin = findViewById(R.id.button_login);
        textInputLayoutUsername = findViewById(R.id.text_input_username);
        textInputLayoutPassword = findViewById(R.id.text_input_password);
        checkBoxAcc = findViewById(R.id.check_box_acc);
        progressBar = findViewById(R.id.progress_bar_login);


    }
}
