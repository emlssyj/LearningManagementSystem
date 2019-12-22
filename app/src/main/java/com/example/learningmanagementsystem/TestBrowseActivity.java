package com.example.learningmanagementsystem;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.lucasr.twowayview.widget.SpannableGridLayoutManager;
import org.lucasr.twowayview.widget.TwoWayView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestBrowseActivity extends AppCompatActivity {
    //获取的json数据
    public String date;
    Context context = this;
    public TwoWayView twoWayView;
    public List<String> list = new ArrayList<>();

    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSIONS_STORAGE = {

            Manifest.permission.READ_EXTERNAL_STORAGE,

            Manifest.permission.WRITE_EXTERNAL_STORAGE

    };



    /**

     * 在对sd卡进行读写操作之前调用这个方法

     * Checks if the app has permission to write to device storage

     * If the app does not has permission then the user will be prompted to grant permissions

     */

    public static void verifyStoragePermissions(Activity activity) {

        // Check if we have write permission

        int permission = ActivityCompat.checkSelfPermission(activity,

                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {

            // We don't have permission so prompt the user

            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,

                    REQUEST_EXTERNAL_STORAGE);

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_browse);
        twoWayView = (TwoWayView) findViewById(R.id.list);
        //获取数据
        okhttpDate();

        RadioButton search = findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestBrowseActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void okhttpDate() {
        Log.i("TAG", "--ok-");
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://10.0.2.2:8080/elearn/courses").build();
                try {
                    Response sponse = client.newCall(request).execute();
                    date = sponse.body().string();
                    //解析
                    jsonJXDate(date);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    private void jsonJXDate(String date) {
        if (date != null) {
            try {
                JSONArray array = new JSONArray(date);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jObject = array.getJSONObject(i);
                    String lecturer = jObject.getString("sharedUrl");
                    String name = jObject.getString("name");
                    String description = jObject.getString("description");
                    String avatar = jObject.getString("avatar");
                    String lecturerPic = jObject.getString("bigAvatar");
                    Course courses = new Course(lecturer, name, description, avatar, lecturerPic);

                    list.add(name);
                    list.add(lecturer);
                    list.add(lecturerPic);
                    list.add(description);
                    list.add(avatar);
                }
                list.add("     ");
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //添加分割线
                    SpannableGridLayoutManager manager = new SpannableGridLayoutManager(TestBrowseActivity.this);
                    twoWayView.setLayoutManager(manager);

                    TwoWayViewAdapter adapter = new TwoWayViewAdapter(TestBrowseActivity.this, list);
                    twoWayView.setAdapter(adapter);
                    System.out.println(list);

                    adapter.setOnItemClickListener(new MyOnItemClickListener() {
                        @Override
                        public void OnItemClickListener(View view, int position) {
                            if(position%5==4) {
                                Intent it = new Intent(TestBrowseActivity.this, VideoActivity.class);
                                String name = list.get(position - 4);


                                Bundle bundle = new Bundle();
                                bundle.putString("name", name);


                                //Intent携带数据包
                                it.putExtras(bundle);
                                startActivity(it);

                                Toast.makeText(getApplicationContext(), list.get(position) + "被点击", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    break;


            }
        }
    };

}