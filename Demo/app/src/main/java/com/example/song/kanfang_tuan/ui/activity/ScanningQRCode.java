package com.example.song.kanfang_tuan.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.song.kanfang_tuan.R;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScanningQRCode extends NoActionBarActivity implements CodeUtils.AnalyzeCallback{

    @BindView(R.id.btn_open_flashLight)
    Button btnOpenFlashLight;
    @BindView(R.id.btn_close_flashLight)
    Button btnCloseFlashLight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning_qrcode);
        ButterKnife.bind(this);

        //创建CaptureFragment
        CaptureFragment cf = new CaptureFragment();
        //设置CaptureFragment的布局
        CodeUtils.setFragmentArgs(cf, R.layout.fragment_sacnning_capture);
        //设置解析回调接口
        cf.setAnalyzeCallback(this);
        //把CaptureFragment添加到Activity中
        getSupportFragmentManager().beginTransaction().replace(R.id.captureLayout, cf).commit();

    }

    @Override
    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
        //扫码成功，回调
        //先获取intent
        Intent intent = getIntent();
        //设置结果值
        Bundle bundle = new Bundle();
        bundle.putString(CodeUtils.RESULT_STRING, result);
        intent.putExtras(bundle);
        //setResult
        setResult(1, intent);
        //finish
        finish();
    }

    @Override
    public void onAnalyzeFailed() {
        //扫码失败，回调
        //先获取intent
        Intent intent = getIntent();
        //设置结果值
        Bundle bundle = new Bundle();
        bundle.putString(CodeUtils.RESULT_STRING, "解析失败");
        intent.putExtras(bundle);
        //setResult
        setResult(0, intent);
        //finish
        finish();
    }

    @OnClick({R.id.btn_open_flashLight, R.id.btn_close_flashLight})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_open_flashLight:
                //打开闪光灯
                CodeUtils.isLightEnable(true);
                break;
            case R.id.btn_close_flashLight:
                //关闭闪光灯
                CodeUtils.isLightEnable(false);
                break;
        }
    }
}
