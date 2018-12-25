package com.example.zhan.heathmanage.Login.Servers.server;

public interface UserServer {
   public  void Register(String UserPhone , String UserPassword);
   public void FirstLogin(String UserPhone,String UserPassword);

}
