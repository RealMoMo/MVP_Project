package com.example.song.kanfang_tuan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.song.kanfang_tuan.R;
import com.example.song.kanfang_tuan.presenter.Ipresenter.ISearchPresenter;
import com.example.song.kanfang_tuan.presenter.SearchPresenter;
import com.example.song.kanfang_tuan.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.song.kanfang_tuan.utils.Constant.SEARCH;

public class HomeSearchActivity extends NoActionBarActivity implements TextWatcher, AbsListView.OnScrollListener, AdapterView.OnItemClickListener {




    @BindView(R.id.search_edittext)
    public EditText searchEdittext;
    @BindView(R.id.search_clear_iv)
    public ImageView searchClearIv;
    @BindView(R.id.search_tv_cancel_search)
    public TextView searchTvCancelSearch;
    @BindView(R.id.search_lv)
    public ListView searchLv;
    @BindView(R.id.search_loading_iv)
    public ImageView searchLoadingIv;
    @BindView(R.id.search_loadingmore_iv)
    public ImageView searchLoadingmoreIv;


    private ISearchPresenter presenter;


    //editext的检索内容的长度
    private int searchTextLen;
    //检索的关键字
    private String keyWord;
    //是否加载更多的标识
    private boolean isAddMore = false;
    //cityid
    private String cityId;
    //加载的页数
    private int currentpage =1;


    //handler发runnable
    private Handler handler = new Handler();

    //根据edittext的文本，控制某些控件的是否可见
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (searchTextLen > 0) {
                searchTvCancelSearch.setText(SEARCH);
                searchClearIv.setVisibility(View.VISIBLE);
            } else {
                searchTvCancelSearch.setText(Constant.CANCEL);
                searchClearIv.setVisibility(View.INVISIBLE);
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);


        setupViews();
        presenter = new SearchPresenter(this);
        //获取cityid
        cityId= presenter.getCityId();
    }

    private void setupViews() {

        ButterKnife.bind(this);

        //默认是文本为取消
        searchTvCancelSearch.setText(Constant.CANCEL);

        //edittext文本变化监听
        searchEdittext.addTextChangedListener(this);

        //listview滚动监听
        searchLv.setOnScrollListener(this);
        //listview item点击事件监听
        searchLv.setOnItemClickListener(this);
    }

    @OnClick({R.id.search_clear_iv, R.id.search_tv_cancel_search})
    public void onClick(View view) {
        switch (view.getId()) {
            //清空edittext文本内容
            case R.id.search_clear_iv:{
                searchEdittext.setText("");
            }
                break;
            //搜索 or 取消 的动作
            case R.id.search_tv_cancel_search:{
                if(searchTvCancelSearch.getText().equals(SEARCH)){
                    hideKeyboard(view);
                    //设置控件不可点击(待数据加载完成，才可以点击)
                    view.setClickable(false);
                    //显示正在加载的图片
                    searchLoadingIv.setVisibility(View.VISIBLE);
                    //请求数据的方法
                    currentpage = 1;
                    presenter.getSearchData(cityId,keyWord = searchEdittext.getText().toString().trim(),currentpage);

                }else if(searchTvCancelSearch.getText().equals(Constant.CANCEL)){
                    finish();
                }
            }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s!=null) {
            searchTextLen = s.toString().length();
            //通过handler调用搜索方法,延迟搜索，减少误输入搜索对控件展示与否的重复下达指令。
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, 300);
        }
    }


    //隐藏键盘的方法
    private void hideKeyboard(View view) {
        view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(isAddMore&&scrollState==SCROLL_STATE_IDLE){
            //显示加载更多的图片
            searchLoadingmoreIv.setVisibility(View.VISIBLE);
            currentpage++;
            presenter.getSearchData(cityId,keyWord,currentpage);
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(firstVisibleItem+visibleItemCount==totalItemCount){
            isAddMore =true;
        }else{
            isAddMore =false;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String fid = presenter.getHouseFid(position);
        Intent intent = new Intent(this,HouseDetailActivity.class);
        intent.putExtra(Constant.FID,fid);
        startActivity(intent);
    }


}
