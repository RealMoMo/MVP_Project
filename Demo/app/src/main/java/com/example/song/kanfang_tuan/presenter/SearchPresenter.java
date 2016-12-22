package com.example.song.kanfang_tuan.presenter;

import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.song.kanfang_tuan.R;
import com.example.song.kanfang_tuan.adapter.SearchAdapter;
import com.example.song.kanfang_tuan.bean.HomeSearchEntity;
import com.example.song.kanfang_tuan.iface.OnHomeSearchLoadListener;
import com.example.song.kanfang_tuan.model.Imodel.ISearchModel;
import com.example.song.kanfang_tuan.model.SearchModel;
import com.example.song.kanfang_tuan.presenter.Ipresenter.ISearchPresenter;
import com.example.song.kanfang_tuan.ui.activity.HomeSearchActivity;
import com.example.song.kanfang_tuan.utils.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/22 0022.
 */

public class SearchPresenter implements ISearchPresenter,OnHomeSearchLoadListener{

    private HomeSearchActivity homeSearchActivity;
    private ISearchModel iSearchModel;
    private SearchAdapter adapter;
    private ListView lv;
    private List<HomeSearchEntity.DataEntity> totalList;
    private List<HomeSearchEntity.DataEntity> tempList;

    public SearchPresenter(HomeSearchActivity homeSearchActivity) {
        this.homeSearchActivity = homeSearchActivity;
        this.iSearchModel = new SearchModel(this);
        lv = homeSearchActivity.searchLv;

        totalList = new ArrayList<>();
        tempList = new ArrayList<>();
        adapter = new SearchAdapter(homeSearchActivity,totalList, R.layout.search_listview_layout1);
        lv.setAdapter(adapter);
    }

    @Override
    public void getSearchDataSuccess(List<HomeSearchEntity.DataEntity> list) {
        if(totalList.size()==0){
            homeSearchActivity.searchLoadingIv.setVisibility(View.INVISIBLE);
        }else{
            homeSearchActivity.searchLoadingmoreIv.setVisibility(View.INVISIBLE);
        }
        totalList.addAll(list);
        adapter.notifyDataSetChanged();
        //设置按钮可以点击,并改变其文本
        homeSearchActivity.searchTvCancelSearch.setClickable(true);
        homeSearchActivity.searchTvCancelSearch.setText(Constant.CANCEL);
    }

    @Override
    public void getDataFail() {
        //设置按钮可以点击
        homeSearchActivity.searchTvCancelSearch.setClickable(true);
        homeSearchActivity.searchTvCancelSearch.setText(Constant.CANCEL);

        homeSearchActivity.searchLoadingIv.setVisibility(View.INVISIBLE);
        homeSearchActivity.searchLoadingmoreIv.setVisibility(View.INVISIBLE);

        Toast.makeText(homeSearchActivity,"没有更多数据",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void noNetworking() {
        //设置按钮可以点击
        homeSearchActivity.searchTvCancelSearch.setClickable(true);
        homeSearchActivity.searchTvCancelSearch.setText(Constant.CANCEL);

        homeSearchActivity.searchLoadingIv.setVisibility(View.INVISIBLE);
        homeSearchActivity.searchLoadingmoreIv.setVisibility(View.INVISIBLE);

        Toast.makeText(homeSearchActivity,"网络不顺畅",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getSearchData(String cityid, String keyword, int page) {
        if(page==1){
            totalList.clear();
        }
        iSearchModel.getSearchContent(cityid,keyword,page);
    }


    @Override
    public String getCityId() {
        return iSearchModel.getCityId();
    }


    @Override
    public String getHouseFid(int postion) {

        return iSearchModel.getHouseFid(postion);
    }
}
