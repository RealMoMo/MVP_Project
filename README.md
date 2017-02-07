##mvp_demo

看房混搭百思不得姐app

MVP模式不是很溜，可能应用的不够严谨！

---
关于作者：
RealMoMo
> 关于我，欢迎关注  
   微信：[Real_Mo]()  
   邮箱：momo_weiye@126.com
-------------
####开发目的: 
<br>练手MVP开发模式</br>


###开发环境
Android Studio2.0


### 下载安装
导入模块，重新配置适合你开发环境build.gradle文件

```java  
  
apply plugin: 'com.android.application'
apply plugin: 'com.neenbedankt.android-apt'//增加这一句(butterknife)

android {
    compileSdkVersion 23
    buildToolsVersion "23.0.3"

    defaultConfig {
        applicationId "com.example.song.kanfang_tuan"
        minSdkVersion 15
        targetSdkVersion 22
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    compile fileTree(include: ['*.jar'], dir: 'libs')
    androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
        exclude group: 'com.android.support', module: 'support-annotations'
    })
    //增加这一句
    compile 'com.android.support:appcompat-v7:23.4.0'
    compile 'com.android.support:design:23.4.0'
    compile 'com.squareup.retrofit2:retrofit:2.1.0'
    compile 'com.squareup.retrofit2:converter-scalars:2.1.0'
    compile 'com.squareup.retrofit2:converter-gson:2.1.0'
    compile 'com.jakewharton:butterknife:8.4.0'
    compile 'com.squareup.picasso:picasso:2.5.2'
    compile 'com.android.support:support-v4:23.4.0'
    testCompile 'junit:junit:4.12'
    apt 'com.jakewharton:butterknife-compiler:8.4.0'
    compile 'com.youth.banner:banner:1.4.4'
    compile 'cn.yipianfengye.android:zxing-library:2.1'
    compile 'se.emilsjolander:stickylistheaders:2.7.0'
    compile files('libs/MobCommons-2016.1107.1809.jar')
    compile files('libs/MobTools-2016.1107.1809.jar')
    compile files('libs/ShareSDK-Core-2.7.10.jar')
    compile files('libs/ShareSDK-QQ-2.7.10.jar')
	compile files('libs/ShareSDK-Wechat-2.7.10.jar')
    compile files('libs/ShareSDK-Wechat-Core-2.7.10.jar')
    compile files('libs/ShareSDK-Wechat-Moments-2.7.10.jar')
	compile 'com.charonchui.vitamio:vitamio:4.2.2'
}


  
```

###Thanks
Everyone who has contributed code and reported issues and pull requests!



###TODO
<br>数据缓存</br>


###Version
<br>1.0实现大致功能----2016.12.27</br>

