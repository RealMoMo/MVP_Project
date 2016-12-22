package com.example.song.kanfang_tuan;

import android.app.Application;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by 11355 on 2016/12/19.
 *
 * 测试测试测试
 */

public class MyApp extends Application {

    private static MyApp myApp;
    @Override
    public void onCreate() {
        super.onCreate();

        myApp=this;

        //初始化zxing
        ZXingLibrary.initDisplayOpinion(this);
        //初始化sharesdk
        ShareSDK.initSDK(this);
    }
    public static MyApp getApp(){
        return myApp;
    }
}
