package com.example.zhan.heathmanage.Main.Service;

import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.zhan.heathmanage.Internet.Net;
import com.example.zhan.heathmanage.Internet.OKHttp;
import com.example.zhan.heathmanage.Main.MainActivity;
import com.example.zhan.heathmanage.Main.Menu.ChangePasswordActivity;
import com.example.zhan.heathmanage.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MenuDaoImp implements MenuDao {
    MainActivity mainActivity;
    ChangePasswordActivity changePasswordActivity;

    public MenuDaoImp(ChangePasswordActivity changePasswordActivity) {
        this.changePasswordActivity = changePasswordActivity;
    }

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

    @Override
    public void ChangePassword(String userPhone, String userPassword) {
        String url=Net.ChangePassword+"?userPhone="+userPhone+"&userPassword="+userPassword;
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
                    JSONObject jsonObject1=jsonObject.getJSONObject("ChangePassword");
                    String warning=jsonObject1.getString("warning");
                    if(warning.equals("2")){
                        Looper.prepare();
                        Toast.makeText(MyApplication.getContext(),"该用户不存在",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }else {
                        changePasswordActivity.successcallback();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getWeather(String cityid) {
        String url="http://wthrcdn.etouch.cn/weather_mini?citykey="+cityid;
        Log.v("zjc",url);
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
                    JSONObject jsonObject1=jsonObject.getJSONObject("data");
                    //JSONObject jsonObject2=jsonObject1.getJSONObject("forecast");
                    JSONArray jsonArray=jsonObject1.getJSONArray("forecast");
                    JSONObject jsonObject3=jsonArray.getJSONObject(0);
                    String high=jsonObject3.getString("high");
                    String type=jsonObject3.getString("type");
                    //high.re
                    high.substring(2);
                    mainActivity.CallBack(high,type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
