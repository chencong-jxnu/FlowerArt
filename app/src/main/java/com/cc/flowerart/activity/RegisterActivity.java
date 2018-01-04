package com.cc.flowerart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static com.cc.flowerart.R.id.register_btn_register;


/**
 * 注册界面
 */
public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "SMS-SDK";
    private EditText et_phone, et_password, et_code;//账号和密码输入框
    private Button btn_register, btn_getCode;//登陆按钮
    private ImageView iv_frok;//返回图标
    private EventHandler eventHandler;
    private AsyncTaskHelper.OnDataDownloadListener downloadListener;
    private AsyncTaskHelper taskHelper; // 加载网络类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        listener();
    }

    private void listener() {
        btn_register.setOnClickListener(new MyClickedListener());
        btn_getCode.setOnClickListener(new MyClickedListener());
        iv_frok.setOnClickListener(new MyClickedListener());
    }

    private void init() {

        //短信验证码相关
        SMSSDK.initSDK(this, "1f9cd801a5658", "a745508c8c6bf17cb7478d3dc6be7163");
        eventHandler = new EventHandler() {
            /**
             * 在操作之后被触发
             *
             * @param event  参数1
             * @param result 参数2 SMSSDK.RESULT_COMPLETE表示操作成功，为SMSSDK.RESULT_ERROR表示操作失败
             * @param data   事件操作的结果
             */
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message message = myHandler.obtainMessage(0x00);
                message.arg1 = event;
                message.arg2 = result;
                message.obj = data;
                myHandler.sendMessage(message);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);

        et_phone = (EditText) findViewById(R.id.register_et_phone);
        et_password = (EditText) findViewById(R.id.forget_password_et_password);
        et_code = (EditText) findViewById(R.id.register_et_code);
        btn_register = (Button) findViewById(R.id.register_btn_register);
        btn_getCode = (Button) findViewById(R.id.register_btn_getCode);
        iv_frok = (ImageView) findViewById(R.id.register_iv_frok);
        taskHelper = new AsyncTaskHelper();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }


    /**
     * 内部类实现按钮监听方法
     */
    class MyClickedListener implements View.OnClickListener {
        Intent intent;

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.register_btn_getCode:
                    //点击获取验证码
                    getCode();
                    break;
                case register_btn_register:
                    //点击穿数据到后台判断是否注册成功
                    register();
                    break;
                case R.id.register_iv_frok:
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }

        /**
         * 注册操作，验证短信验证码
         */
        private void register() {
            String password = et_password.getText().toString();
            if (StringUtil.isBlank(password)) {
                Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
            } else if (password.length() < 6) {
                Toast.makeText(getApplicationContext(), "密码不能小于6位", Toast.LENGTH_SHORT).show();
            } else {
                String code = et_code.getText().toString();
                if (null != code && code.length() == 4) {
                    Log.d(TAG, code);
                    SMSSDK.submitVerificationCode("86", et_phone.getText().toString(), code);
                } else {
                    Toast.makeText(getApplicationContext(), "验证码输入有误", Toast.LENGTH_SHORT).show();
                }
            }
        }

        /**
         * 获取验证码
         */
        private void getCode() {
            String strPhoneNumber = et_phone.getText().toString();
            if (null == strPhoneNumber || "".equals(strPhoneNumber) || !StringUtil.isPhoneNum(strPhoneNumber)) {
                Toast.makeText(getApplicationContext(), "电话号码输入有误", Toast.LENGTH_SHORT).show();
                return;
            }
            SMSSDK.getVerificationCode("86", strPhoneNumber);
            btn_getCode.setClickable(false);
            btn_getCode.setTextColor(getResources().getColor(R.color.colorCutLine));
            //开启线程去更新button的text
            new Thread() {
                @Override
                public void run() {
                    int totalTime = 60;
                    for (int i = 0; i < totalTime; i++) {
                        Message message = myHandler.obtainMessage(0x01);
                        message.arg1 = totalTime - i;
                        myHandler.sendMessage(message);
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    myHandler.sendEmptyMessage(0x02);
                }
            }.start();
        }
    }

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x00:
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    Log.e(TAG, "result : " + result + ", event: " + event + ", data : " + data);
                    if (result == SMSSDK.RESULT_COMPLETE) { //回调  当返回的结果是complete
                        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) { //获取验证码
                            Toast.makeText(getApplicationContext(), "发送验证码成功", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "get verification code successful.");
                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) { //提交验证码
                            Log.d(TAG, "submit code successful");
                            // 连接服务器将密码账号存储起来
                            ServeConfig config = new ServeConfig("UserServlet", "add");
                            User t = new User();
                            t.setId(et_phone.getText().toString());
                            t.setPassword(et_password.getText().toString());
                            Map<String, Object> map = BeanUtil.BeanToMap(t);
                            String str = JSON.toJSONString(map);
                            try {
                                String URL = config.getURL() + StringUtil.strEncode(str);
                                downloadListener = new AsyncTaskHelper.OnDataDownloadListener() { // 访问服务器后获取返回数据
                                    @Override
                                    public void onDataDownload(byte[] result) {
                                        String jsonString = new String(result); // 返回的字节数组转换为字符串
                                        Map<String, Object> resultMap = new HashMap<String, Object>();
                                        resultMap = FastJsonTools.getMap(jsonString);
                                        int code = (int) resultMap.get("code");
                                        if (code == 1) {
                                            Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                                            User t = new User();
                                            t.setId(et_phone.getText().toString());
                                            t.setPassword(et_password.getText().toString());
                                            CacheConfig.user = t;
                                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                            finish();
                                        } else if (code == 0) {
                                            Toast.makeText(getApplicationContext(), "注册失败，该手机号已经注册！",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                };
                                taskHelper.downloadData(getApplicationContext(), URL, downloadListener); // 发起服务器的访问
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d(TAG, data.toString());
                        }
                    } else { //进行操作出错，通过下面的信息区分析错误原因
                        try {
                            Throwable throwable = (Throwable) data;
                            throwable.printStackTrace();
                            JSONObject object = new JSONObject(throwable.getMessage());
                            String des = object.optString("detail");//错误描述
                            int status = object.optInt("status");//错误代码
                            //错误代码：  http://wiki.mob.com/android-api-%E9%94%99%E8%AF%AF%E7%A0%81%E5%8F%82%E8%80%83/
                            Log.e(TAG, "status: " + status + ", detail: " + des);
                            if (status > 0 && !TextUtils.isEmpty(des)) {
                                Toast.makeText(getApplicationContext(), des, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage());
                        }
                    }
                    break;
                case 0x01:
                    btn_getCode.setText("重新发送(" + msg.arg1 + ")");
                    break;
                case 0x02:
                    btn_getCode.setText("获取验证码");
                    btn_getCode.setClickable(true);
                    btn_getCode.setTextColor(getResources().getColor(R.color.colorText));
                    break;
            }
        }
    };
}
