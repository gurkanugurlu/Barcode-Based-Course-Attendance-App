package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.myapplication.model.PassChange;
import com.example.myapplication.model.Student;
import com.example.myapplication.model.api.JsonPlaceHolderApi;
import com.example.myapplication.model.api.RetrofitBringer;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {
    private TextView txtNo, txtAd, txtSoyad, txtPass;
    private Button btnPassword;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private Student student;
    private ProgressBar progressBar;
    private TextInputLayout textInputLayoutOldPass, textInputLayoutNewPass;
    private SharedPreferences sharedPreferences;

    private void initComponents(View view) {
        txtAd = view.findViewById(R.id.text_ogrenci_ad);
        txtNo = view.findViewById(R.id.text_ogrenci_no);
        txtSoyad = view.findViewById(R.id.text_ogrenci_soyad);
        //txtPass=view.findViewById(R.id.text_password); //gerek yok suan
        btnPassword = view.findViewById(R.id.button_change_password);
        jsonPlaceHolderApi = RetrofitBringer.bringRetrofit().create(JsonPlaceHolderApi.class);
        progressBar = view.findViewById(R.id.progress_bar_profile);
        textInputLayoutOldPass = view.findViewById(R.id.text_input_old_pass);
        textInputLayoutNewPass = view.findViewById(R.id.text_input_new_pass);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initComponents(view);
        progressBar.setVisibility(View.INVISIBLE);
        student = (Student) getActivity().getIntent().getSerializableExtra("student");
        setLayout(student);
        String currentPass = student.getStudent_password();
        registerEventHandlers(student);
        sharedPreferences = getActivity().getSharedPreferences("pref", MODE_PRIVATE);
        return view;

    }

    private void setLayout(Student student) {

        txtAd.setText(student.getStudent_name());
        String studentNo = String.valueOf(student.getStudent_id());
        txtNo.setText(studentNo);
        //txtPass.setText(student.getStudent_password());
        txtSoyad.setText(student.getStudent_surname());

    }

    private void registerEventHandlers(Student student) {
        btnPasswordEventHandler(student);

    }

    private void btnPasswordEventHandler(final Student student) {
        btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isOldPassTrue(student);
                if (isOldPassTrue(student)) {
                    progressBar.setVisibility(View.VISIBLE);
                    String newPass = textInputLayoutNewPass.getEditText().getText().toString();
                    PassChange passChange = new PassChange(student.getStudent_id(),newPass);
                    Call<PassChange> call = jsonPlaceHolderApi.changePassword(passChange);
                    call.enqueue(new Callback<PassChange>() {
                        @Override
                        public void onResponse(Call<PassChange> call, Response<PassChange> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getActivity(), "Şifreniz Değiştirildi", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                textInputLayoutNewPass.getEditText().setText("");
                                textInputLayoutOldPass.getEditText().setText("");

                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Yeniden Giriş Yapmanız Gerekiyor");
                                builder.setIcon(R.drawable.ic_warning);
                                builder.setMessage("Şifre Değiştirdiğiniz için yeniden giriş yapmanız gerekiyor");
                                builder.setPositiveButton("ÇIKIŞ", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putBoolean("loginState", false);
                                        editor.commit();
                                        getActivity().finish();
                                    }
                                });

                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();

                            }
                        }

                        @Override
                        public void onFailure(Call<PassChange> call, Throwable t) {
                            String code = t.getMessage();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }

            private boolean isOldPassTrue(Student student) {

                String oldPass = textInputLayoutOldPass.getEditText().getText().toString();
                String a = student.getStudent_password();
                if (oldPass.equals(a))
                    return true;
                else {
                    textInputLayoutOldPass.getEditText().setError("Sifreniz doğru degil");

                    return false;
                }
            }
        });
    }
}
