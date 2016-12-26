package com.example.song.kanfang_tuan.ui.activity;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.song.kanfang_tuan.MyApp;
import com.example.song.kanfang_tuan.R;
import com.example.song.kanfang_tuan.adapter.VideoDetailsAdapter;
import com.example.song.kanfang_tuan.bean.MVideoBean;
import com.example.song.kanfang_tuan.presenter.DetailsPresenter;
import com.example.song.kanfang_tuan.presenter.Ipresenter.IDetailsPresenter;
import com.example.song.kanfang_tuan.utils.L;
import com.example.song.kanfang_tuan.utils.NetworkUtils;
import com.example.song.kanfang_tuan.utils.ShareToQQ;
import com.example.song.kanfang_tuan.widget.CircularBitmap;
import com.squareup.picasso.Picasso;

import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

import static android.R.attr.duration;
import static com.example.song.kanfang_tuan.R.id.iv_xiazai_y;
import static com.example.song.kanfang_tuan.R.id.vv_video_y;
import static com.example.song.kanfang_tuan.utils.Constant.INTENT_TYPE;
import static com.example.song.kanfang_tuan.utils.Constant.INTENT_VIDEO_KEY;
import static com.example.song.kanfang_tuan.utils.Constant.VIDEO_TYPE;
import static com.example.song.kanfang_tuan.utils.Constant.heightPixels;
import static com.example.song.kanfang_tuan.utils.Constant.widthPixels;


/**
 * Created by Administrator on 2016/12/19 0019.
 */

public class DetailsActivity extends NoActionBarActivity implements IDetailsActivity, View.OnClickListener, AbsListView.OnScrollListener, SeekBar.OnSeekBarChangeListener {


    private ListView lv_details;
    private View head_view;
    private VideoView vv_video;
    IDetailsPresenter iDetailsPresenter;

    String video_id;

    //每一次上拉加载数据的条数
    public static final int num = 5;
    //上拉加载的次数
    int numberOfpLoadMore = 1;
    //是否加载更多
    boolean isMoreData = false;

    /*vv_video.start();
        vv_video.setVideoURI();
        vv_video.resume();
        vv_video.pause();
        vv_video.isValid()*/

    private static final String VIDEO_URI = "videouri";
    private static final int SET_URI = -9;//开始播放
    private static final int START_PLAY_VIDEO = -10;//开始播放
    private static final int PAUSE_PLAY_VIDEO = -11;//暂停播放
    private static final int RESUMR_PLAY_VIDEO = -12;//恢复播放
    private static final int SEEK_TO_POSITION = -13;//设置视频进度
    private static final int SEEKBAR_SET_PROGRESS = -14;//进度条设置进度


    //下载视频，按钮设置tag
    private static final int TAG_VIDEO_DOWN = -21;
    //分享绑定内容
    private static final int TAG_SHARED = -22;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SET_URI: {
                    Bundle bundle = msg.getData();
                    String uri = bundle.getString(VIDEO_URI);
                    if (NetworkUtils.isNetworkLink()) {
                        vv_video.setVideoURI(Uri.parse(uri));
                        head_view.findViewById(R.id.iv_thumbnail_y).setVisibility(View.INVISIBLE);
                        head_view.findViewById(R.id.iv_playSing_y).setVisibility(View.INVISIBLE);
                        handler.sendMessage(handler.obtainMessage(SEEKBAR_SET_PROGRESS));
                    } else {
                        Toast.makeText(DetailsActivity.this, "网络不可用", Toast.LENGTH_LONG).show();
                    }
                }
                break;
                case START_PLAY_VIDEO: {
                    if (!vv_video.isPlaying()) {
                        vv_video.start();
                        handler.sendMessage(handler.obtainMessage(SEEKBAR_SET_PROGRESS));
                    }
                    head_view.findViewById(R.id.iv_thumbnail_y).setVisibility(View.INVISIBLE);
                    head_view.findViewById(R.id.iv_playSing_y).setVisibility(View.INVISIBLE);
                }
                break;
                case PAUSE_PLAY_VIDEO: {
                    if (vv_video.isPlaying()) {
                        vv_video.pause();
                        handler.removeMessages(SEEKBAR_SET_PROGRESS);
                        head_view.findViewById(R.id.iv_playSing_y).setVisibility(View.VISIBLE);
                    }
                }
                break;
                case RESUMR_PLAY_VIDEO: {
                    if (!vv_video.isPlaying()) {
                        vv_video.resume();
                        head_view.findViewById(R.id.iv_playSing_y).setVisibility(View.INVISIBLE);
                        handler.sendMessage(handler.obtainMessage(SEEKBAR_SET_PROGRESS));
                    }
                }
                break;
                case SEEK_TO_POSITION: {
                    int position = msg.arg1;
                    if (position >= 0 && position <= duration) {
                        vv_video.seekTo(position);
                        handler.sendMessage(handler.obtainMessage(SEEKBAR_SET_PROGRESS));
                    }
                }
                break;
                case SEEKBAR_SET_PROGRESS: {
                    int progress = current2progress();
                    sb_video_position.setProgress(progress);
                    ((TextView) head_view.findViewById(R.id.tv_nowTime_y)).setText(getTime(duration_y * progress / 100));
                    handler.sendMessageDelayed(handler.obtainMessage(SEEKBAR_SET_PROGRESS), 1000);
                }
                break;
            }
        }
    };
    private SeekBar sb_video_position;
    //获取下载参数,视频时长，单位：秒
    private int duration_y;
    private DownloadManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Vitamio.initialize(this);//vitanio 初始化
        manager = ((DownloadManager) getSystemService(DOWNLOAD_SERVICE));//初始化DownLoadManager
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        MVideoBean.ListBean listBean = intent.getParcelableExtra(INTENT_VIDEO_KEY);

        if (listBean == null) {
            L.d("listbean 为空");
            return;
        }
        video_id = listBean.getId();

        initViews(listBean);
        initDatas();
        getData(0, num * numberOfpLoadMore);

        //获取点击的对象的tag
        int type = intent.getIntExtra(INTENT_TYPE, 0);
        if (type == VIDEO_TYPE) {
            Message message = handler.obtainMessage(SET_URI);
            Bundle bundle = new Bundle();
            bundle.putString(VIDEO_URI, listBean.getVideo().getVideo().get(1));
            message.setData(bundle);
            handler.sendMessage(message);
        }
    }

    public void getData(int startNum, int endNum) {
        iDetailsPresenter.getData(video_id, startNum, endNum);
    }

    private void initDatas() {
        iDetailsPresenter = new DetailsPresenter(this);
    }

    private void initViews(MVideoBean.ListBean listBean) {
        lv_details = ((ListView) findViewById(R.id.lv_details_y));

        head_view = LayoutInflater.from(this).inflate(R.layout.video_details_head, null);

        lv_details.addHeaderView(head_view);

        initHead(listBean);
        lv_details.setOnScrollListener(this);
    }

    //设置adapter
    public void lvSetAdapter(VideoDetailsAdapter adapter) {
        lv_details.setAdapter(adapter);
    }

    //获取头部数量
    public int getLvHeadNumber() {
        return lv_details.getHeaderViewsCount();
    }

    public void initHead(MVideoBean.ListBean listBean) {

        ImageView iv_head = (ImageView) head_view.findViewById(R.id.iv_head_y);//头像图片
        String head_uri = listBean.getU().getHeader().get(0);
        if (!head_uri.endsWith("gif")) {
            Picasso.with(this).load(head_uri).transform(new CircularBitmap()).into(iv_head);
        }

        //判断是否为VIP
        boolean isVIP = listBean.getU().isIs_vip();
        TextView tv_name = (TextView) head_view.findViewById(R.id.tv_name_y);
        tv_name.setText(listBean.getU().getName());//昵称
        if (isVIP) {
            tv_name.setTextColor(Color.RED);
        }

        //下载按钮
        ImageView iv_xiazai = (ImageView) head_view.findViewById(iv_xiazai_y);
        String video_loaddown_url = listBean.getVideo().getDownload().get(0);
        iv_xiazai.setTag(TAG_VIDEO_DOWN, video_loaddown_url);

        TextView tv_time = (TextView) head_view.findViewById(R.id.tv_time_y);
        tv_time.setText(listBean.getPasstime());//发布时间

        TextView tv_title = (TextView) head_view.findViewById(R.id.tv_title_y);
        tv_title.setText(listBean.getText());//标题

        ImageView iv_thumbnail = (ImageView) head_view.findViewById(R.id.iv_thumbnail_y);
        String img_uri = listBean.getVideo().getThumbnail().get(0);
        if (!img_uri.endsWith("gif")) {
            Picasso.with(MyApp.getApp()).load(img_uri).into(iv_thumbnail);//缩略图
        }

        //设置缩略图和视频控件的宽高
        float v_width = listBean.getVideo().getWidth();
        float v_height = listBean.getVideo().getHeight();

        if (v_width > widthPixels) {
            v_height = widthPixels / v_width * v_height;
            v_width = widthPixels;
        } else {
            if (v_height > heightPixels / 2) {
                v_width = heightPixels / 2 / v_height * v_width;
                v_height = heightPixels / 2;
            } else {
                v_height = widthPixels / v_width * v_height;
                v_width = widthPixels;
            }
        }
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams((int) v_width, (int) v_height);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        iv_thumbnail.setLayoutParams(params);
        iv_thumbnail.requestLayout();

        //视频播放控件
        vv_video = (VideoView) head_view.findViewById(vv_video_y);
        vv_video.setLayoutParams(params);
        vv_video.requestLayout();


        //播放状态标记
        ImageView iv_playsing = (ImageView) head_view.findViewById(R.id.iv_playSing_y);

        //进度条
        sb_video_position = ((SeekBar) head_view.findViewById(R.id.sb_video_position_y));
        sb_video_position.setOnSeekBarChangeListener(this);

        TextView tv_playCount = (TextView) head_view.findViewById(R.id.tv_nowTime_y);
        tv_playCount.setText("00:00");//现在播放的时间

        //视频时长
        TextView tv_long = (TextView) head_view.findViewById(R.id.tv_allTime_y);
        duration_y = listBean.getVideo().getDuration();
        String time = getTime(duration_y);
        tv_long.setText(time);

        ImageView iv_likes = (ImageView) head_view.findViewById(R.id.iv_likes_y);
        TextView tv_likesNum = (TextView) head_view.findViewById(R.id.tv_likesNum_y);
        ImageView iv_bads = (ImageView) head_view.findViewById(R.id.iv_bads_y);
        TextView tv_badsNum = (TextView) head_view.findViewById(R.id.tv_badsNum_y);

        iv_likes.setImageResource(R.drawable.ding_not_clicked);
        tv_likesNum.setText(String.valueOf(listBean.getUp()));//点赞数
        tv_likesNum.setTextColor(Color.BLACK);

        iv_bads.setImageResource(R.drawable.cai_not_clicked);
        tv_badsNum.setText(String.valueOf(listBean.getDown()));//点不赞数
        tv_badsNum.setTextColor(Color.BLACK);

        ImageView iv_share = (ImageView) head_view.findViewById(R.id.iv_share_y);
        TextView tv_shareNum = (TextView) head_view.findViewById(R.id.tv_shareNum_y);
        tv_shareNum.setText(String.valueOf(listBean.getForward()));//分享数
        iv_share.setTag(TAG_SHARED, listBean);
        tv_shareNum.setTag(TAG_SHARED, listBean);

        TextView tv_commentNum = (TextView) head_view.findViewById(R.id.tv_commentNum_y);
        tv_commentNum.setText(String.valueOf(listBean.getComment()));//评论数


        //点赞和点不赞的两个动画图片，首先隐藏
        ImageView dianzan = (ImageView) head_view.findViewById(R.id.iv_dianzan);
        ImageView undianzan = (ImageView) head_view.findViewById(R.id.iv_undianzan);
        dianzan.setVisibility(View.INVISIBLE);
        undianzan.setVisibility(View.INVISIBLE);

        //控件设置可点击事件
        setClickListeners(iv_xiazai, iv_playsing, iv_thumbnail, iv_share, tv_shareNum, iv_bads, tv_badsNum, iv_likes, tv_likesNum);

        //视频点击事件
        vv_video.setOnTouchListener(new View.OnTouchListener() {
            private long first_down_time;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        first_down_time = System.currentTimeMillis();
                        return true;
                    }
                    case MotionEvent.ACTION_UP: {
                        long up_time = System.currentTimeMillis();
                        if (up_time - first_down_time < 200) {
                            if (vv_video.isPlaying()) {
                                handler.sendMessage(handler.obtainMessage(PAUSE_PLAY_VIDEO));
                            } else {
                                handler.sendMessage(handler.obtainMessage(RESUMR_PLAY_VIDEO));
                            }
                        }
                    }
                    break;
                }
                return false;
            }
        });
    }

    /**
     * 实现点击事件
     *
     * @param v 被点击的按钮
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击开始播放视频,发送消息播放视频
            case R.id.iv_playSing_y: {
                handler.sendMessage(handler.obtainMessage(START_PLAY_VIDEO));
            }
            break;
            //点赞
            case R.id.iv_likes_y:
            case R.id.tv_likesNum_y: {

            }
            break;
            //点不赞
            case R.id.iv_bads_y:
            case R.id.tv_badsNum_y: {

            }
            break;
            //点分享
            case R.id.iv_share_y:
            case R.id.tv_shareNum_y: {
                MVideoBean.ListBean tag_listbean = (MVideoBean.ListBean) v.getTag(TAG_SHARED);
                if (tag_listbean != null) {
                    ShareToQQ.showShare(tag_listbean.getShare_url(), tag_listbean.getVideo().getThumbnail_small().get(0), tag_listbean.getText());
                }
            }
            break;
            //点击下载
            case iv_xiazai_y: {
                String downLoad_url = (String) v.getTag(TAG_VIDEO_DOWN);
                L.s("下载路径" + downLoad_url);
                cilck_download(downLoad_url);
            }
            break;
        }
    }

    private void cilck_download(String url) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle("正在下载视频。。。");
        request.setDescription("正在下载，请稍后。。。");
        //把请求放到下载队列中
        manager.enqueue(request);
    }

    /**
     * 点赞或点踩后设置四者不可点击
     *
     * @param view 点赞和点踩得布局
     */
    private void noDoClick(View view) {
        view.findViewById(R.id.iv_likes_y).setClickable(false);
        view.findViewById(R.id.tv_likesNum_y).setClickable(false);
        view.findViewById(R.id.iv_bads_y).setClickable(false);
        view.findViewById(R.id.tv_badsNum_y).setClickable(false);
    }

    /**
     * 点赞和点踩得动画
     *
     * @param view 需要动画的控件
     */
    private void startAnimation(View view) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1.5f, 0, 1.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0,
                ScaleAnimation.RELATIVE_TO_SELF, 0);
        scaleAnimation.setDuration(500);
        view.startAnimation(scaleAnimation);
    }

    //设置所有控件可点击
    private void setClickListeners(View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setOnClickListener(this);
        }
    }

    //上拉加载更多
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (isMoreData && scrollState == SCROLL_STATE_IDLE) {
            numberOfpLoadMore++;
            getData(num * (numberOfpLoadMore - 1), num * numberOfpLoadMore);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem + visibleItemCount == totalItemCount) {
            isMoreData = true;
        } else {
            isMoreData = false;
        }
    }

    //将视频播放的进度转换成进度条的进度
    private int current2progress() {
        long currentPosition = vv_video.getCurrentPosition();
        //视频总时长
        long duration = vv_video.getDuration();
        long position = currentPosition * 100 / duration;
        position = Math.max(0, position);
        position = Math.min(position, 100);
        return (int) position;
    }

    //将进度条的进度转换成视频播放的进度
    private long progress2current(int position) {
        long duration = vv_video.getDuration();
        long current = duration * position / 100;
        return current;
    }

    //进度条的监听事件
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            long current = progress2current(progress);
            Message message = handler.obtainMessage(SEEK_TO_POSITION);
            message.arg1 = (int) current;
            handler.sendMessage(message);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private String getTime(int time) {
        int minute = time / 60;
        int second = time % 60;
        if (minute == 0) {
            if (second < 10) {
                return "00:0" + second;
            }
            return "00:" + second;
        } else {
            if (second < 10) {
                return minute + ":0" + second;
            }
            return minute + ":" + second;
        }
    }

    //退出时关闭handler
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(SEEKBAR_SET_PROGRESS);
        vv_video.stopPlayback();
    }
}
