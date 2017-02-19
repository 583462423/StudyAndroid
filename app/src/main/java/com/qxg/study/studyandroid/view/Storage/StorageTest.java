package com.qxg.study.studyandroid.view.Storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.qxg.study.studyandroid.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StorageTest extends AppCompatActivity {

    @BindView(R.id.edit_text)
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_test);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.file_write, R.id.file_read,R.id.shread_pref_write,R.id.shread_pref_read})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.file_write:

                String data = editText.getText().toString();
                FileOutputStream out = null;
                BufferedWriter writer = null;
                try {
                    //通过openFileOutput来获取/data/data/<packageName>/tmp.txt文件流
                    out = openFileOutput("tmp.txt", Context.MODE_PRIVATE);
                    writer = new BufferedWriter(new OutputStreamWriter(out));
                    //使用writer.write()方法来写入数据
                    writer.write(data);

                    Toast.makeText(StorageTest.this,"写入成功",Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (writer != null) {
                            //关闭流
                            writer.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.file_read:
                StringBuilder outData = new StringBuilder();
                FileInputStream in = null;
                BufferedReader reader = null;
                try {
                    String line;
                    in = openFileInput("tmp.txt");
                    reader = new BufferedReader(new InputStreamReader(in));
                    while ((line = reader.readLine()) != null) {
                        outData.append(line);
                    }

                    Toast.makeText(StorageTest.this, "读取出的数据是:" + outData.toString(), Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                break;

            case R.id.shread_pref_write:
                SharedPreferences sp = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("tmp","123");
                editor.commit();

                Toast.makeText(this,"写入成功",Toast.LENGTH_SHORT).show();
                break;

            case R.id.shread_pref_read:
                SharedPreferences sp2 = getPreferences(MODE_PRIVATE);
                String value = sp2.getString("tmp","默认值");
                Toast.makeText(this,"取得数据是：" + value,Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
