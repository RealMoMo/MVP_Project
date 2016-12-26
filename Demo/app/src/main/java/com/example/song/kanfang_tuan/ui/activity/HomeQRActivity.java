package com.example.song.kanfang_tuan.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.song.kanfang_tuan.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class HomeQRActivity extends NoActionBarActivity {

    /**
     * 扫码返回
     */
    public static final int INTENT_REQUEST_SCAN = 1;
    /**
     * 生成二维码的宽高
     */
    public static final int QRCODE_HEIGHT = 400;
    public static final int QRCODE_WIDTH = 400;

    @BindView(R.id.btn_scanQRCode)
    Button btnScanQRCode;
    @BindView(R.id.et_textQR)
    EditText etTextQR;
    @BindView(R.id.textQRHint)
    TextInputLayout textQRHint;
    @BindView(R.id.btn_createQR)
    Button btnCreateQR;
    @BindView(R.id.img_showQR)
    ImageView imgShowQR;
    @BindView(R.id.activity_home_qr)
    RelativeLayout activityHomeQr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_qr);
        ButterKnife.bind(this);
    }

    /**
     * 文本框的输入监听
     */
    @OnTextChanged(value = R.id.et_textQR, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChanged(CharSequence s, int start, int before, int count) {
        // 判断输入不为空，按钮可点击
        if (etTextQR.length() != 0) {
            btnCreateQR.setEnabled(true);
        } else {
            btnCreateQR.setEnabled(false);
        }
    }


    @OnClick({R.id.btn_scanQRCode, R.id.btn_createQR})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_scanQRCode:
                //点击跳转到扫码页面
                Intent intent = new Intent(this, ScanningQRCode.class);
                startActivityForResult(intent, INTENT_REQUEST_SCAN);
                break;
            case R.id.btn_createQR:
                //点击生成二维码
                //点击生成顺便关闭输入法
//                closeInputMethod(view);
                Bitmap qrCodeBitmap = getQRCodeBitmap(etTextQR.getText().toString(), QRCODE_WIDTH, QRCODE_HEIGHT);
                imgShowQR.setImageBitmap(qrCodeBitmap);
                break;
        }
    }

    //返回扫码的结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case INTENT_REQUEST_SCAN: {
                //扫码返回结果
                if (data != null) {
                    //取出data中的bundle
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        //取出结果
                        String value = bundle.getString(CodeUtils.RESULT_STRING);
                        Log.d("TAG", "onActivityResult: 扫描结果" + value);
                        Toast.makeText(HomeQRActivity.this, "扫描结果：" + value, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    /**
     * 当触摸屏幕时把输入法隐藏
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(HomeQRActivity.this
                            .getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return super.onTouchEvent(event);
    }

    /**
     * 关闭输入法
     */
    private void closeInputMethod(View view) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 对输入的字段生成二维码
     */
    private Bitmap getQRCodeBitmap(String content, int width, int height) {
        QRCodeWriter writer = new QRCodeWriter();
        //对数据进行编码
        Map<EncodeHintType, Object> hints = new HashMap<>();
        //文本的编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //容错级别
        //        L(1), 7%
        //        M(0), 15%
        //        Q(3), 25%
        //        H(2); 30%
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
        //对数据进行编码
        try {
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] colors = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (bitMatrix.get(j, i)) {
                        colors[i * width + j] = Color.BLACK;
                    } else {
                        colors[i * width + j] = Color.WHITE;
                    }
                }
            }
            //1.生成Bitmap的颜色数组
            //2.数组偏移量
            //3.生成的Bitmap水平方向上的像素点个数
            //4.Bitmap宽高
            //5.色彩模式
            return Bitmap.createBitmap(colors, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

}
