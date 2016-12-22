package com.example.song.kanfang_tuan.presenter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.song.kanfang_tuan.MyApp;
import com.example.song.kanfang_tuan.R;
import com.example.song.kanfang_tuan.base.BaseAdapter;
import com.example.song.kanfang_tuan.bean.HomeBannerEntity;
import com.example.song.kanfang_tuan.bean.HomeContentEntity;
import com.example.song.kanfang_tuan.iface.OnHomeContentLoadLinstener;
import com.example.song.kanfang_tuan.model.HomeFragmentModel;
import com.example.song.kanfang_tuan.model.Imodel.IHomeFragmentModel;
import com.example.song.kanfang_tuan.presenter.Ipresenter.IHomeFragmentPresenter;
import com.example.song.kanfang_tuan.ui.fragment.HomeFragment;
import com.example.song.kanfang_tuan.utils.L;
import com.example.song.kanfang_tuan.widget.BannerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/19 0019.
 */

public class HomeFragmentPresenter implements IHomeFragmentPresenter,OnHomeContentLoadLinstener{

    //model对象
    private IHomeFragmentModel iHomeFragmentModel;
    //homefragment对象--主要是获取homefragment的控件
    private HomeFragment iHomeFragment;

    private BaseAdapter<HomeContentEntity.DataEntity> adapter;
    private ListView listView;
    private List<HomeContentEntity.DataEntity> data;

    private List<HomeBannerEntity.DataEntity>  bannerData;
    private BannerView bannerView;


    public HomeFragmentPresenter(HomeFragment iHomeFragment) {
        this.iHomeFragment = iHomeFragment;
        iHomeFragmentModel = new HomeFragmentModel(this);
        //初始化数据
        bannerData = new ArrayList<>();
        data = new ArrayList<>();
        //初始化adapter
        adapter = new HomeAdapter(iHomeFragment.getContext(),data,
                R.layout.home_listview_layout1,R.layout.home_listview_layout2,R.layout.home_listview_layout3);
        //listview设置adapter
        iHomeFragment.lv.setAdapter(adapter);
    }


    @Override
    public List<HomeContentEntity.DataEntity> getData(String cityid,int pageflag) {
        //下拉刷新时，把之前的数据清空
        if(pageflag==0){
            data.clear();
        }
        data.addAll(iHomeFragmentModel.getHomeContent(cityid,pageflag));
        adapter.notifyDataSetChanged();
        //刷新结束
        iHomeFragment.refresh.setRefreshing(false);
        return data;

    }

    @Override
    public void addBannerView(String cityId) {
        //获取广告数据
        iHomeFragmentModel.getHomeBannerContent(cityId);
    }

    //listview item点击回调的方法，获取该item的url数据
    @Override
    public String getItemUrl(int positon) {
        return data.get(positon).getSurl();
    }

    //保存cityInfo
    @Override
    public void saveCityInfo(String cityName, String cityId) {
        iHomeFragmentModel.saveCityInfo(cityName,cityId);
    }

    //获取cityInfo
    @Override
    public String[] getCityInfo() {

        return iHomeFragmentModel.getCityInfo();
    }

    //获取新闻数据成功回调的方法
    @Override
    public void getDataSuccess(List<HomeContentEntity.DataEntity> list) {
        data.addAll(list);
        adapter.notifyDataSetChanged();
    }

    //获取广告数据成功回调的方法
    @Override
    public void getBannerSuccess(List<HomeBannerEntity.DataEntity> list) {
        //存放图片地址的集合
        List<String> imgUrls = new ArrayList<String>();
        //存放图片标题的集合
        List<String> titles = new ArrayList<String>();

        for(HomeBannerEntity.DataEntity entity :list){
            imgUrls.add(entity.getPicurl());
            titles.add(entity.getTitle());
        }
        HomeFragment iHomeFragment = (HomeFragment) this.iHomeFragment;
        //通过BannerView的公开方法，设置图片、标题并开始轮播广告
        iHomeFragment.homeBannerView.setBannerData(imgUrls,titles);

    }

    @Override
    public void getDataFail() {
        L.s("getDataFail");
    }

    @Override
    public void noNetworking() {
        L.s("noNetworking");
    }


    /**
     * HomeAdapter处理HomeContent数据
     */
    class HomeAdapter extends BaseAdapter<HomeContentEntity.DataEntity> {

        public HomeAdapter(Context context, List<HomeContentEntity.DataEntity> data, int... layoutId) {
            super(context, data, layoutId);
        }

        @Override
        public void bindData(final int position, ViewHolder mHolder) {

            HomeContentEntity.DataEntity dataEntity = data.get(position);
            int viewType = dataEntity.getType();
            switch (viewType){
                //单图
                case 0:{
                    //图片
                    ImageView iv = (ImageView) mHolder.mView.findViewById(R.id.iv_home_lv1);
                    Picasso.with(MyApp.getApp()).load(dataEntity.getGroupthumbnail()).into(iv);
                    //标题
                    TextView tv_title = (TextView) mHolder.mView.findViewById(R.id.tv_home_lv1_title);
                    tv_title.setText(dataEntity.getTitle());
                    //内容
                    TextView tv_summary = (TextView) mHolder.mView.findViewById(R.id.tv_home_lv1_summary);
                    tv_summary.setText(dataEntity.getSummary());
                    //评论数
                    TextView tv_comment = (TextView) mHolder.mView.findViewById(R.id.tv_home_lv1_comment);
                    tv_comment.setText(""+dataEntity.getCommentcount()+"评");
                }break;
                //多图
                case 1:{
                    //图片
                    ImageView iv = (ImageView) mHolder.mView.findViewById(R.id.iv_home_lv2);
                    Picasso.with(MyApp.getApp()).load(dataEntity.getGroupthumbnail()).into(iv);
                    //标题
                    TextView tv_title = (TextView) mHolder.mView.findViewById(R.id.tv_home_lv2_title);
                    tv_title.setText(dataEntity.getTitle());
                    TextView tv_comment = (TextView) mHolder.mView.findViewById(R.id.tv_home_lv2_comment);
                    tv_comment.setText(""+dataEntity.getCommentcount()+"评");
                }break;
                //专题
                case 2:{
                    //图片
                    ImageView iv = (ImageView) mHolder.mView.findViewById(R.id.iv_home_lv3);
                    Picasso.with(MyApp.getApp()).load(dataEntity.getGroupthumbnail()).into(iv);
                    //标题
                    TextView tv_title = (TextView) mHolder.mView.findViewById(R.id.tv_home_lv3_title);
                    tv_title.setText(dataEntity.getTitle());
                    //内容
                    TextView tv_summary = (TextView) mHolder.mView.findViewById(R.id.tv_home_lv3_summary);
                    tv_summary.setText(dataEntity.getSummary());
                    //评论数
                    TextView tv_comment = (TextView) mHolder.mView.findViewById(R.id.tv_home_lv3_comment);
                    tv_comment.setText(""+dataEntity.getCommentcount()+"评");
                }break;

            }


        }

        @Override
        public int getItemViewType(int position) {
            //返回当前加载view的类型
            return data.get(position).getType();
        }
    }



}
