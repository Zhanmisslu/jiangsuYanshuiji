package com.example.zhan.heathmanage.Main.FindFragment.Service;

public interface PersonalDao {
    public void getPersonal(String userId);
    public void GetPersonalList(String userId);
    public void GetFollowStatus(String userId,String followedUserId);
}
