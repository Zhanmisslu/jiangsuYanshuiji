package com.example.zhan.heathmanage.Main.FindFragment.Service;

public interface HotDao {
    public void getHotList();
    public void GetPostList(String postingId);
    public void GetFollowStatus(String userId,String followedUserId);

}
