package com.example.song.kanfang_tuan.utils;

import android.util.Log;
import android.widget.Toast;

import com.example.song.kanfang_tuan.MyApp;

/**
 * Created by Administrator on 2016/12/19 0019.
 */

public class L {

    private static final boolean isPlay = true;


    public static void d(String str) {
        if (isPlay) {
            Log.d("KF", "d: " + str);
        }
    }

    public static void s(String str){
        Toast.makeText(MyApp.getApp(),str,Toast.LENGTH_SHORT).show();
    }

    public static void l(String str){
        Toast.makeText(MyApp.getApp(),str,Toast.LENGTH_LONG).show();
    }

}
