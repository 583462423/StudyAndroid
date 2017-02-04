package com.qxg.study.studyandroid.view.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qxg.study.studyandroid.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.simple_fm, R.id.alter_fm,R.id.alter_back_fm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.simple_fm:
                //简单用法
                startActivity(new Intent(this,SimpleFragmentActivity.class));
                break;
            case R.id.alter_fm:
                startActivity(new Intent(this,AlterFragmentActivity.class));
                break;
            case R.id.alter_back_fm:
                Intent intent = new Intent(this,AlterFragmentActivity.class);
                intent.putExtra("flag",1);
                startActivity(intent);
                break;
        }
    }
}
