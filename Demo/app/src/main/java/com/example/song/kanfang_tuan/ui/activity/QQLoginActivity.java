package com.example.song.kanfang_tuan.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.song.kanfang_tuan.R;
import com.example.song.kanfang_tuan.ui.fragment.MineFragment;
import com.example.song.kanfang_tuan.utils.Constant;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;


/**
 * QQ登录界面
 */
public class QQLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqlogin);
    }


    public void testLogin(View view) {
        Platform platform= ShareSDK.getPlatform(QQ.NAME);
        platform.SSOSetting(false);
        if(platform!=null){
            platform.setPlatformActionListener(new PlatformActionListener() {
                @Override
                public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                    //从platform里面获取用户的信息
                    PlatformDb db = platform.getDb();
//                long expiresIn = db.getExpiresIn();
                    String userIcon = db.getUserIcon();
                    Log.d("realmo",userIcon);
                    String userName = db.getUserName();
                    Intent intent = new Intent(QQLoginActivity.this,MineFragment.class);
                    intent.putExtra(Constant.LOGIN_NAME,userName);
                    intent.putExtra(Constant.LOGIN_ICON,userIcon);
                    QQLoginActivity.this.setResult(Constant.RESULTCODE_OK,intent);
                    QQLoginActivity.this.finish();
                }

                @Override
                public void onError(Platform platform, int i, Throwable throwable) {
                    Toast.makeText(QQLoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel(Platform platform, int i) {
                    Toast.makeText(QQLoginActivity.this,"取消登录",Toast.LENGTH_SHORT).show();
                }
            });
//            platform.showUser(null);

            //只是单独授权登录(即每次登录都需要授权，避免上面showUser方法在切换QQ登录仍返回之前的信息。)
            platform.authorize();
        }


    }
}
