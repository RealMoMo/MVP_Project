package com.example.song.kanfang_tuan.iface;

import com.example.song.kanfang_tuan.bean.CityEntity;

import java.util.List;

/**
 * Created by MAJIA on 2016/12/21.
 */

public interface OnCityChoiceLoadListener {
    public void getDataSuccess(List<CityEntity> list);
    public void getDataFail();
    public void noNetworking();
}
