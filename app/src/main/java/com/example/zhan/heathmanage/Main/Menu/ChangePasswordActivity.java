package com.example.zhan.heathmanage.Main.Menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.Login.LoginActivity;
import com.example.zhan.heathmanage.Main.Service.MenuDao;
import com.example.zhan.heathmanage.Main.Service.MenuDaoImp;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangePasswordActivity extends BaseActivity {
    @BindView(R.id.changepw_old_et)
    EditText changePw_old_et;
    @BindView(R.id.changepw_new_et) EditText changePw_new_et;
    @BindView(R.id.changepw_new2_et) EditText changePw_new2_et;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    MenuDao menuDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        pref = getSharedPreferences("UserList", MODE_PRIVATE);
        editor = pref.edit();
        menuDao=new MenuDaoImp(this);


    }
    @OnClick(R.id.changepw_back_ll)
    public void changepw_back_ll_Click(){
        finish();
    }

    @OnClick(R.id.changepw_confirm_ll)
    public void changepw_confirm_ll_Click(){
        boolean flag=false;
        if(TextUtils.isEmpty(changePw_old_et.getText().toString())){
            flag=true;
        }
        if(TextUtils.isEmpty(changePw_new_et.getText().toString())){
            flag=true;
        }
        if(TextUtils.isEmpty(changePw_new2_et.getText().toString())){
            flag=true;
        }
        if(flag){
            Toast.makeText(getApplication(),"您还没有填写完整的信息",Toast.LENGTH_SHORT).show();
        }else if(!changePw_new2_et.getText().toString().equals(changePw_new_et.getText().toString())){
            Toast.makeText(getApplication(),"您输入的两次新密码不相同",Toast.LENGTH_SHORT).show();
        }else {
            menuDao.ChangePassword(MyApplication.getUserPhone(),changePw_new_et.getText().toString());
        }
        }
    public void successcallback() {
        //清楚所有信息，回到登陆界面
        editor.remove("Login_User");
        editor.remove("Login_Password");
        editor.apply();
        Intent intent=new Intent(ChangePasswordActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
