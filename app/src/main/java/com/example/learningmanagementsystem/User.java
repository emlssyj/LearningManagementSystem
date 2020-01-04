package com.example.learningmanagementsystem;



import androidx.databinding.ObservableField;

//User模型
public class User {
    //用户名
    public ObservableField<String > userName=new ObservableField<>();
    //密码
    public ObservableField<String >password=new ObservableField<>();
    //反馈
    public ObservableField<String >status=new ObservableField<>();

    public User() {
    }
}