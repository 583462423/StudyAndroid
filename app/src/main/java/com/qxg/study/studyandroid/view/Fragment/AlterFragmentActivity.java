package com.qxg.study.studyandroid.view.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.qxg.study.studyandroid.R;
import com.qxg.study.studyandroid.fragments.AnotherFragment;
import com.qxg.study.studyandroid.fragments.SimpleFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlterFragmentActivity extends AppCompatActivity {

    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_fragment);
        ButterKnife.bind(this);
        replaceFragment(new SimpleFragment());

        flag = getIntent().getIntExtra("flag",0);
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.framelayout,fragment);
        if(flag == 1){
            ft.addToBackStack(null);
        }
        ft.commit();
    }



    @OnClick(R.id.alter_fragment)
    public void onClick() {
        replaceFragment(new AnotherFragment());
    }
}
