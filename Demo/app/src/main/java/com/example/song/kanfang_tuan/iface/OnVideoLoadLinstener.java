package com.example.song.kanfang_tuan.iface;

import com.example.song.kanfang_tuan.bean.MVideoBean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/20 0020.
 */

public interface OnVideoLoadLinstener {
    public void getDataSuccess(List<MVideoBean.ListBean> list);
    public void getDataFail();
    public void noNetworking();
}
