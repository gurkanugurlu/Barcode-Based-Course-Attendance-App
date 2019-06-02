package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.model.HistorySender;
import com.example.myapplication.model.Lesson;
import com.example.myapplication.model.Student;
import com.example.myapplication.model.adapters.HistoryAdapter;
import com.example.myapplication.model.adapters.LessonAdapter;
import com.example.myapplication.model.api.JsonPlaceHolderApi;
import com.example.myapplication.model.api.RetrofitBringer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragment extends Fragment {
private JsonPlaceHolderApi jsonPlaceHolderApi;
    private ListView listView;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    private void initComponents(View view) {
        listView=view.findViewById(R.id.deneme2);
        progressBar=view.findViewById(R.id.progress_bar_history);
    }
    private void loadData(List<HistorySender> historySenders){
        HistoryAdapter adapter=new HistoryAdapter(getActivity(),historySenders);
        listView.setAdapter(adapter);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_history,container,false);
        initComponents(view);
        progressBar.setVisibility(View.VISIBLE);
        Student student=(Student) getActivity().getIntent().getSerializableExtra("student");
        jsonPlaceHolderApi= RetrofitBringer.bringRetrofit().create(JsonPlaceHolderApi.class);
        Call<List<HistorySender>> call=jsonPlaceHolderApi.getHistory(String.valueOf(student.getStudent_id()));
        call.enqueue(new Callback<List<HistorySender>>() {
            @Override
            public void onResponse(Call<List<HistorySender>> call, Response<List<HistorySender>> response) {
                progressBar.setVisibility(View.GONE);
                List<HistorySender> senders=response.body();
                loadData(senders);
            }

            @Override
            public void onFailure(Call<List<HistorySender>> call, Throwable t) {
            String hata=t.getMessage();
            }
        });
        return view;
    }

}
