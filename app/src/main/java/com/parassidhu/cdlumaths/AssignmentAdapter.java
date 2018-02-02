package com.parassidhu.cdlumaths;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.*;
import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {
    private ArrayList<ListItem> android_versions;
    private List<ListItem> listItems;
    private LayoutInflater layoutInflater;
    private Animation animationUp, animationDown;
    private SharedPreferences sharedPreferences;
    private Context context;
    private int newList[];
    private final int COUNTDOWN_RUNNING_TIME = 500;
    int i =0;

    public AssignmentAdapter(Context context, java.util.List<ListItem> android_versions,
                       Animation animationUp, Animation animationDown, int newList[]) {
        this.context = context;
        this.listItems = android_versions;
        this.animationDown = animationDown;
        this.animationUp = animationUp;
        this.newList = new int[6];
        this.newList = newList;
    }

    @Override
    public AssignmentAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.assign_row, viewGroup, false);
        return new ViewHolder(view);
    }

    public String getRandomColor(int a){
        String colors[] = {"#ef5350","#f44336","#ef5350"};
        return colors[a];
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        sharedPreferences = context.getSharedPreferences("Assignments", Context.MODE_PRIVATE);
        ListItem listItem = listItems.get(i);
        if (i%2==2)
            holder.itemView.setBackgroundColor(Color.parseColor(getRandomColor(0)));
        else if (i%2==1)
            holder.itemView.setBackgroundColor(Color.parseColor(getRandomColor(1)));
        else if (i%2==0)
            holder.itemView.setBackgroundColor(Color.parseColor(getRandomColor(2)));


        holder.tv_android.setText(listItem.getSubName());
        holder.contentLayout.setText(listItem.getContent());
        holder.expandit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.dot.isShown())
                    holder.dot.setVisibility(View.GONE);
                if (holder.contentLayout.isShown()) {
                    holder.contentLayout.startAnimation(animationUp);
                    CountDownTimer countDownTimerStatic = new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            holder.contentLayout.setVisibility(View.GONE);
                        }
                    };
                    countDownTimerStatic.start();
                } else {
                    holder.contentLayout.setVisibility(View.VISIBLE);
                    holder.contentLayout.startAnimation(animationDown);
                }
            }
        });
        if (newList!=null) {
            if (i == 0) {
                if (newList[0] == -1)
                    holder.dot.setVisibility(View.VISIBLE);
                else holder.dot.setVisibility(View.GONE);
            } else {
                if (newList[i] == i) {
                    holder.dot.setVisibility(View.VISIBLE);
                } else
                    holder.dot.setVisibility(View.GONE);
            }
        }
        else {
            holder.dot.setVisibility(View.GONE);
        }

    }
    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_android;
        private TextView contentLayout;
        RelativeLayout expandit;
        ImageView dot;
        public ViewHolder(View view) {
            super(view);
            tv_android = (TextView)view.findViewById(R.id.tv_android);
            contentLayout = (TextView) view.findViewById(R.id.content);
            expandit = (RelativeLayout)view.findViewById(R.id.expandit);
            dot = (ImageView)view.findViewById(R.id.dot);
        }
    }
}