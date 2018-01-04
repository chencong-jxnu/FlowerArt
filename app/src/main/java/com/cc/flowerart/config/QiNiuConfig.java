package com.cc.flowerart.config;

import android.util.Log;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.File;

/**
 * Created by Administrator on 2017/7/15.
 */

public class QiNiuConfig {
    public static String QINIU_AK = "1viklm3YqUOT9zaswD__hclSJXBISjLZndqTCsMC";
    public static String QINIU_SK = "QyeN3lrGla5Nk5GMYsxr4G6sfxytnx0zeijWlEX3";
    public static String QINIU_BUCKNAME = "flower-art";
    public static String QINIU_TOKEN = "1viklm3YqUOT9zaswD__hclSJXBISjLZndqTCsMC:n0OOunUixJtCNosPcIkLzcbDDC4=:eyJzY29wZSI6ImZsb3dlci1hcnQiLCJkZWFkbGluZSI6MzMwMjQ2NzU2NX0=";

    public static void uploadImage(String key, String token, File data) {
        UploadManager uploadManager = new UploadManager();
        uploadManager.put(data, key, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if (info.isOK()) {
                            Log.i("qiniu", "Upload Success");
                        } else {
                            Log.i("qiniu", "Upload Fail");
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                    }
                }, null);
    }

    public static void uploadImage(String key, String token, byte[] data) {
        UploadManager uploadManager = new UploadManager();
        uploadManager.put(data, key, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if (info.isOK()) {
                            Log.i("qiniu", "Upload Success");
                        } else {
                            Log.i("qiniu", "Upload Fail");
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                    }
                }, null);
    }

    public static void uploadImage(String key, String token, String path) {
        UploadManager uploadManager = new UploadManager();
        uploadManager.put(path, key, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if (info.isOK()) {
                            Log.i("qiniu", "Upload Success");
                        } else {
                            Log.i("qiniu", "Upload Fail>>" + info.error);
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                    }
                }, null);
    }
}
