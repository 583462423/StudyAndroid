package com.qxg.study.studyandroid.view;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mittsu.markedview.MarkedView;
import com.qxg.study.studyandroid.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReadMdActivity extends AppCompatActivity {


//    @BindView(R.id.md)
//    MarkdownPreviewView md;
    @BindView(R.id.md)
    MarkedView md;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_md);

        ButterKnife.bind(this);
        String name = getIntent().getStringExtra("name");

        AssetManager as = getAssets();
        try {
            InputStream is = as.open(name);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder buffer = new StringBuilder("");
            String str = "";
            while ((str = br.readLine()) != null) {
                buffer.append(str);
                buffer.append("\n");
            }

            //给md控件传入待解析内容
            //md.setMdUrl(buffer.toString(),true);

            md.setMDText(buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
