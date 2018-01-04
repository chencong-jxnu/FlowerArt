package com.cc.flowerart.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cc.flowerart.R;
import com.cc.flowerart.config.CacheConfig;
import com.cc.flowerart.config.ScreenConfig;

import java.util.List;


/**
 * Created by Administrator on 2017/8/13.
 */

public class PostPicturesAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;
    private PictureClickListener listener;

    public interface PictureClickListener {
        public void ShowPicter(String pictureUrl);
    }

    public PostPicturesAdapter(Context context) {
        this.context = context;
    }

    public void setPictureClickListener(PictureClickListener listener) {
        this.listener = listener;
    }

    private void setListeners(ViewHolder holder, final int position) {
        holder.iv_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.ShowPicter(list.get(position));
            }
        });

    }


    public void setData(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_gridview_item, parent, false);
            holder = new ViewHolder();
            holder.iv_picture = (ImageView) convertView.findViewById(R.id.post_gridview_item_iv_picture);
            ScreenConfig.init(context);
            int width = (ScreenConfig.width_pix - 40 * (int) ScreenConfig.density) / 3;
            ViewGroup.LayoutParams params = holder.iv_picture.getLayoutParams();
            params.height = width;
            params.width = width;
            holder.iv_picture.setLayoutParams(params);
            // 对ViewHolder对象设置Tag
            convertView.setTag(holder);
        } else {
            // 获取Tag，避免重复使用findViewById()
            holder = (ViewHolder) convertView.getTag();
        }
        setPicture(holder, position);
        //设置点击事件
        setListeners(holder, position);
        return convertView;
    }

    private void setPicture(final ViewHolder holder, int position) {
        final String url = list.get(position);
        Glide.with(context).load(url).into(holder.iv_picture);
        //CacheConfig.setImaget2SDCard(url,holder.iv_picture);
    }

    private class ViewHolder {
        private ImageView iv_picture;
    }
}
