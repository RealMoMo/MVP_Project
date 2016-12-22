package com.example.song.kanfang_tuan.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2016/12/19 0019.
 */

public class NoActionBarActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar()!=null){
            //去掉ActionBar
            getSupportActionBar().hide();
        }
    }
}
