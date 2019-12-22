package com.example.learningmanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class TestBUGActivity extends AppCompatActivity {
    //获取的json数据
    public String date;
    Context context = this;
    public RecyclerView View;
    public List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bug);
        View = (RecyclerView) findViewById(R.id.recyclerview);
        //获取数据
        okhttpDate();
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        RecycleAdapterDome adapterDome = new RecycleAdapterDome(context,list);
        View.setLayoutManager(manager);
        adapterDome.appendList(list);
        View.setAdapter(adapterDome);
        System.out.println(list);

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


                    list.add(name);
                    list.add(description);
                    list.add(avatar);
                    list.add(lecturer);
                    list.add(lecturerPic);
                }
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
                    LinearLayoutManager manager = new LinearLayoutManager(context);
                    manager.setOrientation(LinearLayoutManager.VERTICAL);
                    RecycleAdapterDome adapterDome = new RecycleAdapterDome(context,list);
                    View.setLayoutManager(manager);
                    adapterDome.appendList(list);
                    View.setAdapter(adapterDome);
                    break;


            }
        }
    };

}