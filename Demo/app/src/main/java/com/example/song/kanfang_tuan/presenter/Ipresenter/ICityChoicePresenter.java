package com.example.song.kanfang_tuan.presenter.Ipresenter;

import com.example.song.kanfang_tuan.bean.CityEntity;

import java.util.List;

/**
 * Created by MAJIA on 2016/12/20.
 */

public interface ICityChoicePresenter {
    List<CityEntity> getCityData();
    //城市检索
    void searchCity(String str_search);
}
