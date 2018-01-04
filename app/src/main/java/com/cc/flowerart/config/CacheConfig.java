package com.cc.flowerart.config;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;

import com.cc.flowerart.bean.User;
import com.cc.flowerart.utils.FileUtils;
import com.cc.flowerart.utils.ImageUtil;
import net.bither.util.NativeUtil;
import com.cc.flowerart.utils.StringUtil;

import java.io.IOException;

import static com.cc.flowerart.utils.ImageUtil.getLoacalBitmap;


/**
 * Created by Administrator on 2017/8/5.
 */

public class CacheConfig {
    public static User user;

    /**
     * 图片缓存到SD卡
     *
     * @param key
     * @param imageView
     */
/*
    public static synchronized void setImaget2SDCard(final String key, final ImageView imageView) {
        if (key != null) {
            String fileName = StringUtil.URL2ID(key) + ".png";
            final String path = FileUtils.IMG_CACHE_XUTILS_SDCARD_PATH + fileName;
            Bitmap bitmap = getLoacalBitmap(path);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                final Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Bundle data = msg.getData();
                        byte[] val = data.getByteArray("value");
                        Bitmap bitmap = ImageUtil.Bytes2Bimap(val);
                        NativeUtil.compressBitmap(bitmap,path);
                        imageView.setImageBitmap(getLoacalBitmap(path));
//                        imageView.setImageBitmap(bitmap);
//                        byte[] byteImg = ImageUtil.Bitmap2Bytes(bitmap);
//                        try {
//                            FileUtils.saveFile2SDCard(path, byteImg);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                    }
                };
                Runnable networkTask = new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = ImageUtil.getBitMapByUrl(key);
                        byte[] t = ImageUtil.Bitmap2Bytes(bitmap);
                        Bundle data = new Bundle();
                        data.putByteArray("value", t);
                        Message msg = new Message();
                        msg.setData(data);
                        handler.sendMessage(msg);
                    }
                };
                new Thread(networkTask).start();
            }
        }
    }

    public static synchronized String setImaget2SDCard(final String key, final WebView webView) {
        if (key != null) {
            String fileName = StringUtil.URL2ID(key);
            final String path = FileUtils.IMG_CACHE_XUTILS_SDCARD_PATH + fileName;
            Bitmap bitmap = getLoacalBitmap(path);
            if (bitmap != null) {
                HtmlConfig html = new HtmlConfig("file://" + path);
                Log.w("html", html.getHtml());
                webView.loadDataWithBaseURL(null, html.getHtml(), "text/html", "utf-8", null);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebChromeClient(new WebChromeClient());
            } else {
                final Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Bundle data = msg.getData();
                        byte[] val = data.getByteArray("value");
                        Bitmap bitmap = ImageUtil.Bytes2Bimap(val);

                        byte[] byteImg = ImageUtil.Bitmap2Bytes(bitmap);

                        HtmlConfig html = new HtmlConfig(key);
                        webView.loadDataWithBaseURL(null, html.getHtml(), "text/html", "utf-8", null);
                        webView.getSettings().setJavaScriptEnabled(true);
                        webView.setWebChromeClient(new WebChromeClient());

                        try {
                            FileUtils.saveFile2SDCard(path, byteImg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Runnable networkTask = new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = ImageUtil.getBitMapByUrl(key);
                        byte[] t = ImageUtil.Bitmap2Bytes(bitmap);
                        Bundle data = new Bundle();
                        data.putByteArray("value", t);
                        Message msg = new Message();
                        msg.setData(data);
                        handler.sendMessage(msg);
                    }
                };
                new Thread(networkTask).start();
            }
        }
        return null;
    }
*/
}
