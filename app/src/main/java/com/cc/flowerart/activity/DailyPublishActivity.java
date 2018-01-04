package com.cc.flowerart.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cc.flowerart.R;
import com.cc.flowerart.adapter.DailyPublishAdapter;
import com.cc.flowerart.bean.DailyPublish;
import com.cc.flowerart.config.ServeConfig;
import com.cc.flowerart.utils.AsyncTaskHelper;
import com.cc.flowerart.utils.BeanUtil;
import com.cc.flowerart.utils.FastJsonTools;
import com.cc.flowerart.utils.StringUtil;

import java.util.List;
import java.util.Map;

/**
 *
 *
 */
public class DailyPublishActivity extends AppCompatActivity {
    private ImageView iv_back;
    private GridView gv_content;
    private String kind;
    private List<DailyPublish> dailyPublicList;
    private DailyPublishAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_publish);
        init();
        getList(kind);
        setListener();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.setData(dailyPublicList);
    }

    private void setListener() {
        iv_back.setOnClickListener(new MyClickedListener());
        adapter.setClickedListener(new MyClickedListener());
    }

    private void init() {

        iv_back = (ImageView) findViewById(R.id.activity_daily_publish_iv_back);
        gv_content = (GridView) findViewById(R.id.activity_daily_publish_gv_content);
        kind = getIntent().getStringExtra("kind");
        adapter = new DailyPublishAdapter(getApplicationContext());
        gv_content.setAdapter(adapter);
    }

    public void getList(String kind) {
        ServeConfig config = new ServeConfig("DailyPublishServlet", "getDailyPublishByKind");
        DailyPublish t = new DailyPublish();
        t.setKind(kind);
        Map<String, Object> map = BeanUtil.BeanToMap(t);
        String str = JSON.toJSONString(map);
        String url = config.getURL() + StringUtil.strEncode(str);
        AsyncTaskHelper.OnDataDownloadListener downloadListener;
        AsyncTaskHelper taskHelper = new AsyncTaskHelper();
        downloadListener = new AsyncTaskHelper.OnDataDownloadListener() {
            @Override
            public void onDataDownload(byte[] result) {
                //处理从后台获得的数据
                String jsonString = new String(result); // 返回的字节数组转换为字符串
                Map<String, Object> resultMap;
                resultMap = FastJsonTools.getMap(jsonString);
                int code = (int) resultMap.get("code");
                if (code == 1) {
                    String dailyPublishInfo = resultMap.get("result").toString();
                    dailyPublicList = FastJsonTools.getBeans(dailyPublishInfo, DailyPublish.class);
                    //TODO 根据发布时间排序
                    adapter.setData(dailyPublicList);
                } else if (code == 0) {
                    Toast.makeText(getApplicationContext(), "没有更多帖子啦", Toast.LENGTH_SHORT).show();
                }
            }
        };
        taskHelper.downloadData(getApplicationContext(), url, downloadListener); // 发起服务器的访问
    }

    class MyClickedListener implements View.OnClickListener, DailyPublishAdapter.ClickedListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.activity_daily_publish_iv_back:
                    onBackPressed();
                    break;
            }
        }

        @Override
        public void ShowContent(DailyPublish dailyPublish,int position) {
            dailyPublish.setBrowseNum(dailyPublish.getBrowseNum() + 1);
            Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
            intent.putExtra("url", dailyPublish.getFileUrl());
            intent.putExtra("collectionNum", dailyPublish.getCollectionNum());
            intent.putExtra("browseNum", dailyPublish.getBrowseNum());
            intent.putExtra("praiseNum", dailyPublish.getPraiseNum());
            startActivity(intent);

            ServeConfig config = new ServeConfig("DailyPublishServlet", "update");
            Map<String, Object> map = BeanUtil.BeanToMap(dailyPublish);
            String str = JSON.toJSONString(map);
            String url = config.getURL() + StringUtil.strEncode(str);
            AsyncTaskHelper.OnDataDownloadListener downloadListener;
            AsyncTaskHelper taskHelper = new AsyncTaskHelper();
            downloadListener = new AsyncTaskHelper.OnDataDownloadListener() {
                @Override
                public void onDataDownload(byte[] result) {
                    //处理从后台获得的数据
                    String jsonString = new String(result); // 返回的字节数组转换为字符串
                    Map<String, Object> resultMap;
                    resultMap = FastJsonTools.getMap(jsonString);
                    int code = (int) resultMap.get("code");
                    if (code == 1) {
                        Log.i("updateDailyPublish", "浏览人数更新成功");
                    } else if (code == 0) {
                        Log.i("updateDailyPublish", "浏览人数更新失败");
                    }
                }
            };
            taskHelper.downloadData(getApplicationContext(), url, downloadListener); // 发起服务器的访问
        }
    }
}
