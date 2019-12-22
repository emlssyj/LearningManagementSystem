package com.example.learningmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

public class ClassMessageActivity extends AppCompatActivity {
String name;
Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_message);

        TextView tv1 = findViewById(R.id.textView);
        TextView tv2 = findViewById(R.id.textView2);
        ImageView iv = findViewById(R.id.imageView);
        TextView tv4 = findViewById(R.id.textView4);
        ImageView iv2 = findViewById(R.id.imageView2);
//使用Bundle接收数据
        bundle = getIntent().getExtras();
        name = bundle.getString("name");
        String text = bundle.getString("text");
        String lecturer = bundle.getString("lecturer");
        String lecturerPic = bundle.getString("lecturerPic");
        tv1.setText(name);
        tv2.setText(text);
        tv4.setText(lecturer);
        Glide.with(this).load("http://10.0.2.2:8080/elearn/materials/"+lecturerPic+"/file").into(iv);
        iv2.setImageResource(R.drawable.play);
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassMessageActivity.this,VideoActivity.class);


                intent.putExtras(bundle);
                System.out.println(bundle);
                startActivity(intent);
            }
        });
    }
}
