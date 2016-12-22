package com.example.song.kanfang_tuan.presenter.Ipresenter;

import com.example.song.kanfang_tuan.bean.HomeContentEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/12/19 0019.
 */

public interface IHomeFragmentPresenter {


        public List<HomeContentEntity.DataEntity> getData(String cityId,int pageflag);

        public void addBannerView(String cityId);

        public String getItemUrl(int positon);

        public void saveCityInfo(String cityName,String cityId);

        public String[] getCityInfo();




}
