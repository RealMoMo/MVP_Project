package com.example.song.kanfang_tuan.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.song.kanfang_tuan.R;
import com.example.song.kanfang_tuan.utils.Constant;


/**
 * 点击首页内容展示的界面
 * 用webView展示
 */
public class HomeDetailActivity extends AppCompatActivity {


    private WebView webView;
    //链接网址
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_detail);

        Intent intent = getIntent();
        url = intent.getStringExtra(Constant.HOME_DETAIL_KEY);

        setupWebView();
    }

    private void setupWebView() {
        webView = (WebView) findViewById(R.id.webview_homedetail);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(url);

    }

    //点击backspace可返回上个页面，而不是退出(若webview只加载了一个页面)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.getVisibility() == View.VISIBLE) {
                // 按返回时，看网页是否能返回
                if (webView.canGoBack()) {
                    webView.goBack();
                    //返回true webview自己处理
                    return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
