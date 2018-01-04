package com.cc.flowerart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cc.flowerart.R;

/**
 * 登陆前主界面
 */
public class MainActivity extends AppCompatActivity {
    private Button btn_login, btn_register;//选择登陆和注册按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_login_register);
        init();
        listener();
    }

    /**
     * 事件监听方法
     */
    private void listener() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 控件初始化方法
     */
    private void init() {
        btn_login = (Button) findViewById(R.id.loginregister_login);
        btn_register = (Button) findViewById(R.id.loginregister_register);
    }
}
