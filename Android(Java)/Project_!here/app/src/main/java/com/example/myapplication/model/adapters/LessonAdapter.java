package com.example.myapplication.model.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Lesson;
import com.example.myapplication.model.LessonSender;

import java.util.List;

import static com.example.myapplication.R.color.primary;
import static com.example.myapplication.R.color.primary_light;

public class LessonAdapter  extends BaseAdapter {
    private Context context;
    private List<LessonSender> lessonArrayList;
    public LessonAdapter(Context context, List<LessonSender> lessonArrayList) {
        this.context = context;
        this.lessonArrayList = lessonArrayList;
    }
    @Override
    public int getCount() {
        return lessonArrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return lessonArrayList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LessonSender lesson = (LessonSender) getItem(position);
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        LinearLayout layout = (LinearLayout) layoutInflater.inflate(R.layout.list_view_lessons, null);
        TextView lblId=layout.findViewById(R.id.lbl1);
        TextView lblAd=layout.findViewById(R.id.lbl2);
        TextView lblBS=layout.findViewById(R.id.lbl3);
        //TextView lblBSA=layout.findViewById(R.id.lbl4);
        lblId.setText(lesson.getLesson_id().toString());
        lblAd.setText(lesson.getLesson_name());
        lblBS.setText(lesson.getLesson_onset_time()+" "+lesson.getLesson_expiration_time());
        Resources resources = context.getResources();
        lblAd.setTextColor(resources.getColor(primary));
        lblBS.setGravity(Gravity.RIGHT);
        parent.setBackgroundColor(resources.getColor(primary_light));
        //lblBSA.setText(lesson.getLesson_expiration_time());
        return layout;
    }
}
