package com.cc.flowerart.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.cc.flowerart.R;
import com.cc.flowerart.adapter.CommentListAdapter;
import com.cc.flowerart.adapter.PostPicturesAdapter;
import com.cc.flowerart.adapter.PostsListAdapter;
import com.cc.flowerart.bean.Comment;
import com.cc.flowerart.bean.User;
import com.cc.flowerart.config.CacheConfig;
import com.cc.flowerart.config.ServeConfig;
import com.cc.flowerart.utils.AsyncTaskHelper;
import com.cc.flowerart.utils.BeanUtil;
import com.cc.flowerart.utils.DateUtil;
import com.cc.flowerart.utils.FastJsonTools;
import com.cc.flowerart.utils.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentActivity extends AppCompatActivity {
    private Button bt_send;
    private EditText et_comment;
    private GridView gv_pictures;
    private ListView lv_comments;
    private TextView tv_time, tv_content;
    private ImageView iv_userIcon,iv_back;
    private String postID;
    private AsyncTaskHelper.OnDataDownloadListener downloadListener;
    private AsyncTaskHelper taskHelper; // 加载网络类
    private CommentListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        init();
        setUser();//设置贴主信息
        setPostInfo();//设置帖子的内容
        setCommentLitView();//设置显示评论
    }

    private void setUser() {
        ServeConfig config = new ServeConfig("UserServlet", "select");
        User t = new User();
        t.setId(getIntent().getStringExtra("postUserID"));
        Map<String, Object> map = BeanUtil.BeanToMap(t);
        String str = JSON.toJSONString(map);
        String URL = config.getURL() + StringUtil.strEncode(str);
        downloadListener = new AsyncTaskHelper.OnDataDownloadListener() { // 访问服务器后获取返回数据
            @Override
            public void onDataDownload(byte[] result) {
                String jsonString = new String(result); // 返回的字节数组转换为字符串
                Map<String, Object> resultMap;
                resultMap = FastJsonTools.getMap(jsonString);
                int code = (int) resultMap.get("code");
                if (code == 1) {
                    String userInfo = resultMap.get("result").toString();
                    User user = FastJsonTools.getBeans(userInfo, User.class).get(0);
                    Glide.with(getApplicationContext()).load(user.getIconUrl()).into(iv_userIcon);
                } else if (code == 0) {

                }
            }
        };
        taskHelper.downloadData(getApplicationContext(), URL, downloadListener); // 发起服务器的访问
    }

    private void setPostInfo() {
        /*
        设置帖子的时间和内容
         */
        tv_time.setText(getIntent().getStringExtra("postTime"));
        tv_content.setText(getIntent().getStringExtra("postContent"));
        /*
        设置帖子的图片
         */
        setPictures();
    }

    private void setPictures() {
        List<String> urlsList = new ArrayList<>();
        String str = getIntent().getStringExtra("postPictureUrls");
        String urls[] = str.split(",");
        for (int i = 0; i < urls.length; i++) {
            urlsList.add(urls[i]);
        }
        PostPicturesAdapter adapter = new PostPicturesAdapter(this);
        adapter.setData(urlsList);
        gv_pictures.setAdapter(adapter);
        adapter.setPictureClickListener(new MyPictureClickedListener());
    }

    private void setCommentLitView() {
        ServeConfig config = new ServeConfig("CommentServlet","getCommentNumByTargetId");
        Comment t = new Comment();
        t.setTargetId(postID);
        Map<String, Object> map = BeanUtil.BeanToMap(t);
        String str = JSON.toJSONString(map);
        String URL = config.getURL() + StringUtil.strEncode(str);
        Log.e("URL",URL);
        downloadListener = new AsyncTaskHelper.OnDataDownloadListener() {
            @Override
            public void onDataDownload(byte[] result) {
                String jsonString = new String(result); // 返回的字节数组转换为字符串
                Map<String, Object> resultMap;
                resultMap = FastJsonTools.getMap(jsonString);
                int code = (int) resultMap.get("code");
                if (code == 1) {
                    String commentsInfo = resultMap.get("result").toString();
                    List<Comment> commentList= FastJsonTools.getBeans(commentsInfo, Comment.class);
                    adapter.setData(commentList);
                } else if (code == 0) {
                    Toast.makeText(getApplicationContext(), "暂无评论",
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
        taskHelper.downloadData(getApplicationContext(), URL, downloadListener); // 发起服务器的访问
    }

    private void init() {
        iv_back = (ImageView) findViewById(R.id.activity_comment_iv_back);
        iv_back.setOnClickListener(new MyOnCliskListener());
        et_comment = (EditText) findViewById(R.id.activity_comment_et_comment);
        bt_send = (Button) findViewById(R.id.activity_comment_bt_send);
        bt_send.setOnClickListener(new MyOnCliskListener());
        taskHelper = new AsyncTaskHelper();
        postID = getIntent().getStringExtra("postID");
        lv_comments = (ListView) findViewById(R.id.activity_comment_lv_comments);
        View view = LayoutInflater.from(this).inflate(R.layout.comment_listview_headview,lv_comments,false);
        gv_pictures = (GridView) view.findViewById(R.id.comment_listview_headview_gv_pictures);
        iv_userIcon = (ImageView) view.findViewById(R.id.comment_listview_headview_iv_userIcon);
        tv_time= (TextView) view.findViewById(R.id.comment_listview_headview_tv_time);
        tv_content= (TextView) view.findViewById(R.id.comment_listview_headview_tv_content);
        adapter = new CommentListAdapter(getApplicationContext());
        lv_comments.addHeaderView(view);
        lv_comments.setAdapter(adapter);
    }

    /**
     * 发送评论
     *
     */
    private void sendComment() {
        Comment comment = new Comment();
        comment.setId(StringUtil.getUniqueId(10));
        comment.setUserId(CacheConfig.user.getId());
        comment.setTargetId(postID);
        comment.setKind("*");
        comment.setTime(DateUtil.getFormatDate(new Date()));
        comment.setContent(et_comment.getText().toString());
        ServeConfig config = new ServeConfig("CommentServlet","add");
        Map<String, Object> map = BeanUtil.BeanToMap(comment);
        String str = JSON.toJSONString(map);
        String URL = config.getURL() + StringUtil.strEncode(str);
        Log.e("URL",URL);
        AsyncTaskHelper.OnDataDownloadListener downloadListener;
        AsyncTaskHelper taskHelper = new AsyncTaskHelper(); // 加载网络类
        downloadListener = new AsyncTaskHelper.OnDataDownloadListener() {
            @Override
            public void onDataDownload(byte[] result) {
                String jsonString = new String(result); // 返回的字节数组转换为字符串
                Map<String, Object> resultMap;
                resultMap = FastJsonTools.getMap(jsonString);
                int code = (int) resultMap.get("code");
                if (code == 1) {
                    Toast.makeText(getApplicationContext(), "评论成功", Toast.LENGTH_SHORT).show();
                    et_comment.setText("");
                } else if (code == 0) {
                    Toast.makeText(getApplicationContext(), "评论失败", Toast.LENGTH_SHORT).show();
                }
            }
        };
        taskHelper.downloadData(getApplicationContext(), URL, downloadListener); // 发起服务器的访问
    }
    private class MyPictureClickedListener implements PostPicturesAdapter.PictureClickListener {
        @Override
        public void ShowPicter(String pictureUrl) {
            //TODO 全屏显示大图
            Intent intent = new Intent(getApplicationContext(), ShowPictureActivity.class);
            intent.putExtra("pictureUrl", pictureUrl);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "全屏显示大图", Toast.LENGTH_SHORT).show();
        }
    }
    private class MyOnCliskListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.activity_comment_bt_send:
                    sendComment();
                    setCommentLitView();
                    break;
                case R.id.activity_comment_iv_back:
                    finish();
            }
        }
    }


}
