package com.example.song.kanfang_tuan.api;

import com.example.song.kanfang_tuan.bean.HomeBannerEntity;
import com.example.song.kanfang_tuan.bean.HomeContentEntity;
import com.example.song.kanfang_tuan.bean.HomeSearchEntity;
import com.example.song.kanfang_tuan.bean.MVideoBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by 11355 on 2016/12/19.
 */

public interface IApiService {

    /**
     * 百思不得姐的baseuri
     */
    public static final String BAISI_BASE_URL = "http://s.budejie.com/";
    /**
     * 百思不得姐的视频接口
     */
    public static final String VIDEO_URL = "topic/list/jingxuan/41/budejie-android-6.6.2/0-{json}.json?market=guanwang&udid=861619000088224&appname=baisibudejie&os=4.2.2&client=android&visiting=&mac=00%3A08%3A22%3A2c%3A0f%3A5e&ver=6.6.2";

    //方法   有一个参数
    @GET(VIDEO_URL)
    Call<MVideoBean> getVideoData(@Path("json") int json);


    /**
     * 百思不得姐视频详情界面
     */
    public static final String BAISI_DETAILS_BASEURL = "http://c.api.budejie.com/";
    /**
     * 百思不得姐的视频详情及评论界面数据接口
     */
    public static final String DETAILS_URL = "topic/comment_list/{video_id}/0/budejie-android-6.6.1/0-{number}.json?";
    //方法
    @GET(DETAILS_URL)
    Call<String> getDetailsData(@Path("video_id") String video_id, @Path("number") int number);


    //================homefragment=========================

    //主机地址，域名
    public static final String HOME_BASE_URL = "http://ikft.house.qq.com/";

    //城市选择
    public static final String CITY_CHOICE = "index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&act=kftcitylistnew&channel=71&devid=866500021200250&appname=QQHouse&mod=appkft";
    @GET(CITY_CHOICE)
    Call<String> getCity();

    /**
     * 首页 ListView内容
     * <p>
     * 进入或刷新：
     * pageflag=0
     * buttonmore=0
     * ------------
     * 加载更多：
     * pageflag=1
     * buttonmore=0
     * lastid  为之前加载数据的最后一个item的id
     * <p>
     * <p>
     * 2个参数： pageflag lastid  刷新的时候lastid=0即可
     */
    public static final String HOME_CONTENT = "index.php?act=newslist&mod=appkft&devua=appkft_1080_1920_XiaomiMI4LTE_2.3_Android23&appname=QQHouse&guid=866500027180423&devid=866500027180423&huid=H999116942&majorversion=v2&reqnum=20&channel=65&buttonmore=0";


    /**
     * 获取首页的Banner数据
     * <p/>
     * cityid是可变
     * <p>
     * 参数：cityid
     */
    public static final String HOME_BANNER = "index.php?act=homepage&mod=appkft&devua=appkft_1080_1920_XiaomiMI4LTE_2.3_Android23&appname=QQHouse&guid=866500027180423&devid=866500027180423&huid=H999116942&channel=65";


    //首页广告轮播
    @GET(HOME_BANNER)
    Call<HomeBannerEntity> getHomeBannerContent(@Query("cityid") String cityid);

    //首页listview
    @GET(HOME_CONTENT)
    Call<HomeContentEntity> getHomeListViewContent(@Query("cityid") String cityId,
                                                   @Query("pageflag") int pageflag,
                                                   @Query("lastid") String lasid);



    /**
     * homefragment点击搜索
     * 搜索结果列表（点击搜索之后获得）
     *
     * 请求参数：cityid ,keyword,page
     */
    public static final String HOUSE_SEARCH_RESULT = "index.php?act=searchhouse&mod=appkft&order=0&devua=appkft_1080_1920_XiaomiMI4LTE_2.3_Android23&appname=QQHouse&guid=866500027180423&devid=866500027180423&huid=H999116942&majorversion=v2&rn=10&channel=65&searchtype=normal";

    /**
     * 首页的楼盘搜索
     *
     * @param cityId
     * @param keyword
     * @param page
     * @return
     */
    @GET(HOUSE_SEARCH_RESULT)
    Call<HomeSearchEntity> getHomeSearchContent(@Query("cityid")String cityId,
                                                @Query("keyword")String keyword,
                                                @Query("page")int page);



    /**
     * 搜索界面的listview_item的楼盘详情(暂不写，在HouseDetailActivity用webview展示)
     *
     * 其baseurl ="http://ikft.house.qq.com/";
     *
     * 请求参数：hid=163944   对应的是SearchContentEntitiy.DataEntity fid信息
     */
    public static final String SEARCH_HOUSE_DETAIL="index.php?act=houseinfo&mod=appkft&devua=appkft_1080_1920_XiaomiMI4LTE_2.3_Android23&appname=QQHouse&guid=866500027180423&devid=866500027180423&huid=H999116942&channel=65&majorversion=v2";

//===============================================
    /**
     * 获取首页Listview_item的资讯详情baseUrl
     * <p/>
     */
    public static final String HOME_ITEM_URL = "http://m.house.qq.com/a/";


    /**
     * 获取首页Listview_item的资讯详情
     * <p/>
     */
    public static final String HOUSE_DETAIL = "index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&devid=866500021200250&appname=QQHouse&mod=appkft&act=newsdetail&channel=71";


}
