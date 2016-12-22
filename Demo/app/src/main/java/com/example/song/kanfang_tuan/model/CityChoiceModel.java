package com.example.song.kanfang_tuan.model;

import com.example.song.kanfang_tuan.api.ApiManager;
import com.example.song.kanfang_tuan.api.IApiService;
import com.example.song.kanfang_tuan.bean.CityEntity;
import com.example.song.kanfang_tuan.iface.OnCityChoiceLoadListener;
import com.example.song.kanfang_tuan.model.Imodel.ICityChoiceModel;
import com.example.song.kanfang_tuan.utils.CityJsonUtils;
import com.example.song.kanfang_tuan.utils.L;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MAJIA on 2016/12/20.
 */

public class CityChoiceModel implements ICityChoiceModel {

    List<CityEntity> data;
    OnCityChoiceLoadListener listener;

    public CityChoiceModel(OnCityChoiceLoadListener listener) {
        this.listener = listener;
        data = new ArrayList<>();
    }

    @Override
    public List<CityEntity> getCityData() {

        IApiService iApiService = ApiManager.creatStringApi(IApiService.HOME_BASE_URL);
        Call<String> call = iApiService.getCity();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = response.body();
                L.d("城市选择的数据："+body);
                try {
                    //通过CityJsonUtils解析，得到数据集合
                    List<CityEntity> cityByJson = CityJsonUtils.getCityByJson(body);
                    listener.getDataSuccess(cityByJson);
                    data.clear();
                    data.addAll(cityByJson);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                L.d("城市数据获取错误！！！！！！！！！1"+call.toString());

            }
        });

        return data;
    }
}
