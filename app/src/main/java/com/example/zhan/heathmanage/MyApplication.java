package com.example.zhan.heathmanage;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.mob.MobSDK;

public class MyApplication extends Application {

    private static Context context;//上下文 并生成get方法
    private static String UserPhone;
    private static String UserPassword;
    private SharedPreferences pref;//喜好设置
    private SharedPreferences.Editor editor;//让sharedPreferences处于编辑状态

    @Override
    public void onCreate() {
        super.onCreate();
        context =getApplicationContext();
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        editor=pref.edit();
        MobSDK.init(this);
    }

    public static String getUserPhone() {
        return UserPhone;
    }

    public static void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public static String getUserPassword() {
        return UserPassword;
    }

    public static void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        MyApplication.context = context;
    }
}
