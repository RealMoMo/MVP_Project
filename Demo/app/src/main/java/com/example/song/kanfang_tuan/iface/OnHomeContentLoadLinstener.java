package com.example.song.kanfang_tuan.iface;

import com.example.song.kanfang_tuan.bean.HomeBannerEntity;
import com.example.song.kanfang_tuan.bean.HomeContentEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/12/20 0020.
 */

public interface OnHomeContentLoadLinstener {
    public void getDataSuccess(List<HomeContentEntity.DataEntity> list);
    public void getDataFail();
    public void noNetworking();

    public void getBannerSuccess(List<HomeBannerEntity.DataEntity> list);

}
