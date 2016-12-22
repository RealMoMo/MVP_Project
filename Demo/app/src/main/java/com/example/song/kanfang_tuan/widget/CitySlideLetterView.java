package com.example.song.kanfang_tuan.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by MAJIA on 2016/12/20.
 */

public class CitySlideLetterView extends View {
    //画笔
    Paint mPaint;
    //要绘制的文本
    String text;

    int textW;
    int textH;

    RectF rf;

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(200);
        textW = (int) mPaint.measureText("M");
        textH = (int) (mPaint.descent() - mPaint.ascent());
    }

    public CitySlideLetterView(Context context) {
        super(context);
        init();
    }

    public CitySlideLetterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasureWH(widthMeasureSpec, 1), getMeasureWH(heightMeasureSpec, 2));
        //设置矩形区域
        rf = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
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
                    return textW + getPaddingRight() + getPaddingLeft();
                } else {
                    //如果是高度，则是所有文本的高度之和
                    return textH + getPaddingTop() + getPaddingBottom();
                }
            }
        }
        return size;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景
        mPaint.setColor(Color.parseColor("#88000000"));
        canvas.drawRoundRect(rf, 20, 20, mPaint);
        //再绘制文本
        //设置文本颜色
        mPaint.setColor(Color.parseColor("#FFAAAAAA"));
        if (text != null) {
            float dx = getMeasuredWidth() - mPaint.measureText(text);
            float dy = (getMeasuredHeight() - textH) / 2;
            canvas.drawText(text, dx / 2, dy - mPaint.ascent(), mPaint);
        }
    }

    /**
     * 绘制字符
     * @param str
     */
    public void setText(String str){
        //从外部传入字符串，设置给text
        text = str;
        //重绘界面
        invalidate();
    }
}
