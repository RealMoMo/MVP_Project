package com.example.song.kanfang_tuan.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.song.kanfang_tuan.R;
import com.example.song.kanfang_tuan.ui.fragment.HomeFragment;
import com.example.song.kanfang_tuan.ui.fragment.MineFragment;
import com.example.song.kanfang_tuan.ui.fragment.MsgFragment;
import com.example.song.kanfang_tuan.ui.fragment.VideoFragment;
import com.example.song.kanfang_tuan.utils.Constant;
import com.example.song.kanfang_tuan.utils.SaveImageToGallery;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.frame)
    FrameLayout mFrame;
    @BindView(R.id.img_home)
    ImageView mImgHome;
    @BindView(R.id.tv_home)
    TextView mTvHome;
    @BindView(R.id.relat_home)
    RelativeLayout mRelatHome;
    @BindView(R.id.img_video)
    ImageView mImgVideo;
    @BindView(R.id.tv_video)
    TextView mTvVideo;
    @BindView(R.id.relat_video)
    RelativeLayout mRelatVideo;
    @BindView(R.id.img_updata)
    ImageView mImgUpdata;
    @BindView(R.id.img_msg)
    ImageView mImgMsg;
    @BindView(R.id.tv_msg)
    TextView mTvMsg;
    @BindView(R.id.relat_msg)
    RelativeLayout mRelatMsg;
    @BindView(R.id.img_mine)
    ImageView mImgMine;
    @BindView(R.id.tv_mine)
    TextView mTvMine;
    @BindView(R.id.relat_mine)
    RelativeLayout mRelatMine;
    private Fragment mCurrentFrgment = null;

    //侧滑栏头像的图片
    private ImageView headImage;
    private int mTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ButterKnife.bind(this);
        //初始化所有tag并新建fragment
        setAllTag();
        //设置默认选中
        changeTab(0);
        mImgHome.setSelected(true);
        mTvHome.setSelected(true);

        //获取侧滑栏的头像点击
        View headerView = navigationView.getHeaderView(0);
        headImage = ((ImageView) headerView.findViewById(R.id.imageView));
        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击选择头像切换
                choseHeadImageFromGallery();
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplication(), "取消返回", Toast.LENGTH_LONG).show();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constant.REQUEST_TAKE_PHOTO_CODE: {
                //请求拍照
                Bundle bundle = data.getExtras();
                // 获取相机返回的数据，并转换为Bitmap图片格式
                Bitmap bitmap = (Bitmap) bundle.get("data");
                //调用保存拍照图片的工具类
                SaveImageToGallery.saveImageToGallery(this, bitmap);
            }
            break;

            case Constant.CODE_GALLERY_REQUEST: {
                //请求剪裁头像图片
                cropRawPhoto(data.getData());
            }
            break;

            case Constant.CODE_RESULT_REQUEST:
                //请求设置头像图片
                if (data != null) {
                    setImageToHeadView(data);
                }
                break;
        }
    }



    /**
     * 从本地相册选取图片作为头像
     */
    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent();
        // 设置文件类型
        intentFromGallery.setType(Constant.IMAGE_TYPE);
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentFromGallery, Constant.CODE_GALLERY_REQUEST);
    }


    /**
     * 系统自带的图片剪裁
     */
    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,Constant.IMAGE_TYPE);

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", Constant.OUTPUT_X);
        intent.putExtra("outputY", Constant.OUTPUT_Y);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, Constant.CODE_RESULT_REQUEST);
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            headImage.setImageBitmap(photo);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            //点击打开摄像头进行拍照保存到本地
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, Constant.REQUEST_TAKE_PHOTO_CODE);
        } else if (id == R.id.nav_gallery) {
            //点击显示系统相册的图片
            Intent intent = new Intent();
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType(Constant.IMAGE_TYPE);
            //根据版本号不同使用不同的Action
            if (Build.VERSION.SDK_INT < 19) {
                intent.setAction(Intent.ACTION_GET_CONTENT);
            } else {
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            }
            startActivityForResult(intent, Constant.IMAGE_REQUEST_CODE);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    List<Fragment> mFragments = new ArrayList<>();

    @OnClick({R.id.img_home, R.id.img_video, R.id.img_updata, R.id.img_msg, R.id.img_mine})
    public void onClick(View view) {
        setUnselect();
        switch (view.getId()) {
            case R.id.img_home:
                mTag = (int) view.getTag();
                mImgHome.setSelected(true);
                mTvHome.setSelected(true);
                break;
            case R.id.img_video:
                mTag = (int) view.getTag();
                mImgVideo.setSelected(true);
                mTvVideo.setSelected(true);
                break;
            case R.id.img_updata:
                break;
            case R.id.img_msg:
                mTag = (int) view.getTag();
                mImgMsg.setSelected(true);
                mTvMsg.setSelected(true);
                break;
            case R.id.img_mine:
                mTag = (int) view.getTag();

                mImgMine.setSelected(true);
                mTvMine.setSelected(true);
                break;
        }
        changeTab(mTag);
    }

    //为所有img设置tag
    private void setAllTag() {
        mImgHome.setTag(0);
        mImgVideo.setTag(1);
        mImgMsg.setTag(2);
        mImgMine.setTag(3);

        mFragments.add(new HomeFragment());
        mFragments.add(new VideoFragment());
        mFragments.add(new MsgFragment());
        mFragments.add(new MineFragment());

    }

    //将所有控件设置为默认状态
    private void setUnselect() {
        mImgHome.setSelected(false);
        mImgVideo.setSelected(false);
        mImgMsg.setSelected(false);
        mImgMine.setSelected(false);

        mTvHome.setSelected(false);
        mTvVideo.setSelected(false);
        mTvMsg.setSelected(false);
        mTvMine.setSelected(false);

    }

    //设置选中的fragm
    private void changeTab(int i) {
        //获取Transaction对象
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //如果mCurrentFrgment则隐藏
        if (mCurrentFrgment != null) {
            fragmentTransaction.hide(mCurrentFrgment);
        }
        //获取即将展示的fragment，，如果为空就从数组中取
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(mFragments.get(i).getClass().getName());
        if (fragment == null) {
            fragment = mFragments.get(i);
        }
        //将获取到的fragment设置为当前展示的fragment
        mCurrentFrgment = fragment;
        //如果当前的添加过就直接show、否则先添加
        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.frame, fragment, fragment.getClass().getName());
        } else {
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commit();
    }

    /**
     * 现场恢复，解决fragment重叠问题
     *
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        for (int i = 0; i < mFragments.size(); i++) {

            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentByTag(mFragments.get(i).getClass().getName());
            FragmentTransaction ft = fm.beginTransaction();
            if (i != 0) {
                ft.hide(fragment);
            } else {
                ft.show(fragment);
            }
            ft.commit();
        }
    }
}
