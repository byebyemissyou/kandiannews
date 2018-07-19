package com.boya.kandiannews.activity.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.boya.kandiannews.R;
import com.boya.kandiannews.mvp.interfaceview.LoginInterface;

public class LoginActivity extends AppCompatActivity implements LoginInterface{
    @Override
    public void loginState(String state) {
        if (state.equals("1")){
            //登录失败
        }else if (state.equals("0")){
            //登录成功
        }
    }

    @Override
    public void userInfo(String info) {
        //登录成功返回用户信息并操作(传递给下一个页面等)
        tost("登录成功：用户个人信息为"+info);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    @Override
    public void tost(String s) {



    }

    @Override
    public void closeFinish() {

    }
}
