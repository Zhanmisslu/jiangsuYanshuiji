package com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp;

import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.zhan.heathmanage.Internet.Net;
import com.example.zhan.heathmanage.Internet.OKHttp;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.PersonalActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.AttentionInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.PersonalInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Service.PersonalDao;
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

public class PersonalDaoImp implements PersonalDao {
    PersonalInfo personalInfo;
    PersonalActivity personalActivity;
    AttentionInfo attentionInfo;
    List<AttentionInfo>attentionInfoList;

    public PersonalDaoImp(PersonalActivity personalActivity) {
        this.personalActivity = personalActivity;
    }

    @Override
    public void getPersonal(String userId) {
        String url= Net.GetPersonal+"?userId="+userId;
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
                    JSONObject jsonObject1=jsonObject.getJSONObject("ShowUserInfo");
                    personalInfo=new PersonalInfo();
                    String userId=jsonObject1.getString("userId");
                    String userNickName=jsonObject1.getString("userNickName");
                    String userPhoto=jsonObject1.getString("userPhoto");
                    String userSex=jsonObject1.getString("userSex");
                    String userFollowedNum=jsonObject1.getString("userFollowedNum");
                    String userFollowNum=jsonObject1.getString("userFollowNum");

                    personalInfo.setUserFollowedNum(userFollowedNum);
                    personalInfo.setUserSex(userSex);
                    personalInfo.setUserFollowNum(userFollowNum);
                    personalInfo.setUserNickName(userNickName);
                    personalInfo.setUserId(userId);
                    personalInfo.setUserPhono(userPhoto);
                    personalActivity.InitPersonalInfo(personalInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void GetPersonalList(String userId) {
        String url=Net.GetPersonalList+"?userId="+userId;
        attentionInfoList=new ArrayList<>();
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
                    JSONArray jsonArray=jsonObject.getJSONArray("ShowHotPosting");
                    JSONObject jsonObject1=jsonArray.getJSONObject(0);
                    String warning=jsonObject1.getString("warning");
                    if (warning.equals("1")){
                        Looper.prepare();
                        Toast.makeText(MyApplication.getContext(),"暂无动态",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }else {
                        for (int i=0;i<jsonArray.length();i++){
                            attentionInfo=new AttentionInfo();
                            jsonObject1=jsonArray.getJSONObject(i);
                            String postingId=jsonObject1.getString("postingId");
                            String nickname = jsonObject1.getString("userNickName");
                            String image = jsonObject1.getString("userPhoto");
                            String supportNum=jsonObject1.getString("postingLike");
                            String content=jsonObject1.getString("postingContent");
                            String picture=jsonObject1.getString("postingImg");
                            String time=jsonObject1.getString("postingTime");
                            String location=jsonObject1.getString("postingLocation");
                            String commentNum=jsonObject1.getString("commentNum");
                            attentionInfo.setCommentNum(commentNum);
                            attentionInfo.setLocation(location);
                            attentionInfo.setTime(time);
                            attentionInfo.setPostingId(postingId);
                            attentionInfo.setImage(image);
                            attentionInfo.setNickName(nickname);
                            attentionInfo.setSupportNum(supportNum);
                             attentionInfo.setPciture(picture);
                            attentionInfo.setContent(content);
                            attentionInfoList.add(attentionInfo);
                        }
                        personalActivity.InitPersonalList(attentionInfoList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void GetFollowStatus(final String userId, final String followedUserId) {
        String url=Net.GetFollowStatus+"?userId="+userId+"&followedUserId="+followedUserId;
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
                    JSONObject jsonObject1=jsonObject.getJSONObject("GetFollowStatus");
                    String warning=jsonObject1.getString("warning");
                    if(warning.equals("1") && userId.equals(followedUserId)){//没有关注
                        personalActivity.Self();
                    }else if(warning.equals("0")){
                        personalActivity.Concern();
                    }else {
                        personalActivity.NoConcern();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
