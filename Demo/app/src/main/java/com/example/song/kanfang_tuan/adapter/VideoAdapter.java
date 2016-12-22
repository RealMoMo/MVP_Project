package com.example.song.kanfang_tuan.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.song.kanfang_tuan.MyApp;
import com.example.song.kanfang_tuan.R;
import com.example.song.kanfang_tuan.base.BaseAdapter;
import com.example.song.kanfang_tuan.bean.MVideoBean;
import com.example.song.kanfang_tuan.ui.activity.DetailsActivity;
import com.example.song.kanfang_tuan.widget.CircularBitmap;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.song.kanfang_tuan.utils.Constant.heightPixels;
import static com.example.song.kanfang_tuan.utils.Constant.widthPixels;

/**
 * Created by Administrator on 2016/12/20 0020.
 */

public class VideoAdapter extends BaseAdapter<MVideoBean.ListBean> implements View.OnClickListener {

    List<MVideoBean.ListBean> data;
    Context context;

    //改变数据
    private static final int BING_ITEM = -1;
    //是否已经点赞或者不赞
    private static final int BING_POSITION = -2;

    public VideoAdapter(Context context, List<MVideoBean.ListBean> data, int... layoutId) {
        super(context, data, layoutId);
        this.data = data;
        this.context = context;
    }


    @Override
    public void bindData(int position, ViewHolder mHolder) {
        MVideoBean.ListBean listBean = data.get(position);
        mHolder.mView.setTag(BING_POSITION, position);

        ImageView iv_head = (ImageView) mHolder.mView.findViewById(R.id.iv_head_y);
        setImg(listBean.getU().getHeader().get(0), iv_head, 1);//头像图片

        //判断是否为VIP
        boolean isVIP = listBean.getU().isIs_vip();
        TextView tv_name = (TextView) mHolder.mView.findViewById(R.id.tv_name_y);
        tv_name.setText(listBean.getU().getName());//昵称
        if (isVIP) {
            tv_name.setTextColor(Color.RED);
        }

        //下载按钮隐藏
        ImageView iv_xiazai = (ImageView) mHolder.mView.findViewById(R.id.iv_xiazai_y);
        iv_xiazai.setVisibility(View.INVISIBLE);

        TextView tv_time = (TextView) mHolder.mView.findViewById(R.id.tv_time_y);
        tv_time.setText(listBean.getPasstime());//发布时间

        TextView tv_title = (TextView) mHolder.mView.findViewById(R.id.tv_title_y);
        tv_title.setText(listBean.getText());//标题

        ImageView iv_thumbnail = (ImageView) mHolder.mView.findViewById(R.id.iv_thumbnail_y);
        setImg(listBean.getVideo().getThumbnail().get(0), iv_thumbnail);//缩略图

        float v_width = listBean.getVideo().getWidth();
        float v_height = listBean.getVideo().getHeight();

        if (v_width > widthPixels) {
            v_height = widthPixels / v_width * v_height;
            v_width = widthPixels;
        }else {
            if (v_height > heightPixels/2) {
                v_width = heightPixels/2 / v_height * v_width;
                v_height = heightPixels/2;
            }else {
                v_height = widthPixels / v_width * v_height;
                v_width = widthPixels;
            }
        }
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams((int)v_width, (int)v_height);
        params.gravity= Gravity.CENTER_HORIZONTAL;
        iv_thumbnail.setLayoutParams(params);
        iv_thumbnail.requestLayout();

        //播放状态标记
        ImageView iv_playsing = (ImageView) mHolder.mView.findViewById(R.id.iv_playSing_y);

        TextView tv_playCount = (TextView) mHolder.mView.findViewById(R.id.tv_playCount_y);
        tv_playCount.setText(listBean.getVideo().getPlaycount() + "次播放");//播放次数

        //视频时长
        TextView tv_long = (TextView) mHolder.mView.findViewById(R.id.tv_long_y);
        int playfcount = listBean.getVideo().getPlayfcount();
        int minute = playfcount / 60;
        int second = playfcount % 60;
        if (minute == 0) {
            tv_long.setText("00:" + second);
        } else {
            tv_long.setText(minute + ":" + second);
        }

        ImageView iv_likes = (ImageView) mHolder.mView.findViewById(R.id.iv_likes_y);
        TextView tv_likesNum = (TextView) mHolder.mView.findViewById(R.id.tv_likesNum_y);
        ImageView iv_bads = (ImageView) mHolder.mView.findViewById(R.id.iv_bads_y);
        TextView tv_badsNum = (TextView) mHolder.mView.findViewById(R.id.tv_badsNum_y);
//        if (!data.get(position).isDlick()) {
        iv_likes.setImageResource(R.drawable.ding_not_clicked);
        tv_likesNum.setText(String.valueOf(listBean.getUp()));//点赞数
        tv_likesNum.setTextColor(Color.BLACK);
//            //点击 点赞
        iv_likes.setOnClickListener(this);
        iv_likes.setTag(BING_ITEM, mHolder.mView);

        tv_likesNum.setOnClickListener(this);
        tv_likesNum.setTag(BING_ITEM, mHolder.mView);
//        } else {
//            iv_likes.setImageResource(R.drawable.ding_has_clicked);
//            tv_likesNum.setText(String.valueOf(listBean.getUp()));//点赞数
//            tv_likesNum.setTextColor(Color.RED);
//            noDoClick(mHolder.mView);
//        }
//
//        if (!data.get(position).isDlick()) {
        iv_bads.setImageResource(R.drawable.cai_not_clicked);
        tv_badsNum.setText(String.valueOf(listBean.getDown()));//点不赞数
        tv_badsNum.setTextColor(Color.BLACK);
        //点击非点赞
        iv_bads.setOnClickListener(this);
        tv_badsNum.setTag(BING_ITEM, mHolder.mView);

        tv_badsNum.setOnClickListener(this);
        iv_bads.setTag(BING_ITEM, mHolder.mView);
//        } else {
//            iv_likes.setImageResource(R.drawable.cai_has_clicked);
//            tv_likesNum.setText(String.valueOf(listBean.getDown()));
//            tv_likesNum.setTextColor(Color.RED);
//            noDoClick(mHolder.mView);
//        }


        ImageView iv_share = (ImageView) mHolder.mView.findViewById(R.id.iv_share_y);
        TextView tv_shareNum = (TextView) mHolder.mView.findViewById(R.id.tv_shareNum_y);
        tv_shareNum.setText(String.valueOf(listBean.getForward()));//分享数

        ImageView iv_comment = (ImageView) mHolder.mView.findViewById(R.id.iv_comment_y);
        TextView tv_commentNum = (TextView) mHolder.mView.findViewById(R.id.tv_commentNum_y);
        tv_commentNum.setText(String.valueOf(listBean.getComment()));//评论数

        //点赞和点不赞的两个动画图片，首先隐藏
        ImageView dianzan = (ImageView) mHolder.mView.findViewById(R.id.iv_dianzan);
        ImageView undianzan = (ImageView) mHolder.mView.findViewById(R.id.iv_undianzan);
        dianzan.setVisibility(View.INVISIBLE);
        undianzan.setVisibility(View.INVISIBLE);

        //图片点击播放视频
        iv_thumbnail.setOnClickListener(this);
        iv_playsing.setOnClickListener(this);


        //点击分享
        iv_share.setOnClickListener(this);
        tv_shareNum.setOnClickListener(this);
        //点击评论
        iv_comment.setOnClickListener(this);
        tv_commentNum.setOnClickListener(this);

        //设置评论区域
        setCommentLayout(mHolder.mView,listBean);
    }

    private void setCommentLayout(View mView, MVideoBean.ListBean listBean) {
        LinearLayout commentLayout = (LinearLayout) mView.findViewById(R.id.ll_commentLayout);
        commentLayout.removeAllViews();
        List<MVideoBean.ListBean.TopCommentsBean> top_comments = listBean.getTop_comments();
        if (top_comments!=null&&top_comments.size()>0){
            for (int i = 0; i < top_comments.size(); i++) {
                TextView textView=new TextView(context);
                String str="<font color='#0000ff'>"+top_comments.get(i).getU().getName()+":"+"</font>"+"<font color='#000000'>"+top_comments.get(i).getContent()+"</font>";
                textView.setTextSize(12);
                textView.setText(Html.fromHtml(str));
                commentLayout.addView(textView);
            }
        }else {
            return;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击跳转到视频播放界面
            case R.id.iv_thumbnail_y:
            case R.id.iv_playSing_y: {
                Intent intent = new Intent(context, DetailsActivity.class);
                context.startActivity(intent);
            }
            break;
            //点赞
            case R.id.iv_likes_y:
            case R.id.tv_likesNum_y: {
                View item = (View) v.getTag(BING_ITEM);
                Integer position = (Integer) item.getTag(BING_POSITION);
                TextView tv_dianzan = (TextView) item.findViewById(R.id.tv_likesNum_y);
                String string = tv_dianzan.getText().toString();
                tv_dianzan.setTextColor(Color.RED);
                tv_dianzan.setText(String.valueOf(Integer.parseInt(string) + 1));
                ((ImageView) item.findViewById(R.id.iv_likes_y)).setImageResource(R.drawable.ding_has_clicked);
                noDoClick(item);
                data.get(position).setDlick(true);
                startAnimation(item.findViewById(R.id.iv_dianzan));
            }
            break;
            //点不赞
            case R.id.iv_bads_y:
            case R.id.tv_badsNum_y: {
                View item = (View) v.getTag(BING_ITEM);
                Integer position = (Integer) item.getTag(BING_POSITION);
                TextView tv_undianzan = (TextView) item.findViewById(R.id.tv_badsNum_y);
                String string = tv_undianzan.getText().toString();
                tv_undianzan.setTextColor(Color.RED);
                tv_undianzan.setText(String.valueOf(Integer.parseInt(string) + 1));
                ((ImageView) item.findViewById(R.id.iv_bads_y)).setImageResource(R.drawable.cai_has_clicked);
                noDoClick(item);
                data.get(position).setDlick(true);
                startAnimation(item.findViewById(R.id.iv_undianzan));
            }
            break;
            //点分享
            case R.id.iv_share_y:
            case R.id.tv_shareNum_y: {
            }
            break;
            //点评论,跳转到视频播放界面的评论区域
            case R.id.iv_comment_y:
            case R.id.tv_commentNum_y: {
            }
            break;
        }
    }

    //textView设置文字
    private void setTVContext(String context, TextView textView) {
        textView.setText(context);
    }

    //设置图片文件
    private void setImg(String url, ImageView imageView) {
        Picasso.with(MyApp.getApp()).load(url).into(imageView);
    }

    private void setImg(String url, ImageView imageView, int type) {
        Picasso.with(MyApp.getApp()).load(url).transform(new CircularBitmap()).into(imageView);
    }

    private void noDoClick(View view) {
        view.findViewById(R.id.iv_likes_y).setClickable(false);
        view.findViewById(R.id.tv_likesNum_y).setClickable(false);
        view.findViewById(R.id.iv_bads_y).setClickable(false);
        view.findViewById(R.id.tv_badsNum_y).setClickable(false);
    }

    private void startAnimation(View view) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1.5f, 0, 1.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0,
                ScaleAnimation.RELATIVE_TO_SELF, 0);
        scaleAnimation.setDuration(500);
        view.startAnimation(scaleAnimation);
    }
}
