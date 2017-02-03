package com.qxg.study.studyandroid.view.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qxg.study.studyandroid.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecyclerViewTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_test);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.simple_rv, R.id.horizontal,R.id.staggered,R.id.header_linear,R.id.header_grid,R.id.header_staggered,R.id.decoration,
    R.id.my_decoration})
    public void onClick(View view) {
        Intent intent = new Intent(RecyclerViewTest.this,SimpleRVActivity.class);
        switch (view.getId()) {
            case R.id.simple_rv:
                //RV的简单用法
                intent.putExtra("flag",0);
                startActivity(intent);
                break;
            case R.id.horizontal:
                intent.putExtra("flag",1);
                startActivity(intent);
                break;
            case R.id.staggered:
                intent.putExtra("flag",2);
                startActivity(intent);
                break;
            case R.id.header_linear:
                intent.putExtra("flag",3);
                startActivity(intent);
                break;
            case R.id.header_grid:
                intent.putExtra("flag",4);
                startActivity(intent);
                break;

            case R.id.header_staggered:
                intent.putExtra("flag",5);
                startActivity(intent);
                break;

            case R.id.decoration:
                intent.putExtra("flag",6);
                startActivity(intent);
                break;
            case R.id.my_decoration:
                intent.putExtra("flag",7);
                startActivity(intent);
                break;
        }
    }
}
