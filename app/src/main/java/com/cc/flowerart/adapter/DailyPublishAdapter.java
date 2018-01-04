package com.cc.flowerart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cc.flowerart.R;
import com.cc.flowerart.bean.DailyPublish;
import com.cc.flowerart.config.CacheConfig;
import com.cc.flowerart.config.ScreenConfig;

import java.util.List;

/**
 * Created by Administrator on 2017/8/20.
 */

public class DailyPublishAdapter extends BaseAdapter {
    List<DailyPublish> list;
    private Context context;
    private ClickedListener listener;

    public interface ClickedListener {
        public void ShowContent(DailyPublish dailyPublish,int position);
    }

    public DailyPublishAdapter(Context context) {
        this.context = context;
    }

    public void setClickedListener(ClickedListener listener) {
        this.listener = listener;
    }

    public void setData(List<DailyPublish> list) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.daily_publish_item, parent, false);
            holder = new ViewHolder();
            holder.iv_titleIcon = (ImageView) convertView.findViewById(R.id.daily_publish_item_iv_titltIcon);
            holder.tv_browseNum = (TextView) convertView.findViewById(R.id.daily_publish_item_tv_browseNum);
            holder.tv_title = (TextView) convertView.findViewById(R.id.daily_publish_item_tv_title);
            holder.tv_time = (TextView) convertView.findViewById(R.id.daily_publish_item_tv_time);
            ScreenConfig.init(context);
            int width = (ScreenConfig.width_pix - 40 * (int) ScreenConfig.density) / 2;
            ViewGroup.LayoutParams params = holder.iv_titleIcon.getLayoutParams();
            params.height = width;
            params.width = width;
            holder.iv_titleIcon.setLayoutParams(params);
            // 对ViewHolder对象设置Tag
            convertView.setTag(holder);
        } else {
            // 获取Tag，避免重复使用findViewById()
            holder = (ViewHolder) convertView.getTag();
        }
        setInfo(holder, position);
        //设置点击事件
        setListeners(holder, position);
        return convertView;
    }

    private void setListeners(ViewHolder holder, final int position) {
        holder.iv_titleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.ShowContent(list.get(position),position);
            }
        });
    }

    private void setInfo(ViewHolder holder, int position) {
        DailyPublish dailyPublish = list.get(position);
        String title = dailyPublish.getTitle();
        String time = dailyPublish.getTime();
        int browseNum = dailyPublish.getBrowseNum();
        String titleIcon = dailyPublish.getTitleIcon();
        holder.tv_title.setText(title);
        holder.tv_time.setText(time);
        holder.tv_browseNum.setText(browseNum + "");
        Glide.with(context).load(titleIcon).into(holder.iv_titleIcon);
    }

    class ViewHolder {
        private TextView tv_browseNum;
        private TextView tv_time,tv_title;
        private ImageView iv_titleIcon;
    }
}
