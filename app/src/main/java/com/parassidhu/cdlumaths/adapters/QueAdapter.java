package com.parassidhu.cdlumaths.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parassidhu.cdlumaths.R;

import java.util.ArrayList;

public class QueAdapter extends RecyclerView.Adapter<QueAdapter.ViewHolder> {
    private ArrayList<String> names;
    private Context context;

    public QueAdapter(Context context, ArrayList<String> names) {
        this.context = context;
        this.names = names;
    }

    @Override
    public QueAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_que, viewGroup, false);
        return new ViewHolder(view);
    }

    public int getRandomColor(int a){
        int colors[] = {R.drawable.grad_red,R.drawable.grad_blue};
        return colors[a];
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        viewHolder.tv_android.setText(names.get(i));
        viewHolder.itemView.setBackgroundResource(getRandomColor(i%2));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_android;
        public ViewHolder(View view) {
            super(view);
            tv_android = view.findViewById(R.id.tv_android);
        }
    }
}