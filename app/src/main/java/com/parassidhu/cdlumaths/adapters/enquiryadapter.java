package com.parassidhu.cdlumaths.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parassidhu.cdlumaths.models.GetSetGo;
import com.parassidhu.cdlumaths.activities.MyResult;
import com.parassidhu.cdlumaths.R;

import java.util.ArrayList;

public class enquiryadapter extends RecyclerView.Adapter<enquiryadapter.ViewHolder> {
    int i =0;

    Context context;
    private LayoutInflater inflater;
    MyResult myResult;
    private ArrayList<GetSetGo> mListe;

    public enquiryadapter(Context context,ArrayList<GetSetGo> abc,MyResult myResult) {
        this.myResult=myResult;
        this.inflater = LayoutInflater.from(context);
        this.mListe = abc;
        this.context = context;
    }

    public long getItemId(int position) {
        return (long) position;
    }
    @Override
    public enquiryadapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_result, viewGroup, false);

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