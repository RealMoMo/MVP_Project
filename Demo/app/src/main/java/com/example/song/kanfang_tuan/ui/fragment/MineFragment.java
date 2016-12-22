package com.example.song.kanfang_tuan.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.song.kanfang_tuan.R;
import com.example.song.kanfang_tuan.base.BaseFragement;
import com.example.song.kanfang_tuan.ui.activity.QQLoginActivity;
import com.example.song.kanfang_tuan.utils.Constant;
import com.example.song.kanfang_tuan.widget.CircularBitmap;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragement {


    @BindView(R.id.iv_minefragment_headicon)
    ImageView ivMinefragmentHeadicon;
    @BindView(R.id.tv_minefragment_login)
    TextView tvMinefragmentLogin;
    @BindView(R.id.mine_setting)
    ImageView mineSetting;
    @BindView(R.id.myqb)
    ImageView myqb;
    @BindView(R.id.collention)
    ImageView collention;
    @BindView(R.id.group)
    ImageView group;
    @BindView(R.id.baoming)
    ImageView baoming;
    @BindView(R.id.app_commend)
    ImageView appCommend;

    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.iv_minefragment_headicon, R.id.tv_minefragment_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_minefragment_headicon:
            case R.id.tv_minefragment_login:{
                Intent intent = new Intent(getActivity(), QQLoginActivity.class);
                startActivityForResult(intent, Constant.QQLOGIN_REQUESTCODE);
            }
                break;
        }
    }


    //处理Loginactivity返回的登录数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null) {
            String headUrl = data.getStringExtra(Constant.LOGIN_ICON);
            String name = data.getStringExtra(Constant.LOGIN_NAME);
            Picasso.with(getContext()).load(headUrl).fit().transform(new CircularBitmap()).into(ivMinefragmentHeadicon);
            tvMinefragmentLogin.setText(name);
        }
    }
}
