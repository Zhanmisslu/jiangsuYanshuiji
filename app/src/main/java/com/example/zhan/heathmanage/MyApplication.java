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
    private static String UserWeight="";
    private static String UserHigh;
    private static String UserAge;
    private static String UserSex;
    private static String UserNickName;
    private static String UserPhoto;
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

    public static String getUserPhoto() {
        return UserPhoto;
    }

    public static void setUserPhoto(String userPhoto) {
        UserPhoto = userPhoto;
    }

    public static String getUserHigh() {
        return UserHigh;
    }

    public static void setUserHigh(String userHigh) {
        UserHigh = userHigh;
    }

    public static String getUserAge() {
        return UserAge;
    }

    public static void setUserAge(String userAge) {
        UserAge = userAge;
    }

    public static String getUserSex() {
        return UserSex;
    }

    public static void setUserSex(String userSex) {
        UserSex = userSex;
    }

    public static String getUserNickName() {
        return UserNickName;
    }

    public static void setUserNickName(String userNickName) {
        UserNickName = userNickName;
    }

    public static String getUserWeight() {
        return UserWeight;
    }

    public static void setUserWeight(String userWeight) {
        UserWeight = userWeight;
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
