package com.example.song.kanfang_tuan.model.Imodel;

import com.example.song.kanfang_tuan.bean.MVideoBean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/19 0019.
 */

public interface IVideoModel {
    public List<MVideoBean.ListBean> getData(int start,int end);
}
