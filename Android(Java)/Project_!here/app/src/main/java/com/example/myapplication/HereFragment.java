package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.barcode_classes.BarcodeCaptureActivity;
import com.example.myapplication.model.Student;
import com.example.myapplication.model.StudentPackage;
import com.example.myapplication.model.api.JsonPlaceHolderApi;
import com.example.myapplication.model.api.RetrofitBringer;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HereFragment extends Fragment {

    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";
    private ImageButton imageButton;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private Student student;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        student = (Student) getActivity().getIntent().getSerializableExtra("student");

        View view = inflater.inflate(R.layout.fragment_here, container, false);
        imageButton = view.findViewById(R.id.button_here);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), BarcodeCaptureActivity.class);
                startActivityForResult(intent, RC_BARCODE_CAPTURE);
            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    int type = barcode.valueFormat;


                    switch (type) {
                        case Barcode.CONTACT_INFO:
                            Log.i(TAG, barcode.contactInfo.title);
                            break;
                        case Barcode.EMAIL:
                            Log.i(TAG, barcode.email.address);
                            break;
                        case Barcode.ISBN:
                            Log.i(TAG, barcode.rawValue);
                            break;
                        case Barcode.PHONE:
                            Log.i(TAG, barcode.phone.number);
                            break;
                        case Barcode.PRODUCT:
                            Log.i(TAG, barcode.rawValue);
                            break;
                        case Barcode.SMS:
                            Log.i(TAG, barcode.sms.message);
                            break;
                        case Barcode.TEXT: //:TODO BARCODE KARSILAMA !!
                            // Toast.makeText(getActivity(), barcode.rawValue, Toast.LENGTH_SHORT).show();
                            vibration();
                            Log.i(TAG, barcode.rawValue);
                            jsonPlaceHolderApi = RetrofitBringer.bringRetrofit().create(JsonPlaceHolderApi.class);
                            String content = barcode.rawValue;
                            StudentPackage studentPackage = new StudentPackage();
                            studentPackage.setStudent(student);
                            studentPackage.setContent(content);
                            Call<StudentPackage> call = jsonPlaceHolderApi.readBarcode(studentPackage);
                            call.enqueue(new Callback<StudentPackage>() {
                                @Override
                                public void onResponse(Call<StudentPackage> call, Response<StudentPackage> response) {
                                    StudentPackage s = response.body();
                                }

                                @Override
                                public void onFailure(Call<StudentPackage> call, Throwable t) {
                                    String a = t.getMessage();
                                }
                            });
                            break;
                        case Barcode.URL:
                            Log.i(TAG, "url: " + barcode.url.url);
                            Toast.makeText(getActivity(), "Gecersiz barcode", Toast.LENGTH_SHORT).show();
                            break;
                        case Barcode.WIFI:
                            Log.i(TAG, barcode.wifi.ssid);
                            Toast.makeText(getActivity(), "Gecersiz barcode", Toast.LENGTH_SHORT).show();
                            break;
                        case Barcode.GEO:
                            Log.i(TAG, barcode.geoPoint.lat + ":" + barcode.geoPoint.lng);
                            Toast.makeText(getActivity(), "Gecersiz barcode", Toast.LENGTH_SHORT).show();
                            break;
                        case Barcode.CALENDAR_EVENT:
                            Log.i(TAG, barcode.calendarEvent.description);
                            Toast.makeText(getActivity(), "Gecersiz barcode", Toast.LENGTH_SHORT).show();
                            break;
                        case Barcode.DRIVER_LICENSE:
                            Log.i(TAG, barcode.driverLicense.licenseNumber);
                            Toast.makeText(getActivity(), "Gecersiz barcode", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Log.i(TAG, barcode.rawValue);
                            Toast.makeText(getActivity(), "Gecersiz barcode", Toast.LENGTH_SHORT).show();
                            break;
                    }

                }
            }

        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

    private void vibration() {
        long[] pattern = new long[]{0L, 150, 500L};
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(getActivity().VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(pattern, -1);
        }
    }
}

