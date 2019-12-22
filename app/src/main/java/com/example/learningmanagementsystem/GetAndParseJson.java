package com.example.learningmanagementsystem;

import android.os.Handler;
import android.os.Message;

import com.example.learningmanagementsystem.Course;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class GetAndParseJson {
    //你要訪問的地址
    private String url="http://10.0.2.2:8080/elearn/courses";
    public static final int PARSESUCCWSS=0x2016;
    private Handler handler;
    public GetAndParseJson(Handler handler) {
        this.handler=handler;
    }
    /**
     * 获取网络上的json
     */
    public void getJsonFromInternet () {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection conn=(HttpURLConnection) new URL(url).openConnection();
                    conn.setConnectTimeout(3000);
                    conn.setRequestMethod("GET");

                    if (conn.getResponseCode()==200) {
                        InputStream inputStream=conn.getInputStream();
                        List<Course> listCourse=parseJson(inputStream);
                        if (listCourse.size()>0) {
                            Message msg=new Message();
                            msg.what=PARSESUCCWSS;//通知UI线程Json解析完成
                            msg.obj=listCourse;//将解析出的数据传递给UI线程
                            handler.sendMessage(msg);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    /**
     * 解析json格式的输入流转换成List
     */
    protected List<Course> parseJson(InputStream inputStream)throws Exception {
        List<Course>listNews=new ArrayList<Course>();
        byte[]jsonBytes=convertIsToByteArray(inputStream);
        String json=new String(jsonBytes);
        try {
            JSONArray jsonArray=new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jObject=jsonArray.getJSONObject(i);
                String lecturer=jObject.getString("sharedUrl");
                String name=jObject.getString("name");
                String description = jObject.getString("description");
                String avatar = jObject.getString("avatar");
                String lecturerPic = jObject.getString("bigAvatar");
                Course news=new Course(lecturer, name, description,avatar,lecturerPic);
                listNews.add(news);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listNews;
    }
    /**
     * 将输入流转化成ByteArray
     */
    private byte[] convertIsToByteArray(InputStream inputStream) {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        byte buffer[]=new byte[1024];
        int length=0;
        try {
            while ((length=inputStream.read(buffer))!=-1) {
                baos.write(buffer, 0, length);
            }
            inputStream.close();
            baos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }
}
