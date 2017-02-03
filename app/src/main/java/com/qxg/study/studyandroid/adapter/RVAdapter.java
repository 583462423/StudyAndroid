package com.qxg.study.studyandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.qxg.study.studyandroid.R;

import java.util.ArrayList;

/**
 * Created by qxg on 17-2-3.
 */

public class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<String> tmps;
    private Context mContext;

    public RVAdapter(Context context,ArrayList<String> tmps){
        mContext = context;
        this.tmps = tmps;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_simple_rv,parent,false);
        final MyHolder holder =  new MyHolder(view);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"点击了第" + holder.getAdapterPosition() + "个",Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyHolder)holder).textView.setText(tmps.get(position));
        ((MyHolder)holder).textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return tmps.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tmp);
        }
    }
}
