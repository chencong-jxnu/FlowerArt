package com.cc.flowerart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.flowerart.R;
import com.cc.widget.circleimageview.CircleImageView;

/**
 * 登陆后主界面的fragment
 */
public class GuideFragment extends Fragment {

    private int bgRes; //每个fragment的图片资源id
    private String content;//每个fragment的String内容
    private CircleImageView imageView; //自定义的圆形ImageView控件
    private TextView tv_content;//显示内容的TextView
    private RelativeLayout relativeLayout;//fragment背后的RelativeLayout用于设置监听事件

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bgRes = getArguments().getInt("data");//获取HomeActivity传过来的图片资源
        content = getArguments().getString("content");//获取HomeActivity传过来的String内容资源
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragement_guide, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = (CircleImageView) getView().findViewById(R.id.image);
        tv_content = (TextView) getView().findViewById(R.id.tv_content);
        relativeLayout = (RelativeLayout) getView().findViewById(R.id.fragment_guide);
        tv_content.setText(content);//设置内容
        imageView.setImageResource(bgRes);//设置图片

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 跳转到相应模块
                switch(content){
                    case "个人中心":
                        startActivity(new Intent(getActivity(),PersonalPageActivity.class));
                        break;
                    case "植物百科":
                        Intent intent1 = new Intent(getActivity(),DailyPublishActivity.class);
                        intent1.putExtra("kind","PlantEncyclopedia");
                        startActivity(intent1);
                        break;
                    case "讨论中心":
                        startActivity(new Intent(getContext(),ForumActivity.class));
                        break;
                    case "园艺学堂":
                        Intent intent2 = new Intent(getActivity(),DailyPublishActivity.class);
                        intent2.putExtra("kind","GardenArt");
                        startActivity(intent2);
                        break;
                    case "花卉商城":
                        Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });
    }
}
