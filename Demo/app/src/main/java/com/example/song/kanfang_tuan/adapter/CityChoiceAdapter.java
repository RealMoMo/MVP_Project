package com.example.song.kanfang_tuan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.song.kanfang_tuan.R;
import com.example.song.kanfang_tuan.bean.CityEntity;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by MAJIA on 2016/12/20.
 */

public class CityChoiceAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    List<CityEntity> data;
    LayoutInflater inflater;

    List<CityEntity> allData;

    public CityChoiceAdapter(Context context, List<CityEntity> data) {
        this.data = data;
        inflater = LayoutInflater.from(context);

        //==========集合的深复制和浅复制===================
        allData = new ArrayList<>();
        allData.addAll(data);
    }

    public void setAllData(List<CityEntity> data){
        allData.clear();
        allData.addAll(data);
    }

    //==============StickyListHeadersAdapter===============
    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeadViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.city_head_layout, parent, false);
            viewHolder = new HeadViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (HeadViewHolder) convertView.getTag();
        }
        //关键，绑定数据
        CityEntity bean = data.get(position);
        viewHolder.tv_head.setText(bean.getLettes());

        return convertView;
    }

    /**
     * 重点：返回头部分类id
     */
    @Override
    public long getHeaderId(int position) {
        return data.get(position).getTypeId();
    }

    class HeadViewHolder {
        TextView tv_head;

        public HeadViewHolder(View view) {
            tv_head = (TextView) view.findViewById(R.id.city_head);
        }
    }


    //============BaseAdapter===================================
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BeanViewHolder viewHolder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.city_item_layout, viewGroup, false);
            viewHolder = new BeanViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (BeanViewHolder) view.getTag();
        }
        //绑定数据
        CityEntity city = data.get(i);
        viewHolder.tv_cityname.setText(city.getCityname());
        return view;
    }

    class BeanViewHolder {
        TextView tv_cityname;

        public BeanViewHolder(View view) {
            tv_cityname = (TextView) view.findViewById(R.id.city_name);
        }
    }

    /**
     * @param strSearch 搜索的字符串
     */
    //检索方法
    public void search(String strSearch) {
//        //先清除展示的数据
//        data.clear();
//        //如果搜索字符串为null或长度为0,就加载所以数据
//        if (strSearch == null || strSearch.length() == 0) {
//            data.addAll(allData);
//        } else {
//            //搜索逻辑代码
//            for (CityEntity city : allData) {
//                //如果城市名中，包含有搜索的字符串，表示检索到了
//                if (city.getCityname().contains(strSearch)) {
//                    data.add(city);
//                }
//            }
//        }
//        //把搜索的结果设置给自己，再更新新界面
//        notifyDataSetChanged();

        //先清除展示的数据
        data.clear();
        //如果搜索字符串为null或长度为0，就加载所有数据
        if(strSearch==null||strSearch.length()==0){
            data.addAll(allData);
        }else {
            //搜索逻辑代码
            //if else if顺序不能乱
            for (CityEntity city : allData) {
                //(中文搜索)如果城市名中，包含有搜索的字符串，表示检索到了
                if (city.getCityname().contains(strSearch)) {
                    data.add(city);
                    //拼音首字母搜索
                }else if(city.getCitywordheadpinyin().contains(strSearch)){
                    data.add(city);
                    //单个拼音字完全匹配搜索
                }else if(searchOneWord(city,strSearch)){
                    data.add(city);
                    //完全匹配搜索
                }else if(searchAllWord(city,strSearch)){
                    data.add(city);
                }


            }


        }
        //把搜索的结果设置给自己，再更新界面
        notifyDataSetChanged();

    }



    private boolean searchAllWord(CityEntity bean,String strSearch){
        String cityName = bean.getCitypinyin().replace(" ","");

        return  isSame(cityName,strSearch);

    }

    private boolean searchOneWord(CityEntity bean,String strSearch){
        boolean flag;
        String[] cityName = bean.getCitypinyin().split(" ");
        for (int i=0;i<cityName.length;i++) {


            if(isSame(cityName[i],strSearch)==true){
                return true;
            }
        }
        return false;
    }

    //完全匹配字符串方法
    private boolean isSame(String word,String strSearch){
        String matchWord = word.substring(0,word.length()<strSearch.length()?word.length():strSearch.length());
        return matchWord.equals(strSearch);
    }


}
