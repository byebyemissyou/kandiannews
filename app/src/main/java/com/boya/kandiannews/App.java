package com.boya.kandiannews;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;

import com.andsync.xpermission.XPermissionUtils;
import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.boya.kandiannews.mvp.bean.User;
import com.boya.kandiannews.mvp.interfaceview.permission.PermissionListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;


/**
 * Created by Administrator on 2017/8/10.
 */

public class App extends Application {
    public static Context applicationContext;
    private static User userObject;//用户信息对象
    public static int istate = 0;


    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        Utils.init(this);//初始化android工具类
        okHttpUtils();//网络请求框架初始化
        seetingJPush();//极光推送
        seetingShare();//极光分享


    }

    /**
     * 极光分享
     */
    private void seetingShare() {
        JShareInterface.setDebugMode(true);
        /**
         * since 1.5.0，1.5.0版本后增加API，支持在代码中设置第三方appKey等信息，当PlatformConfig为null时，或者使用JShareInterface.init(Context)时需要配置assets目录下的JGShareSDK.xml
         **/
        //*
        JShareInterface.init(this/*, platformConfig*/);
    }

    /**
     * 极光推送
     */
    private void seetingJPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        setAlias();//极光绑定用户
    }

    /**
     * 极光绑定用户
     */
    private static void setAlias() {
        if (getUserData() != null)
            JPushInterface.setAlias(applicationContext, Integer.parseInt(getUserData().getData().getIuserid()), getUserData().getData().getIuserid());

    }


    private void okHttpUtils() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30000, TimeUnit.MILLISECONDS)//
                .readTimeout(30000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }


    //用户json缓存目录
    public static String getUserPath() {
        return applicationContext.getFilesDir() + "/user";
    }

    //阅读了的文章
    public static String getReadPath() {
        return applicationContext.getFilesDir() + "/read";
    }

    //数据缓存目录
    public static String getPackageDataPath() {
        return applicationContext.getFilesDir() + "/data";
    }

   //下载目录
   public static String getDownloadsPath() {
       return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
   }
    /**
     * //从本地获取用户信息
     *
     * @return 返回用户对象，用户未登录返回null
     */
    public static User getUserData() {
        //如果用户已登陆，PID从本地读取
        if (userObject == null) {
            String userJson = readString(getUserPath(), "user");
            if (userJson != null) {
                Log.d("App", userJson);
                userObject = new Gson().fromJson(userJson, User.class);
                return userObject;
            } else {
                return null;
            }
        } else
            return userObject;
    }

    /**
     * 删除用户信息（json字符）
     */
    public static void delteUserData() {
        delteDirString(getUserPath());
        userObject = null;

    }

    /**
     * 写入用户信息（json字符）
     *
     * @param jsonString
     */
    public static void writeUserData(String jsonString) {
        writeString(getUserPath(), "user", jsonString);
        setAlias();//极光绑定用户
    }

    /**
     * 写入信息
     */
    public static void writeString(String path, String key, String msg) {
        CacheUtils.getInstance(new File(path)).put(key, EncodeUtils.base64Encode(msg));//加密写入缓存
    }

    /**
     * 读取信息
     */
    public static String readString(String path, String key) {
        String string = CacheUtils.getInstance(new File(path)).getString(key);
        if (string == null)
            return null;
        return new String(EncodeUtils.base64Decode(string));//读取信息并解密
    }

    /**
     * 删除指定目录信息
     */
    public static void delteDirString(String path) {
        CacheUtils.getInstance(new File(path)).clear();
    }

    /**
     * 删除指定目录指定key信息
     */
    public static void delteString(String path, String key) {
        CacheUtils.getInstance(new File(path)).remove(key);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 吐司
     *
     * @param text        显示内容
     * @param isShowShort 是否短时间显示
     */
    public static void tost(String text, boolean isShowShort) {
        ToastUtils.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        if (isShowShort)
            ToastUtils.showShort(text);
        else
            ToastUtils.showLong(text);

    }

    /**
     * 检查权限
     *
     * @param requestCode     权限码
     * @param permissionsName 权限名
     * @param permissions     权限
     */
    public static void checkAndPermission(final Activity context, final int requestCode, final String permissionsName, final PermissionListener listener, String... permissions) {
        XPermissionUtils.requestPermissions(context, requestCode, permissions,
                new XPermissionUtils.OnPermissionListener() {
                    public AlertDialog checkAndPermissionDialog;

                    @Override
                    public void onPermissionGranted() {
                        listener.listener();
                    }

                    @Override
                    public void onPermissionDenied(final String[] deniedPermissions, boolean alwaysDenied) {
                        // 拒绝 -> 提示此公告的意义，并可再次尝试获取权限
                        if (alwaysDenied) { // 拒绝后不再询问 -> 提示跳转到设置
                            //提示跳转到设置
                            if (!context.isFinishing())
                                checkAndPermissionDialog = new AlertDialog.Builder(context)
                                        .setTitle("温馨提示")
                                        .setMessage(App.applicationContext.getString(R.string.app_name)+"需要·" + permissionsName + "·权限才能正常使用,请在设置中授权！")
                                        .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                context.startActivity(getAppDetailSettingIntent());//提示跳转到设置
                                            }
                                        })
                                        .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                System.exit(0);
                                            }
                                        }).show();
                        } else {
                            if (!context.isFinishing())
                                checkAndPermissionDialog = new AlertDialog.Builder(context)
                                        .setTitle("温馨提示")
                                        .setMessage(App.applicationContext.getString(R.string.app_name)+"需要·" + permissionsName + "·权限才能正常使用")
                                        .setPositiveButton("授权", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                XPermissionUtils.requestPermissionsAgain(context, deniedPermissions, requestCode);
                                            }
                                        })
                                        .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                System.exit(0);
                                            }
                                        }).show();
                        }
                    }
                });
    }

    /**
     * 获取应用详情页面intent
     *
     * @return
     */
    private static Intent getAppDetailSettingIntent() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", applicationContext.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", applicationContext.getPackageName());
        }
        return localIntent;
    }

    /**
     * OnTrimMemory是Android 4.0之后提供的API，系统会根据不同的内存状态来回调。系统提供的回调有：
     * Application.onTrimMemory()
     * Activity.onTrimMemory()
     * Fragement.OnTrimMemory()
     * Service.onTrimMemory()
     * ContentProvider.OnTrimMemory()
     * OnTrimMemory的参数是一个int数值，代表不同的内存状态：
     * TRIM_MEMORY_COMPLETE：内存不足，并且该进程在后台进程列表最后一个，马上就要被清理
     * TRIM_MEMORY_MODERATE：内存不足，并且该进程在后台进程列表的中部。
     * TRIM_MEMORY_BACKGROUND：内存不足，并且该进程是后台进程。
     * TRIM_MEMORY_UI_HIDDEN：内存不足，并且该进程的UI已经不可见了。
     * 以上4个是4.0增加
     * TRIM_MEMORY_RUNNING_CRITICAL：内存不足(后台进程不足3个)，并且该进程优先级比较高，需要清理内存
     * TRIM_MEMORY_RUNNING_LOW：内存不足(后台进程不足5个)，并且该进程优先级比较高，需要清理内存
     * TRIM_MEMORY_RUNNING_MODERATE：内存不足(后台进程超过5个)，并且该进程优先级比较高，需要清理内存
     * 以上3个是4.1增加
     * 系统也提供了一个ComponentCallbacks2，通过Context.registerComponentCallbacks()注册后，就会被系统回调到。
     *
     * @param level
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }


    /**
     * 是否更新图片
     *
     * @param imageUrl
     * @param requestOptions
     */
    private static void isUpdataImage(Object imageUrl, RequestOptions requestOptions) {
        /**
         * 第二种方法：
         .signature(new StringSignature("01"))//增加签名
         这个api是增加签名，地址不变，改变这个签名参数也会不读取缓存重新请求。
         我就是用这个方法，传递url不变也可以重新请求，不读取缓存。这个参数可以绑定版本号，每次更新重新获取，或者请求后台
         强大的Glide
         */
        if (/*pictureUpdate != null &&*/ imageUrl instanceof String) {
            String imageUrl1 = (String) imageUrl;
            String imageName = imageUrl1.substring(imageUrl1.lastIndexOf("/") + 1, imageUrl1.length());//截取N中，第0个位置到最后一个f之前的全部字符
//            if (asList.contains(imageName)) {//需要更新的图片
//                requestOptions.signature(new ObjectKey(""));//增加签名
//            }
        }
    }

    /**
     * 加载 16：9 图片(长图)
     *
     * @param imageUrl      图片路径
     * @param imageView     加载图片控件
     * @param isplaceholder 是否加载占位图
     */

    public static void loadingImageFill(Object imageUrl, ImageView imageView, boolean isplaceholder) {

        RequestBuilder<Drawable> objectDrawableRequestBuilder = Glide.with(applicationContext)
                .load(imageUrl);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.fitCenter();//比例填充
        requestOptions.priority(Priority.HIGH);//优先加载
        //isUpdataImage(imageUrl, requestOptions);//是否更新图片
        if (isplaceholder)//是否缓存
        {
            requestOptions.placeholder(R.mipmap.image_failure_fill);//设置占位图
            requestOptions.error(R.mipmap.image_failure_fill);//设置错误图片

        }
        objectDrawableRequestBuilder.apply(requestOptions);//加载配置信息
        objectDrawableRequestBuilder.into(imageView);
    }


    /**
     * 加载 9：16 图片（宽图）
     *
     * @param imageUrl      图片路径
     * @param imageView     加载图片控件
     * @param isplaceholder 是否加载占位图
     */
    public static  void loadingImageAd(Object imageUrl, ImageView imageView, boolean isplaceholder) {
        RequestBuilder<Drawable> objectDrawableRequestBuilder = Glide.with(applicationContext)
                .load(imageUrl);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.fitCenter();//比例填充
        requestOptions.priority(Priority.HIGH);//优先加载
        //isUpdataImage(imageUrl, requestOptions);//是否更新图片
        if (isplaceholder)//是否缓存
        {
            requestOptions.placeholder(R.mipmap.image_loding_ad);//设置占位图
            requestOptions.error(R.mipmap.image_failure_ad);//设置错误图片

        }
        objectDrawableRequestBuilder.apply(requestOptions);//加载配置信息
        objectDrawableRequestBuilder.into(imageView);
    }

    /**
     * 加载 1：1 图片（方图）
     *
     * @param imageUrl      图片路径
     * @param imageView     加载图片控件
     * @param isplaceholder 是否加载占位图
     */
    public static void loadingImageItem(Object imageUrl, ImageView imageView, boolean isplaceholder) {
        RequestBuilder<Drawable> objectDrawableRequestBuilder = Glide.with(applicationContext)
                .load(imageUrl);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.fitCenter();//比例填充
        requestOptions.priority(Priority.HIGH);//优先加载
        //isUpdataImage(imageUrl, requestOptions);//是否更新图片
        if (isplaceholder)//是否缓存
        {
            requestOptions.placeholder(R.mipmap.image_loading_item);//设置占位图
            requestOptions.error(R.mipmap.image_failure_item);//设置错误图片

        }
        objectDrawableRequestBuilder.apply(requestOptions);//加载配置信息
        objectDrawableRequestBuilder.into(imageView);
    }

}

