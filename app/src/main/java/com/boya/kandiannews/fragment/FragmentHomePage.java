package com.boya.kandiannews.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.boya.kandiannews.App;
import com.boya.kandiannews.R;
import com.boya.kandiannews.activity.ActivityNewsDetails;
import com.boya.kandiannews.adpter.NewsRecyclerViewAdapter;
import com.boya.kandiannews.diy_view.RecycleViewDivider;
import com.boya.kandiannews.diy_view.tablayout.TabLayout;
import com.boya.kandiannews.mvp.bean.NewsDetails;
import com.boya.kandiannews.mvp.bean.NewsList;
import com.boya.kandiannews.mvp.interfaceview.NewsInterface;
import com.boya.kandiannews.mvp.presenter.NewsPresenter;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.weavey.loading.lib.LoadingLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 首页
 */
public class FragmentHomePage extends AllFragment implements NewsInterface {


    @BindView(R.id.ll_bar)
    LinearLayout mLlBar;
    @BindView(R.id.fhp_TabLayout)
    TabLayout mFhpTabLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.smartrefreshlayout)
    SmartRefreshLayout mSmartrefreshlayout;
    @BindView(R.id.loadinglayout)
    LoadingLayout mLoadinglayout;
    private View view;
    private Unbinder unbinder;
    private NewsPresenter newsPresenter;
    //int limit, int page
    private int limit = 0, page = 10;
    private NewsRecyclerViewAdapter newsRecyclerViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        listener();
        newsPresenter = new NewsPresenter(this);//新闻列表
        newsPresenter.newsListData(limit = 0, page = 10);
        return view;
    }

    private void initView() {
        setPaddingTop(mLlBar);
        closeFontVariety();
        String[] arr = {"推荐", "教育", "科技", "娱乐", "体育", "健康", "军事", "旅游", "汽车", "房地产", "家居"};
        for (int i = 0; i < arr.length; i++) {
            mFhpTabLayout.addTab(mFhpTabLayout.newTab().setText(arr[i]));
        }
        MaterialHeader materialHeader = new MaterialHeader(getActivity());
        materialHeader.setColorSchemeColors(Color.parseColor("#E33638"), Color.parseColor("#ff5a6b"), Color.parseColor("#f77738"));
        mSmartrefreshlayout.setRefreshHeader(materialHeader);
        mSmartrefreshlayout.setRefreshFooter(new BallPulseFooter(getActivity()));
        //设置RecycleView
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 2, Color.parseColor("#E8E8E8")));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        newsRecyclerViewAdapter = new NewsRecyclerViewAdapter();
        mRecyclerView.setAdapter(newsRecyclerViewAdapter);


    }

    private void listener() {
        mFhpTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                newsRecyclerViewAdapter.deleteData();
                String tabStringh = mFhpTabLayout.getTabAt(mFhpTabLayout.getSelectedTabPosition()).getText().toString();
                if (tabStringh.equals("推荐")) {
                    newsPresenter.newsListData(limit = 0, page = 10);
                } else {//类目
                    newsPresenter.getNewsPlateData(tabStringh, limit = 0, page = 10);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mSmartrefreshlayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override//下拉刷新
            public void onRefresh(RefreshLayout refreshlayout) {
                newsRecyclerViewAdapter.deleteData();
                String tabStringh = mFhpTabLayout.getTabAt(mFhpTabLayout.getSelectedTabPosition()).getText().toString();
                if (tabStringh.equals("推荐")) {
                    newsPresenter.newsListData(limit = 0, page = 10);
                } else {//类目
                    newsPresenter.getNewsPlateData(tabStringh, limit = 0, page = 10);
                }

            }

            @Override//上拉加載
            public void onLoadmore(RefreshLayout refreshlayout) {
                String tabStringh = mFhpTabLayout.getTabAt(mFhpTabLayout.getSelectedTabPosition()).getText().toString();
                if (tabStringh.equals("推荐")) {
                    newsPresenter.newsListData(limit += 10, page += 10);
                } else {//类目
                    newsPresenter.getNewsPlateData(tabStringh, limit += 10, page += 10);
                }

            }
        });
        newsRecyclerViewAdapter.setOnItemClickListener(new NewsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItem(NewsList.DataBean rowsBean) {
                String tabStringh = mFhpTabLayout.getTabAt(mFhpTabLayout.getSelectedTabPosition()).getText().toString();
                if (tabStringh.equals("推荐")) {
                    newsPresenter.newsDetailsData(rowsBean.getFrom(), rowsBean.getId());
                } else {//类目
                    newsPresenter.newsDetailsData(tabStringh, rowsBean.getId());
                }


            }
        });
    }


    @Override
    public void newsList(List<NewsList.DataBean> rows) {
        newsRecyclerViewAdapter.addData(rows);
    }

    @Override
    public void newsPlate(List<NewsList.DataBean> rows) {
        newsRecyclerViewAdapter.addData(rows);
    }

    @Override
    public void newsDetails(NewsDetails.DataBean dataBean) {
        Intent intent = new Intent(getActivity(), ActivityNewsDetails.class);
        intent.putExtra("div", dataBean.getDiv());
        startActivity(intent);
    }

    @Override
    public void tost(String s) {
        App.tost(s, false);
    }

    @Override
    public void closeFinish() {
        mSmartrefreshlayout.finishRefresh(100);
        mSmartrefreshlayout.finishLoadmore(100);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
