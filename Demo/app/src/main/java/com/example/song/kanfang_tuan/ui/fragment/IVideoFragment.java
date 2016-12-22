package com.example.song.kanfang_tuan.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;

import com.example.song.kanfang_tuan.adapter.VideoAdapter;

/**
 * Created by Administrator on 2016/12/19 0019.
 */

public interface IVideoFragment {

    public void setLvVideo(VideoAdapter videoAdapter);

    public SwipeRefreshLayout getSrl_downRefresh();
}
