package com.boya.kandiannews.wxapi;


import android.content.Intent;
import android.os.Bundle;

import cn.jiguang.share.wechat.WeChatHandleActivity;

public class WXEntryActivity extends WeChatHandleActivity {

    // 注意： 如果复写了 onCreate 方法、onNewIntent 方法，那么必须调用父类方法，否则无法获取分享结果，例如：
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}
