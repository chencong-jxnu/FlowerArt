package com.cc.flowerart.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.cc.flowerart.R;
import com.cc.flowerart.bean.Comment;
import com.cc.flowerart.bean.User;
import com.cc.flowerart.config.ServeConfig;
import com.cc.flowerart.utils.AsyncTaskHelper;
import com.cc.flowerart.utils.BeanUtil;
import com.cc.flowerart.utils.FastJsonTools;
import com.cc.flowerart.utils.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/3 0003.
 */

public class CommentListAdapter extends BaseAdapter {
    private Context context;
    private List<Comment> list;


    public CommentListAdapter(Context context){
        this.context = context;
    }
    public void setData(List<Comment> list) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_listview_item, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            // 获取Tag，避免重复使用findViewById()
            holder = (ViewHolder) convertView.getTag();
        }
        //设置评论数据
        setCommentData(position,holder);
        return convertView;
    }

    private void setCommentData(int position, final ViewHolder holder) {
        final Comment comment = list.get(position);
        String userID = comment.getUserId();
        User user = holder.map.get(userID);
        if(user == null){
            AsyncTaskHelper.OnDataDownloadListener downloadListener;
            AsyncTaskHelper taskHelper = new AsyncTaskHelper(); // 加载网络类
            ServeConfig config = new ServeConfig("UserServlet", "select");
            user = new User();
            user.setId(userID);
            Map<String, Object> map = BeanUtil.BeanToMap(user);
            String str = JSON.toJSONString(map);
            String URL = config.getURL() + StringUtil.strEncode(str);
            downloadListener = new AsyncTaskHelper.OnDataDownloadListener() { // 访问服务器后获取返回数据
                @Override
                public void onDataDownload(byte[] result) {
                    String jsonString = new String(result); // 返回的字节数组转换为字符串
                    Map<String, Object> resultMap = new HashMap<String, Object>();
                    resultMap = FastJsonTools.getMap(jsonString);
                    int code = (int) resultMap.get("code");
                    if (code == 1) {
                        String userInfo = resultMap.get("result").toString();
                        User user = FastJsonTools.getBeans(userInfo, User.class).get(0);
                        Glide.with(context).load(user.getIconUrl()).into(holder.iv_userIcon);
                        holder.tv_nickName.setText(user.getNickname());
                        holder.tv_comment.setText(comment.getContent());
                        holder.map.put(user.getId(),user);
                    } else if (code == 0) {

                    }
                }
            };
            taskHelper.downloadData(context, URL, downloadListener); // 发起服务器的访问
        }else{
            Glide.with(context).load(user.getIconUrl()).into(holder.iv_userIcon);
            holder.tv_nickName.setText(user.getNickname());
            holder.tv_comment.setText(comment.getContent());
        }

    }

    private class ViewHolder{
        private TextView tv_nickName,tv_comment;
        private ImageView iv_userIcon;
        private Map<String,User> map;

        ViewHolder(View view){
            map = new HashMap<>();
            tv_nickName = (TextView) view.findViewById(R.id.comment_listview_item_tv_nickName);
            tv_comment = (TextView) view.findViewById(R.id.comment_listview_item_tv_comment);
            iv_userIcon = (ImageView) view.findViewById(R.id.comment_istview_iv_userIcon);
        }

    }
}
