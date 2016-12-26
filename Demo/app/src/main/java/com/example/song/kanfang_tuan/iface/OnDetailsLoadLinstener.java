package com.example.song.kanfang_tuan.iface;

import com.example.song.kanfang_tuan.bean.DetailsBean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/21 0021.
 */

public interface OnDetailsLoadLinstener {
    public void getDataSuccess(List<DetailsBean> list);
    public void getDataFail();
    public void noNetworking();
}
