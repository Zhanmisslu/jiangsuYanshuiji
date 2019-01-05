
package com.example.zhan.heathmanage.Main.Menu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.anlia.photofactory.factory.PhotoFactory;
import com.anlia.photofactory.permission.PermissionAlwaysDenied;
import com.anlia.photofactory.result.ResultData;
import com.bumptech.glide.Glide;
import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Server.server.UpdateUseServer;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Server.serverImp.UpdateUserServerImp;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.anlia.photofactory.factory.PhotoFactory.ERROR_CROP_DATA;
import static com.example.zhan.heathmanage.MyApplication.getContext;

public class UserActivity extends BaseActivity {
    private Button sex_man,sex_woman,sex_back;
    @BindView(R.id.menu_user_sex)
    TextView menu_user_sex;
    @BindView(R.id.menu_user_sex_img)
    ImageView menu_user_sex_img;
    @BindView(R.id.menu_user_age)
    EditText menu_user_age;
    @BindView(R.id.menu_user_weight)
    EditText  menu_user_weight;
    @BindView(R.id.menu_user_high)
    EditText  menu_user_high;
    @BindView(R.id.menu_user_nick)
    TextView  menu_user_nick;
    @BindView(R.id.menu_user_head_img)
    RoundedImageView menu_user_head_img;
    UpdateUseServer updateUseServer ;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private PhotoFactory photoFactory;
    private final String TAG = "UserActivity";
    private String picName;
    private static Bitmap bm;
    public static String img;
    private static String UserImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        updateUseServer = new UpdateUserServerImp(1,this);
        photoFactory=new PhotoFactory(this);
        updateUseServer.GetUserMessage();
        picName = Calendar.getInstance().getTimeInMillis() + ".png";
        preferences = getSharedPreferences("UserList", MODE_PRIVATE);

        editor = preferences.edit();
        img=preferences.getString("UserPhoto","");
        setMessage();
    }
    //设置个人信息
    public void setMessage(){
        if (!MyApplication.getUserNickName().equals("null")){
            menu_user_nick.setText(MyApplication.getUserNickName());
        }
        if (!MyApplication.getUserWeight().equals("null")){
            menu_user_weight.setText(MyApplication.getUserWeight());
        }

        if (!MyApplication.getUserHigh().equals("null")){
            menu_user_high.setText(MyApplication.getUserHigh());
        }
        if (!MyApplication.getUserAge().equals("null")){
            menu_user_age.setText(MyApplication.getUserAge());
        }
        if (MyApplication.getUserSex().equals("男")){
            menu_user_sex_img.setImageResource(R.drawable.man_click);
            menu_user_sex.setText(MyApplication.getUserSex());
        }else if (MyApplication.getUserSex().equals("女")){
            menu_user_sex_img.setImageResource(R.drawable.woman_click);
            menu_user_sex.setText(MyApplication.getUserSex());
        }
    }

    //修改头像
    @OnClick(R.id.menu_user_head_img)
    public void menu_user_head_img_OnClick(){
        uploadHeadImage();
    }
    @OnClick(R.id.menu_user_nick)
    public void menu_user_nick_OnClick(){
        Intent intent = new Intent(this,UpdateNameActivity.class);
        intent.putExtra("nick",menu_user_nick.getText().toString());
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode ==1){
            String nick = data.getStringExtra("Nick");
            menu_user_nick.setText(nick);
        }
    }

    //修改性别
    @OnClick(R.id.menu_user_sex)
    public void menu_user_sex_OnClick(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);//创建对话框
        View view = getLayoutInflater().from(this).inflate(R.layout.user_sex,null);
        builder.setView(view);
        final AlertDialog   dialog=builder.create();
        setdialog(dialog);

        sex_man = view.findViewById(R.id.sex_man);
        sex_woman = view.findViewById(R.id.sex_woman);
        sex_back = view.findViewById(R.id.sex_back);
        sex_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu_user_sex.setText("男");
                menu_user_sex_img.setImageResource(R.drawable.man_click);
                dialog.dismiss();
        }
        });
        sex_woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu_user_sex.setText("女");
                menu_user_sex_img.setImageResource(R.drawable.woman_click);
                dialog.dismiss();
            }
        });
        sex_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    //绘画底部弹窗
    public void setdialog(AlertDialog dialog){
        //创建dialog
        dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//设置dialog背景为透明
        Window window =dialog.getWindow();
        window.setGravity(Gravity.BOTTOM); //设置弹窗在底部
        window.setWindowAnimations(R.style.AnimBottom);//动画
        WindowManager m =getWindowManager();
        Display d =m.getDefaultDisplay();//为获取屏幕宽、高
        WindowManager.LayoutParams p =dialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width =d.getWidth();//宽度设置为屏幕
        dialog.getWindow().setAttributes(p); //设置生效
    }
    //个人信息修改按钮
    @OnClick(R.id.menu_user_finish)
    public void menu_user_finish_OnClick(){
        updateUseServer.UpdateUser(menu_user_high.getText().toString(),menu_user_weight.getText().toString()
                ,menu_user_sex.getText().toString(),menu_user_age.getText().toString());
        updateUseServer.UpdateImage(MyApplication.getUserPhone(),UserImage);

    }

    //Server的回调函数
    public void callback(){
        MyApplication.setUserWeight(menu_user_weight.getText().toString());
        MyApplication.setUserAge(menu_user_age.getText().toString());
        MyApplication.setUserHigh(menu_user_high.getText().toString());
        MyApplication.setUserNickName(menu_user_nick.getText().toString());
        MyApplication.setUserSex(menu_user_sex.getText().toString());
        editor.putString("Login_Weight",menu_user_weight.getText().toString());
        editor.putString("Login_Age",menu_user_age.getText().toString());
        editor.putString("Login_Height",menu_user_high.getText().toString());
        editor.putString("Login_NickName",menu_user_nick.getText().toString());
        editor.putString("Login_Sex",menu_user_sex.getText().toString());
        editor.commit();
        finish();
        Looper.prepare();
        Toast.makeText(this,"修改信息成功",Toast.LENGTH_SHORT).show();
        Looper.loop();
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
                photoFactory = new PhotoFactory(UserActivity.this, Environment.getExternalStorageDirectory() + "/" + "DCIM", picName);
                photoFactory.FromCamera()
                        .AddOutPutExtra()
                        .StartForResult(new PhotoFactory.OnResultListener() {
                            @Override
                            public void onCancel() {
                                Log.e(TAG, "取消从相册选择");
                            }

                            @Override
                            public void onSuccess(ResultData resultData) {
                                dealSelectPhoto(resultData);
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });

                popupWindow.dismiss();
            }

        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
                photoFactory = new PhotoFactory(UserActivity.this, Environment.getExternalStorageDirectory() + "/" + "DCIM", picName);
                photoFactory.FromGallery()
                        .StartForResult(new PhotoFactory.OnResultListener() {
                            @Override
                            public void onCancel() {
                                Log.e(TAG, "取消从相册选择");
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
                        Toast.makeText(UserActivity.this, error, Toast.LENGTH_SHORT).show();
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
                        Log.e(TAG, "取消裁剪");
                    }

                    @Override
                    public void onSuccess(ResultData data) {
                        bm=data.GetBitmap();
                        UserImage=convertIconToString(bm);
                        dealCropPhoto(data.addScaleCompress(164, 164).GetBitmap());
                    }

                    @Override
                    public void onError(String error) {
                        switch (error) {
                            case ERROR_CROP_DATA:
                                Toast.makeText(UserActivity.this, "data为空", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
    }
    private void dealCropPhoto(Bitmap bitmap) {
        menu_user_head_img.setImageBitmap(bitmap);
    }
    public  String convertIconToString(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);  int options = 100;
        while ( baos.toByteArray().length / 1024>500) { //循环判断如果压缩后图片是否大于1000kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
            Log.i("Compress",baos.toByteArray().length+"");
        }
        byte[] bytes = baos.toByteArray();return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
    public void getUserMessageCallBack(final String NickName, final String Height, final String Weight, final String Age, final String Sex, final String Image){
        this.img=Image;
        Handler handler = new Handler(getMainLooper());
        handler.post(new Runnable() {
            @Override public void run() {
                menu_user_nick.setText(NickName);
                menu_user_high.setText(Height);
                menu_user_weight.setText(Weight);
                menu_user_age.setText(Age);
                if (Sex.equals("男")) {
                    menu_user_sex_img.setImageResource(R.drawable.man_click);
                    menu_user_sex.setText(MyApplication.getUserSex());
                } else if (Sex.equals("女")) {
                    menu_user_sex_img.setImageResource(R.drawable.woman_click);
                    menu_user_sex.setText(MyApplication.getUserSex());
                }
                Glide.with(getContext())
                        .load(Image)
                        .asBitmap()
                        .into(menu_user_head_img);

                editor.putString("UserPhoto",Image);
                MyApplication.setUserPhoto(preferences.getString("UserPhoto",""));
                editor.commit();
            }
        });

    }
}
