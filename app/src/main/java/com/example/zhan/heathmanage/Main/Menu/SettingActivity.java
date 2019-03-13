package com.example.zhan.heathmanage.Main.Menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.BasicsTools.CleanCacheUtil;
import com.example.zhan.heathmanage.Login.LoginActivity;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.ViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.zhan.heathmanage.MyApplication.getContext;

public class SettingActivity extends BaseActivity {
    @BindView(R.id.exit_bt)
    Button exit_bt;
    @BindView(R.id.setting_userimage_iv)
    RoundedImageView setting_userimage_iv;
    @BindView(R.id.phone_tv)
    TextView phone_tv;
    @BindView(R.id.setting_back_ib)
    ImageButton setting_back_ib;
    SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private static ImageView notify_iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        preferences = getSharedPreferences("UserList", MODE_PRIVATE);
        editor=preferences.edit();
        Glide.with(getContext())
                .load(MyApplication.getPhoto())
                .asBitmap()
                .error(R.drawable.head)
                .into(setting_userimage_iv);
        phone_tv.setText(MyApplication.getUserPhone());
        notify_iv=findViewById(R.id.notify_iv);
        if(preferences.getBoolean("notify",true)){
            notify_iv.setImageResource(R.drawable.on);
        }else {
            notify_iv.setImageResource(R.drawable.off);
        }

    }
    @OnClick(R.id.setting_back_ib)
    public void setting_back_ib_Onclick(){
        finish();
    }
    @OnClick(R.id.notify_iv)
    public void my_notify_iv_Click(){
        if(preferences.getBoolean("notify",true)){

            ConfirmDialog.newInstance("notify_true")
                    .setMargin(60)
                    .setOutCancel(false)
                    .show(getSupportFragmentManager());
        }else{
            ConfirmDialog.newInstance("notify_false")
                    .setMargin(60)
                    .setOutCancel(false)
                    .show(getSupportFragmentManager());
        }
    }
    @OnClick(R.id.exit_bt)
    public void exit_bt_Onclick(){
        ConfirmDialog.newInstance("exit")
                .setMargin(60)
                .setOutCancel(false)
                .show(getSupportFragmentManager());
    }
    public static class ConfirmDialog extends BaseNiceDialog {
        private String type;

        public static ConfirmDialog newInstance(String type) {
            Bundle bundle = new Bundle();
            bundle.putString("type", type);
            ConfirmDialog dialog = new ConfirmDialog();
            dialog.setArguments(bundle);
            return dialog;
        }
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle bundle = getArguments();
            if (bundle == null) {
                return;
            }
            type = bundle.getString("type");
        }
        @Override
        public int intLayoutId() {
            return  R.layout.confirm_layout;
        }

        @Override
        public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
            if ("exit".equals(type)) {
                holder.setText(R.id.title, "提示");
                holder.setText(R.id.message, "是否退出登陆?");

                holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                holder.setOnClickListener(R.id.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        exit();
                        getActivity().finish();
                    }
                });
            }else if("notify_true".equals(type)){
                holder.setText(R.id.title, "提示");
                holder.setText(R.id.message, "是否关闭消息提醒？");

                holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                holder.setOnClickListener(R.id.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        //JPushInterface.stopPush(MyApplication.getContext());
                        notify_iv.setImageResource(R.drawable.off);
                        editor.putBoolean("notify",false);
                        editor.apply();
                    }
                });
            }else if ("notify_false".equals(type)) {
                holder.setText(R.id.title, "提示");
                holder.setText(R.id.message, "是否开启消息提醒？");

                holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                holder.setOnClickListener(R.id.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        //JPushInterface.resumePush(MyApplication.getContext());
                        notify_iv.setImageResource(R.drawable.on);
                        editor.putBoolean("notify",true);
                        editor.apply();

                    }
                });
            }else  if ("clean".equals(type)) {
                holder.setText(R.id.title, "提示");
                holder.setText(R.id.message, "是否清除缓存?");

                holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                holder.setOnClickListener(R.id.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        CleanCacheUtil.clearAllCache(getContext());
                        Toast.makeText(getContext(),"缓存已清理完成",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }
    }
    public static void exit(){
        Intent intent=new Intent(MyApplication.getContext(), LoginActivity.class);
      //  editor.putString("Login_User","");
      //  editor.putString("Login_Password","");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        editor.remove("Login_Weight");
       // editor.clear();
        editor.apply();
        MyApplication.getContext().startActivity(intent);
    }
    //清空缓存
    @OnClick(R.id.clean_ll)
    public void mine_clean_ll_Click(){
        ConfirmDialog.newInstance("clean")
                .setMargin(60)
                .setOutCancel(false)
                .show(getSupportFragmentManager());
    }

}
