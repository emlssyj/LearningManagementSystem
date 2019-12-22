package com.example.learningmanagementsystem;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lucasr.twowayview.widget.SpannableGridLayoutManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public String date;
    private RecyclerView recyclerView;//声明RecyclerView
    private RecycleAdapterDome adapterDome;//声明适配器
    private Context context;

    public List<String> list = new ArrayList<>();
    public List<String> message = new ArrayList<>();
    public List<String> lec = new ArrayList<>();
    public List<String> lecPic = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        okhttpDate();
        adapterDome = new RecycleAdapterDome(context,list);
        /*
        与ListView效果对应的可以通过LiManager来设置
        与GridView效果对应的可以通过GridLayoutManager来设nearLayout置
        与瀑布流对应的可以通过StaggeredGridLayoutManager来设置
        */
        //LinearLayoutManager man  ager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);





        RadioButton browse = findViewById(R.id.browse);

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,TestBrowseActivity.class);
                startActivity(intent);
            }
        });

        EditText et = findViewById(R.id.editText2);

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
                adapterDome.getFilter().filter(sequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
                    lec.add(lecturer);
                    lecPic.add(lecturerPic);
                    message.add(description);
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
                    adapterDome = new RecycleAdapterDome(context,list);
        /*
        与ListView效果对应的可以通过LiManager来设置
        与GridView效果对应的可以通过GridLayoutManager来设nearLayout置
        与瀑布流对应的可以通过StaggeredGridLayoutManager来设置
        */
                    //LinearLayoutManager man  ager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
                    LinearLayoutManager manager = new LinearLayoutManager(context);
                    manager.setOrientation(LinearLayoutManager.VERTICAL);

                    recyclerView.setLayoutManager(manager);
                    adapterDome.appendList(list);
                    recyclerView.setAdapter(adapterDome);
                    adapterDome.setOnItemClickListener(new MyOnItemClickListener() {
                        @Override
                        public void OnItemClickListener(View view, int position) {
                            Intent it = new Intent(MainActivity.this,ClassMessageActivity.class);
                            String name = list.get(position);
                            String text = message.get(position);
                            String l = lec.get(position);
                            String lp = lecPic.get(position);

                            Bundle bundle = new Bundle();
                            bundle.putString("name", name);
                            bundle.putString("text", text);
                            bundle.putString("lecturer",l);
                            bundle.putString("lecturerPic",lp);

                            //Intent携带数据包
                            it.putExtras(bundle);
                            startActivity(it);


                        }
                    });
                    break;


            }
        }
    };
}
