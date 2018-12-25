package com.example.zhan.heathmanage.Register.Server.serverImp;

import android.os.Looper;
import android.widget.Toast;

import com.example.zhan.heathmanage.Internet.Net;
import com.example.zhan.heathmanage.Internet.OKHttp;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.Register.Register3Activity;
import com.example.zhan.heathmanage.Register.Server.server.RegisterServer;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterServerImp implements RegisterServer {

    Register3Activity register3Activity;

    public RegisterServerImp(){}
    public RegisterServerImp(Register3Activity register3Activity){
        this.register3Activity = register3Activity;
    }
    @Override
    public void Register(String UserPhone, String UserPassword) {
       String URL = Net.Register +"?userPhone="+UserPhone+"&userPassword="+UserPassword;
        OKHttp.sendOkhttpGetRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(),"注册失败",Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                  register3Activity.RegisterCallBack();
            }
        });
    }

}
