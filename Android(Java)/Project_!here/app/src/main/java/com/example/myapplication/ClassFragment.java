package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.Lesson;
import com.example.myapplication.model.LessonSender;
import com.example.myapplication.model.Student;
import com.example.myapplication.model.adapters.LessonAdapter;
import com.example.myapplication.model.api.JsonPlaceHolderApi;
import com.example.myapplication.model.api.RetrofitBringer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassFragment extends Fragment {
private TextView textView;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private Student student;
    private RecyclerView recyclerView;
    private ListView listView;
    private ProgressBar progressBar;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void initComponents(View view) {
       // textView=view.findViewById(R.id.txtGecici);

        listView=view.findViewById(R.id.deneme);
        progressBar=(ProgressBar) view.findViewById(R.id.progress_bar_class);
    }


    private void loadData(List<LessonSender> lessons){
        LessonAdapter adapter=new LessonAdapter(getActivity(),lessons);
        listView.setAdapter(adapter);



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_class,container,false);
        initComponents(view);
        Student student=(Student) getActivity().getIntent().getSerializableExtra("student");
        jsonPlaceHolderApi= RetrofitBringer.bringRetrofit().create(JsonPlaceHolderApi.class);

        Call<List<LessonSender>> call=jsonPlaceHolderApi.getStudentsList(String.valueOf(student.getStudent_id()));
        call.enqueue(new Callback<List<LessonSender>>() {
            @Override
            public void onResponse(Call<List<LessonSender>> call, Response<List<LessonSender>> response) {
                progressBar.setVisibility(View.GONE);
                int code=response.code();
                List<LessonSender> lessons=response.body();
                loadData(lessons);
            }

            @Override
            public void onFailure(Call<List<LessonSender>> call, Throwable t) {
                String a=t.getMessage();

            }
        });
        return view;
    }



}
