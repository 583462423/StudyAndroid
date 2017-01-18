package com.qxg.study.studyandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qxg.study.studyandroid.R;
import com.qxg.study.studyandroid.view.ReadMdActivity;

import java.util.ArrayList;

/**
 * Created by qxg on 17-1-16.
 */

public class MdAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<String> mds;
    Context context;

    public MdAdapter(String[] mds,Context context){
        this.mds = new ArrayList<String>();
        this.context = context;
        for (String now :
                mds) {
            if(now.endsWith(".md")){
                this.mds.add(now.substring(0,now.length()-3));
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_md,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final String name = mds.get(position);
        ((Holder)holder).name.setText(name);
        ((Holder)holder).name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((Holder)holder).layout.getVisibility() == View.VISIBLE){
                    ((Holder)holder).layout.setVisibility(View.GONE);
                }else{
                    ((Holder)holder).layout.setVisibility(View.VISIBLE);
                }
            }
        });

        ((Holder)holder).md.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReadMdActivity.class);
                intent.putExtra("name",name + ".md");
                context.startActivity(intent);
            }
        });
        ((Holder)holder).test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过反射机制找到测试对象，注意测试对象名字必须是XXXXTest这种样式

                String className = context.getPackageName() + ".view." + name + "." + name + "Test";
                Log.i("ClassName",className);
                try {
                    Intent intent = new Intent(context,Class.forName(className));
                    context.startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mds.size();
    }

    private static class Holder extends RecyclerView.ViewHolder {

        public TextView name;
        public View layout;
        public TextView md;
        public TextView test;
        public Holder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            layout = itemView.findViewById(R.id.layout);
            md = (TextView) itemView.findViewById(R.id.md);
            test = (TextView) itemView.findViewById(R.id.test);
        }
    }
}
