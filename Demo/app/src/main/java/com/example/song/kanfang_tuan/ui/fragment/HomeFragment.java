package com.example.song.kanfang_tuan.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.song.kanfang_tuan.R;
import com.example.song.kanfang_tuan.presenter.HomeFragmentPresenter;
import com.example.song.kanfang_tuan.presenter.Ipresenter.IHomeFragmentPresenter;
import com.example.song.kanfang_tuan.ui.activity.CityChoiceActivity;
import com.example.song.kanfang_tuan.ui.activity.HomeDetailActivity;
import com.example.song.kanfang_tuan.ui.activity.HomeQRActivity;
import com.example.song.kanfang_tuan.ui.activity.HomeSearchActivity;
import com.example.song.kanfang_tuan.utils.Constant;
import com.example.song.kanfang_tuan.widget.BannerView;
import com.example.song.kanfang_tuan.widget.HomeSecondView;

import static com.example.song.kanfang_tuan.R.id.tv_cityname;

/**
 * HomeFragment
 */
public class HomeFragment extends Fragment implements View.OnClickListener, AbsListView.OnScrollListener, AdapterView.OnItemClickListener {

    /**
     * 跳转到城市选择界面
     */
   public static final int INTENT_REQUEST_CITY = 5;

    //控件
    private TextView tv_cityName;
    private TextView tv_search;
    private ImageView iv_scan;
    private HomeSecondView homeSecondView;
    /**
     * 这3个控件public 是要presenter处理它们的相关事务
     * 设置adapter
     * 刷新完成
     * BannerView展示的数据
     */
    public  SwipeRefreshLayout refresh;
    public  ListView lv;
    public BannerView homeBannerView;

    //是否加载更多的标记
    private boolean isAddMore;

    /**
     *
     * 首页 ListView内容
     *
     * 进入或刷新：
     * pageflag=0
     * 原有数据清空
     *
     * ------------
     * 加载更多：
     * pageflag=1
     * lastid  为之前加载数据的最后一个item的id

     */
    private int pageflag;


    private String cityId="1";
    private String cityName;

    private  IHomeFragmentPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化自定义控件
        homeBannerView = new BannerView(getContext());
        homeSecondView = new HomeSecondView(getContext());
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //初始化控件
        setupViews(view);

        presenter = new HomeFragmentPresenter(this);

        //从SharedPreferences获取原先的城市选择信息
        String[] cityInfo = presenter.getCityInfo();
        //设置cityName
        tv_cityName.setText(cityInfo[0]);
        //获取cityid
        cityId = cityInfo[1];

        //通过presenter接口的实现类，获取Banner内容并加载到BannerView
        presenter.addBannerView(cityId);

        //通过presenter接口的实现类，获取listview内容
        presenter.getData(cityId,pageflag);

    }

    private void setupViews(View view) {
        tv_cityName  = (TextView) view.findViewById(tv_cityname);
        tv_search  = (TextView) view.findViewById(R.id.tv_search);
        iv_scan  = (ImageView) view.findViewById(R.id.iv_scan);
        refresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh_header);
        lv = (ListView) view.findViewById(R.id.lv_content);
        //listview 添加头部控件
        lv.addHeaderView(homeBannerView,null,false);
        lv.addHeaderView(homeSecondView,null,false);
        //设置点击事件
        tv_cityName.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        iv_scan.setOnClickListener(this);
        //listview滚动监听---是否加载更多
        lv.setOnScrollListener(this);
        //listview item点击监听---跳转到item的详情内容界面
        lv.setOnItemClickListener(this);

        //下拉刷新监听
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageflag = 0 ;
                //重新获取广告与新闻的数据
                presenter.addBannerView(cityId);
                presenter.getData(cityId,pageflag);
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case INTENT_REQUEST_CITY: {
                //从城市选择界面返回
                if (data != null) {
                    //取出数据
                    //城市名称
                    cityName = data.getStringExtra(Constant.KEY_CITYNAME);
                    cityId = data.getStringExtra(Constant.KEY_CITYID);
                    presenter.saveCityInfo(cityName,cityId);
                    //把城市名设置给textView
                    tv_cityName.setText(cityName);
                    //设置城市
                    //获取数据
                    presenter.getData(cityId,0);
                    //设置banner随城市的变化
                    presenter.addBannerView(cityId);
                }
            }
            break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //跳转到城市选择
            case tv_cityname:{
                Intent intent = new Intent(getActivity(), CityChoiceActivity.class);
                startActivityForResult(intent,INTENT_REQUEST_CITY);
            }break;
            //跳转到搜索界面
            case R.id.tv_search:{
                Intent intent = new Intent(getActivity(), HomeSearchActivity.class);
                startActivity(intent);

            }break;
            //跳转到扫一扫界面
            case R.id.iv_scan:{
                Intent intent = new Intent(getActivity(), HomeQRActivity.class);
                startActivity(intent);

            }break;
        }

    }

    //listview加载更多
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(isAddMore&&scrollState==SCROLL_STATE_IDLE){
            //获取更多数据
            pageflag =1 ;
            presenter.getData(cityId,pageflag);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
       //listview滚动到底部,加载更多标志设置为true，否则为false
        if(firstVisibleItem+visibleItemCount==totalItemCount){
            isAddMore=true;
        }else{
            isAddMore=false;
        }
    }

    //listview item点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String itemUrl = presenter.getItemUrl(position-lv.getHeaderViewsCount());
        Intent intent = new Intent(getActivity(), HomeDetailActivity.class);
        intent.putExtra(Constant.HOME_DETAIL_KEY,itemUrl);
        startActivity(intent);
    }
}