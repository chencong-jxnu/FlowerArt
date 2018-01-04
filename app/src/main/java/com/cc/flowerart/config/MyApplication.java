package com.cc.flowerart.config;

import android.app.Application;
import android.util.Log;

import com.cc.flowerart.utils.FileUtils;

//import static com.cc.flowerart.config.CacheConfig.initMemoryCache;

/**
 * Created by Administrator on 2017/8/19.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    /**
     * 初始化一些全局的类和变量
     */
    private void init() {
        ScreenConfig.init(getApplicationContext());
        Log.w("test<<<<<01","test01");
        FileUtils.createInstance(getApplicationContext());
        Log.w("test<<<<<03","test03");
    }
}
