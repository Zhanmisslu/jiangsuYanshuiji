package com.example.zhan.heathmanage.Internet;

public class Net {
    public final static String Head = "http://www.mcartoria.com:8080/Health/";//服务器地址http://i3c8x94lqb.51http.tech/
    public final static String Register = Head + "Register"; //注册用户
    public final static String PasswordLogin = Head + "PasswordLogin";//密码登录
    public final static String ChangeUserInfo = Head +"ChangeUserInfo";//修改个人信息
    public final static String GetDataByDay = Head + "GetDataByDay";//日视图的数据
    public final static String GetDataByWeek = Head + "GetDataByWeek";//周视图的数据
    public final static String ChangeUserNickName = Head + "ChangeUserNickName";//修改昵称
    public final static String ChangeUserImage = Head + "ChangeUserPhoto";//修改头像
    public final static String ShowUserInfo = Head + "ShowUserInfo";//获取用户信息
    public final static String ChangeUserEmergency = Head + "ChangeUserEmergency";//修改紧急联系人


}
