package com.example.song.kanfang_tuan.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.song.kanfang_tuan.R;
import com.example.song.kanfang_tuan.adapter.VideoAdapter;
import com.example.song.kanfang_tuan.presenter.Ipresenter.IVideoPresenter;
import com.example.song.kanfang_tuan.presenter.VideoPresenter;
import com.example.song.kanfang_tuan.utils.L;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment implements IVideoFragment, SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {

    IVideoPresenter iVideoPresenter;
    private ListView lv_video;
    private SwipeRefreshLayout srl_downRefresh;

    //每一次上拉加载数据的条数
    private static final int num = 5;
    //上拉加载的次数
    int numberOfUpLoadMore = 1;
    //是否加载更多
    boolean isMoreData = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //初始化控件
        initViews(view);
        //创建VideoPresenter的实例
        iVideoPresenter = new VideoPresenter(this);
        //获取数据
        iVideoPresenter.getData(numberOfUpLoadMore * num);
    }

    private void initViews(View view) {
        lv_video = ((ListView) view.findViewById(R.id.lv_video));
        srl_downRefresh = ((SwipeRefreshLayout) view.findViewById(R.id.srl_downRefresh_y));

        //下拉刷新监听
        srl_downRefresh.setOnRefreshListener(this);
        //上拉加载更多监听
        lv_video.setOnScrollListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.d("onDestroy");
    }


    @Override
    public void setLvVideo(VideoAdapter videoAdapter) {
        lv_video.setAdapter(videoAdapter);
    }

    //返回下拉刷新控件对象,供外界调用
    public SwipeRefreshLayout getSrl_downRefresh() {
        return srl_downRefresh;
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        iVideoPresenter.getData(num * numberOfUpLoadMore);
    }

    //上拉加载更多
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (isMoreData && scrollState == SCROLL_STATE_IDLE) {
            numberOfUpLoadMore++;
            iVideoPresenter.getData(numberOfUpLoadMore * num);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem + visibleItemCount == totalItemCount) {
            isMoreData = true;
        } else {
            isMoreData = false;
        }
    }
}
