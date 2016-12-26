package com.example.song.kanfang_tuan.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.song.kanfang_tuan.MyApp;

/**
 * Created by Administrator on 2016/12/19 0019.
 */

public class NetworkUtils {

    public static boolean isNetworkLink() {

        ConnectivityManager connectivityManager = (ConnectivityManager) MyApp.getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;

    }
}
