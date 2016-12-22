package com.example.song.kanfang_tuan.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    List<T> data;
    LayoutInflater mLayoutInflater;
    int[] layoutId;
    Context context;
    private ViewHolder mHolder;

    public BaseAdapter(Context context, List<T> data, int... layoutId) {
        this.data = data;
        this.layoutId = layoutId;
        this.context=context;
    }



    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView=LayoutInflater.from(context).inflate(layoutId[getItemViewType(position)],parent,false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        bindData(position,mHolder);
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return layoutId.length;
    }


    public class ViewHolder {
        public View mView;

        public ViewHolder(View view) {
            mView = view;
        }
    }

    public abstract void bindData(int position, ViewHolder mHolder);
}