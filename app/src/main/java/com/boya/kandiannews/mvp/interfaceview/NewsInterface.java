package com.boya.kandiannews.mvp.interfaceview;

import com.boya.kandiannews.mvp.bean.NewsDetails;
import com.boya.kandiannews.mvp.bean.NewsList;

import java.util.List;

/**
 * 新闻类目丶列表
 */
public interface NewsInterface extends TostCloseInterface {
    void newsList(List<NewsList.DataBean> rows);//首页新闻列表

    void newsPlate(List<NewsList.DataBean> rows);//类目新闻列表

    void newsDetails(NewsDetails.DataBean dataBean);//新闻详情
}
