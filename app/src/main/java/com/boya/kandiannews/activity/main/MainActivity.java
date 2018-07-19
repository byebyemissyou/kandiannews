package com.boya.kandiannews.activity.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.boya.kandiannews.R;
import com.boya.kandiannews.fragment.FragmentHomePage;
import com.boya.kandiannews.fragment.FragmentInviter;
import com.boya.kandiannews.fragment.FragmentMakeMoney;
import com.boya.kandiannews.fragment.FragmentMy;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AllActivity {
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private List<String> mTitles = Arrays.asList("首页", "邀人", "赚钱", "我的");
    private TreeMap<Integer, WeakReference<Fragment>> weakReferenceArrayList = new TreeMap<>();
    private List<Integer> mIconSelectIds = Arrays.asList(R.mipmap.shouye_select, R.mipmap.yaoren_select, R.mipmap.makemoney_select, R.mipmap.wode_select);//选择
    private List<Integer> mIconUnselectIds = Arrays.asList(R.mipmap.shouye_unselect, R.mipmap.yaoren_unselect, R.mipmap.makemoney_unselect, R.mipmap.wode_unselect);//未选择
    private CommonTabLayout mCommontablayout;
    private FragmentManager fm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        listener();
        openStatusBarIsTransparentFontVariety();
    }

    private void initView() {
        mCommontablayout = (CommonTabLayout) findViewById(R.id.commontablayout);
        for (int i = 0; i < mTitles.size(); i++) {
            mTabEntities.add(new TabEntity(mTitles.get(i), mIconSelectIds.get(i), mIconUnselectIds.get(i)));
        }
        mCommontablayout.setTabData(mTabEntities);
        showFragment(0, new FragmentHomePage());


    }

    private void listener() {
        mCommontablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switch (mTitles.get(position)) {
                    case "首页":
                        showFragment(position, new FragmentHomePage());
                        break;
                    case "邀人":
                        showFragment(position, new FragmentInviter());
                        break;
                    case "赚钱":
                        showFragment(position, new FragmentMakeMoney());
                        break;
                    case "我的":
                        showFragment(position, new FragmentMy());
                        break;

                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    /**
     * 显示指定Fragment（其他隐藏）
     *
     * @param tag
     */
    public void showFragment(int tag, Fragment fragment) {
        //开启事务
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!weakReferenceArrayList.containsKey(tag)) {//查找是否有此标识的Fragment，没有则添加
            weakReferenceArrayList.put(tag, new WeakReference<Fragment>(fragment));
            transaction.add(R.id.ll_LinearLayout, weakReferenceArrayList.get(tag).get(), String.valueOf(tag));
        }
        //显示指定Fragment（其他隐藏）
        for (Map.Entry<Integer, WeakReference<Fragment>> entry : weakReferenceArrayList.entrySet()) {
            if (tag == entry.getKey()) {
                transaction.show(entry.getValue().get());
            } else
                transaction.hide(entry.getValue().get());
        }
        transaction.commit();
    }
}
