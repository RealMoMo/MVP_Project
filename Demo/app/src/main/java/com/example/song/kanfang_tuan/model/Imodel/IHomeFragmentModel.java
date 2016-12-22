package com.example.song.kanfang_tuan.model.Imodel;

import com.example.song.kanfang_tuan.bean.HomeBannerEntity;
import com.example.song.kanfang_tuan.bean.HomeContentEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/12/19 0019.
 */
public interface IHomeFragmentModel {


    //保存cityid cityname
    public void saveCityInfo(String cityName,String cityId);
    //获取cityid cityname
    public String[] getCityInfo();

    //listview
    public void saveHomeContent(HomeContentEntity entity);
    
    public List<HomeContentEntity.DataEntity> getHomeContent(String cityId,int pageflag);


    //bannerview
    public void saveBannerContent(HomeBannerEntity entity);

    public List<HomeBannerEntity.DataEntity> getHomeBannerContent(String cityId);







}
