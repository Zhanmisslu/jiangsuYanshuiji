package com.example.zhan.heathmanage.Register;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.R;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    //注册中输入的手机号
    @BindView(R.id.register_phone)
    EditText register_phone;
    //下一步按钮
    @BindView(R.id.register_next)
    Button register_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        OnRegisterChange();//输入编辑框的监听事件
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
    //下一步按钮的点击事件处理
    @OnClick(R.id.register_next)
    public void register_next_OnClick(){
        Intent intent = new Intent(RegisterActivity.this,Register2Activity.class);
        intent.putExtra("RegisterPhone",register_phone.getText().toString());
        startActivity(intent);
    }
}
