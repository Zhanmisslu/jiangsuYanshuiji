package com.example.zhan.heathmanage.Main.EvaluteFragment.Server.server;

public interface UpdateUseServer {
    public void UpdateUser(String userHeight,String userWeight,String userSex,String userAge);
    public void UpdateNickName(String NickName);
    public void UpdateImage(String UsePhone,String UsePhoto);
    public void GetUserMessage();
}
