package com.example.zhan.heathmanage.Login.Servers.server;

public interface UserServer {
   public  void Register(String UserPhone , String UserPassword);
   public  void FirstLogin(int i,String UserPhone,String UserPassword);
   public  void UserEvalute(String UserPhone);
   public void TextLogin(String UserPhone);
}
