package com.qxg.study.studyandroid.view.ContentProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.qxg.study.studyandroid.R;
import com.qxg.study.studyandroid.adapter.RVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactsActivity extends AppCompatActivity {

    @BindView(R.id.contacts)
    RecyclerView contacts;
    RecyclerView.Adapter adapter;
    List<String> contactList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);


        contactList = new ArrayList<>();
        contacts.setAdapter(adapter = new RVAdapter(this,contactList));
        contacts.setLayoutManager(new LinearLayoutManager(this));

        //首先要判断是否申请了权限
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            //如果没有申请，就要申请权限
            //如果有必要，可以ActivityCompat.shouldShowRequestPermissionRationale()来解释为何要获取该权限
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
        }else{
            readContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //如果授权了
                    readContacts();
                }else{
                    //未授权
                    Toast.makeText(this, "授权失败，读取联系人失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void readContacts(){
        Cursor cursor =
                getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        if(cursor != null){
            while(cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactList.add(name + ":" + number);
                adapter.notifyDataSetChanged();
            }
            cursor.close();
        }
    }

}
