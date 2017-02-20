package com.qxg.study.studyandroid.view.Process;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.qxg.study.studyandroid.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProcessTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.call)
    public void onClick() {
        call();
    }

    private void call() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:10086"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //如果没有授予该权限，就申请权限
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE)){
                Toast.makeText(this,"解释权限",Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(ProcessTest.this,new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.CAMERA},1);
        }else{
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            Log.i("---->权限返回结果",grantResult + ".");
        }
        switch(requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //权限已经授予
                    call();
                }else{
                    //权限被拒绝
                    Toast.makeText(this,"权限被拒绝",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
