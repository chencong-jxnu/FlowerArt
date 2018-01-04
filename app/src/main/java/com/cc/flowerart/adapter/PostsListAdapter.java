package com.cc.flowerart.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.cc.flowerart.R;
import com.cc.flowerart.activity.ShowPictureActivity;
import com.cc.flowerart.bean.Post;
import com.cc.flowerart.bean.User;
import com.cc.flowerart.config.ServeConfig;
import com.cc.flowerart.utils.AsyncTaskHelper;
import com.cc.flowerart.utils.BeanUtil;
import com.cc.flowerart.utils.FastJsonTools;
import com.cc.flowerart.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**
 * Created by Administrator on 2017/8/12.
 */

public class PostsListAdapter extends BaseAdapter {
    private Context context;
    private List<Post> list;
    private PostClickListener listener;

    public PostsListAdapter(Context context) {
        this.context = context;
    }

    public interface PostClickListener {
        public void addCollection(Post post);

        public void addPraise(Post post);

        public void addComment(Post post);

        public void lookPost(Post post);
    }

    public void setPostClickListener(PostClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<Post> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private void setListeners(ViewHolder holder, int position) {
        final Post post = list.get(position);
        holder.rl_answer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                listener.addComment(post);
            }
        });
        holder.rl_collection.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                listener.addCollection(post);
            }
        });
        holder.rl_praise.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                listener.addPraise(post);
            }
        });
        holder.rl_userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.lookPost(post);
            }
        });
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

    // 生成一个列表项
    // @convertView:滑动列表的时候已经滑出去的item，即将被回收内存
    // @parent: 装载item的父容器，这里就是ListView
    // 当生成一个列表项的时候，每次都要调用getView()方法来创建，而新建View和findViewById()这两个操作很耗时
    // 进行优化的步骤：1.复用converView。2.使用自定义类ViewHolder。
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.froum_listview_item, parent, false);
            holder = new ViewHolder(convertView);
            // 对ViewHolder对象设置Tag
            convertView.setTag(holder);
        } else {
            // 获取Tag，避免重复使用findViewById()
            holder = (ViewHolder) convertView.getTag();
        }
        setPostInfo(holder, position);
        //设置点击事件
        setListeners(holder, position);
        return convertView;
    }

    private void setPostInfo(ViewHolder holder, int position) {
        Post post = list.get(position);
        holder.tv_time.setText(post.getTime());
        //获取点赞数
        holder.tv_praiseNum.setText(list.get(position).getPraiseNum() + "");
        //获取收藏数
        holder.tv_collectionNum.setText(list.get(position).getCollectionNum() + "");
        //获取评论数量
        holder.tv_answerNum.setText(list.get(position).getCommentNum() + "");
        //设置帖子内容
        holder.tv_content.setText(post.getContent());
        //设置用户信息
        setUser(position, holder);
        //设置图片
        setPictures(position, holder);

    }

    private void setPictures(int position, ViewHolder holder) {
        List<String> urlsList = new ArrayList<>();
        String str = list.get(position).getPictureUrls();
        String urls[] = str.split(",");
        for (int i = 0; i < urls.length; i++) {
            urlsList.add(urls[i]);
        }
        PostPicturesAdapter adapter = new PostPicturesAdapter(context);
        adapter.setData(urlsList);
        holder.gv_pictures.setAdapter(adapter);
        adapter.setPictureClickListener(new MyPictureClickedListener());
    }

    private void setUser(int position, final ViewHolder holder) {
        ServeConfig config = new ServeConfig("UserServlet", "select");
        Map<String, Object> map;
        final User t = new User();
        t.setId(list.get(position).getUserId());
        map = BeanUtil.BeanToMap(t);
        String str = JSON.toJSONString(map);
        String URL = config.getURL() + StringUtil.strEncode(str);
        AsyncTaskHelper.OnDataDownloadListener downloadListener;
        AsyncTaskHelper taskHelper = new AsyncTaskHelper();
        downloadListener = new AsyncTaskHelper.OnDataDownloadListener() {
            @Override
            public void onDataDownload(byte[] result) {
                //处理从后台获得的数据
                String jsonString = new String(result); // 返回的字节数组转换为字符串
                Map<String, Object> resultMap;
                resultMap = FastJsonTools.getMap(jsonString);
                int code = (int) resultMap.get("code");
                if (code == 1) {
                    String userInfo = resultMap.get("result").toString();
                    User user = FastJsonTools.getBeans(userInfo, User.class).get(0);
                    //获取楼主的昵称
                    holder.tv_nikeName.setText(user.getNickname());
                    //设置楼主头像
                    Glide.with(context).load(user.getIconUrl()).into(holder.iv_userIcon);
                } else if (code == 0) {
                    Toast.makeText(context, resultMap.get("msg").toString(), Toast.LENGTH_SHORT).show();
                }
            }
        };
        taskHelper.downloadData(context, URL, downloadListener); // 发起服务器的访问
    }

    private class MyPictureClickedListener implements PostPicturesAdapter.PictureClickListener {
        @Override
        public void ShowPicter(String pictureUrl) {
            //TODO 全屏显示大图
            Intent intent = new Intent(context, ShowPictureActivity.class);
            intent.putExtra("pictureUrl", pictureUrl);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            Toast.makeText(context, "全屏显示大图", Toast.LENGTH_SHORT).show();
        }
    }

    private class ViewHolder {
        // 定义布局中的组件
        private RelativeLayout rl_collection, rl_answer, rl_praise, rl_userInfo;
        private TextView tv_collectionNum, tv_answerNum, tv_praiseNum, tv_nikeName, tv_time, tv_content;
        private GridView gv_pictures;
        private ImageView iv_userIcon;

        ViewHolder(View view) {
            rl_userInfo = (RelativeLayout) view.findViewById(R.id.froum_listview_item_rl_userInfo);
            rl_answer = (RelativeLayout) view.findViewById(R.id.froum_listview_item_rl_answer);
            rl_collection = (RelativeLayout) view.findViewById(R.id.froum_listview_item_rl_collection);
            rl_praise = (RelativeLayout) view.findViewById(R.id.froum_listview_item_rl_praise);
            tv_answerNum = (TextView) view.findViewById(R.id.froum_listview_item_tv_answerNum);
            tv_collectionNum = (TextView) view.findViewById(R.id.froum_listview_item_tv_collectionNum);
            tv_nikeName = (TextView) view.findViewById(R.id.froum_listview_item_tv_nikeName);
            tv_praiseNum = (TextView) view.findViewById(R.id.froum_listview_item_tv_praiseNum);
            tv_time = (TextView) view.findViewById(R.id.froum_listview_item_tv_time);
            tv_content = (TextView) view.findViewById(R.id.froum_listview_item_tv_content);
            iv_userIcon = (ImageView) view.findViewById(R.id.froum_listview_item_iv_userIcon);
            gv_pictures = (GridView) view.findViewById(R.id.froum_listview_item_gv_pictures);
        }
    }

}
