package com.cc.flowerart.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cc.flowerart.R;
import com.cc.flowerart.bean.Post;
import com.cc.flowerart.config.CacheConfig;
import com.cc.flowerart.config.QiNiuConfig;
import com.cc.flowerart.config.ServeConfig;
import com.cc.flowerart.utils.AsyncTaskHelper;
import com.cc.flowerart.utils.BeanUtil;
import com.cc.flowerart.utils.DateUtil;
import com.cc.flowerart.utils.FastJsonTools;
import com.cc.flowerart.utils.StringUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


/**
 * 发帖
 */
public class PublishActivity extends AppCompatActivity {
    private String path;
    private String kind;//帖子分类
    private static final int REQUEST_PICK = 0;
    private ImageView iv_back;
    private TextView tv_publish, tv_flowerArt, tv_gardenArt, tv_house, tv_life, tv_temp;
    private GridView gv_addImag;
    private EditText et_postContent;
    private GridAdapter gridAdapter;
    //存放所有选择的照片
    private ArrayList<String> allSelectedPicture = new ArrayList<String>();

    //存放从选择界面选择的照片
    private ArrayList<String> selectedPicture = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        //设置在activity启动的时候输入法默认是不开启的
        // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)//设置当前线程的优先级
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//使用MD5对UIL进行加密命名
                .diskCacheSize(100 * 1024 * 1024)//50 Mb sd卡(本地)缓存的最大值
                .diskCacheFileCount(300)// 可以缓存的文件数量
                .tasksProcessingOrder(QueueProcessingType.LIFO)//后进先出
                .build();

        //初始化操作
        ImageLoader.getInstance().init(config);
        initView();
        listener();
    }

    private void listener() {
        tv_publish.setOnClickListener(new MyClickedListener());
        tv_flowerArt.setOnClickListener(new MyClickedListener());
        tv_gardenArt.setOnClickListener(new MyClickedListener());
        tv_house.setOnClickListener(new MyClickedListener());
        tv_life.setOnClickListener(new MyClickedListener());
        iv_back.setOnClickListener(new MyClickedListener());
    }

    /**
     * 初始化视图
     */
    private void initView() {
        tv_publish = (TextView) findViewById(R.id.publish_tv_publish);
        tv_flowerArt = (TextView) findViewById(R.id.activity_publish_tv_flowerArt);
        tv_gardenArt = (TextView) findViewById(R.id.activity_publish_tv_gardenArt);
        tv_house = (TextView) findViewById(R.id.activity_publish_tv_house);
        tv_life = (TextView) findViewById(R.id.activity_publish_tv_life);

        iv_back = (ImageView) findViewById(R.id.publish_iv_back);
        et_postContent = (EditText) findViewById(R.id.publish_et_postContent);
        //显示图片
        gv_addImag = (GridView) findViewById(R.id.activity_publish_gv_addImage);
        gridAdapter = new GridAdapter();
        gv_addImag.setAdapter(gridAdapter);
    }

    private void setTextView(TextView view) {
        if (tv_temp == null) {
            view.setTextColor(getResources().getColor(R.color.colorTextSelected));
            tv_temp = view;
        } else if (tv_temp != view) {
            view.setTextColor(getResources().getColor(R.color.colorTextSelected));
            tv_temp.setTextColor(getResources().getColor(R.color.colorText));
            tv_temp = view;
        }
    }

    /**
     * 展示图片的GridView的适配器
     */
    class GridAdapter extends BaseAdapter {

        public LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());

        @Override
        public int getCount() {
            return allSelectedPicture.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.childgrid_item, null);

                holder.image = (ImageView) convertView.findViewById(R.id.child_iv);
                holder.btn = (ImageView) convertView.findViewById(R.id.child_delete);

                holder.image.setScaleType(ImageView.ScaleType.CENTER_CROP);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == allSelectedPicture.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getApplicationContext().getResources(), R.mipmap.icon_addpic));
                holder.btn.setVisibility(View.GONE);

                holder.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectClick();
                    }
                });
                if (position == 9) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                ImageLoader.getInstance().displayImage("file://" + allSelectedPicture.get(position),
                        holder.image);

                holder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击后移除图片
                        allSelectedPicture.remove(position);
                        //更新UI
                        gv_addImag.setAdapter(gridAdapter);
                    }
                });

            }
            return convertView;
        }
    }

    class MyClickedListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.publish_iv_back:
                    startActivity(new Intent(getApplicationContext(), PersonalPageActivity.class));
                    finish();
                    break;
                case R.id.activity_publish_tv_flowerArt:
                    setTextView(tv_flowerArt);
                    kind = "flowerArt";
                    break;
                case R.id.activity_publish_tv_gardenArt:
                    setTextView(tv_gardenArt);
                    kind = "gardenArt";
                    break;
                case R.id.activity_publish_tv_house:
                    setTextView(tv_house);
                    kind = "house";
                    break;
                case R.id.activity_publish_tv_life:
                    setTextView(tv_life);
                    kind = "life";
                    break;
                case R.id.publish_tv_publish:
                    publish();
                    break;
            }
        }
    }

    class ViewHolder {
        public ImageView image;
        public ImageView btn;
    }

    private void selectClick() {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), SelectPictureActivity.class);

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("allSelectedPicture", allSelectedPicture);
        intent.putExtras(bundle);

        if (allSelectedPicture.size() < 9) {
            startActivityForResult(intent, REQUEST_PICK);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            selectedPicture = (ArrayList) data.getSerializableExtra(SelectPictureActivity.INTENT_SELECTED_PICTURE);
            for (String str : selectedPicture) {
                if (!allSelectedPicture.contains(str)) {
                    allSelectedPicture.add(str);
                    gv_addImag.setAdapter(gridAdapter);
                }
            }
        }
    }

    /**
     * 发帖操作
     */
    public void publish() {
        String content = et_postContent.getText().toString();
        if (content == null) {
            Toast.makeText(getApplicationContext(), "发帖内容不能为空", Toast.LENGTH_SHORT).show();
        } else if (kind == null) {
            Toast.makeText(getApplicationContext(), "请选择帖子分类", Toast.LENGTH_SHORT).show();
        }
        String postId = StringUtil.getUniqueId(10);
        StringBuffer sb = new StringBuffer();
        if (!allSelectedPicture.isEmpty()) {
            path = allSelectedPicture.toString();
            path = path.replace("[", "");
            path = path.replace("]", "");
            path = path.replace(" ", "");
            String[] paths = path.split(",");
            //选中图片上传七牛
            for (int i = 0; i < paths.length; i++) {
                String key = CacheConfig.user.getId() + "/" + postId + "/" + (new Date().getTime()) + ".png";
                sb = sb.append("http://ot4o92kcv.bkt.clouddn.com/");
                sb = sb.append(key + ",");
                QiNiuConfig.uploadImage(key, QiNiuConfig.QINIU_TOKEN, paths[i]);
            }
            String urls = sb.toString();
            urls = urls.substring(0, urls.length() - 1);
            Post post = new Post();
            post.setId(postId);
            post.setKind(kind);
            post.setContent(content);
            post.setPictureUrls(urls);
            post.setTime(DateUtil.getFormatDate("yyyy-MM-dd HH:mm", new Date()));
            post.setUserId(CacheConfig.user.getId());
            ServeConfig config = new ServeConfig("PostServlet", "add");
            //获取url
            Map<String, Object> map;
            map = BeanUtil.BeanToMap(post);
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
                        Toast.makeText(getApplicationContext(), "发布成功", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else if (code == 0) {
                        Toast.makeText(getApplicationContext(), "发布失败", Toast.LENGTH_SHORT).show();
                    }
                }
            };
            taskHelper.downloadData(getApplicationContext(), URL, downloadListener); // 发起服务器的访问
        }
        //TODO 将keys和帖子内容上传数据库

    }
}
