package com.example.zhan.heathmanage;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class MyApplication extends Application {

    private static Context context;//上下文 并生成get方法

    private SharedPreferences pref;//喜好设置
    private SharedPreferences.Editor editor;//让sharedPreferences处于编辑状态

    @Override
    public void onCreate() {
        super.onCreate();
        context =getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        MyApplication.context = context;
    }
}
