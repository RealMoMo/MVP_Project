package com.example.song.kanfang_tuan.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by 11355 on 2016/12/19.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected BasePresenter mPresenter;
    protected abstract void onInitView(Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutResource() != 0)
            setContentView(getLayoutResource());
        ButterKnife.bind(this);
        this.onInitView(savedInstanceState);
    }

    protected abstract int getLayoutResource();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();

    }
}
