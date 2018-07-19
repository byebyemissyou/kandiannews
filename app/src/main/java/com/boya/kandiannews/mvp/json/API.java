package com.boya.kandiannews.mvp.json;

import android.util.Log;

import com.blankj.utilcode.util.EncodeUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/8/9.
 * 统一协议 http://112.74.58.83:6161  app.zhuankaixin.cn
 * <p>
 * 统一协议2 http://120.79.204.186:6161  test.zhuankaixin.cn
 * 用户模块的域名 http://user.app.zhuankaixin.cn：7878
 * 积分模块统一协议 http://interface.app.zhuankaixin.cn:8077
 * <p>
 * http://39.108.165.134:6161
 */

public class API {
    //public static final String QUDAONUMBER = "lizhuan000";//渠道号
    //统一协议
    public static final String URL_DEFAULT = "http://www.lizhuanwang.com:8089";//统一协议
    public static final String URL_DEFAULT2 = "http://www.chaohuizhuan.com:8023";//统一协议 http://www.chaohuizhuan.com:8023
    public static final String URL_DEFAULT_USER = "http://user.app.zhuankaixin.cn:7878";//用户模块统一协议
    public static final String URL_DEFAULT_INTEGRAL = "http://interface.app.zhuankaixin.cn:8077";//积分模块统一协议
    public static final String IMAGEURL_DEFAULT = "http://cdn.lizhuanwang.xyz/img/";//图片统一协议
    public static final String APKURL_DEFAULT = "http://cdn.lizhuanwang.xyz/jobapk/";//其他app统一协议


    public static final String GET_NEWS_LIST = URL_DEFAULT + "/news/get_news_list.do", GET_NEWS_LIST_KEY = "";//获取首页新闻列表  /news/get_news_list.do
    public static final String GET_NEWS_PLATE = URL_DEFAULT + "/news/get_news_plate.do", GET_NEWS_PLATE_KEY = "";//获取类目新闻列表  /news/get_news_plate.do
    public static final String GET_NEWS_DETAILS = URL_DEFAULT + "/news/get_news_details.do", GET_NEWS_DETAILS_KEY = "";//获取新闻详情  /news/get_news_details.do
    public static final String GET_LOGIN = URL_DEFAULT + "/news/get_news_details.do";

    public static void base64Encode() {
        List<String> asList = Arrays.asList("http://112.74.58.83:6161");
        for (int i = 0; i < asList.size(); i++) {
            String s = new String(EncodeUtils.base64Encode(asList.get(i)));//加密
            Log.d("API111", s);
        }
    }

    public static String base64Decode(String msg) {
        return new String(EncodeUtils.base64Decode(msg));//读取信息并解密
    }
}
