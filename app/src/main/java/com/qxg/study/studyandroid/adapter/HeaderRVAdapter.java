package com.qxg.study.studyandroid.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qxg.study.studyandroid.R;

import java.util.ArrayList;

/**
 * Created by qxg on 17-2-3.
 */

public class HeaderRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;

    private Context mContext;
    private ArrayList<String> lists;
    private boolean headerFLag = false;

    public HeaderRVAdapter(Context context, ArrayList<String> lists){
        mContext = context;
        this.lists = lists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == TYPE_HEADER){
            headerFLag = true;
            view = LayoutInflater.from(mContext).inflate(R.layout.header_view,parent,false);
        }else{
            headerFLag = false;
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_simple_rv,parent,false);
        }
        return new MyHolder(view);
    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if(lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(holder.getLayoutPosition() == 0);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_HEADER)return;
        ((MyHolder)holder).textView.setText(lists.get(position-1));//注意这里的实际位置应该要-1的
    }

    @Override
    public int getItemCount() {
        return lists.size() + 1;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyHolder(View itemView) {
            super(itemView);
            if(headerFLag)return;
            textView = (TextView) itemView.findViewById(R.id.tmp);
        }
    }
}
