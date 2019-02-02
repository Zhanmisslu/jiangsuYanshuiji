package com.example.zhan.heathmanage.Main.FindFragment.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Adapter.NinePicturesAdapter;
import com.example.zhan.heathmanage.Main.FindFragment.View.NoScrollGridView;
import com.example.zhan.heathmanage.Main.FindFragment.util.ImageLoaderUtils;
import com.example.zhan.heathmanage.R;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

public class DynamicActivity extends BaseActivity {
    @BindView(R.id.dynamic_back_ib)
    ImageButton dynamic_back_ib;
    @BindView(R.id.content_et)EditText content_et;
    @BindView(R.id.publish_bt)Button publish_bt;
    @BindView(R.id.gridview)NoScrollGridView gridview;
    @BindView(R.id.location_iv)ImageView location_iv;
    @BindView(R.id.address_tv)TextView address_tv;
    private NinePicturesAdapter ninePicturesAdapter;
    private int REQUEST_CODE = 120;
    int flag=0;
    public static String textData="不显示位置";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);
        initView();
    }
    private void initView() {


        ninePicturesAdapter = new NinePicturesAdapter(this, 9, new NinePicturesAdapter.OnClickAddListener() {
            @Override
            public void onClickAdd(int positin) {
                choosePhoto();
            }
        });
        gridview.setAdapter(ninePicturesAdapter);

        publish_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(content_et.getText().toString())) {
                    //将发表内容提交后台
                    ToastUtils.showShort("发表成功");
                    finish();
                } else {
                    ToastUtils.showShort("发表内容不能为空！");
                }
            }
        });
    }
    /**
     * 开启图片选择器
     */
    private void choosePhoto() {
        ImgSelConfig config = new ImgSelConfig.Builder(loader)
                // 是否多选
                .multiSelect(true)
                // 确定按钮背景色
                .btnBgColor(Color.TRANSPARENT)
                .titleBgColor(ContextCompat.getColor(this, R.color.main_color))
                // 使用沉浸式状态栏
                .statusBarColor(ContextCompat.getColor(this, R.color.main_color))
                // 返回图标ResId
                .backResId(R.drawable.left_back)
                .title("图片")
                // 第一个是否显示相机
                .needCamera(true)
                // 最大选择图片数量
                .maxNum(9 - ninePicturesAdapter.getPhotoCount())
                .build();
        ImgSelActivity.startActivity(this, config, REQUEST_CODE);
    }
    private ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            ImageLoaderUtils.display(context, imageView, path);
        }
    };
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            if (ninePicturesAdapter != null) {
                ninePicturesAdapter.addAll(pathList);
            }
        }
        if(requestCode==100){

            if(resultCode==0){
                    textData = data.getStringExtra("address");
                    flag = data.getIntExtra("position", -1);
                    if (textData.equals("不显示位置")) {
                        address_tv.setText("所在位置");
                        address_tv.setTextColor(R.color.black);
                        location_iv.setImageResource(R.drawable.location);
                    } else {
                        address_tv.setText(textData);
                        address_tv.setTextColor(R.color.green);
                        location_iv.setImageResource(R.drawable.location_press);
                    }

            }
        }

    }

        @OnClick(R.id.dynamic_back_ib)
    public void dynamic_back_ib_Onclick(){
        finish();
    }
    @OnClick(R.id.loaction_ll)
    public void location_ll_Onclick(){
        RxPermissions.getInstance(this)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION)//多个权限用","隔开
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            //当所有权限都允许之后，返回true
                            Intent intent=new Intent(DynamicActivity.this,LocationListActivity.class);
                            intent.putExtra("pos",flag);
                            startActivityForResult(intent,100);
                        } else {
                            //只要有一个权限禁止，返回false，
                            //下一次申请只申请没通过申请的权限
                            finish();
                        }
                    }
                });

    }

}
