package com.example.song.kanfang_tuan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.example.song.kanfang_tuan.R;
import com.example.song.kanfang_tuan.adapter.CityChoiceAdapter;
import com.example.song.kanfang_tuan.bean.CityEntity;
import com.example.song.kanfang_tuan.presenter.CityChoicePresenter;
import com.example.song.kanfang_tuan.presenter.Ipresenter.ICityChoicePresenter;
import com.example.song.kanfang_tuan.ui.fragment.HomeFragment;
import com.example.song.kanfang_tuan.utils.Constant;
import com.example.song.kanfang_tuan.widget.CitySlideLetterView;
import com.example.song.kanfang_tuan.widget.CitySlideView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class CityChoiceActivity extends NoActionBarActivity implements ICityChoiceActivity, AdapterView.OnItemClickListener, CitySlideView.SlideClick {

    @BindView(R.id.city_choice_cancel)
    Button cityChoiceCancel;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.lv)
    StickyListHeadersListView lv;
    @BindView(R.id.slideView)
    CitySlideView slideView;
    @BindView(R.id.letterView)
    CitySlideLetterView letterView;

    ICityChoicePresenter iCityChoicePresenter;
    private List<CityEntity> data;

    //    搜索的字符串
    String str_search;
    //    延迟搜索的handler
    Handler handler = new Handler();

    //搜索的runnable
    Runnable serachRunnable = new Runnable() {
        @Override
        public void run() {
            //搜索的代码
            iCityChoicePresenter.searchCity(str_search);
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_choice);
        ButterKnife.bind(this);
        lv.setOnItemClickListener(this);

        //创建ICityChoicePresenter实例
        iCityChoicePresenter = new CityChoicePresenter(this);
        //获取数据(要设置为全局变量，不然城市选择的侧滑栏会崩)
        data = iCityChoicePresenter.getCityData();

        //设置侧滑控件的监听器
        slideView.setOnSlideClick(this);
    }


    @OnClick(R.id.city_choice_cancel)
    public void onClick() {
        finish();
    }

    @Override
    public void setCityChoiceAdapter(CityChoiceAdapter cityChoiceAdapter) {
        lv.setAdapter(cityChoiceAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent();
        //设置数据
        intent.putExtra(Constant.KEY_CITYNAME, data.get(i).getCityname());
        intent.putExtra(Constant.KEY_CITYID, data.get(i).getCityid());
        setResult(HomeFragment.INTENT_REQUEST_CITY, intent);
        //结束
        finish();
    }

    @Override
    public void slideOnClick(int position, String str) {
        //侧滑时回调方法
        //letterView显示出来
        //设置letterView的text
        letterView.setVisibility(View.VISIBLE);
        letterView.setText(str);
        //listView滑动到指定位置
        //从data集合中找到第一条数据，以str开头的数据的位置
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getLettes().equals(str)) {
                //把listView滑动到i的位置
                lv.setSelection(i);
                //找到返回
                return;
            }
        }
    }

    @Override
    public void slideUp() {
        //侧滑控件手指抬起时
        letterView.setVisibility(View.GONE);
    }


    /**
     * 输入框的监听绑定
     */

    @OnTextChanged(value = R.id.et_search, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterTextChanged(Editable editable) {
        //调用搜索方法,延迟搜索，减少误输入搜索
        //设置搜索字符串
        str_search = editable.toString();
        //清掉前面的搜索
        handler.removeCallbacks(serachRunnable);
        //延迟搜索
        handler.postDelayed(serachRunnable, 500);
    }

    /**
     * 当触摸屏幕时把输入法隐藏
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(CityChoiceActivity.this
                            .getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return super.onTouchEvent(event);
    }
}
