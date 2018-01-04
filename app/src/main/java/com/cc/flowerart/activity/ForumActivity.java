package com.cc.flowerart.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cc.flowerart.R;
import com.cc.flowerart.adapter.PostsListAdapter;
import com.cc.flowerart.bean.Post;
import com.cc.flowerart.config.ServeConfig;
import com.cc.flowerart.utils.AsyncTaskHelper;
import com.cc.flowerart.utils.BeanUtil;
import com.cc.flowerart.utils.FastJsonTools;
import com.cc.flowerart.utils.StringUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ForumActivity extends AppCompatActivity {
    private TextView tv_new, tv_flowerArt, tv_gardenArt, tv_house, tv_life, tv_temp;
    private ListView lv_posts;
    private ImageView iv_back;
    private List<Post> postList = new LinkedList<>();
    private PostsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        init();
        listener();
    }

    private void listener() {
        tv_new.setOnClickListener(new MyClickedListener());
        tv_flowerArt.setOnClickListener(new MyClickedListener());
        tv_gardenArt.setOnClickListener(new MyClickedListener());
        tv_house.setOnClickListener(new MyClickedListener());
        tv_life.setOnClickListener(new MyClickedListener());
        iv_back.setOnClickListener(new MyClickedListener());

        adapter.setPostClickListener(new MyPostClickedListener());
    }

    private void init() {
        adapter = new PostsListAdapter(getApplicationContext());
        tv_new = (TextView) findViewById(R.id.activity_forum_tv_new);
        tv_temp = tv_new;
        tv_flowerArt = (TextView) findViewById(R.id.activity_forum_tv_flowerArt);
        tv_gardenArt = (TextView) findViewById(R.id.activity_forum_tv_gardenArt);
        tv_house = (TextView) findViewById(R.id.activity_forum_tv_house);
        tv_life = (TextView) findViewById(R.id.activity_forum_tv_life);

        lv_posts = (ListView) findViewById(R.id.activity_forum_lv_posts);
        lv_posts.setAdapter(adapter);

        iv_back = (ImageView) findViewById(R.id.activity_forum_iv_back);
        getPostList("new");
    }

    private void getPostList(String kind) {

        ServeConfig config;
        Map<String, Object> map;
        Post t = new Post();
        t.setKind(kind);
        if(kind.equals("new")){
            config = new ServeConfig("PostServlet", "selectAll");
        }else{
            config = new ServeConfig("PostServlet", "getPostByKind");
        }
        map = BeanUtil.BeanToMap(t);
        String str = JSON.toJSONString(map);
        String URL = config.getURL() + StringUtil.strEncode(str);
        //访问后台数据库,得到数据
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
                    String postInfo = resultMap.get("result").toString();
                    postList = FastJsonTools.getBeans(postInfo, Post.class);
                    adapter.setData(postList);
                } else if (code == 0) {
                    Toast.makeText(getApplicationContext(), "没有更多帖子啦", Toast.LENGTH_SHORT).show();
                }
            }
        };
        taskHelper.downloadData(getApplicationContext(), URL, downloadListener); // 发起服务器的访问
    }

    class MyClickedListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.activity_forum_tv_new:
                    setTextView(tv_new,"new");
                    break;
                case R.id.activity_forum_tv_flowerArt:
                    setTextView(tv_flowerArt,"flowerArt");
                    break;
                case R.id.activity_forum_tv_gardenArt:
                    setTextView(tv_gardenArt,"gardenArt");
                    break;
                case R.id.activity_forum_tv_house:
                    setTextView(tv_house,"house");
                    break;
                case R.id.activity_forum_tv_life:
                    setTextView(tv_life,"life");
                    break;
                case R.id.activity_forum_iv_back:
                    onBackPressed();
                    break;
            }
        }
        private void setTextView(TextView view,String kind){
            if (tv_temp != view) {
                view.setTextColor(getResources().getColor(R.color.colorTextSelected));
                tv_temp.setTextColor(getResources().getColor(R.color.colorText));
                tv_temp = view;
                getPostList(kind);
                adapter.notifyDataSetChanged();
            }
        }
    }

    class MyPostClickedListener implements PostsListAdapter.PostClickListener {
        //TODO 完成帖子添加收藏
        @Override
        public void addCollection(Post post) {
            Toast.makeText(getApplicationContext(), post.getContent() + "*addCollection", Toast.LENGTH_SHORT).show();
        }
        //TODO 完成帖子添加赞
        @Override
        public void addPraise(Post post) {
            Toast.makeText(getApplicationContext(), post.getContent() + "*addPraise", Toast.LENGTH_SHORT).show();
        }
        //TODO 完成帖子添加评论
        @Override
        public void addComment(Post post) {
            Toast.makeText(getApplicationContext(), post.getContent() + "*addComment", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),CommentActivity.class);
            intent.putExtra("postID",post.getId());
            intent.putExtra("postUserID",post.getUserId());
            intent.putExtra("postContent",post.getContent());
            intent.putExtra("postTime",post.getTime());
            intent.putExtra("postPictureUrls",post.getPictureUrls());
            startActivity(intent);
        }

        @Override
        public void lookPost(Post post) {
            Toast.makeText(getApplicationContext(), post.getContent() + "*lookPost", Toast.LENGTH_SHORT).show();
        }
    }
}
