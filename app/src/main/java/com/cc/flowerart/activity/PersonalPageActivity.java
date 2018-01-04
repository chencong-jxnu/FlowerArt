package com.cc.flowerart.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cc.flowerart.R;
import com.cc.flowerart.config.CacheConfig;

import static com.cc.flowerart.R.id.personal_page_iv_back;

public class PersonalPageActivity extends AppCompatActivity {
    private ImageView iv_usericon,iv_publish,iv_partake,iv_collection,iv_order,iv_trolley,iv_news,iv_back;
    private LinearLayout ll_addPraise,ll_concern,ll_fans;
    private TextView tv_praise_num,tv_nickname,tv_sign,tv_concerm_num,tv_fans_num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_page);
        init();
        listener();
    }

    /**
     * 点击事件的监听方法
     */
    private void listener() {
        iv_usericon.setOnClickListener(new MyClickedListener());
        iv_publish.setOnClickListener(new MyClickedListener());
        iv_partake.setOnClickListener(new MyClickedListener());
        iv_collection.setOnClickListener(new MyClickedListener());
        iv_order.setOnClickListener(new MyClickedListener());
        iv_trolley.setOnClickListener(new MyClickedListener());
        iv_news.setOnClickListener(new MyClickedListener());
        iv_back.setOnClickListener(new MyClickedListener());
        ll_addPraise.setOnClickListener(new MyClickedListener());
        ll_concern.setOnClickListener(new MyClickedListener());
        ll_fans.setOnClickListener(new MyClickedListener());
    }

    /**
     * 初始化控件
     */
    private void init() {

        iv_usericon = (ImageView) findViewById(R.id.personal_page_iv_usericon);//用户头像
        iv_publish = (ImageView) findViewById(R.id.personal_page_iv_publish);//用户发帖
        iv_partake = (ImageView) findViewById(R.id.personal_page_iv_partake);//用户参与
        iv_collection = (ImageView) findViewById(R.id.personal_page_iv_collection);//用户收藏
        iv_order = (ImageView) findViewById(R.id.personal_page_iv_order);//用户订单
        iv_trolley = (ImageView) findViewById(R.id.personal_page_iv_trolley);//用户购物车
        iv_news = (ImageView) findViewById(R.id.personal_page_iv_news);//用户消息
        iv_back = (ImageView) findViewById(personal_page_iv_back);//返回按钮
        ll_addPraise = (LinearLayout) findViewById(R.id.personal_page_ll_addPraise);//用户点赞
        ll_concern = (LinearLayout) findViewById(R.id.personal_page_ll_concern);//用户关注
        ll_fans = (LinearLayout) findViewById(R.id.personal_page_ll_fans);//用户粉丝

        tv_praise_num = (TextView) findViewById(R.id.personal_page_tv_praise_num);//用户得到的点赞数量
        tv_nickname = (TextView) findViewById(R.id.personal_page_tv_nickname);//用户昵称
        tv_nickname.setText(CacheConfig.user.getNickname());
        tv_sign = (TextView) findViewById(R.id.personal_page_tv_sign);//用户个性签名
        tv_sign.setText(CacheConfig.user.getSign());
        tv_concerm_num = (TextView) findViewById(R.id.personal_page_tv_concerm_num);//用户关注的数量
        tv_fans_num = (TextView) findViewById(R.id.personal_page_tv_fans_num);//用户的粉丝数量
        Glide.with(this).load(CacheConfig.user.getIconUrl()).into(iv_usericon);
        //CacheConfig.setImaget2SDCard(CacheConfig.user.getIconUrl(),iv_usericon);
}
    /**
     * 内部类实现按钮监听方法
     */
    class MyClickedListener implements View.OnClickListener {
        Intent intent;

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.personal_page_iv_usericon:
                    startActivity(new Intent(getApplicationContext(),UserInfoActivity.class));
                    finish();
                    break;
                case R.id.personal_page_iv_publish:
                    startActivity(new Intent(getApplicationContext(),PublishActivity.class));
                    finish();
                    break;
                case R.id.personal_page_iv_partake:
                    startActivity(new Intent(getApplicationContext(),PartakeActivity.class));
                    finish();
                    break;
                case R.id.personal_page_iv_collection:
                    startActivity(new Intent(getApplicationContext(),CollevtionActivity.class));
                    finish();
                    break;
                case R.id.personal_page_iv_order:
                    startActivity(new Intent(getApplicationContext(),OrderActivity.class));
                    finish();
                    break;
                case R.id.personal_page_iv_trolley:
                    startActivity(new Intent(getApplicationContext(),TrolleyActivity.class));
                    finish();
                    break;
                case R.id.personal_page_iv_news:
                    startActivity(new Intent(getApplicationContext(),NewsActivity.class));
                    finish();
                    break;
                case personal_page_iv_back:
                    onBackPressed();
                    break;
                case R.id.personal_page_ll_addPraise:
                    //TODO 用户个人界面点赞
                    Toast.makeText(getApplicationContext(), "点赞", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.personal_page_ll_concern:
                    startActivity(new Intent(getApplicationContext(),ConcernActivity.class));
                    finish();
                    break;
                case R.id.personal_page_ll_fans:
                    startActivity(new Intent(getApplicationContext(),FansActivity.class));
                    finish();
                    break;
            }
        }
    }
}
