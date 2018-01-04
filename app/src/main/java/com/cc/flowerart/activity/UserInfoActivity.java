package com.cc.flowerart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cc.flowerart.R;
import com.cc.flowerart.config.CacheConfig;
import com.cc.widget.circleimageview.CircleImageView;

public class UserInfoActivity extends AppCompatActivity {
    private ImageView iv_back;
    private CircleImageView iv_userIcon;
    private TextView tv_save, tv_sex, tv_add;
    private EditText et_nickname, et_sign, et_phoneNum;
    private RelativeLayout rl_changeUserIcon, rl_sex, rl_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        init();
        listener();
    }

    /**
     * 点击事件的监听
     */
    private void listener() {
        iv_userIcon.setOnClickListener(new MyClickedListener());
        iv_back.setOnClickListener(new MyClickedListener());
        tv_save.setOnClickListener(new MyClickedListener());
        rl_add.setOnClickListener(new MyClickedListener());
        rl_changeUserIcon.setOnClickListener(new MyClickedListener());
        rl_sex.setOnClickListener(new MyClickedListener());

    }

    /**
     * 初始化控件
     */
    private void init() {
        iv_userIcon = (CircleImageView) findViewById(R.id.user_info_iv_userIcon);
        iv_back = (ImageView) findViewById(R.id.user_info_iv_back);
        tv_save = (TextView) findViewById(R.id.user_info_tv_save);
        tv_sex = (TextView) findViewById(R.id.user_info_tv_sex);
        tv_sex.setText(CacheConfig.user.getSex());
        tv_add = (TextView) findViewById(R.id.user_info_tv_add);
        tv_add.setText(CacheConfig.user.getAddress());
        et_nickname = (EditText) findViewById(R.id.user_info_et_nickname);
        et_nickname.setText(CacheConfig.user.getNickname());
        et_sign = (EditText) findViewById(R.id.user_info_et_sign);
        et_sign.setText(CacheConfig.user.getSign());
        et_phoneNum = (EditText) findViewById(R.id.user_info_et_phoneNum);
        et_phoneNum.setText(CacheConfig.user.getId());
        rl_changeUserIcon = (RelativeLayout) findViewById(R.id.user_info_rl_changeUserIcon);
        rl_sex = (RelativeLayout) findViewById(R.id.user_info_rl_sex);
        rl_add = (RelativeLayout) findViewById(R.id.user_info_rl_add);

        //   CacheConfig.initMemoryCache();
        Glide.with(this).load(CacheConfig.user.getIconUrl()).into(iv_userIcon);
        //CacheConfig.setImaget2SDCard(CacheConfig.user.getIconUrl(), iv_userIcon);

    }

    /**
     * 内部类，实现控件的监听方法
     */
    class MyClickedListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.user_info_iv_userIcon:
                    break;
                case R.id.user_info_iv_back:
                    startActivity(new Intent(getApplicationContext(), PersonalPageActivity.class));
                    finish();
                    break;
                case R.id.user_info_tv_save:
                    //TODO 更新数据库
                    startActivity(new Intent(getApplicationContext(), PersonalPageActivity.class));
                    finish();
                    break;
                case R.id.user_info_rl_add:
                    //TODO 弹出地址三级联动
                    Toast.makeText(getApplicationContext(), "地址", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.user_info_rl_changeUserIcon:
                    //TODO 弹出图片选择器
                    Toast.makeText(getApplicationContext(), "用户更改头像", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.user_info_rl_sex:
                    //TODO 弹出性别选择器
                    Toast.makeText(getApplicationContext(), "用户性别", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
