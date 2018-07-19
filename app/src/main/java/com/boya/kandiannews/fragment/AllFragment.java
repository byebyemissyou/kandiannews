package com.boya.kandiannews.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

import com.blankj.utilcode.util.BarUtils;

public class AllFragment extends Fragment {
    //开启  状态栏字体颜色随背景颜色变化
    public void openFontVariety() {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    //关闭  状态栏字体颜色随背景颜色变化
    public void closeFontVariety() {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    public void setPaddingTop(View view) {
        view.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0);
    }
}
