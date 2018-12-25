package com.example.zhan.heathmanage.Main.Menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.Login.LoginActivity;
import com.example.zhan.heathmanage.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {
    @BindView(R.id.exit_bt)
    Button exit_bt;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        preferences = getSharedPreferences("UserList", MODE_PRIVATE);
        editor=preferences.edit();
        ButterKnife.bind(this);

    }
    @OnClick(R.id.exit_bt)
    public void exit_bt_Onclick(){
        Intent intent=new Intent(SettingActivity.this, LoginActivity.class);
//        editor.putString("Login_User","");
//        editor.putString("Login_Password","");
        editor.remove("Login_User");
        editor.remove("Login_Password");
        editor.apply();
        startActivity(intent);
    }
}
