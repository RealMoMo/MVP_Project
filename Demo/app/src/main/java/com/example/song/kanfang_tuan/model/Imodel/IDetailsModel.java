package com.example.song.kanfang_tuan.model.Imodel;

import com.example.song.kanfang_tuan.bean.DetailsBean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/19 0019.
 */

public interface IDetailsModel {
    //联网获取数据
    public List<DetailsBean> getData(String video_id, int startNum, int endNum);
}
