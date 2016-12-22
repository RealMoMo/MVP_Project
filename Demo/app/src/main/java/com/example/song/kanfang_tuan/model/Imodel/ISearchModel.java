package com.example.song.kanfang_tuan.model.Imodel;

import com.example.song.kanfang_tuan.bean.HomeSearchEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/12/22 0022.
 */

public interface ISearchModel {

    public List<HomeSearchEntity.DataEntity>  getSearchContent(String cityid,String keyword,int page);

    public String getCityId();

    public String getHouseFid(int position);
}
