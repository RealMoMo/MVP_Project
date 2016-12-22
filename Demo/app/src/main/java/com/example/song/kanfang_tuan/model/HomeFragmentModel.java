package com.example.song.kanfang_tuan.model;

import com.example.song.kanfang_tuan.MyApp;
import com.example.song.kanfang_tuan.api.ApiManager;
import com.example.song.kanfang_tuan.api.IApiService;
import com.example.song.kanfang_tuan.bean.HomeBannerEntity;
import com.example.song.kanfang_tuan.bean.HomeContentEntity;
import com.example.song.kanfang_tuan.iface.OnHomeContentLoadLinstener;
import com.example.song.kanfang_tuan.model.Imodel.IHomeFragmentModel;
import com.example.song.kanfang_tuan.utils.SharedUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/12/19 0019.
 */

public class HomeFragmentModel implements IHomeFragmentModel {


    OnHomeContentLoadLinstener listener;
    //listview 总数据集合（需要其最后数据的lastid）
    List<HomeContentEntity.DataEntity> homeContentList;
    //listview加载的最新数据集合
    List<HomeContentEntity.DataEntity> tempList;
    //广告数据集合
    List<HomeBannerEntity.DataEntity>  homeBannerList;

    public HomeFragmentModel(OnHomeContentLoadLinstener listener) {
        this.listener= listener;
        homeContentList = new ArrayList<>();
        tempList = new ArrayList<>();
        homeBannerList = new ArrayList<>();
    }




    //保存城市信息
    @Override
    public void saveCityInfo(String cityName, String cityId) {
        SharedUtils.saveCityInfo(MyApp.getApp(),cityName,cityId);
    }

    //获取城市信息
    @Override
    public String[] getCityInfo() {
        return SharedUtils.getCityInfo(MyApp.getApp());
    }

    //保存加载的数据(做缓存时补充)
    @Override
    public void saveHomeContent(HomeContentEntity entity) {

    }


    //获取listview内容
    @Override
    public List<HomeContentEntity.DataEntity> getHomeContent(String cityId,int pageflag) {
        tempList.clear();
        String lastId="0";
        //若是加载更多，获取数据集合最后一个的id
        if(pageflag==1){
            lastId = homeContentList.get(homeContentList.size()-1).getId();
        }else{
            //若下拉刷新，清空数据
            homeContentList.clear();
        }

        IApiService iApiService = ApiManager.creatApi(IApiService.HOME_BASE_URL);
        Call<HomeContentEntity> call = iApiService.getHomeListViewContent(cityId,pageflag,lastId);

        call.enqueue(new Callback<HomeContentEntity>() {
            @Override
            public void onResponse(Call<HomeContentEntity> call, Response<HomeContentEntity> response) {

                List<HomeContentEntity.DataEntity> data = response.body().getData();
                if(data!=null){
                    for(HomeContentEntity.DataEntity i : data){
                        //专题数据返回的type为100，将其转为2。（因为单图和多图的type分别为0和1）
                        if(i.getType()==100){
                            i.setType(2);

                        }
                    }
                    //数据添加到集合中
                    tempList.addAll(data);
                    homeContentList.addAll(tempList);
                    //回调，获取数据成功
                    listener.getDataSuccess(tempList);

                }
                else{
                    //回调，获取数据失败
                    listener.getDataFail();
                }
            }

            @Override
            public void onFailure(Call<HomeContentEntity> call, Throwable t) {
                //回调，服务器等问题获取失败
                listener.noNetworking();
            }
        });

        return tempList;

    }

    //保存广告轮播加载的数据(做缓存时补充)
    @Override
    public void saveBannerContent(HomeBannerEntity entity) {

    }

    //获取广告内容
    @Override
    public List<HomeBannerEntity.DataEntity> getHomeBannerContent(String cityId) {

        homeBannerList.clear();

        IApiService iApiService = ApiManager.creatApi(IApiService.HOME_BASE_URL);
        Call<HomeBannerEntity> call = iApiService.getHomeBannerContent(cityId);
        call.enqueue(new Callback<HomeBannerEntity>() {
            @Override
            public void onResponse(Call<HomeBannerEntity> call, Response<HomeBannerEntity> response) {
                List<HomeBannerEntity.DataEntity> data = response.body().getData();

                if(data!=null){

                    homeBannerList.addAll(data);
                    listener.getBannerSuccess(homeBannerList);
                }
                else{
                    listener.getDataFail();
                }
            }

            @Override
            public void onFailure(Call<HomeBannerEntity> call, Throwable t) {
                listener.noNetworking();
            }
        });


        return homeBannerList;


    }
}
