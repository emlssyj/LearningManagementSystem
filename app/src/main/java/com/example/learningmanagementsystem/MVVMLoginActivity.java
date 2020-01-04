package com.example.learningmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.learningmanagementsystem.databinding.ActivityMvvmloginBinding;

public class MVVMLoginActivity extends AppCompatActivity {
    UserViewModel userViewModel;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        ActivityMvvmloginBinding dataBindingUtil= DataBindingUtil.setContentView(MVVMLoginActivity.this, R.layout.activity_mvvmlogin);
        userViewModel=new UserViewModel(MVVMLoginActivity.this,dataBindingUtil);
        progressBar=findViewById(R.id.progressBar);

    }
    @Override
    protected void onStart() {
        super.onStart();
        userViewModel.init();
    }
    public void click(View view) {
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                userViewModel.login();
                progressBar.setVisibility(View.GONE);
                if(userViewModel.user.status.get()=="success"){
                    Toast.makeText(getApplicationContext(), userViewModel.user.userName.get()+",欢迎您！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MVVMLoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "用户名或密码错误！", Toast.LENGTH_SHORT).show();
                }
            }
        },20);
    }
}
