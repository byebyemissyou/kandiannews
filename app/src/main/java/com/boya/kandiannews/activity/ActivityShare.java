package com.boya.kandiannews.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.boya.kandiannews.R;
import com.boya.kandiannews.bottomsheetpopupdialoglib.ShareBottomPopupDialog;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jiguang.share.android.api.PlatActionListener;
import cn.jiguang.share.android.api.Platform;

public class ActivityShare extends AppCompatActivity {


    @BindView(R.id.bt)
    Button mBt;
    @BindView(R.id.all_layout)
    RelativeLayout mAllLayout;
    private String ImagePath;
    View dialogView;
    private ShareBottomPopupDialog shareBottomPopupDialog;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String toastMsg = (String) msg.obj;
            Toast.makeText(ActivityShare.this, toastMsg, Toast.LENGTH_SHORT).show();

        }
    };
    private PlatActionListener mPlatActionListener = new PlatActionListener() {
        @Override
        public void onComplete(Platform platform, int action, HashMap<String, Object> data) {
            if (handler != null) {
                Message message = handler.obtainMessage();
                message.obj = "分享成功";
                handler.sendMessage(message);
            }
        }

        @Override
        public void onError(Platform platform, int action, int errorCode, Throwable error) {
            if (handler != null) {
                Message message = handler.obtainMessage();
                message.obj = "分享失败:" + (error != null ? error.getMessage() : "") + "---" + errorCode;
                handler.sendMessage(message);
            }
        }

        @Override
        public void onCancel(Platform platform, int action) {
            if (handler != null) {
                Message message = handler.obtainMessage();
                message.obj = "分享取消";
                handler.sendMessage(message);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);
        initView();
        listener();


//        ImagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/share_image.png";
//        ImageUtils.save(ImageUtils.getBitmap(R.mipmap.ic_launcher),
//                ImagePath, Bitmap.CompressFormat.PNG);
//        ShareParams shareParams = new ShareParams();
//        shareParams.setTitle("share_title");
//        shareParams.setText("sdijfsdkjfklsdjfklsdfjds");
//        shareParams.setShareType(Platform.SHARE_IMAGE);
//        shareParams.setUrl("http://www.baidu.com");
//        shareParams.setImagePath(ImagePath);
//        JShareInterface.share(QQ.Name, shareParams, mPlatActionListener);
        //startActivity(new Intent(this, ShareTypeActivity.class));
    }


    private void initView() {
        dialogView = LayoutInflater.from(this).inflate(R.layout.share_bottom_dialog, null);
        shareBottomPopupDialog = new ShareBottomPopupDialog(this, dialogView);
    }

    private void listener() {
        dialogView.findViewById(R.id.share_qq_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityShare.this, "QQ", Toast.LENGTH_SHORT).show();
                shareBottomPopupDialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.share_to_qq_zone_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityShare.this, "qq空间", Toast.LENGTH_SHORT).show();
                shareBottomPopupDialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.share_weixin_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityShare.this, "微信", Toast.LENGTH_SHORT).show();
                shareBottomPopupDialog.dismiss();
            }
        });

        dialogView.findViewById(R.id.share_wxcircle_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityShare.this, "微信朋友圈", Toast.LENGTH_SHORT).show();
                shareBottomPopupDialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.share_to_weibo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityShare.this, "微博", Toast.LENGTH_SHORT).show();
                shareBottomPopupDialog.dismiss();
            }
        });
        //取消
        dialogView.findViewById(R.id.share_pop_cancle_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareBottomPopupDialog.dismiss();
            }
        });
    }

    @OnClick(R.id.bt)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt:
                showDialog();
//                BottomDialog.create(getSupportFragmentManager())
//                        .setLayoutRes(R.layout.dialog_layout)      // dialog layout
//                        .show();
                break;
        }
    }

    public void showDialog() {
        shareBottomPopupDialog.showPopup(mAllLayout);
    }


}


