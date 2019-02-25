package com.example.zhan.heathmanage.Main.FindFragment.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.anlia.photofactory.factory.PhotoFactory;
import com.anlia.photofactory.permission.PermissionAlwaysDenied;
import com.anlia.photofactory.result.ResultData;
import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Service.DynamicDao;
import com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp.DynamicDaoImp;
import com.example.zhan.heathmanage.Main.Menu.UserActivity;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

import static com.anlia.photofactory.factory.PhotoFactory.ERROR_CROP_DATA;

public class PublishPostingActivity extends BaseActivity {
    @BindView(R.id.publishposting_bt)
    Button publishposting_bt;
    @BindView(R.id.publish_image_iv)ImageView publish_image_iv;
    @BindView(R.id.publishcontent_et)EditText publishcontent_et;
    @BindView(R.id.publishlocation_iv)ImageView publishlocation_iv;
    @BindView(R.id.publishaddress_tv)TextView publishaddress_tv;
    private static Bitmap bm;
    public static String img;
    private static String UserImage;
    private static Bitmap image;
    int flag=0;
    private PhotoFactory photoFactory;
    private static String picName;
    public static String textData="不显示位置";
    DynamicDao dynamicDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_posting);
        picName = Calendar.getInstance().getTimeInMillis() + ".png";
        dynamicDao=new DynamicDaoImp();
    }
    @OnClick(R.id.publishposting_back_ib)
    public void publishposting_back_ib_Onclick(){
        finish();
    }
    @OnClick(R.id.publishposting_bt)
    public void publishposting_bt(){

    }
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){

            if(resultCode==0){
                textData = data.getStringExtra("address");
                flag = data.getIntExtra("position", -1);
                if (textData.equals("不显示位置")) {
                    publishaddress_tv.setText("所在位置");
                    publishaddress_tv.setTextColor(R.color.black);
                    publishlocation_iv.setImageResource(R.drawable.location);
                } else {
                    publishaddress_tv.setText(textData);
                    publishaddress_tv.setTextColor(R.color.green);
                    publishlocation_iv.setImageResource(R.drawable.location_press);
                }

            }
        }
    }

    @OnClick(R.id.publishloaction_ll)
    public void publishloaction_ll_Onclick(){
        RxPermissions.getInstance(this)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION)//多个权限用","隔开
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            //当所有权限都允许之后，返回true
                            Intent intent=new Intent(PublishPostingActivity.this,LocationListActivity.class);
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
    @OnClick(R.id.publish_image_iv)
    public void publish_image_iv_Onclick(){
        uploadHeadImage();
    }
    private void uploadHeadImage() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_popupwindow, null);
        TextView btnCarema = view.findViewById(R.id.btn_camera);
        TextView btnPhoto = view.findViewById(R.id.btn_photo);
        TextView btnCancel = view.findViewById(R.id.btn_cancel);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        View parent = LayoutInflater.from(this).inflate(R.layout.activity_user, null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });
        PhotoFactory.setPermissionAlwaysDeniedAction(new PermissionAlwaysDenied.Action() {
            @Override
            public void onAction(Context context, List<String> permissions, final PermissionAlwaysDenied.Executor executor) {
                List<String> permissionNames = PhotoFactory.transformPermissionText(context, permissions);
                String permissionText = TextUtils.join("权限\n", permissionNames);

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                builder.setTitle("权限说明");
                builder.setMessage("您禁止了以下权限的动态申请：\n\n" + permissionText + "权限\n\n是否去应用权限管理中手动授权呢？");
                builder.setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        executor.toSetting();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
        btnCarema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
                //跳转到调用系统相机
                photoFactory = new PhotoFactory(PublishPostingActivity.this, Environment.getExternalStorageDirectory() + "/" + "DCIM", picName);
                photoFactory.FromCamera()
                        .AddOutPutExtra()
                        .StartForResult(new PhotoFactory.OnResultListener() {
                            @Override
                            public void onCancel() {
                                Log.e("zjc", "取消从相册选择");
                            }

                            @Override
                            public void onSuccess(ResultData resultData) {
                                dealSelectPhoto(resultData);
                            }

                            @Override
                            public void onError(String error) {
                                Log.v("aaaa", error);
                            }
                        });

                popupWindow.dismiss();
            }

        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
                photoFactory = new PhotoFactory(PublishPostingActivity.this, Environment.getExternalStorageDirectory() + "/" + "DCIM", picName);
                photoFactory.FromGallery().StartForResult(new PhotoFactory.OnResultListener() {
                    @Override
                    public void onCancel() {
                        Log.e("zjc", "取消从相册选择");
                    }

                    @Override
                    public void onSuccess(ResultData resultData) {
                        dealSelectPhoto(resultData);
//                            Uri uri = resultData.GetUri();
//                            imgPhoto.setImageURI(uri);
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
                popupWindow.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private void dealSelectPhoto(ResultData resultData) {
        Uri uri = resultData
                .setExceptionListener(new ResultData.OnExceptionListener() {
                    @Override
                    public void onCatch(String error, Exception e) {
                        Toast.makeText(PublishPostingActivity.this, error, Toast.LENGTH_SHORT).show();
                        return;
                    }
                })
                .GetUri();
        photoFactory.FromCrop(uri)
                .AddAspectX(1)
                .AddAspectY(1)
                .StartForResult(new PhotoFactory.OnResultListener() {
                    @Override
                    public void onCancel() {
                        Log.e("zjc", "取消裁剪");
                    }

                    @Override
                    public void onSuccess(ResultData data) {
                        bm = data.GetBitmap();
                        UserImage = convertIconToString(bm);
                        dealCropPhoto(data.addScaleCompress(164, 164).GetBitmap());
                        image = data.addScaleCompress(164, 164).GetBitmap();
                        //updateUseServer.UpdateImage(MyApplication.getUserPhone(), UserImage);
                    }

                    @Override
                    public void onError(String error) {
                        switch (error) {
                            case ERROR_CROP_DATA:
                                Toast.makeText(PublishPostingActivity.this, "data为空", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
    }

    private void dealCropPhoto(Bitmap bitmap) {
        publish_image_iv.setImageBitmap(bitmap);
    }

    public String convertIconToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        int options = 100;
        while (baos.toByteArray().length / 1024 > 500) { //循环判断如果压缩后图片是否大于1000kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
            Log.i("Compress", baos.toByteArray().length + "");
        }
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

}
