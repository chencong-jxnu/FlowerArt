package com.cc.flowerart.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.cc.flowerart.R;
import com.cc.flowerart.config.CacheConfig;
import com.cc.flowerart.config.HtmlConfig;

import java.util.concurrent.ExecutionException;

public class WebViewActivity extends AppCompatActivity {
    private WebView wb_dailyPublic;
    private RelativeLayout rl_collection, rl_browse, rl_praise;
    private TextView tv_collectionNum, tv_browseNum, tv_praiseNum;
    private ImageView iv_back;
    private String url;
    private int collectionNum, browseNum, praiseNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        init();
        showContent(url);
        setListener();
    }

    private void showContent(String url) {
       // Glide.with(this).load(url).into(wb_dailyPublic);
        HtmlConfig html = new HtmlConfig(url);
        wb_dailyPublic.loadDataWithBaseURL(null, html.getHtml(), "text/html", "utf-8", null);
        wb_dailyPublic.getSettings().setJavaScriptEnabled(true);
        wb_dailyPublic.setWebChromeClient(new WebChromeClient());
        //       CacheConfig.setImaget2SDCard(url, wb_dailyPublic);
        tv_collectionNum.setText(collectionNum + "");
        tv_browseNum.setText(browseNum + "");
        tv_praiseNum.setText(praiseNum + "");
    }

    private void setListener() {
        rl_collection.setOnClickListener(new MyClickedListener());
        rl_browse.setOnClickListener(new MyClickedListener());
        rl_praise.setOnClickListener(new MyClickedListener());
        iv_back.setOnClickListener(new MyClickedListener());
    }

    private void init() {
        url = getIntent().getStringExtra("url");
        collectionNum = getIntent().getIntExtra("collectionNum", 0);
        browseNum = getIntent().getIntExtra("browseNum", 0);
        praiseNum = getIntent().getIntExtra("praiseNum", 0);

        wb_dailyPublic = (WebView) findViewById(R.id.activity_web_view_wb_dailyPublic);
        rl_collection = (RelativeLayout) findViewById(R.id.activity_web_view_rl_collection);
        rl_browse = (RelativeLayout) findViewById(R.id.activity_web_view_rl_browse);
        rl_praise = (RelativeLayout) findViewById(R.id.activity_web_view_rl_praise);
        tv_collectionNum = (TextView) findViewById(R.id.activity_web_view_tv_collectionNum);
        tv_browseNum = (TextView) findViewById(R.id.activity_web_view_tv_browseNum);
        tv_praiseNum = (TextView) findViewById(R.id.activity_web_view_tv_praiseNum);
        iv_back = (ImageView) findViewById(R.id.activity_web_view_iv_back);
    }

    /**
     * 内部类实现按钮监听方法
     */
    class MyClickedListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.activity_web_view_iv_back:
                    onBackPressed();
                    break;
                case R.id.activity_web_view_rl_collection:
                    //TODO 添加收藏
                    break;
                case R.id.activity_web_view_rl_browse:
                    //TODO 添加浏览量
                    break;
                case R.id.activity_web_view_rl_praise:
                    //TODO 添加赞
                    break;
            }
        }
    }
}
