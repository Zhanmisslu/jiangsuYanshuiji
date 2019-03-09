package com.example.zhan.heathmanage.Main.FindFragment.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Service.DynamicDao;
import com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp.DynamicDaoImp;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.zyao89.view.zloading.ZLoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

import static com.zyao89.view.zloading.Z_TYPE.DOUBLE_CIRCLE;
import static com.zyao89.view.zloading.Z_TYPE.MUSIC_PATH;

public class PublishTextActivity extends BaseActivity {
    @BindView(R.id.publishpostingtext_bt)Button publishpostingtext_bt;
    @BindView(R.id.publishtextcontent_et)EditText publishtextcontent_et;
    @BindView(R.id.publishtextaddress_tv)TextView publishtextaddress_tv;
    @BindView(R.id.publishtextlocation_iv)ImageView publishtextlocation_iv;
    @BindView(R.id.publishtextloaction_ll)LinearLayout publishtextloaction_ll;
    int flag=0;
    String UserImage="null";
    ZLoadingDialog dialog;
    DynamicDao dynamicDao;
    public static String textData="不显示位置";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_text);
        dialog=new ZLoadingDialog(this);
        dynamicDao=new DynamicDaoImp(this);

    }
    @OnClick(R.id.publishtextposting_back_ib)
    public void publishtextposting_back_ib_Onclick(){
        finish();
    }
    @OnClick(R.id.publishtextloaction_ll)
    public void publishtextloaction_ll_Onclick(){
        RxPermissions.getInstance(this)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION)//多个权限用","隔开
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            //当所有权限都允许之后，返回true
                            Intent intent=new Intent(PublishTextActivity.this,LocationListActivity.class);
                            intent.putExtra("pos",flag);
                            startActivityForResult(intent,200);
                        } else {
                            //只要有一个权限禁止，返回false，
                            //下一次申请只申请没通过申请的权限
                            finish();
                        }
                    }
                });

    }
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200){

            if(resultCode==0){
                textData = data.getStringExtra("address");
                flag = data.getIntExtra("position", -1);
                if (textData.equals("不显示位置")) {
                    publishtextaddress_tv.setText("所在位置");
                    publishtextaddress_tv.setTextColor(R.color.black);
                    publishtextlocation_iv.setImageResource(R.drawable.location);
                } else {
                    publishtextaddress_tv.setText(textData);
                    publishtextaddress_tv.setTextColor(R.color.green);
                    publishtextlocation_iv.setImageResource(R.drawable.location_press);
                }

            }
        }
    }
    public void Init(){
        dialog.setLoadingBuilder(MUSIC_PATH)
                .setLoadingColor(Color.parseColor("#ff5305"))
                .setHintText("正在加载中...")
//                                .setHintTextSize(16) // 设置字体大小
                .setHintTextColor(Color.GRAY)  // 设置字体颜色
//                                .setDurationTime(0.5) // 设置动画时间百分比

                .setDialogBackgroundColor(Color.parseColor("#cc111111")) // 设置背景色
                .show();
    }
    @OnClick(R.id.publishpostingtext_bt)
    public void publishpostingtext_bt_Onclick(){
    Init();
    if(publishtextaddress_tv.getText().toString().equals("所在位置")){
        dynamicDao.PublishPosting(MyApplication.getUserPhone(),UserImage,publishtextcontent_et.getText().toString(),"null");

    }else {
        dynamicDao.PublishPosting(MyApplication.getUserPhone(),UserImage,publishtextcontent_et.getText().toString(),publishtextaddress_tv.getText().toString());

    }
    }

    public void callback() {
        //dialog.dismiss();
        finish();
    }
}
