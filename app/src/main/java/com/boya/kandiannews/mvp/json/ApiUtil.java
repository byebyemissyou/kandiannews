package com.boya.kandiannews.mvp.json;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.boya.kandiannews.App;
import com.boya.kandiannews.mvp.interfaceview.IsNetworkInterface;
import com.google.gson.JsonObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;

import okhttp3.MediaType;

/**
 * Created by 管理员1 on 2017/9/23.
 */

public class ApiUtil {
    private static ApiUtil ApiUtil;

    private StringBuilder stringBuilder;
    private String encryptMD5ToString;
    private JsonObject jsonObject = new JsonObject();
    private HashMap<String, String> params;


    private ApiUtil() {

    }

    public static ApiUtil getApiUtil() {
        if (ApiUtil == null) {
            synchronized (ApiUtil.class) {
                ApiUtil = new ApiUtil();
            }
        }

        return ApiUtil;
    }

    /**
     * 获取设备信息
     * TODO检查权限
     *
     * @return
     */
    public String getDeviceInformationMD5() {
        if (encryptMD5ToString == null) {
            stringBuilder = new StringBuilder();
            //获取App信息
            for (AppUtils.AppInfo appInfo : AppUtils.getAppsInfo()) {
                Log.d("ApiUtil", appInfo.getName());
                stringBuilder.append(appInfo.getName());
            }
            stringBuilder.append(DeviceUtils.getSDKVersionCode());
//        stringBuilder.append(DeviceUtils.getAndroidID());
//        stringBuilder.append(DeviceUtils.getMacAddress());
//        stringBuilder.append(DeviceUtils.getManufacturer());
//        stringBuilder.append(DeviceUtils.getModel());
//        stringBuilder.append(PhoneUtils.getPhoneType());
            encryptMD5ToString = EncryptUtils.encryptMD5ToString(stringBuilder.toString());
            return encryptMD5ToString;
        }
        return encryptMD5ToString;
    }

    /**
     * 读取application 节点  meta-data 信息
     */
    public String readMetaDataFromApplication() {
        ApplicationInfo appInfo = null;
        try {
            appInfo = App.applicationContext.getPackageManager()
                    .getApplicationInfo(App.applicationContext.getPackageName(),
                            PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String mTag = appInfo.metaData.getString("UMENG_CHANNEL");
        return mTag;


    }

    /**
     * 获取接口加密参数
     *
     * @param key
     * @return
     */
    private String[] getSign(String key) {
        String currentTimeMillisString = String.valueOf(System.currentTimeMillis());
        String singMD5 = EncryptUtils.encryptMD5ToString(EncryptUtils.encryptMD5ToString(currentTimeMillisString) + key);
        String[] strings = {currentTimeMillisString, singMD5};
        return strings;
    }

    /**
     * 下载
     *
     * @param appUrl 下载地址
     */
    public void download(String appUrl, IsNetworkInterface isNetworkInterface, Callback callback) {
        if (IsNetwork(isNetworkInterface)) return;
        OkHttpUtils//
                .get()//
                .url(appUrl)//
                .build()//
                .execute(callback);
    }

    /**
     * 获取首页新闻列表
     *
     * @param isNetworkInterface
     * @param callback           //GET_NEWS_PLATE
     */

    public void getNewsList(int limit, int page, IsNetworkInterface isNetworkInterface, Callback callback) {
        if (IsNetwork(isNetworkInterface)) return;
        String[] dateSign = getSign(API.GET_NEWS_LIST_KEY);
        jsonObject = new JsonObject();
        jsonObject.addProperty("timestamp", dateSign[0]);
        jsonObject.addProperty("sign", dateSign[1]);
        jsonObject.addProperty("page", page);//页数
        jsonObject.addProperty("limit", limit);//条数
        request(API.GET_NEWS_LIST, jsonObject.toString(), callback);
    }


    public void getLoginData(String login,String password,IsNetworkInterface isNetworkInterface,Callback callback){
        if (IsNetwork(isNetworkInterface))return;
        jsonObject = new JsonObject();
        jsonObject.addProperty("login",login);
        jsonObject.addProperty("password",password);
        request(API.GET_LOGIN,jsonObject.toString(),callback);
    }

    /**
     * 获取类目新闻列表
     *
     * @param navigation
     * @param limit
     * @param page
     * @param isNetworkInterface
     * @param callback
     */
    public void getNewsPlate(String navigation, int limit, int page, IsNetworkInterface isNetworkInterface, Callback callback) {
        if (IsNetwork(isNetworkInterface)) return;
        String[] dateSign = getSign(API.GET_NEWS_PLATE_KEY);
        jsonObject = new JsonObject();
        jsonObject.addProperty("timestamp", dateSign[0]);
        jsonObject.addProperty("sign", dateSign[1]);
        jsonObject.addProperty("navigation", navigation); //类目
        jsonObject.addProperty("page", page);//页数
        jsonObject.addProperty("limit", limit);//条数
        request(API.GET_NEWS_PLATE, jsonObject.toString(), callback);
    }

    public void getNewsDetails(String navigation, String id, IsNetworkInterface isNetworkInterface, Callback callback) {
        if (IsNetwork(isNetworkInterface)) return;
        String[] dateSign = getSign(API.GET_NEWS_DETAILS_KEY);
        jsonObject = new JsonObject();
        jsonObject.addProperty("timestamp", dateSign[0]);
        jsonObject.addProperty("sign", dateSign[1]);
        jsonObject.addProperty("navigation", navigation); //类目
        jsonObject.addProperty("id", id);
        request(API.GET_NEWS_DETAILS, jsonObject.toString(), callback);
    }

    /**
     * 是否有网
     *
     * @param isNetworkInterface
     * @return
     */
    private boolean IsNetwork(IsNetworkInterface isNetworkInterface) {
        if (!NetworkUtils.isConnected()) {
            isNetworkInterface.NoNetwork();
            return true;
        }
        return false;
    }

    //请求
    private void request(String url, String content, Callback callback) {
        OkHttpUtils
                .postString()
                .url(url)
                .content(content)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(callback);
    }
}
