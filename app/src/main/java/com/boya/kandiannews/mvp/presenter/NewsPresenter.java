package com.boya.kandiannews.mvp.presenter;

import android.util.Log;

import com.boya.kandiannews.App;
import com.boya.kandiannews.R;
import com.boya.kandiannews.mvp.bean.NewsDetails;
import com.boya.kandiannews.mvp.bean.NewsList;
import com.boya.kandiannews.mvp.interfaceview.IsNetworkInterface;
import com.boya.kandiannews.mvp.interfaceview.NewsInterface;
import com.boya.kandiannews.mvp.json.ApiUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * 新闻类目丶列表
 */
public class NewsPresenter {
    private NewsInterface newsInterface;

    public NewsPresenter(NewsInterface newsInterface) {
        this.newsInterface = newsInterface;
    }

    /**
     * 获取首页新闻列表
     */
    public void newsListData(int limit, int page) {
        ApiUtil.getApiUtil().getNewsList(limit, page, new IsNetworkInterface() {
            @Override
            public void NoNetwork() {
                newsInterface.tost(App.applicationContext.getString(R.string.noNetwork));
                newsInterface.closeFinish();
            }
        }, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                newsInterface.tost(App.applicationContext.getString(R.string.noData));
                newsInterface.closeFinish();
            }

            @Override
            public void onResponse(String response, int id) {
                newsInterface.closeFinish();
                if (response != null && !response.equals("")) {
                    NewsList newsList = new Gson().fromJson(response, NewsList.class);
                    if (newsList.getData() != null && newsList.getData().size() > 0) {
                        newsInterface.newsList(newsList.getData());
                    } else {
                        newsInterface.tost(newsList.getDesc());
                    }
                } else
                    newsInterface.tost(App.applicationContext.getString(R.string.noData));
            }
        });

    }

    /**
     * 获取类目新闻列表
     *
     * @param navigation
     * @param limit
     * @param page
     */
    public void getNewsPlateData(String navigation, int limit, int page) {

        ApiUtil.getApiUtil().getNewsPlate(navigation, limit, page, new IsNetworkInterface() {
            @Override
            public void NoNetwork() {
                newsInterface.tost(App.applicationContext.getString(R.string.noNetwork));
                newsInterface.closeFinish();
            }
        }, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                newsInterface.tost(App.applicationContext.getString(R.string.noData));
                newsInterface.closeFinish();
            }

            @Override
            public void onResponse(String response, int id) {
                Log.d("getNewsPlateData", response);
                newsInterface.closeFinish();
                if (response != null && !response.equals("")) {
                    NewsList newsList = new Gson().fromJson(response, NewsList.class);
                    if (newsList.getData() != null && newsList.getData().size() > 0) {
                        newsInterface.newsPlate(newsList.getData());
                    } else {
                        newsInterface.tost(newsList.getDesc());
                    }
                } else
                    newsInterface.tost(App.applicationContext.getString(R.string.noData));
            }
        });
    }

    /**
     * 查询新闻详情
     */
    public void newsDetailsData(String navigation, String id) {
        ApiUtil.getApiUtil().getNewsDetails(navigation, id, new IsNetworkInterface() {
            @Override
            public void NoNetwork() {
                newsInterface.tost(App.applicationContext.getString(R.string.noNetwork));
                newsInterface.closeFinish();
            }
        }, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                newsInterface.tost(App.applicationContext.getString(R.string.noData));
                newsInterface.closeFinish();
            }

            @Override
            public void onResponse(String response, int id) {
                newsInterface.closeFinish();
                if (response != null && !response.equals("")) {
                    NewsDetails newsDetails = new Gson().fromJson(response, NewsDetails.class);
                    if (newsDetails.getData() != null && newsDetails.getData().size() > 0) {
                        newsInterface.newsDetails(newsDetails.getData().get(0));
                    } else
                        newsInterface.tost(newsDetails.getDesc());
                } else
                    newsInterface.tost(App.applicationContext.getString(R.string.noData));
            }
        });
    }
}
