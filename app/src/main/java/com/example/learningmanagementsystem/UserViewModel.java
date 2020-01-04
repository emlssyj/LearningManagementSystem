package com.example.learningmanagementsystem;

import android.content.Context;
import android.util.Log;
import com.example.learningmanagementsystem.databinding.ActivityMvvmloginBinding;
public class UserViewModel {
    private Context context;
    private ActivityMvvmloginBinding activityMainBinding;
    public static final String TAG="KKKKKKK";
    public User user;
    public UserLocalData data;
    public UserViewModel(Context context, ActivityMvvmloginBinding activityMainBinding){
        this.context=context;
        this.activityMainBinding=activityMainBinding;
    }
    public void init(){
        this.user=new User();
        this.activityMainBinding.setViewModel(UserViewModel.this);
        data=new UserLocalData();
    }
    //登录
    public void login(){
        //业务逻辑
        Log.e(TAG,"status:"+user.status.get());
        Log.e(TAG,"username:"+user.userName.get());
        Log.e(TAG,"password:"+user.password.get());
        //权限的校验
        if (data.findByUser(this.user))
            this.user.status.set("success");
        else
            this.user.status.set("error");
    }
}