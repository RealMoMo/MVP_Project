package com.example.song.kanfang_tuan.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by 11355 on 2016/12/19.
 */

public abstract class BaseFragement extends Fragment {
    protected View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutResource() != 0) {
            rootView = inflater.inflate(getLayoutResource(), null);
        } else {
            rootView = super.onCreateView(inflater, container, savedInstanceState);
        }
        ButterKnife.bind(this, rootView);
        this.onInitView(savedInstanceState);
        return rootView;
    }
    protected abstract int getLayoutResource();
    protected abstract void onInitView(Bundle savedInstanceState);

    protected BasePresenter mPresenter;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }
}
