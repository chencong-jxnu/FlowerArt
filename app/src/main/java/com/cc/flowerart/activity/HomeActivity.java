package com.cc.flowerart.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.cc.flowerart.R;
import com.cc.flowerart.utils.ImageUtil;
import com.cc.widget.viewpage.ModelPagerAdapter;
import com.cc.widget.viewpage.PagerModelManager;
import com.cc.widget.viewpage.ScrollerViewPager;
import com.cc.widget.viewpage.SpringIndicator;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;
import static com.cc.flowerart.R.id.iv;

public class HomeActivity extends AppCompatActivity {
    private ScrollerViewPager viewPager;//主界面滑动的ViewPage
    private SpringIndicator springIndicator;//主界面下面的Indicator
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();//初始化
        setViewPage();//设置ViewPage
    }

    /**
     * 设置ViewPage
     */
    private void setViewPage() {
        PagerModelManager manager = new PagerModelManager();
        manager.addCommonFragment(GuideFragment.class, getBgRes(), getTitles(),getContents());
        ModelPagerAdapter adapter = new ModelPagerAdapter(getSupportFragmentManager(), manager);
        viewPager.setAdapter(adapter);
        viewPager.fixScrollSpeed();
        springIndicator.setViewPager(viewPager);
    }

    private void init() {
        viewPager = (ScrollerViewPager) findViewById(R.id.view_pager);
        springIndicator = (SpringIndicator) findViewById(R.id.indicator);
    }

    /**
     * 设置主界面下面的Indicator的内容，内容可为空但返回list不可为空
     * @return 不为空的List<String> list
     */
    private List<String> getTitles() {
        List<String> list = new ArrayList<String>();
        list.add("*");
        list.add("*");
        list.add("*");
        list.add("*");
        list.add("*");
        return list;
    }

    /**
     * 设置主界面fragment的图片
     * @return List<Integer>
     */
    private List<Integer> getBgRes() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(R.mipmap.plant_encyclopedia);
        list.add(R.mipmap.interior_design);
        list.add(R.mipmap.gardening);
        list.add(R.mipmap.flower_shop);
        list.add(R.mipmap.logo);
        return list;
    }

    /**
     * 设置主界面fragment的String内容
     * @return List<String>
     */
    private List<String> getContents() {
        List<String> list = new ArrayList<String>();
        list.add("植物百科");
        list.add("园艺学堂");
        list.add("讨论中心");
        list.add("花卉商城");
        list.add("个人中心");
        return list;
    }
}