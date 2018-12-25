package com.example.zhan.heathmanage.Register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.R;
import com.example.zhan.heathmanage.Register.View.PhoneCode;

import butterknife.BindView;
import butterknife.OnClick;

public class Register2Activity extends BaseActivity {
    //验证码
    @BindView(R.id.register2_code)
    PhoneCode register2_code;
    private String Phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        Intent intent = getIntent();
        Phone = intent.getStringExtra("RegisterPhone");
        register2_code.setOnInputListener(new PhoneCode.OnInputListener() {
            @Override
            public void onSucess(String code) {
                //输完验证码后的判断事件
                Intent intent = new Intent(Register2Activity.this,Register3Activity.class);
                intent.putExtra("RegisterPhone",Phone);
                startActivity(intent);
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
}
