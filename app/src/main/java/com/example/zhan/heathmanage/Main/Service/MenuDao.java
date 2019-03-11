package com.example.zhan.heathmanage.Main.Service;

public interface MenuDao {
    void GetFollowedNum(String userId);
    void GetFollowNum(String userId);
    void ChangePassword(String userPhone,String userPassword);
}
