package com.parassidhu.cdlumaths.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parassidhu.cdlumaths.R;
import com.parassidhu.cdlumaths.activities.SummaryResult;
import com.parassidhu.cdlumaths.models.GetSetGo;

import java.util.ArrayList;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.ViewHolder> {
    int i =0;

    Context context;
    private LayoutInflater inflater;
    SummaryResult myResult;
    private ArrayList<GetSetGo> mListe;

    public SummaryAdapter(Context context,ArrayList<GetSetGo> abc,SummaryResult myResult) {
        this.myResult=myResult;
        this.inflater = LayoutInflater.from(context);
        this.mListe = abc;
        this.context = context;
    }

    public long getItemId(int position) {
        return (long) position;
    }
    @Override
    public SummaryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_summary, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tv_android.setText(mListe.get(i).getValue());
        viewHolder.subname.setText(mListe.get(i).getKey());
    }
    @Override
    public int getItemCount() {
        return mListe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_android;
        TextView subname;
        public ViewHolder(View view) {
            super(view);
            tv_android = view.findViewById(R.id.tv_android);
            subname = view.findViewById(R.id.subname);
        }
    }
}