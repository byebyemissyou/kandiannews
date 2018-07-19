package com.boya.kandiannews.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.boya.kandiannews.App;
import com.boya.kandiannews.R;
import com.boya.kandiannews.activity.main.AllActivity;
import com.boya.kandiannews.mvp.interfaceview.DownloadInterface;
import com.boya.kandiannews.mvp.presenter.DownloadPresenter;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityNewsDetails extends AllActivity implements DownloadInterface {

    @BindView(R.id.webView)
    WebView mWebView;
    private DownloadPresenter downloadPresenter;
    private String webCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this);
        openStatusBarIsTransparentFontVariety();
        webCode = getIntent().getStringExtra("div");
        String cssUrl = "http://cdn.lizhuanwang.xyz/jobapk/font_650698_jcrlp0abkoqolxr.css";
        File fileCss = new File(App.getPackageDataPath() + "/font_650698_jcrlp0abkoqolxr.css");
        downloadPresenter = new DownloadPresenter(this);
        if (!FileUtils.isFileExists(fileCss)) {
            downloadPresenter.downloadPackage(cssUrl, "font_650698_jcrlp0abkoqolxr.css");
        } else {
            loadWeb();
        }
        //  File readFileCss = new File("file:///android_asset/font_650698_jcrlp0abkoqolxr.css");

    }

    @Override
    public void downloadFailure(Exception e) {


    }

    @Override
    public void downloadSuccessful(File response) {
        loadWeb();
    }

    /**
     * 加载html
     */
    private void loadWeb() {
        File fileWeb = new File(App.getPackageDataPath() + "/web.html");
        FileIOUtils.writeFileFromString(fileWeb, webCode, false);//写入css字节流到文件
        mWebView.loadUrl("file://+" + fileWeb.getAbsolutePath());
    }

    @Override
    public void schedule(float progress) {

    }

    @Override
    public void tost(String s) {

    }

    @Override
    public void closeFinish() {

    }
}
