package com.example.song.kanfang_tuan.model;

import com.example.song.kanfang_tuan.api.ApiManager;
import com.example.song.kanfang_tuan.api.IApiService;
import com.example.song.kanfang_tuan.bean.MVideoBean;
import com.example.song.kanfang_tuan.iface.OnVideoLoadLinstener;
import com.example.song.kanfang_tuan.model.Imodel.IVideoModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/12/19 0019.
 */

public class VideoModel implements IVideoModel {
    List<MVideoBean.ListBean> data;
    OnVideoLoadLinstener linstener;

    public VideoModel(OnVideoLoadLinstener linstener) {
        this.linstener = linstener;
        data = new ArrayList<>();
    }

    @Override
    public List<MVideoBean.ListBean> getData(final int start, int end) {

        IApiService iApiService = ApiManager.creatApi(IApiService.BAISI_BASE_URL);
        Call<MVideoBean> videoData = iApiService.getVideoData(end);
        videoData.enqueue(new Callback<MVideoBean>() {
            @Override
            public void onResponse(Call<MVideoBean> call, Response<MVideoBean> response) {
                MVideoBean body = response.body();
                List<MVideoBean.ListBean> list = body.getList();
                if (list != null && list.size() > 0) {
                    data.clear();
                    for (int i = start; i < list.size(); i++) {
                        data.add(list.get(i));
                    }
                    linstener.getDataSuccess(data);
                } else {
                    linstener.getDataFail();
                }
            }

            @Override
            public void onFailure(Call<MVideoBean> call, Throwable t) {
                linstener.noNetworking();
            }
        });
        return data;
    }


}
