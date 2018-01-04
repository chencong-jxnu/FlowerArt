package com.cc.flowerart.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.flowerart.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SelectPictureActivity extends Activity {

    // 最多选择图片的个数
    private static int MAX_NUM = 9;
    private static final int TAKE_PICTURE = 520;

    public static final String INTENT_MAX_NUM = "intent_max_num";
    public static final String INTENT_SELECTED_PICTURE = "intent_selected_picture";

    private Context context;
    private GridView gridview;
    private PictureAdapter adapter;

    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashMap<String, Integer> tmpDir = new HashMap<String, Integer>();
    private ArrayList<ImageFloder> mDirPaths = new ArrayList<ImageFloder>();

    /**
     * 具体下载图片，缓存图片，显示图片的具体执行类，它有两个具体的方法displayImage(...)、loadImage(...)，
     */
    private ImageLoader loader;

    /**
     * 用于指导每一个Imageloader根据网络图片的状态（空白、下载错误、正在下载）显示对应的图片，
     * 是否将缓存加载到磁盘上，下载完后对图片进行怎么样的处理。
     */
    private DisplayImageOptions options;

    private ContentResolver mContentResolver;

    //选择按钮和完成按钮
    private Button btn_select, btn_ok;

    //图片文件的列表
    private ListView listview;

    private FolderAdapter folderAdapter;

    private ImageFloder imageAll, currentImageFolder;


    //已选择的图片
    private ArrayList<String> selectedPicture = new ArrayList<String>();

    private String cameraPath = null;

    private int isExisted ;

    private ArrayList<String> existedPictureList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);











        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_select_picture);

        MAX_NUM = getIntent().getIntExtra(INTENT_MAX_NUM, 9);

        Bundle bundle = getIntent().getExtras();
        existedPictureList = bundle.getStringArrayList("allSelectedPicture");
        isExisted = existedPictureList.size();

        context = this;
        mContentResolver = getContentResolver();
        loader = ImageLoader.getInstance();

        options = new DisplayImageOptions.Builder()
                // TODO 设置图片在下载期间显示的图片
                .showImageOnLoading(R.mipmap.logo)//设置图片在下载期间显示的图片

                .showImageForEmptyUri(R.mipmap.logo)//设置图片Uri为空或是错误的时候显示的图片

                .showImageOnFail(R.mipmap.logo)//设置图片加载/解码过程中错误时候显示的图片

                .cacheInMemory(true)//设置下载的图片是否缓存在内存中

                .cacheOnDisk(true) //设置下载的图片是否缓存在SD卡中

                .considerExifParams(true)//启用EXIF和JPEG图像格式

                .imageScaleType(ImageScaleType.EXACTLY)//图像将完全按比例缩小的目标大小

                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型

                .build();

        initView();
    }

    /**
     * 点击"所有图片"按钮事件
     * @param v
     */
    public void select(View v) {
        if (listview.getVisibility() == View.VISIBLE) {
            hideListAnimation();
        } else {
            listview.setVisibility(View.VISIBLE);
            showListAnimation();
            folderAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置ListView的移动的动画效果
     */
    public void showListAnimation() {
        TranslateAnimation ta = new TranslateAnimation(1, 0f, 1, 0f, 1, 1f, 1, 0f);
        ta.setDuration(200);
        listview.startAnimation(ta);
    }

    /**
     * 隐藏ListView的移动的动画效果
     */
    public void hideListAnimation() {
        TranslateAnimation ta = new TranslateAnimation(1, 0f, 1, 0f, 1, 0f, 1, 1f);
        ta.setDuration(200);
        listview.startAnimation(ta);
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                listview.setVisibility(View.INVISIBLE);
            }
        });
    }

    /**
     * 点击完成按钮
     */
    public void ok(View v) {
        Intent data = new Intent();
        data.putExtra(INTENT_SELECTED_PICTURE, selectedPicture);
        setResult(RESULT_OK, data);
        this.finish();
    }

    /**
     * 视图初始化
     */
    private void initView() {
        imageAll = new ImageFloder();
        imageAll.setDir(getResources().getString(R.string.dir_all_pictures));
        currentImageFolder = imageAll;
        mDirPaths.add(imageAll);

        btn_ok = (Button) findViewById(R.id.select_picture_btn_ok);
        btn_ok.setText(getResources().getString(R.string.complete) + isExisted + "/" + MAX_NUM);

        btn_select = (Button) findViewById(R.id.select_picture_btn_select);

        gridview = (GridView) findViewById(R.id.select_picture_gridview);
        adapter = new PictureAdapter();
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    goCamare();
                }
            }
        });

        listview = (ListView) findViewById(R.id.select_picture_listview);
        folderAdapter = new FolderAdapter();
        listview.setAdapter(folderAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentImageFolder = mDirPaths.get(position);
                hideListAnimation();
                adapter.notifyDataSetChanged();
                btn_select.setText(currentImageFolder.getName());
            }
        });
        getThumbnail();
    }

    /**
     * 使用相机拍照
     */
    protected void goCamare() {
        if (isExisted + selectedPicture.size() + 1 > MAX_NUM) {
            Toast.makeText(context, getResources().getString(R.string.chose_at_most) + MAX_NUM + getResources().getString(R.string.sheet), Toast.LENGTH_SHORT).show();
            return;
        }

        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri = getOutputMediaFileUri();
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    /**
     * 用于拍照时获取输出的Uri
     */
    protected Uri getOutputMediaFileUri() {
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Night");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        cameraPath = mediaFile.getAbsolutePath();
        return Uri.fromFile(mediaFile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && cameraPath != null) {

            selectedPicture.add(cameraPath);

            Intent data2 = new Intent();
            data2.putExtra(INTENT_SELECTED_PICTURE, selectedPicture);

            setResult(RESULT_OK, data2);
            this.finish();
        }
    }

    public void back(View v) {
        onBackPressed();
    }

    class PictureAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return currentImageFolder.images.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //自定义的一个类用来缓存convertview
            ViewHolder holder = null;

            if (convertView == null) {
                convertView = View.inflate(context, R.layout.grid_item_picture, null);
                holder = new ViewHolder();
                holder.iv = (ImageView) convertView.findViewById(R.id.iv);
                holder.checkBox = (Button) convertView.findViewById(R.id.check);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == 0) {//拍摄图片
                holder.iv.setImageResource(R.mipmap.pickphotos_to_camera_normal);
                holder.checkBox.setVisibility(View.INVISIBLE);
            } else {
                position = position - 1;
                holder.checkBox.setVisibility(View.VISIBLE);

                final ImageItem item = currentImageFolder.images.get(position);

                //显示图片
                loader.displayImage("file://" + item.path, holder.iv, options);

                //是否选中
                boolean isSelected = (selectedPicture.contains(item.path) || existedPictureList.contains(item.path));
                holder.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //若选中的图片多于所设置的上限，不再加入
                        if (!v.isSelected() &&  isExisted + selectedPicture.size() + 1 > MAX_NUM) {
                            Toast.makeText(context, getResources().getString(R.string.chose_at_most) + MAX_NUM + getResources().getString(R.string.sheet), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        //二次选择，移除
                        if (selectedPicture.contains(item.path)|| existedPictureList.contains(item.path)) {
                            selectedPicture.remove(item.path);
                        } else {

                            //加入数组
                            selectedPicture.add(item.path);
                        }

                        btn_ok.setEnabled(selectedPicture.size()>0);
                        btn_ok.setText(getResources().getString(R.string.complete) + (selectedPicture.size()+ isExisted) + "/" + MAX_NUM);

                        v.setSelected(selectedPicture.contains(item.path)|| existedPictureList.contains(item.path));
                    }
                });

                holder.checkBox.setSelected(isSelected);
            }

            return convertView;
        }
    }

    //自定义的一个类用来缓存convertview
    class ViewHolder {
        ImageView iv;
        Button checkBox;
    }

    /**
     * 所有图片
     */
    class FolderAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mDirPaths.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FolderViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.list_dir_item, null);
                holder = new FolderViewHolder();
                holder.id_dir_item_image = (ImageView) convertView.findViewById(R.id.id_dir_item_image);
                holder.id_dir_item_name = (TextView) convertView.findViewById(R.id.id_dir_item_name);
                holder.id_dir_item_count = (TextView) convertView.findViewById(R.id.id_dir_item_count);
                holder.choose = (ImageView) convertView.findViewById(R.id.choose);
                convertView.setTag(holder);
            } else {
                holder = (FolderViewHolder) convertView.getTag();
            }
            ImageFloder item = mDirPaths.get(position);
            loader.displayImage("file://" + item.getFirstImagePath(), holder.id_dir_item_image, options);
            holder.id_dir_item_count.setText(item.images.size() + getResources().getString(R.string.sheet));
            holder.id_dir_item_name.setText(item.getName());

            holder.choose.setVisibility(currentImageFolder == item ? View.VISIBLE : View.GONE);
            return convertView;
        }
    }

    //自定义的一个类用来缓存convertview
    class FolderViewHolder {
        ImageView id_dir_item_image;
        ImageView choose;
        TextView id_dir_item_name;
        TextView id_dir_item_count;
    }

    /**
     * 获取缩略图
     */
    private void getThumbnail() {
        Cursor mCursor = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.ImageColumns.DATA }, "", null,
                MediaStore.MediaColumns.DATE_ADDED + " DESC");

        if (mCursor.moveToFirst()) {
            int _date = mCursor.getColumnIndex(MediaStore.Images.Media.DATA);
            do {

                // 获取图片的路径
                String path = mCursor.getString(_date);
                imageAll.images.add(new ImageItem(path));

                // 获取该图片的父路径名
                File parentFile = new File(path).getParentFile();
                if (parentFile == null) {
                    continue;
                }

                ImageFloder imageFloder = null;
                String dirPath = parentFile.getAbsolutePath();

                if (!tmpDir.containsKey(dirPath)) {
                    // 初始化imageFloder
                    imageFloder = new ImageFloder();
                    imageFloder.setDir(dirPath);
                    imageFloder.setFirstImagePath(path);
                    mDirPaths.add(imageFloder);
                    tmpDir.put(dirPath, mDirPaths.indexOf(imageFloder));
                } else {
                    imageFloder = mDirPaths.get(tmpDir.get(dirPath));
                }

                imageFloder.images.add(new ImageItem(path));
            } while (mCursor.moveToNext());
        }

        mCursor.close();

        tmpDir = null;
    }

    class ImageFloder {
        /**
         * 图片的文件夹路径
         */
        private String dir;

        /**
         * 第一张图片的路径
         */
        private String firstImagePath;
        /**
         * 文件夹的名称
         */
        private String name;

        public List<ImageItem> images = new ArrayList<ImageItem>();

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
            int lastIndexOf = this.dir.lastIndexOf("/");
            this.name = this.dir.substring(lastIndexOf);
        }

        public String getFirstImagePath() {
            return firstImagePath;
        }

        public void setFirstImagePath(String firstImagePath) {
            this.firstImagePath = firstImagePath;
        }

        public String getName() {
            return name;
        }

    }

    class ImageItem {
        String path;

        public ImageItem(String p) {
            this.path = p;
        }
    }

}
