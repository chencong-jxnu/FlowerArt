package com.cc.flowerart.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.cc.flowerart.R;
import com.cc.flowerart.config.CacheConfig;

public class ShowPictureActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView;
    private RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_picture);
        init();
        setListener();
    }

    private void setListener() {
        rl.setOnClickListener(this);
    }

    private void init() {
        String pictureUrl = getIntent().getStringExtra("pictureUrl");
        //Bitmap bitmap = CacheConfig.getBitmapFromMemCache(pictureUrl);
        imageView = (ImageView) findViewById(R.id.activity_show_picture_imageView);
        Glide.with(this).load(pictureUrl).into(imageView);
        //CacheConfig.setImaget2SDCard(pictureUrl,imageView);
        rl = (RelativeLayout) findViewById(R.id.activity_show_picture);
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}
