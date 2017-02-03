package com.qxg.study.studyandroid.view.RecyclerView;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.qxg.study.studyandroid.R;
import com.qxg.study.studyandroid.adapter.HeaderRVAdapter;
import com.qxg.study.studyandroid.adapter.RVAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimpleRVActivity extends AppCompatActivity {

    @BindView(R.id.simple_rv)
    RecyclerView simpleRv;

    private ArrayList<String> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_rv);
        ButterKnife.bind(this);

        initList();
        int flag = getIntent().getIntExtra("flag",0);
        solveFlag(flag); // 针对不同的flag做不同的RV的操作
    }

    private void initList(){
        lists = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            lists.add(i+"");
        }
    }

    private void solveFlag(int flag){
        switch(flag){
            case 0:
                //简单用法
                simpleRv.setAdapter(new RVAdapter(this,lists));
                simpleRv.setLayoutManager(new LinearLayoutManager(this));
                break;
            case 1:
                //横向滚动
                simpleRv.setAdapter(new RVAdapter(this,lists));
                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                simpleRv.setLayoutManager(layoutManager);
                break;
            case 2:
                simpleRv.setAdapter(new RVAdapter(this,lists));
                StaggeredGridLayoutManager sLayoutManager =
                        new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
                simpleRv.setLayoutManager(sLayoutManager);
                break;
            case 3:
                simpleRv.setAdapter(new HeaderRVAdapter(this,lists));
                simpleRv.setLayoutManager(new LinearLayoutManager(this));
                break;
            case 4:
                simpleRv.setLayoutManager(new GridLayoutManager(this,4));
                simpleRv.setAdapter(new HeaderRVAdapter(this,lists));
                break;

            case 5:
                simpleRv.setAdapter(new HeaderRVAdapter(this,lists));
                simpleRv.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
                break;

            case 6:
                //设置分割线
                simpleRv.setAdapter(new RVAdapter(this,lists));
                simpleRv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
                simpleRv.setLayoutManager(new LinearLayoutManager(this));
                break;
            case 7:
                //自定义分割线
                simpleRv.setAdapter(new RVAdapter(this,lists));
                simpleRv.addItemDecoration(new MyDecoration());
                simpleRv.setLayoutManager(new LinearLayoutManager(this));
                break;
        }
    }

    class MyDecoration extends RecyclerView.ItemDecoration{


        int height = 8;
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);

            //画线
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View now = parent.getChildAt(i);

                //获得child的布局信息
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) now.getLayoutParams();
                int top = now.getBottom() + params.bottomMargin;
                int bottom = top + height; //假设是2dp

                //开始画线
                Paint mPaint = new Paint();
                mPaint.setColor(Color.parseColor("#cccccc"));
                c.drawRect(left,top,right,bottom,mPaint);

                //如果要画drawable,可以通过drawable的setBounds来设置所画的位置，再通过drawable的draw方法，将drawable画出来就行
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                //画横线，就是往下偏移一个分割线的高度，而如果是画竖线，就是将宽度传给第三个参数，即：outRect.set(0,0,width,0);
                outRect.set(0, 0, 0, height);

        }
    }
}
