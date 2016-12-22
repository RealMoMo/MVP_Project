package com.example.song.kanfang_tuan.model;

import com.example.song.kanfang_tuan.MyApp;
import com.example.song.kanfang_tuan.api.ApiManager;
import com.example.song.kanfang_tuan.api.IApiService;
import com.example.song.kanfang_tuan.bean.HomeSearchEntity;
import com.example.song.kanfang_tuan.iface.OnHomeSearchLoadListener;
import com.example.song.kanfang_tuan.model.Imodel.ISearchModel;
import com.example.song.kanfang_tuan.utils.SharedUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/12/22 0022.
 */

public class SearchModel implements ISearchModel{


    private List<HomeSearchEntity.DataEntity> totalList;
    private List<HomeSearchEntity.DataEntity> list;
    private OnHomeSearchLoadListener listener;



    public SearchModel(OnHomeSearchLoadListener listener) {
        totalList = new ArrayList<>();
        list = new ArrayList<>();
        this.listener = listener;
    }

    @Override
    public List<HomeSearchEntity.DataEntity> getSearchContent(String cityid, String keyword,int page) {
        //重新搜索，总数据清空
        if(page==1){
            totalList.clear();
        }

        //清空数据
        list.clear();

        IApiService iApiService = ApiManager.creatApi(IApiService.HOME_BASE_URL);
        Call<HomeSearchEntity> call = iApiService.getHomeSearchContent(cityid, keyword,page);
        call.enqueue(new Callback<HomeSearchEntity>() {
            @Override
            public void onResponse(Call<HomeSearchEntity> call, Response<HomeSearchEntity> response) {
                List<HomeSearchEntity.DataEntity> data = response.body().getData();
                if(data!=null&& data.size()>0){
                    //数据返回没有价格为0，将其数据改为价格待定
                    for(HomeSearchEntity.DataEntity i :data){
                        if(i.getFpricedisplaystr().equals("0")){
                            i.setFpricedisplaystr("价格待定");
                        }
                    }
                    list.addAll(data);
                    totalList.addAll(list);
                    listener.getSearchDataSuccess(list);
                }else{
                    listener.getDataFail();
                }
            }

            @Override
            public void onFailure(Call<HomeSearchEntity> call, Throwable t) {
                listener.noNetworking();
            }
        });
        return list;
    }

    @Override
    public String getCityId() {

        return SharedUtils.getCityInfo(MyApp.getApp())[1];
    }

    @Override
    public String getHouseFid(int position) {
        return totalList.get(position).getFid();
    }
}
