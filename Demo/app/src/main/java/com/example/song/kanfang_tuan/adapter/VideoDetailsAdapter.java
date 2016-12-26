package com.example.song.kanfang_tuan.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.song.kanfang_tuan.R;
import com.example.song.kanfang_tuan.base.BaseAdapter;
import com.example.song.kanfang_tuan.bean.DetailsBean;
import com.example.song.kanfang_tuan.widget.CircularBitmap;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/12/21 0021.
 */

public class VideoDetailsAdapter extends BaseAdapter<DetailsBean> {

    Context context;
    List<DetailsBean> data;

    public VideoDetailsAdapter(Context context, List<DetailsBean> data, int layoutId) {
        super(context, data, layoutId);
        this.context = context;
        this.data = data;
    }

    @Override
    public void bindData(int position, ViewHolder mHolder) {
        DetailsBean listBean = data.get(position);
        View item = mHolder.mView;

        //头像
        ImageView iv_comment_head = (ImageView) item.findViewById(R.id.iv_comment_head_y);
        String img_uri = listBean.getProfile_image();
        if (!img_uri.endsWith("gif")) {
            Picasso.with(context).load(img_uri).transform(new CircularBitmap()).into(iv_comment_head);
        }

        //性别
        ImageView iv_sex = (ImageView) item.findViewById(R.id.iv_sex_y);
        if (listBean.getSex().equals("m")) {
            iv_sex.setImageResource(R.drawable.man);
        } else if (listBean.getSex().equals("f")) {
            iv_sex.setImageResource(R.drawable.woman);
        }

        //昵称
        TextView tv_comment_name = (TextView) item.findViewById(R.id.tv_comment_name_y);
        tv_comment_name.setText(listBean.getUsername());
        if (listBean.is_vip()) {
            tv_comment_name.setTextColor(Color.RED);
        }

        TextView tv_tv1 = (TextView) item.findViewById(R.id.tv_comment_tv1_y);
        tv_tv1.setText(listBean.getTotal_cmt_like_count());

        TextView tv_comment_context = (TextView) item.findViewById(R.id.tv_comment_context_y);
        tv_comment_context.setText(listBean.getContent());

    }
}
