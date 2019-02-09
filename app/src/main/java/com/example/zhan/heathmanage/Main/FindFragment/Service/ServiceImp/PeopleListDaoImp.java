package com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp;

import android.os.Looper;
import android.widget.Toast;

import com.example.zhan.heathmanage.Internet.Net;
import com.example.zhan.heathmanage.Internet.OKHttp;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.AddFriendActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.PeopleInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Service.PeopleListDao;
import com.example.zhan.heathmanage.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PeopleListDaoImp implements PeopleListDao {
    AddFriendActivity addFriendActivity;
    PeopleInfo peopleInfo=new PeopleInfo();
    List<PeopleInfo> peopleInfoList=new ArrayList<>();
    String url= Net.GetPeopleList;
    //可能认识的人的列表
    @Override
    public void getPeopleList() {
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
                    JSONArray jsonArray=jsonObject.getJSONArray("aaa");
                    for (int i=0;i<jsonArray.length();i++){
                        peopleInfo=new PeopleInfo();
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String nickname = jsonObject1.getString("UserName");
                        String image = jsonObject1.getString("Image");
                        String phone=jsonObject1.getString("phone");
                        peopleInfo.setPeopleImage(image);
                        peopleInfo.setPhone(phone);
                        peopleInfo.setPeopleNickName(nickname);
                        peopleInfoList.add(peopleInfo);
                    }
                    addFriendActivity.InitPeopleList(peopleInfoList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
//关注按钮
    @Override
    public void attention() {
        String url=Net.Attention;
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
                    String flag=jsonObject.getString("warning");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
