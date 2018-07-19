package com.boya.kandiannews.mvp.interfaceview;

import java.io.File;

/**
 * Created by 管理员1 on 2017/10/28.
 * 下载
 */

public interface DownloadInterface extends TostCloseInterface{
    //下载失败
    void downloadFailure(Exception e);

    //下载成功
    void downloadSuccessful(File response);

    //下载进度
    void schedule(float progress);
}
