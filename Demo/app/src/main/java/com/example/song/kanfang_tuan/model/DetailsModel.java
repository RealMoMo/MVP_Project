package com.example.song.kanfang_tuan.model;

import com.example.song.kanfang_tuan.api.ApiManager;
import com.example.song.kanfang_tuan.bean.DetailsBean;
import com.example.song.kanfang_tuan.iface.OnDetailsLoadLinstener;
import com.example.song.kanfang_tuan.model.Imodel.IDetailsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.song.kanfang_tuan.api.IApiService.BAISI_DETAILS_BASEURL;

/**
 * Created by Administrator on 2016/12/19 0019.
 */

public class DetailsModel implements IDetailsModel {
    List<DetailsBean> data;
    OnDetailsLoadLinstener linstener;

    public DetailsModel(List<DetailsBean> data, OnDetailsLoadLinstener linstener) {
        this.data = data;
        this.linstener = linstener;
    }


    @Override
    public List<DetailsBean> getData(String video_id, final int startNUm, final int endNum) {
        data = new ArrayList<>();

        Call<String> call = ApiManager.creatStringApi(BAISI_DETAILS_BASEURL).getDetailsData(video_id, endNum);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = response.body();

                if (body == null) {
                    linstener.getDataFail();
                    return;
                }

                List<DetailsBean> list = string2Details(body, startNUm, endNum);
                if (list == null) {
                    linstener.getDataFail();
                    return;
                }
                data.clear();
                data.addAll(list);
                linstener.getDataSuccess(data);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                linstener.noNetworking();
            }
        });
        return data;
    }

    private List<DetailsBean> string2Details(String body, int startNum, int endNUm) {
        List<DetailsBean> data = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(body);
            JSONObject normal = jsonObject.getJSONObject("normal");
            JSONObject info = normal.getJSONObject("info");
            String count = info.getString("count");
            if (Integer.parseInt(count) < startNum) {
                return data;
            }
            JSONArray list = normal.getJSONArray("list");
            for (int i = startNum; i < list.length(); i++) {
                JSONObject object = list.getJSONObject(i);
                String content = object.getString("content");
                JSONObject user = object.getJSONObject("user");
                String username = user.getString("username");
                String profile_image = user.getString("profile_image");
                String total_cmt_like_count = user.getString("total_cmt_like_count");
                boolean is_vip = user.getBoolean("is_vip");
                String sex = user.getString("sex");

                DetailsBean detailsBean = new DetailsBean(content, username, profile_image, total_cmt_like_count, is_vip, sex);
                data.add(detailsBean);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

}
