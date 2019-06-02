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
import com.example.myapplication.model.HistorySender;
import com.example.myapplication.model.Lesson;

import java.util.List;

import static com.example.myapplication.R.color.primary;
import static com.example.myapplication.R.color.primary_light;

public class HistoryAdapter extends BaseAdapter {
    private Context context;
    private List<HistorySender> HistorySenders;

    public HistoryAdapter(Context context, List<HistorySender> HistorySenders) {
        this.context = context;
        this.HistorySenders = HistorySenders;


    }

    @Override
    public int getCount() {
        return HistorySenders.size();
    }

    @Override
    public Object getItem(int position) {
        return HistorySenders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HistorySender historySender = (HistorySender) getItem(position);
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        LinearLayout layout = (LinearLayout) layoutInflater.inflate(R.layout.list_view_lessons, null);
        TextView lblId = layout.findViewById(R.id.lbl1);
        TextView lblAd = layout.findViewById(R.id.lbl2);
        TextView lblBS = layout.findViewById(R.id.lbl3);
        //TextView lblBSA = layout.findViewById(R.id.lbl4);
        lblId.setText(historySender.getId().toString());
        lblAd.setText(historySender.getLesson_name());
        lblBS.setText(historySender.getSession_onset_time());
        //lblBSA.setText(historySender.getSession_expiration_time());
        Resources resources = context.getResources();
        lblAd.setTextColor(resources.getColor(primary));
        lblBS.setGravity(Gravity.RIGHT);
        parent.setBackgroundColor(resources.getColor(primary_light));
        //convertView.setBackgroundColor(resources.getColor(R.color.accent));
        return layout;

    }
}
