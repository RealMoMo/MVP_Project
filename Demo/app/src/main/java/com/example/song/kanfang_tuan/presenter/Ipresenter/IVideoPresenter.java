package com.example.song.kanfang_tuan.presenter.Ipresenter;

import com.example.song.kanfang_tuan.bean.MVideoBean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/19 0019.
 */

public interface IVideoPresenter {

    public List<MVideoBean.ListBean> getData(int end);
}
