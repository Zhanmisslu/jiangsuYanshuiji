package com.example.zhan.heathmanage.Main.FindFragment.Service;

public interface PeopleListDao {
    public void getPeopleList(String userId);
    public void attention(String UserId,String followUserId);
    public void RemoveConcern(String UserId,String followUserId);
}
