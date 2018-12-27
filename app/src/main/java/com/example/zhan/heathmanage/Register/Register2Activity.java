package com.example.zhan.heathmanage.Register;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.R;
import com.example.zhan.heathmanage.Register.View.PhoneCode;

import butterknife.BindView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class Register2Activity extends BaseActivity {
    //验证码
    @BindView(R.id.register2_code)
    PhoneCode register2_code;
    private String Phone;
    EventHandler eh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        Intent intent = getIntent();
        Phone = intent.getStringExtra("RegisterPhone");
        SSDKHandle();
        code();//验证码事件处理
    }
    public void SSDKHandle(){
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
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理验证码验证通过的结果
                    Intent intent = new Intent(Register2Activity.this,Register3Activity.class);
                    intent.putExtra("RegisterPhone",Phone);
                    startActivity(intent);
                } else {
                    // TODO 处理错误的结果
                    ((Throwable) data).printStackTrace();
                    Toast.makeText(getApplicationContext(),"验证失败",Toast.LENGTH_LONG).show();
                }
            }
        }
    };
    public void code(){
        register2_code.setOnInputListener(new PhoneCode.OnInputListener() {
            @Override
            public void onSucess(String code) {
                //输完验证码后的判断事件
                SMSSDK.submitVerificationCode("86",Phone,register2_code.getPhoneCode());
            }

            @Override
            public void onInput() {
            }
        });
    }
    @OnClick(R.id.register2_back)
    public void register2_back_OnClick(){
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }
}
