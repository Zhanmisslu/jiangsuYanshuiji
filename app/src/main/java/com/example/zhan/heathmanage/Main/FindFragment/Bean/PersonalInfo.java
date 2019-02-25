package com.example.zhan.heathmanage.Main.FindFragment.Bean;

public class PersonalInfo {
    private String userId;
    private String userNickName;
    private String userPhono;
    private String userSex;
    private String userFollowNum;//关注数
    private String userFollowedNum;//粉丝数

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserPhono() {
        return userPhono;
    }

    public void setUserPhono(String userPhono) {
        this.userPhono = userPhono;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserFollowNum() {
        return userFollowNum;
    }

    public void setUserFollowNum(String userFollowNum) {
        this.userFollowNum = userFollowNum;
    }

    public String getUserFollowedNum() {
        return userFollowedNum;
    }

    public void setUserFollowedNum(String userFollowedNum) {
        this.userFollowedNum = userFollowedNum;
    }
}
