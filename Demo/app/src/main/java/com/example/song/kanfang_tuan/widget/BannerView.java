package com.example.song.kanfang_tuan.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.song.kanfang_tuan.R;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

/**
 * 架包：https://github.com/youth5201314/banner
 *
 * 自定义Banner控件(该自定义控件若要绑定view，需要继承ViewGroup或其子类)
 * <p/>
 * 实现自动轮播效果
 * <p/>
 * 提供图片地址集合和图片标题集合，就能自动获取数据，得到图片，进行轮播
 */
public class BannerView extends FrameLayout{

    Banner banner;

    public BannerView(Context context) {
        super(context);
        init();
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        //加载布局  参数3：true xml定义banner与本自定义view绑定在一起
        LayoutInflater.from(getContext()).inflate(R.layout.home_banner_layout, this,true);
        //初始化控件
        banner = (Banner)findViewById(R.id.banner_home);
        //设置banner风格
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new BannerImageLoader());
        //设置图片集合
//        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.FlipHorizontal);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(Arrays.asList(titles));
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);


    }


    class BannerImageLoader extends ImageLoader{

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //把path转换成ImageView
            Picasso.with(getContext()).load((String)path).into(imageView);
        }
    }


    private String currentCityId;


    public void setBannerData(List<String> imageurls, List<String> titles){

        //设置banner的图片集合及标题集合
        banner.setImages(imageurls);
        banner.setBannerTitles(titles);
        //banner配置完成，开始轮播
        banner.start();

    }

}

