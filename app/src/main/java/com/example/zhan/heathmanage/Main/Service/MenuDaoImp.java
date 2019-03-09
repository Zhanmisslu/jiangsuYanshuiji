package com.example.zhan.heathmanage.Main.Service;

import android.os.Looper;
import android.widget.Toast;

import com.example.zhan.heathmanage.Internet.Net;
import com.example.zhan.heathmanage.Internet.OKHttp;
import com.example.zhan.heathmanage.Main.MainActivity;
import com.example.zhan.heathmanage.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MenuDaoImp implements MenuDao {
    MainActivity mainActivity;

    public MenuDaoImp(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    //粉丝数
    @Override
    public void GetFollowedNum(String userId) {
        String url= Net.GetFollowedNum+"?userId="+userId;
        OKHttp.sendOkhttpGetRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(),"╮(╯▽╰)╭连接不上了",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ResponseData=response.body().string();
                try {
                    JSONObject jsonObject=new JSONObject(ResponseData);
                    JSONObject jsonObject1=jsonObject.getJSONObject("GetFollowedNum");
                    String followedNum=jsonObject1.getString("followedNum");
                    mainActivity.InitfollowedNum(followedNum);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void GetFollowNum(String userId) {
        String url= Net.GetFollowNum+"?userId="+userId;
        OKHttp.sendOkhttpGetRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(),"╮(╯▽╰)╭连接不上了",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ResponseData=response.body().string();
                try {
                    JSONObject jsonObject=new JSONObject(ResponseData);
                    JSONObject jsonObject1=jsonObject.getJSONObject("GetFollowNum");
                    String followedNum=jsonObject1.getString("followNum");
                    mainActivity.InitfollowNum(followedNum);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
