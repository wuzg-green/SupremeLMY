package com.zondy.jwt.jwtmobile.base;

import android.app.Application;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;
import com.zondy.jwt.jwtmobile.global.Constant;
import com.zondy.jwt.jwtmobile.manager.GPSLocationManager;
import com.zondy.jwt.jwtmobile.manager.UrlManager;
import com.zondy.jwt.jwtmobile.util.SharedTool;

import java.util.concurrent.TimeUnit;


//import io.realm.Realm;
//import io.realm.RealmConfiguration;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;

/**
 * Created by sheep on 2017/1/3.
 * 2017年02月07日17:53:56 测试
 */

public class MyApplication extends Application {
    public final static boolean IS_PRODUCT_ENVIRONMENT = true;
    public final static boolean IS_TEST_JINGQLIST = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("sheep", true))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();

        OkHttpUtils.initClient(okHttpClient);
        initIpAndPort();
        GPSLocationManager.getInstance(this, 2, 0).startLocation();//开启GPS定位服务

        Realm.init(this);
        RealmConfiguration realmConfiguration=new RealmConfiguration.Builder().name("JWTLMY.realm").deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);

        //必须调用初始化
        OkGo.init(this);
        OkGo.getInstance()
                .setConnectTimeout(15000)  //全局的连接超时时间
                .setReadTimeOut(15000)     //全局的读取超时时间
                .setWriteTimeOut(15000)    //全局的写入超时时间
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE);
    }

    /**
     * 初始化ip和端口,IP和端口在张家港警务通中有差别,Common为普通的,不变的IP和端口.张家港的是调用接口获取的.
     *
     * @param
     * @return void
     * @throws
     * @Method: com.zondy.xunjing.main.MyApplication.initIpAndPort
     * @Description: TODO
     */
    public void initIpAndPort() {

        String ipAndPort = SharedTool.getInstance().getIp(
                getApplicationContext());
        String[] ips = ipAndPort.split("_");
        if (ips.length == 4 && !TextUtils.isEmpty(ips[0])
                && !TextUtils.isEmpty(ips[1]) && !TextUtils.isEmpty(ips[2])
                && !TextUtils.isEmpty(ips[3])) {
            UrlManager.HOST_IP = ips[0];
            UrlManager.HOST_PORT = ips[1];
            UrlManager.PUSH_HOST_IP = ips[2];
            UrlManager.PUSH_HOST_PORT = ips[3];
        } else {
            // sharedpreference保存的文件不正确,则使用默认配置
            String[] defaultIps = new String[4];
            if (Constant.JWT_AREA_SELECTED.equals(Constant.JWT_AREA_FN)) {
                defaultIps[0] = "127.0.0.1";
                defaultIps[1] = "9096/fl1";
                defaultIps[2] = "127.0.0.1";
                defaultIps[3] = "9092";
            } else if (Constant.JWT_AREA_SELECTED
                    .equals(Constant.JWT_AREA_LYG)) {
                defaultIps[0] = "10.142.137.173";
                defaultIps[1] = "9087";
                defaultIps[2] = "10.142.137.173";
                defaultIps[3] = "5222";
            } else if (Constant.JWT_AREA_SELECTED
                    .equals(Constant.JWT_AREA_ZJG)) {
                defaultIps[0] = "192.168.9.188";
                defaultIps[1] = "8080";
                defaultIps[2] = "192.168.9.188";
                defaultIps[3] = "5222";
            } else if (Constant.JWT_AREA_SELECTED
                    .equals(Constant.JWT_AREA_WH)) {
                defaultIps[0] = "192.168.9.188";
                defaultIps[1] = "8080";
                defaultIps[2] = "61.183.129.187";
                defaultIps[3] = "4041";
            } else if (Constant.JWT_AREA_SELECTED
                    .equals(Constant.JWT_AREA_TEST)) {
                defaultIps[0] = "192.168.10.217";
                defaultIps[1] = "8080";
                defaultIps[2] = "192.168.10.217";
                defaultIps[3] = "5222";
            } else {
                // 默认使用武汉测试地址
                defaultIps[0] = "192.168.10.217";
                defaultIps[1] = "8080";
                defaultIps[2] = "192.168.10.217";
                defaultIps[3] = "5222";
            }

            UrlManager.HOST_IP = defaultIps[0];
            UrlManager.HOST_PORT = defaultIps[1];
            UrlManager.PUSH_HOST_IP = defaultIps[2];
            UrlManager.PUSH_HOST_PORT = defaultIps[3];

        }

    }

}
