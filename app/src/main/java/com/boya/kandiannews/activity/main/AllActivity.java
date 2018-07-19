package com.boya.kandiannews.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.andsync.xpermission.XPermissionUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by Administrator on 2017/8/19.
 */

public class AllActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtils.setPortrait(this);//设置屏幕为竖屏
        // openStatusBarIsTransparentFontVariety();
    }

    //开起 1.全屏显示，但状态栏不会被隐藏覆盖 2. 状态栏字体颜色随背景颜色变化
    public void openStatusBarIsTransparentFontVariety() {
        ImmersionBar.with(this).init();
        //android6.0以后可以对状态栏文字颜色和图标进行修改
            /*
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN：Activity全屏显示，但状态栏不会被隐藏覆盖，
             状态栏依然可见，Activity顶端布局部分会被状态遮住
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR 状态栏字体颜色随背景颜色变化
             */
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    //开启  状态栏字体颜色随背景颜色变化
    public void openFontVariety() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    //关闭  状态栏字体颜色随背景颜色变化
    public void closeFontVariety() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    public void setPaddingTop(View view) {
        view.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
//    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        /**关闭所有avtivity（我这只有一个）**/
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


    /**
     * 权限回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        XPermissionUtils.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Glide.get(this).clearMemory();//清理Glide
    }
    //友盟统计
//    protected void onResume() {
//        super.onResume();
//        MobclickAgent.onResume(this);
//    }
//
//    protected void onPause() {
//        super.onPause();
//        MobclickAgent.onPause(this);
//    }

}
