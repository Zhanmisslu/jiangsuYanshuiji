package com.example.zhan.heathmanage.Register;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.Login.LoginActivity;
import com.example.zhan.heathmanage.R;
import com.example.zhan.heathmanage.Register.Server.server.RegisterServer;
import com.example.zhan.heathmanage.Register.Server.serverImp.RegisterServerImp;

import butterknife.BindView;
import butterknife.OnClick;

public class Register3Activity extends BaseActivity {
    private static Boolean i=false;
    private static Boolean y=false;
    //输入第一次密码的编辑框和图片
    @BindView(R.id.register3_password)
    EditText register3_password;
    @BindView(R.id.register3_password_img)
    ImageView register3_password_img;
    //输入第二次密码的编辑框和图片
    @BindView(R.id.register3_password2)
    EditText register3_password2;
    @BindView(R.id.register3_password2_img)
    ImageView register3_password2_img;
    //注册按钮
    @BindView(R.id.register3_finish)
    Button register3_finish;
    //注册的手机号
    String Phone ;
    //注册的Server
    RegisterServer server ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
        Intent intent = getIntent();
        Phone = intent.getStringExtra("RegisterPhone");
        server = new RegisterServerImp(this);
        OnPasswordChange();//输入编辑框的监听事件
    }
    //返回按钮
    @OnClick(R.id.register3_back)
    public void register3_back_OnClick(){
        finish();
    }
    //注册按钮
    @OnClick(R.id.register3_finish)
    public void register3_finish_OnClick(){
        if (register3_password.getText().toString().equals(register3_password2.getText().toString())){
            server.Register(Phone,register3_password.getText().toString());
        }else {
            Toast.makeText(getApplication(),"请输入相同的密码",Toast.LENGTH_SHORT).show();
        }
    }
    //RegisterServer的回调
    public void RegisterCallBack(){
        Intent intent = new Intent(Register3Activity.this, LoginActivity.class);
        startActivity(intent);
    }
    public void OnPasswordChange(){
        register3_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //当输入时改变颜色
                  if (i!=0){
                      register3_password_img.setImageResource(R.drawable.password_2);
                  }else {
                      register3_password_img.setImageResource(R.drawable.password_1);
                  }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()){
                    i=true;
                }else {
                    i=false;
                }
                if (i==true&&y==true){
                    register3_finish.setEnabled(true);
                    register3_finish.setBackgroundColor(Color.parseColor("#15bdff"));
                    register3_finish.setTextColor(Color.parseColor("#f5f5f5"));
                }else {
                    register3_finish.setEnabled(false);
                    register3_finish.setBackgroundColor(Color.parseColor("#dcdcdc"));
                    register3_finish.setTextColor(Color.parseColor("#bebebe"));
                }
            }
        });

        register3_password2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i!=0){
                    register3_password2_img.setImageResource(R.drawable.password_4);
                }else {
                    register3_password2_img.setImageResource(R.drawable.password_3);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()){
                    y=true;
                }else {
                    y=false;
                }
                if (i==true&&y==true){
                    register3_finish.setEnabled(true);
                    register3_finish.setBackgroundColor(Color.parseColor("#15bdff"));
                    register3_finish.setTextColor(Color.parseColor("#f5f5f5"));
                }else {
                    register3_finish.setEnabled(false);
                    register3_finish.setBackgroundColor(Color.parseColor("#dcdcdc"));
                    register3_finish.setTextColor(Color.parseColor("#bebebe"));
                }
            }
        });
    }
}
