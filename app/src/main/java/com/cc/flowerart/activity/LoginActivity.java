package com.cc.flowerart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cc.flowerart.R;
import com.cc.flowerart.bean.User;
import com.cc.flowerart.config.CacheConfig;
import com.cc.flowerart.config.ServeConfig;
import com.cc.flowerart.utils.AsyncTaskHelper;
import com.cc.flowerart.utils.BeanUtil;
import com.cc.flowerart.utils.FastJsonTools;
import com.cc.flowerart.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 登陆界面
 */
public class LoginActivity extends AppCompatActivity {
    private EditText et_phone, et_password;//账号和密码输入框
    private Button btn_login;//登陆按钮
    private TextView tv_forgetpassword;//忘记密码按钮
    private ImageView iv_frok;//返回图标
    private AsyncTaskHelper.OnDataDownloadListener downloadListener;
    private AsyncTaskHelper taskHelper; // 加载网络类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        listener();
    }

    /**
     * 监听事件方法
     */
    private void listener() {
        btn_login.setOnClickListener(new MyClickedListener());
        tv_forgetpassword.setOnClickListener(new MyClickedListener());
        iv_frok.setOnClickListener(new MyClickedListener());
    }

    /**
     * 初始化方法
     */
    private void init() {
        et_phone = (EditText) findViewById(R.id.login_et_phone);
        et_password = (EditText) findViewById(R.id.login_et_password);
        btn_login = (Button) findViewById(R.id.login_btn_login);
        tv_forgetpassword = (TextView) findViewById(R.id.login_tv_forgetpassword);
        iv_frok = (ImageView) findViewById(R.id.login_iv_frok);
        taskHelper = new AsyncTaskHelper();
    }

    /**
     * 内部类实现按钮监听方法
     */
    class MyClickedListener implements View.OnClickListener {
        Intent intent;

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_btn_login:
                    //TODO 传数据给后台，判断登陆是否成功
                    login();
                    break;
                case R.id.login_tv_forgetpassword:
                    intent = new Intent(getApplicationContext(), ForgetPasswordActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.login_iv_frok:
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    }

    private void login() {
        ServeConfig config = new ServeConfig("UserServlet", "login");
        User t = new User();
        t.setId(et_phone.getText().toString());
        t.setPassword(et_password.getText().toString());
        Map<String, Object> map = BeanUtil.BeanToMap(t);
        String str = JSON.toJSONString(map);
        String URL = config.getURL() + StringUtil.strEncode(str);
        Log.e("LoginUrl",URL);
        downloadListener = new AsyncTaskHelper.OnDataDownloadListener() { // 访问服务器后获取返回数据
            @Override
            public void onDataDownload(byte[] result) {
                String jsonString = new String(result); // 返回的字节数组转换为字符串
                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap = FastJsonTools.getMap(jsonString);
                int code = (int) resultMap.get("code");
                if (code == 1) {
                    String userInfo = resultMap.get("result").toString();
                    User userInDB = FastJsonTools.getBeans(userInfo, User.class).get(0);
                    CacheConfig.user = userInDB;
                    Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                } else if (code == 0) {
                    Toast.makeText(getApplicationContext(), resultMap.get("msg").toString(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
        taskHelper.downloadData(getApplicationContext(), URL, downloadListener); // 发起服务器的访问
    }
}
