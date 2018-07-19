package com.boya.kandiannews.mvp.presenter;

import com.boya.kandiannews.App;
import com.boya.kandiannews.mvp.interfaceview.DownloadInterface;
import com.boya.kandiannews.mvp.interfaceview.IsNetworkInterface;
import com.boya.kandiannews.mvp.json.ApiUtil;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;

/**
 * Created by 管理员1 on 2017/10/28.
 * 下载
 */

public class DownloadPresenter {
    private DownloadInterface downloadInterface;

    public DownloadPresenter(DownloadInterface downloadInterface) {
        this.downloadInterface = downloadInterface;
    }


    /**
     * 下载
     *
     * @param appUrl  下载路径
     * @param appName 文件名
     */
    public void download(String appUrl, final String appName) {
        if (appUrl != null)
            dl(App.getDownloadsPath(), appUrl, appName);
    }
    /**
     * 下载
     *
     * @param appUrl  下载路径
     * @param appName 文件名
     */
    public void downloadPackage(String appUrl, final String appName) {
        if (appUrl != null)
            dl(App.getPackageDataPath(), appUrl, appName);
    }

    /**
     * 下载
     *
     * @param appUrl
     * @param appName
     */
    private void dl(String path, String appUrl, final String appName) {
        ApiUtil.getApiUtil().download(appUrl, new IsNetworkInterface() {
            @Override
            public void NoNetwork() {
                downloadInterface.tost("无网络连接，请检查您的网络···");
                downloadInterface.closeFinish();
            }
        }, new FileCallBack(path, appName) {
            @Override
            public void onError(Call call, Exception e, int id) {
                downloadInterface.closeFinish();
                downloadInterface.downloadFailure(e);
                downloadInterface.tost("下载失败" + e.getMessage());

            }

            @Override
            public void onResponse(File response, int id) {
                downloadInterface.closeFinish();
                downloadInterface.downloadSuccessful(response);

            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                downloadInterface.schedule(progress);
            }
        });
    }
}
