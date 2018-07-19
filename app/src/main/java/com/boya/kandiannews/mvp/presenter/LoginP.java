package com.boya.kandiannews.mvp.presenter;

import android.util.Log;

import com.boya.kandiannews.App;
import com.boya.kandiannews.R;
import com.boya.kandiannews.mvp.bean.User;
import com.boya.kandiannews.mvp.interfaceview.IsNetworkInterface;
import com.boya.kandiannews.mvp.interfaceview.LoginInterface;
import com.boya.kandiannews.mvp.json.ApiUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class LoginP {
    private LoginInterface loginInterface;

    public LoginP(LoginInterface loginInterface) {
        this.loginInterface = loginInterface;
    }
    public void login(String login, String password){
        ApiUtil.getApiUtil().getLoginData(login, password, new IsNetworkInterface() {
            @Override
            public void NoNetwork() {

                loginInterface.tost(App.applicationContext.getString(R.string.noNetwork));
                loginInterface.closeFinish();

            }
        }, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                loginInterface.tost(App.applicationContext.getString(R.string.noData));
                loginInterface.closeFinish();

            }

            @Override
            public void onResponse(String response, int id) {
                Log.d("getLoginData", response);
                loginInterface.closeFinish();
                if (null!=response && !response.equals("")){
                    User user = new Gson().fromJson(response,User.class);
                    loginInterface.loginState(user.getData().getLoginState());
                    if (user.getData().getLoginState().equals("0")){

                        loginInterface.userInfo(user.getData().getUserInfo());

                    }


                }else {

                    loginInterface.tost(App.applicationContext.getString(R.string.noData));
                }


            }
        });

    }




}
