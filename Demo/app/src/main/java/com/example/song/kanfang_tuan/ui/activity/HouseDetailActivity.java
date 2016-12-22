package com.example.song.kanfang_tuan.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.song.kanfang_tuan.R;
import com.example.song.kanfang_tuan.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 点击搜索楼盘item的界面  用webview展示
 */
public class HouseDetailActivity extends NoActionBarActivity {


    /*
     * 完整的url:http://db.house.qq.com/zs_170664/
     * 即最后一串是用城市拼音的首字母 +"_"+对应的楼盘fid+"/"
     *
     * 已测试访问地址可以简化为
     * http://db.house.qq.com/xx_ +对应楼盘的fid
     */

    private final String baseUrl = "http://db.house.qq.com/xx_";
    @BindView(R.id.webview_housedetail)
    WebView webviewHousedetail;
    @BindView(R.id.progressbar_housedetail)
    ProgressBar progressbarHousedetail;
    private String url;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_detail);


        Intent intent = getIntent();
        url = baseUrl + intent.getStringExtra(Constant.FID) + "/";

        setupViews();

        webviewHousedetail.loadUrl(url);
    }

    private void setupViews() {

        ButterKnife.bind(this);
        //配置webview
        webviewHousedetail.getSettings().setJavaScriptEnabled(true);
        webviewHousedetail.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressbarHousedetail.setVisibility(View.VISIBLE);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressbarHousedetail.setVisibility(View.INVISIBLE);
            }
        });

        webviewHousedetail.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
    }


    //点击backspace可返回上个页面，而不是退出(若webview只加载了一个页面)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webviewHousedetail.getVisibility() == View.VISIBLE) {
                // 按返回时，看网页是否能返回
                if (webviewHousedetail.canGoBack()) {
                    webviewHousedetail.goBack();
                    //返回true webview自己处理
                    return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
