package com.example.learningmanagementsystem;

public class UserLocalData {
    //模拟数据库
    String [] userArray={"user1:password1","user2:password2"};
    public UserLocalData() {
    }
    //查找用户
    public boolean findByUser(User user){
        if(user==null)
            throw  new NullPointerException("this user object is null");
        for (int i = 0; i <userArray.length; i++) {
            String  userStr=userArray[i];
            String [] userArray=userStr.split(":");
            String username=userArray[0];
            String password=userArray[1];
            if((user.userName.get()).equals(username)&& (user.password.get()).equals(password)){
                return true;
            }
        }
        return false;
    }
}