package com.example.song.kanfang_tuan.presenter.Ipresenter;

/**
 * Created by Administrator on 2016/12/22 0022.
 */

public interface ISearchPresenter {

    public void  getSearchData(String cityid,String keyword,int page);

    public String getCityId();

    public String getHouseFid(int postion);

}
