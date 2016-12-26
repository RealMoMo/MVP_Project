package com.example.song.kanfang_tuan.ui.activity;

import com.example.song.kanfang_tuan.adapter.VideoDetailsAdapter;

/**
 * Created by Administrator on 2016/12/19 0019.
 */

public interface IDetailsActivity  {
    // 设置adapter
    public void lvSetAdapter(VideoDetailsAdapter adapter);
    //返回头部数量
    public int getLvHeadNumber();
}
