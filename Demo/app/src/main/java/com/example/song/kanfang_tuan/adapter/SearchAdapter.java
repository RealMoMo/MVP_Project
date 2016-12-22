package com.example.song.kanfang_tuan.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.song.kanfang_tuan.R;
import com.example.song.kanfang_tuan.base.BaseAdapter;
import com.example.song.kanfang_tuan.bean.HomeSearchEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/12/22 0022.
 */

public class SearchAdapter extends BaseAdapter<HomeSearchEntity.DataEntity> {

    List<HomeSearchEntity.DataEntity> data;
    Context context;

    public SearchAdapter(Context context, List<HomeSearchEntity.DataEntity> data, int... layoutId) {
        super(context, data, layoutId);
        this.context = context;
        this.data =data;
    }

    @Override
    public void bindData(int position, ViewHolder mHolder) {
        HomeSearchEntity.DataEntity entity = data.get(position);
        //图片
        ImageView iv = (ImageView) mHolder.mView.findViewById(R.id.iv_search);
        Picasso.with(context).load(entity.getFcover()).into(iv);
        //标题
        TextView tv_title = (TextView) mHolder.mView.findViewById(R.id.tv_search_title);
        tv_title.setText(entity.getFname());
        //地区
        TextView tv_place = (TextView) mHolder.mView.findViewById(R.id.tv_search_place);
        tv_place.setText(entity.getFregion());
        //价格
        TextView tv_price = (TextView) mHolder.mView.findViewById(R.id.tv_search_price);
        tv_price.setText(entity.getFpricedisplaystr());
        //地址
        TextView tv_address = (TextView) mHolder.mView.findViewById(R.id.tv_search_address);
        tv_address.setText(entity.getFaddress());
        //特点
        int len = entity.getBookmark().size();
        List<HomeSearchEntity.DataEntity.BookmarkEntity> bookmark = entity.getBookmark();
        //避免视图复用，显示前面没有的内容
        TextView tv_type1 = (TextView) mHolder.mView.findViewById(R.id.tv_search_type1);
        TextView tv_type2 = (TextView) mHolder.mView.findViewById(R.id.tv_search_type2);
        TextView tv_type3 = (TextView) mHolder.mView.findViewById(R.id.tv_search_type3);
        tv_type1.setVisibility(View.INVISIBLE);
        tv_type2.setVisibility(View.INVISIBLE);
        tv_type3.setVisibility(View.INVISIBLE);
        for (int i = 0; i < len; i++) {
            if(i==0){
                tv_type1.setText(bookmark.get(0).getTag());
                tv_type1.setVisibility(View.VISIBLE);
            }else if(i==1){
                tv_type2.setText(bookmark.get(1).getTag());
                tv_type2.setVisibility(View.VISIBLE);
            }else if(i==2){
                tv_type3.setText(bookmark.get(2).getTag());
                tv_type3.setVisibility(View.VISIBLE);
            }
        }

    }
}
