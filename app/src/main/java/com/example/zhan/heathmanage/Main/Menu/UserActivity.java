
package com.example.zhan.heathmanage.Main.Menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Server.server.UpdateUseServer;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Server.serverImp.UpdateUserServerImp;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;

import butterknife.BindView;
import butterknife.OnClick;

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
    UpdateUseServer updateUseServer ;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        updateUseServer = new UpdateUserServerImp(1,this);
        preferences = getSharedPreferences("UserList", MODE_PRIVATE);
        editor = preferences.edit();
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
}
