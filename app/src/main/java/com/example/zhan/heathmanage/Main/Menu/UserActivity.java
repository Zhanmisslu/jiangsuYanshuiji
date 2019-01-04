
package com.example.zhan.heathmanage.Main.Menu;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.R;

import butterknife.BindView;
import butterknife.OnClick;

public class UserActivity extends BaseActivity {
    private Button sex_man,sex_woman,sex_back;
    @BindView(R.id.menu_user_sex)
    TextView menu_user_sex;
    @BindView(R.id.menu_user_sex_img)
    ImageView menu_user_sex_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
    }
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
}
