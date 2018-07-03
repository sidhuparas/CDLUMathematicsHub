package com.parassidhu.cdlumaths.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.models.TTItem;
import com.parassidhu.cdlumaths.utils.AppUtils;

import java.util.ArrayList;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.ViewHolder> {
    private ArrayList<com.parassidhu.cdlumaths.models.TTItem> TTItem;
    private Context context;

    public TimeTableAdapter(Context context,ArrayList<TTItem> android_versions) {
        this.context = context;
        this.TTItem = android_versions;
    }

    @Override
    public TimeTableAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_tt, viewGroup, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        TTItem currentItem = TTItem.get(i);
        viewHolder.now.setText(currentItem.getNow());
        viewHolder.tttime.setText(currentItem.getTime());
        viewHolder.subName.setText(currentItem.getSubName());
        viewHolder.teacherName.setText(currentItem.getTeacherName());

        AppUtils.setFont(context,viewHolder.subName,"graduate.ttf");
        AppUtils.setFont(context,viewHolder.teacherName,"graduate.ttf");

        switch (i) {
            case 0:
                viewHolder.itemView.setBackgroundResource(R.drawable.green);
                break;
            case 1:
                viewHolder.itemView.setBackgroundResource(R.drawable.blue);
                break;
            case 2:
                viewHolder.itemView.setBackgroundResource(R.drawable.nred);
                break;
            case 3:
                viewHolder.itemView.setBackgroundResource(R.drawable.norange);
                break;
            case 4:
                viewHolder.itemView.setBackgroundResource(R.drawable.npink);
                break;
        }

    }
    @Override
    public int getItemCount() {
        return TTItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView now,tttime,subName,teacherName;

        public ViewHolder(View view) {
            super(view);
            now = view.findViewById(R.id.now);
            tttime = view.findViewById(R.id.tttime);
            subName = view.findViewById(R.id.subjectName);
            teacherName = view.findViewById(R.id.teacherName);

        }
    }
}