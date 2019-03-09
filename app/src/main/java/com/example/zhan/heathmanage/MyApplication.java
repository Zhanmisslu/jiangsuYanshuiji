package com.example.zhan.heathmanage;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mob.MobSDK;

public class MyApplication extends Application {

    private static Context context;//上下文 并生成get方法
    //个人的身份信息
    private static String UserPhone;
    private static String UserPassword;
    private static String UserWeight="";
    private static String UserHigh;
    private static String UserAge;
    private static String UserSex;
    private static String UserNickName;
    private static Bitmap UserPhoto;
    private static String Photo;
    private static String EmergencyPhone;
    private static String EmergencyName;
    private static String UserId;
    //个人的资料
    private static String sBP="";
    private static String bloodOxygen;
    private static String dBP;
    private static String heartRate;
    private static String dataTime;
    private static String bloodFat;

    private SharedPreferences pref;//喜好设置
    private SharedPreferences.Editor editor;//让sharedPreferences处于编辑状态

    @Override
    public void onCreate() {
        super.onCreate();
        context =getApplicationContext();
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        editor=pref.edit();
        MobSDK.init(this);
        Fresco.initialize(this);
    }

    public static String getsBP() {
        return sBP;
    }

    public static void setsBP(String sBP) {
        MyApplication.sBP = sBP;
    }

    public static String getBloodOxygen() {
        return bloodOxygen;
    }

    public static void setBloodOxygen(String bloodOxygen) {
        MyApplication.bloodOxygen = bloodOxygen;
    }

    public static String getdBP() {
        return dBP;
    }

    public static void setdBP(String dBP) {
        MyApplication.dBP = dBP;
    }

    public static String getHeartRate() {
        return heartRate;
    }

    public static void setHeartRate(String heartRate) {
        MyApplication.heartRate = heartRate;
    }

    public static String getDataTime() {
        return dataTime;
    }

    public static void setDataTime(String dataTime) {
        MyApplication.dataTime = dataTime;
    }

    public static String getBloodFat() {
        return bloodFat;
    }

    public static void setBloodFat(String bloodFat) {
        MyApplication.bloodFat = bloodFat;
    }

    public static String getUserId() {
        return UserId;
    }

    public static void setUserId(String userId) {
        UserId = userId;
    }

    public static String getEmergencyName() {
        return EmergencyName;
    }

    public static void setEmergencyName(String emergencyName) {
        EmergencyName = emergencyName;
    }

    public static String getEmergencyPhone() {
        return EmergencyPhone;
    }

    public static void setEmergencyPhone(String emergencyPhone) {
        EmergencyPhone = emergencyPhone;
    }

    public static String getPhoto() {
        return Photo;
    }

    public static void setPhoto(String photo) {
        Photo = photo;
    }

    public static Bitmap getUserPhoto() {
        return UserPhoto;
    }

    public static void setUserPhoto(Bitmap userPhoto) {
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
