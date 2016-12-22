package com.example.song.kanfang_tuan.iface;

import com.example.song.kanfang_tuan.bean.HomeSearchEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/12/22 0022.
 */

public interface OnHomeSearchLoadListener {

    public void getSearchDataSuccess(List<HomeSearchEntity.DataEntity> list);
    public void getDataFail();
    public void noNetworking();
}
