package com.cc.flowerart.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.flowerart.R;

/**
 * 消息
 */
public class NewsActivity extends AppCompatActivity {
    private RelativeLayout rl_comment,rl_praise,rl_subscribe,rl_shopping,rl_system;
    private TextView tv_comment_number,tv_praise_number,tv_subscribe_number,tv_shopping_number,tv_system_number;
    ImageView iv_back;
    //TODO
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        init();
    }

    private void init() {
        rl_comment = (RelativeLayout) findViewById(R.id.news_rl_comment);
        rl_praise = (RelativeLayout) findViewById(R.id.news_rl_praise);
        rl_subscribe = (RelativeLayout) findViewById(R.id.news_rl_subscribe);
        rl_shopping = (RelativeLayout) findViewById(R.id.news_rl_shopping);
        rl_system = (RelativeLayout) findViewById(R.id.news_rl_system);

        tv_comment_number = (TextView) findViewById(R.id.news_tv_comment_number);
        tv_praise_number = (TextView) findViewById(R.id.news_tv_praise_number);
        tv_subscribe_number = (TextView) findViewById(R.id.news_tv_subscribe_number);
        tv_shopping_number = (TextView) findViewById(R.id.news_tv_shopping_number);
        tv_system_number = (TextView) findViewById(R.id.news_tv_system_number);

        iv_back = (ImageView) findViewById(R.id.news_iv_back);
    }
    class MyOnclickListener implements View.OnClickListener{
        //TODO
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.news_rl_comment:
                    break;
                case R.id.news_rl_praise:
                    break;
                case R.id.news_rl_subscribe:
                    break;
                case R.id.news_rl_shopping:
                    break;
                case R.id.news_rl_system:
                    break;
                case R.id.news_iv_back:
                    break;
            }
        }
    }
}
