package com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp;

import android.os.Looper;
import android.widget.Toast;

import com.example.zhan.heathmanage.Internet.Net;
import com.example.zhan.heathmanage.Internet.OKHttp;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.AddFriendActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.PersonalActivity;
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
    List<PeopleInfo> peopleInfoList;
    PersonalActivity personalActivity;
    public PeopleListDaoImp() {

    }

    public PeopleListDaoImp(PersonalActivity personalActivity) {
        this.personalActivity = personalActivity;
    }

    public PeopleListDaoImp(AddFriendActivity addFriendActivity) {
        this.addFriendActivity = addFriendActivity;
    }

//    public PeopleListDaoImp(AddFriendActivity addFriendActivity, List<PeopleInfo> peopleInfoList) {
//        this.addFriendActivity = addFriendActivity;
//        this.peopleInfoList = peopleInfoList;
//    }

    //可能认识的人的列表
    @Override
    public void getPeopleList(String userid) {
        String url= Net.GetPeopleList+"?userId="+userid;
        peopleInfoList=new ArrayList<>();
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
                    JSONArray jsonArray=jsonObject.getJSONArray("GetFollowUserList");
                    JSONObject jsonObject2=jsonArray.getJSONObject(0);
                    String warning=jsonObject2.getString("warning");
                    if(warning.equals("1")){//你暂时还没有关注
                        Looper.prepare();
                        Toast.makeText(MyApplication.getContext(),"暂无数据",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }else {
                        //JSONArray jsonArray=jsonObject.getJSONArray("GetFollowUserList");
                        for (int i=0;i<jsonArray.length();i++){
                            peopleInfo=new PeopleInfo();
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String nickname = jsonObject1.getString("friendNickName");
                            String image = jsonObject1.getString("friendPhoto");
                            String userid=jsonObject1.getString("friendId");
                            peopleInfo.setPeopleImage(image);
                            peopleInfo.setUserid(userid);
                            peopleInfo.setPeopleNickName(nickname);
                            peopleInfoList.add(peopleInfo);
                        }
                        addFriendActivity.InitPeopleList(peopleInfoList);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
//关注按钮
    @Override
    public void attention(String UserId,String followUserId) {
        String url=Net.Attention+"?userId="+UserId+"&followedUserId="+followUserId;
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
                    JSONObject jsonObject1=jsonObject.getJSONObject("FollowUser");
                    String warning=jsonObject1.getString("warning");
                    if (warning.equals("0")){
                        Looper.prepare();
                        Toast.makeText(MyApplication.getContext(),"关注成功",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
//已关注  取消关注
    @Override
    public void RemoveConcern(String UserId,String disfollowUserId) {
        String url=Net.RemoveConcern+"?userId="+UserId+"&disFollowedUserId="+disfollowUserId;
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
                    JSONObject jsonObject1=jsonObject.getJSONObject("DisFollowUser");
                    String warning=jsonObject1.getString("warning");
                    if (warning.equals("0")){
                        Looper.prepare();
                        Toast.makeText(MyApplication.getContext(),"取消关注成功",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
