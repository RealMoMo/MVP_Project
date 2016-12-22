package com.example.song.kanfang_tuan.utils;

import com.example.song.kanfang_tuan.bean.CityEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析城市数据
 */

public class CityJsonUtils {

    public static final String[] lettes = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    public static List<CityEntity> getCityByJson(String value) throws Exception {
        //结果数据集合
        ArrayList<CityEntity> data = new ArrayList<>();
        //最外层的json
        JSONObject json = new JSONObject(value);
        //拿到cities数据
        JSONObject cities = json.getJSONObject("cities");
        //取出对应字母的jsonArray
        //循环lettes数组，得到每个字母，，从cities中取出对应的jsonArray
        for (int i = 0; i < lettes.length; i++) {
            JSONArray arr = cities.optJSONArray(lettes[i]);
            //再循环遍历arr中的每个jsonObject
            if (arr != null) {
                for (int j = 0; j < arr.length(); j++) {
                    //取出对应的jsonObject
                    JSONObject tmp = arr.getJSONObject(j);
                    //构建CityEntity
                    CityEntity city = new CityEntity(tmp, i, lettes[i]);
                    //添加到集合中
                    data.add(city);
                }
            }
        }
        return data;
    }
}
