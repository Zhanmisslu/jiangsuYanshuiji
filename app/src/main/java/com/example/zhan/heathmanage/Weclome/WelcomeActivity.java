package com.example.zhan.heathmanage.Weclome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.Login.LoginActivity;
import com.example.zhan.heathmanage.Main.MainActivity;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;

public class WelcomeActivity extends BaseActivity {
    private SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        pref=getSharedPreferences("UserList", MODE_PRIVATE);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if(TextUtils.isEmpty(pref.getString("Login_User",""))){
                    intent=new Intent(WelcomeActivity.this, LoginActivity.class);
                }else {
                    MyApplication.setUserPhone(pref.getString("Login_User",""));
                    MyApplication.setUserPassword(pref.getString("Login_Password",""));
                    intent=new Intent(WelcomeActivity.this,MainActivity.class);

                }
                startActivity(intent);
                finish();
            }

        },1500);
    }
}
