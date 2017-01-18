package com.qxg.study.studyandroid.view.Intent;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.qxg.study.studyandroid.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntentTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_test);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.xianshi, R.id.yinshi1, R.id.yinshi2, R.id.yinshi3, R.id.yinshi4,R.id.yinshi5,R.id.yinshi6
    ,R.id.web,R.id.search,R.id.dial,R.id.result})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xianshi:
                //显示调用
                startActivity(new Intent(IntentTest.this,TmpActivity.class));
                break;
            case R.id.yinshi1:
                Intent intent = new Intent("com.qxg.test.t1");
                startActivity(intent);
                break;
            case R.id.yinshi2:
                Intent intent2 = new Intent("com.qxg.test.t2");
                intent2.addCategory("com.qxg.category.t1");
                startActivity(intent2);
                break;
            case R.id.yinshi3:
                Intent intent3 = new Intent();
                intent3.addCategory("com.qxg.category.t1");
                startActivity(intent3);
                break;
            case R.id.yinshi4:
                Intent intent4 = new Intent("com.qxg.test.t1");
                intent4.addCategory("com.qxg.category.t1");
                intent4.addCategory("android.intent.category.DEFAULT");
                startActivity(intent4);
                break;
            case R.id.yinshi5:
                Intent intent5 = new Intent("com.qxg.test.t1");
                intent5.addCategory("com.qxg.category.t1");
                intent5.addCategory("android.intent.category.OTHER");
                startActivity(intent5);
                break;
            case R.id.yinshi6:
                Intent intent6 = new Intent("com.qxg.test.t1");
                intent6.addCategory("com.qxg.category.t1");
                intent6.addCategory("android.intent.category.DEFAULT");
                intent6.addCategory("android.intent.category.OTHER");
                startActivity(intent6);
                break;
            case R.id.dial:
                Uri uri = Uri.parse("tel:17862708780");
                Intent intentDial = new Intent(Intent.ACTION_DIAL,uri);
                startActivity(intentDial);
                break;
            case R.id.web:
                Uri uri2 = Uri.parse("http://www.baidu.com");
                Intent intentWeb = new Intent(Intent.ACTION_VIEW,uri2);
                startActivity(intentWeb);
                break;
            case R.id.search:
                Intent intentSearch = new Intent(Intent.ACTION_WEB_SEARCH);
                intentSearch.putExtra(SearchManager.QUERY,"搜索内容");
                startActivity(intentSearch);
                break;
            case R.id.result:
                Intent intentResult = new Intent(IntentTest.this,TmpActivity.class);
                intentResult.putExtra("data","1");
                startActivityForResult(intentResult,1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case 1:
                if(resultCode == 2){
                    Toast.makeText(IntentTest.this, "成功返回数据:" + data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
