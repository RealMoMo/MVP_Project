package com.example.song.kanfang_tuan.presenter;

import android.content.Context;

import com.example.song.kanfang_tuan.adapter.CityChoiceAdapter;
import com.example.song.kanfang_tuan.bean.CityEntity;
import com.example.song.kanfang_tuan.iface.OnCityChoiceLoadListener;
import com.example.song.kanfang_tuan.model.CityChoiceModel;
import com.example.song.kanfang_tuan.model.Imodel.ICityChoiceModel;
import com.example.song.kanfang_tuan.presenter.Ipresenter.ICityChoicePresenter;
import com.example.song.kanfang_tuan.ui.activity.ICityChoiceActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MAJIA on 2016/12/20.
 */

public class CityChoicePresenter implements ICityChoicePresenter,OnCityChoiceLoadListener {

    ICityChoiceModel iCityChoiceModel;
    ICityChoiceActivity iCityChoiceActivity;

    CityChoiceAdapter adapter;
    List<CityEntity> data;


    public CityChoicePresenter(ICityChoiceActivity iCityChoiceActivity) {
        this.iCityChoiceActivity = iCityChoiceActivity;
        iCityChoiceModel = new CityChoiceModel(this);
        data = new ArrayList<>();
        adapter = new CityChoiceAdapter((Context) iCityChoiceActivity, data);
        iCityChoiceActivity.setCityChoiceAdapter(adapter);
    }

//    public CityChoicePresenter(ICityChoiceActivity iCityChoiceActivity) {
//        this.iCityChoiceActivity = iCityChoiceActivity;
//        iCityChoiceModel = new CityChoiceModel();
//
//        data = new ArrayList<>();
//        adapter = new CityChoiceAdapter((CityChoiceActivity) iCityChoiceActivity, data);
//        iCityChoiceActivity.setCityChoiceAdapter(adapter);
//    }

    @Override
    public List<CityEntity> getCityData() {
        data.clear();
        data.addAll(iCityChoiceModel.getCityData());
        adapter.setAllData(data);
        adapter.notifyDataSetChanged();
        return data;
    }

    /**
     * 实现城市的检索
     * @param str_search 从CityChoiceActivity获取参数
     */
    @Override
    public void searchCity(String str_search) {
        adapter.search(str_search);
    }

    @Override
    public void getDataSuccess(List<CityEntity> list) {
        data.clear();
        data.addAll(list);
        adapter.setAllData(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getDataFail() {

    }

    @Override
    public void noNetworking() {

    }
}
