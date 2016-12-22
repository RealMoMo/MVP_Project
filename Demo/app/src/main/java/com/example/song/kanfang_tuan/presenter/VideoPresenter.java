package com.example.song.kanfang_tuan.presenter;

import com.example.song.kanfang_tuan.R;
import com.example.song.kanfang_tuan.adapter.VideoAdapter;
import com.example.song.kanfang_tuan.bean.MVideoBean;
import com.example.song.kanfang_tuan.iface.OnVideoLoadLinstener;
import com.example.song.kanfang_tuan.model.Imodel.IVideoModel;
import com.example.song.kanfang_tuan.model.VideoModel;
import com.example.song.kanfang_tuan.presenter.Ipresenter.IVideoPresenter;
import com.example.song.kanfang_tuan.ui.fragment.IVideoFragment;
import com.example.song.kanfang_tuan.ui.fragment.VideoFragment;
import com.example.song.kanfang_tuan.utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/19 0019.
 */

public class VideoPresenter implements IVideoPresenter, OnVideoLoadLinstener {
    private IVideoModel iVideoModel;
    private IVideoFragment iVideoFragment;

    private VideoAdapter adapter;
    private List<MVideoBean.ListBean> data;

    public VideoPresenter(IVideoFragment iVideoFragment) {
        this.iVideoFragment = iVideoFragment;
        iVideoModel = new VideoModel(this);

        data = new ArrayList<>();
        adapter = new VideoAdapter(((VideoFragment) iVideoFragment).getContext(), data, R.layout.item_layout);
        iVideoFragment.setLvVideo(adapter);
    }

    public List<MVideoBean.ListBean> getData(int end) {
        data.clear();
        data.addAll(iVideoModel.getData(end));
        adapter.notifyDataSetChanged();
        return data;
    }

    //数据下载是的三种情况
    //getDataSuccess   下载成功
    //getDataFail      下载失败，参数错误，或者解析失败
    //noNetworking      网络请求失败
    @Override
    public void getDataSuccess(List<MVideoBean.ListBean> list) {
        data.clear();
        data.addAll(list);
        adapter.notifyDataSetChanged();
        refreshFinish();
    }
    @Override
    public void getDataFail() {
        refreshFinish();
        L.s(" 解析失败");
    }

    @Override
    public void noNetworking() {
        refreshFinish();
        L.s("没有网络或者服务器丢失");
    }

    //下拉刷新完成
    private void refreshFinish(){
        iVideoFragment.getSrl_downRefresh().setRefreshing(false);
    }
}
