package com.example.song.kanfang_tuan.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.song.kanfang_tuan.utils.L;

/**
 * Created by MAJIA on 2016/12/20.
 */

public class CitySlideView extends View{

    //定义点击回调接口
    public interface SlideClick {
        public void slideOnClick(int position, String str);

        public void slideUp();
    }

    SlideClick listener;

    /**
     * 设置监听
     *
     * @param click
     */
    public void setOnSlideClick(SlideClick click) {
        this.listener = click;
    }


    //画笔
    Paint mPaint;

    public static final String[] lettes = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    /**
     * 文字宽度
     */
    float textW;
    /**
     * 文字高度
     */
    float textH;
    /**
     * 当前被点击的位置
     */
    int currentPosition = -1;


    public CitySlideView(Context context) {
        super(context);
        init();
    }

    public CitySlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //画笔
        mPaint = new Paint();
        //设置文字大小
        mPaint.setTextSize(28);
        mPaint.setColor(Color.parseColor("#0835e9"));
        mPaint.setAntiAlias(true);
        //计算文本的宽度
        textW = mPaint.measureText(lettes[0]);
        //计算文本的高度
        textH = mPaint.descent() - mPaint.ascent();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasureWH(widthMeasureSpec, 1), getMeasureWH(heightMeasureSpec, 2));
    }

    private int getMeasureWH(int wh, int type) {
        int mode = MeasureSpec.getMode(wh);
        int size = MeasureSpec.getSize(wh);
        //根据模式，计算大小
        switch (mode) {
            case MeasureSpec.EXACTLY:
            case MeasureSpec.UNSPECIFIED:
                return size;
            case MeasureSpec.AT_MOST: {
                //wrap_content
                //如果是宽度，则是文本的测量宽度
                if (type == 1) {
                    //测量宽度
                    return (int) textW + getPaddingRight();
                } else {
                    //如果是高度，则是所有文本的高度之和
                    return (int) (lettes.length * textH) + getPaddingTop() + getPaddingBottom();
                }
            }
        }
        return size;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        L.d("onDraw==============");
        super.onDraw(canvas);
        int len = lettes.length;
        for (int i = 0; i < len; i++) {
            //从字母数组中，取出每个字母，并绘制出来
            float dx = getMeasuredWidth() - mPaint.measureText(lettes[i]);
            float dy = i * textH - mPaint.ascent() + getPaddingTop();

            //如果是被点中的，变颜色
            if (i == currentPosition) {
                //加粗
                mPaint.setFakeBoldText(true);
                mPaint.setColor(Color.parseColor("#FFE90808"));
            } else {
                mPaint.setFakeBoldText(false);
                mPaint.setColor(Color.parseColor("#0835e9"));
            }

            canvas.drawText(lettes[i], dx / 2, dy, mPaint);
        }
    }

    /**
     * 解决点击事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                eventLable(event.getY());
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                eventLable(event.getY());
            }
            break;
            case MotionEvent.ACTION_UP: {
                //设置点击位置为-1
                currentPosition = -1;
                //重绘界面
                invalidate();
                if (listener != null) {
                    listener.slideUp();
                }
            }
            break;
        }

        return true;
    }

    /**
     * 处理点击事件
     *
     * @param y
     */
    private void eventLable(float y) {
        int index = (int) ((y - getPaddingTop()) / textH);
        if (index < 0) {
            index = 0;
        }
        if (index > lettes.length - 1) {
            index = lettes.length - 1;
        }

        //设置点击位置,这个判断解决多次重复相同的绘制
        if (currentPosition != index) {
            L.d("当前点击了：" + index + lettes[index]);
            currentPosition = index;
            //重绘界面
            invalidate();


            //调用接口实例方法
            if (listener != null) {
                listener.slideOnClick(index, lettes[index]);
            }
        }
    }


}
