package com.example.zhan.heathmanage.Login.Servers.serverImp;

import android.os.Looper;
import android.widget.Toast;

import com.example.zhan.heathmanage.Internet.Net;
import com.example.zhan.heathmanage.Internet.OKHttp;
import com.example.zhan.heathmanage.Login.Servers.server.UserServer;
import com.example.zhan.heathmanage.MyApplication;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UserServerImp implements UserServer {

    @Override
    public void Register(String UserPhone, String UserPassword) {
      String URL = Net.Register;
        OKHttp.sendOkhttpGetRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(),"注册失败",Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(),"注册成功",Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        });
    }
}
