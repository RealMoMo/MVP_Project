package com.example.song.kanfang_tuan.utils;

import com.example.song.kanfang_tuan.MyApp;

/**
 * Created by Administrator on 2016/12/20 0020.
 */

public class Constant {


    //点击视频跳转的标记
    public static final int VIDEO_TYPE = -10;
    //点击评论跳转的标记
    public static final int COMMENT_TYPE = -11;
    //type的key
    public static final String INTENT_TYPE = "type";
    //点击跳转到视频详情界面的key
    public static final String INTENT_VIDEO_KEY = "intent_video";


    //屏幕宽度
    public static final int widthPixels = MyApp.getApp().getResources().getDisplayMetrics().widthPixels;
    public static final int heightPixels = MyApp.getApp().getResources().getDisplayMetrics().heightPixels;

    //首页fragment 点击listview item 跳转到HomeDetailActivity的intent存的key
    public static final String HOME_DETAIL_KEY = "itemUrl";

    //minefragment 点击头像或登录 跳转到LoginActivity的requestcode
    public static final int QQLOGIN_REQUESTCODE = 1;


    //intent的resultcode ok
    public static final int RESULTCODE_OK=11;

    //intent的resultcode failed
    public static final int RESULTCODE_FAILED=12;


    //qqlogingactivity的intent用户名的key值
    public static final String LOGIN_NAME="username";

    //qqlogingactivity的intent用户头像的key值
    public static final String LOGIN_ICON="usericon";


    /**
     * 城市名称
     */
    public static final String KEY_CITYNAME = "cityname";
    /**
     * 城市id
     */
    public static final String KEY_CITYID = "cityid";


    // 调用相机拍照的请求码
    public static final int REQUEST_TAKE_PHOTO_CODE = 101;

    /**
     * searchactivity 搜索按钮的文本
     */
    public static final String SEARCH = "搜索";
    /**
     * searchactivity 搜索按钮的文本
     */
    public static final String CANCEL = "取消";

    /**
     * 跳转到housedetailactivity的intent key值
     */
    public static final String FID ="fid";


    /* 请求识别码 */
    public static final String IMAGE_TYPE = "image/*";
    public static final int IMAGE_REQUEST_CODE = 0x102;
    public static final int CODE_GALLERY_REQUEST = 0xa0;
    public static final int CODE_RESULT_REQUEST = 0xa2;

    /**
     * 裁剪原始的图片
     */
    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    public static final int OUTPUT_X = 80;
    public static final int OUTPUT_Y = 80;

}
