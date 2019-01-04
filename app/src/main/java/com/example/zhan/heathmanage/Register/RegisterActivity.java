package com.example.zhan.heathmanage.Register;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.Login.Servers.server.UserServer;
import com.example.zhan.heathmanage.Login.Servers.serverImp.UserServerImp;
import com.example.zhan.heathmanage.R;

import butterknife.BindView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends BaseActivity {

    //注册中输入的手机号
    @BindView(R.id.register_phone)
    EditText register_phone;
    //下一步按钮
    @BindView(R.id.register_next)
    Button register_next;
    EventHandler eh;
    //登录接口
    UserServer userServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        OnRegisterChange();//输入编辑框的监听事件
        userServer = new UserServerImp(this);
        eh=new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {//验证码
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                mHandler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eh);
    }
    //下一步按钮的点击事件处理
    @OnClick(R.id.register_next)
    public void register_next_OnClick(){
       userServer.FirstLogin(1,register_phone.getText().toString(),"");
    }
    //输入编辑框的监听事件
    public void OnRegisterChange(){
        register_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i2 + i == 11){
                    register_next.setEnabled(true);
                    register_next.setBackgroundColor(Color.parseColor("#15bdff"));
                    register_next.setTextColor(Color.parseColor("#f5f5f5"));
                }else {
                    register_next.setEnabled(false);
                    register_next.setBackgroundColor(Color.parseColor("#dcdcdc"));
                    register_next.setTextColor(Color.parseColor("#bebebe"));
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    //返回布局的点击事件
    @OnClick(R.id.register_back)
    public void register_back_OnClick(){
        finish();
    }


    //验证码发送处理
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理成功得到验证码的结果
                    Toast.makeText(getApplicationContext(),"发送验证码成功",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this,Register2Activity.class);
                    intent.putExtra("RegisterPhone",register_phone.getText().toString());
                    startActivity(intent);
                } else {
                    // TODO 处理错误的结果
                    ((Throwable) data).printStackTrace();
                    Toast.makeText(getApplicationContext(),"请输入正确的手机号码",Toast.LENGTH_LONG).show();
                }
            }
        }
    };
    //手机号判重复后的回调函数
    public void registerBack(){
        SMSSDK.getVerificationCode("86", register_phone.getText().toString());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }
}
