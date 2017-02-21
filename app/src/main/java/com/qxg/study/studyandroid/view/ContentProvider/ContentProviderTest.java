package com.qxg.study.studyandroid.view.ContentProvider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qxg.study.studyandroid.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContentProviderTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider_test);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.get_contact, R.id.get_cat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_contact:
                startActivity(new Intent(ContentProviderTest.this,ContactsActivity.class));
                break;
            case R.id.get_cat:
                startActivity(new Intent(ContentProviderTest.this,CatActivity.class));
                break;
        }
    }
}
