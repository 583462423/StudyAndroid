package com.qxg.study.studyandroid.view.Intent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.qxg.study.studyandroid.R;

public class TmpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmp);

        Toast.makeText(TmpActivity.this,"传递的数据是：" + getIntent().getStringExtra("data"),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("result","2");
        setResult(2,intent);
        super.onBackPressed();
    }
}
