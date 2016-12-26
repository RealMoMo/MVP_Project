package com.example.song.kanfang_tuan.presenter;

import com.example.song.kanfang_tuan.R;
import com.example.song.kanfang_tuan.adapter.VideoDetailsAdapter;
import com.example.song.kanfang_tuan.bean.DetailsBean;
import com.example.song.kanfang_tuan.iface.OnDetailsLoadLinstener;
import com.example.song.kanfang_tuan.model.DetailsModel;
import com.example.song.kanfang_tuan.model.Imodel.IDetailsModel;
import com.example.song.kanfang_tuan.presenter.Ipresenter.IDetailsPresenter;
import com.example.song.kanfang_tuan.ui.activity.DetailsActivity;
import com.example.song.kanfang_tuan.ui.activity.IDetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/19 0019.
 */

public class DetailsPresenter implements IDetailsPresenter, OnDetailsLoadLinstener {

    private List<DetailsBean> data;
    VideoDetailsAdapter adapter;
    IDetailsActivity iDetailsActivity;
    IDetailsModel iDetailsModel;


    public DetailsPresenter(IDetailsActivity iDetailsActivity) {
        this.iDetailsActivity = iDetailsActivity;
        iDetailsModel = new DetailsModel(data, this);

        initDatas();

        iDetailsActivity.lvSetAdapter(adapter);
    }


    private void initDatas() {
        data = new ArrayList<>();
        adapter = new VideoDetailsAdapter(((DetailsActivity) iDetailsActivity), data, R.layout.video_details_item);
    }

    @Override
    public void getData(String video_id, int startNum, int endNum) {
        iDetailsModel.getData(video_id, startNum, endNum);
    }

    //联网下载数据三种情况，监听实现
    @Override
    public void getDataSuccess(List<DetailsBean> list) {
        data.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getDataFail() {

    }

    @Override
    public void noNetworking() {

    }

}
