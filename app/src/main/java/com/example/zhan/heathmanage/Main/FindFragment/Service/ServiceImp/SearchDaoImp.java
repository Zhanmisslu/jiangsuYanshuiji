package com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp;

import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.zhan.heathmanage.Internet.Net;
import com.example.zhan.heathmanage.Internet.OKHttp;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.SearchActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.AttentionInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.PeopleInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.AllFragment;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.DynamicFragment;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.UserFragment;
import com.example.zhan.heathmanage.Main.FindFragment.Service.SearchDao;
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

public class SearchDaoImp implements SearchDao {
    List<PeopleInfo> SearchUserList;
    List<AttentionInfo> SearchPostingList;
    List<PeopleInfo> allUserList;
    List<AttentionInfo> allPostingList;
    PeopleInfo peopleInfo;
    AttentionInfo attentionInfo;
    AllFragment allFragment;
    UserFragment userFragment;
    DynamicFragment dynamicFragment;
    SearchActivity searchActivity;

    public SearchDaoImp(SearchActivity searchActivity) {
        this.searchActivity = searchActivity;
    }

    public SearchDaoImp(AllFragment allFragment) {
        this.allFragment = allFragment;
    }

    public SearchDaoImp(UserFragment userFragment) {
        this.userFragment = userFragment;
    }

    public SearchDaoImp(DynamicFragment dynamicFragment) {
        this.dynamicFragment = dynamicFragment;
    }

    @Override
    public void SearchUser(String text) {
        final String url= Net.SearchUser+"?content="+text;
        SearchUserList=new ArrayList<>();
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
                    JSONArray jsonArray=jsonObject.getJSONArray("SearchUser");
                    JSONObject jsonObject2=jsonArray.getJSONObject(0);
                    String warning=jsonObject2.getString("warning");
                    if(warning.equals("1")){
                        Looper.prepare();
                      //  Toast.makeText(MyApplication.getContext(),"没有相关用户",Toast.LENGTH_SHORT).show();
                        userFragment.InitImage();
                        Looper.loop();
                    }else {
                        for (int i=0;i<jsonArray.length();i++) {
                            peopleInfo=new PeopleInfo();
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String userPhoto=jsonObject1.getString("userPhoto");
                            String userNickName=jsonObject1.getString("userNickName");
                            String userId=jsonObject1.getString("userId");
                            peopleInfo.setUserid(userId);
                            peopleInfo.setPeopleNickName(userNickName);
                            peopleInfo.setPeopleImage(userPhoto);
                            SearchUserList.add(peopleInfo);
                        }
                        userFragment.InitUserData(SearchUserList);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void SearchPosting(String text) {
        SearchPostingList=new ArrayList<>();
        String url=Net.SearchPosting+"?content="+text;
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
                    JSONArray jsonArray=jsonObject.getJSONArray("SearchPosting");
                    JSONObject jsonObject1=jsonArray.getJSONObject(0);
                    String warning=jsonObject1.getString("warning");
                    if (warning.equals("1")){
                        Looper.prepare();
                        dynamicFragment.InitImage();
                       // Toast.makeText(MyApplication.getContext(),"没有相关动态",Toast.LENGTH_SHORT).show();
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
                            String userId=jsonObject1.getString("userId");
                            String commentNum=jsonObject1.getString("postingContentNum");
                            attentionInfo.setUserId(userId);
                            attentionInfo.setTime(time);
                            attentionInfo.setPostingId(postingId);
                            attentionInfo.setImage(image);
                            attentionInfo.setNickName(nickname);
                            attentionInfo.setSupportNum(supportNum);
                            attentionInfo.setCommentNum(commentNum);
                            attentionInfo.setPciture(picture);
                            attentionInfo.setContent(content);
                            SearchPostingList.add(attentionInfo);
                        }
                        dynamicFragment.InitList(SearchPostingList);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void SearchAllUser(String text) {
        final String url= Net.SearchUser+"?content="+text;
        allUserList=new ArrayList<>();
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
                    JSONArray jsonArray=jsonObject.getJSONArray("SearchUser");
                    JSONObject jsonObject2=jsonArray.getJSONObject(0);
                    String warning=jsonObject2.getString("warning");
                    if(warning.equals("1")){
                        Looper.prepare();
                        allFragment.InitAllUserData(allUserList);
                        //Toast.makeText(MyApplication.getContext(),"没有相关用户",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }else {
                        for (int i=0;i<jsonArray.length();i++) {
                            peopleInfo=new PeopleInfo();
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String userPhoto=jsonObject1.getString("userPhoto");
                            String userNickName=jsonObject1.getString("userNickName");
                            String userId=jsonObject1.getString("userId");
                            peopleInfo.setUserid(userId);
                            peopleInfo.setPeopleNickName(userNickName);
                            peopleInfo.setPeopleImage(userPhoto);
                            allUserList.add(peopleInfo);
                        }
                        allFragment.InitAllUserData(allUserList);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void SearchAllPosting(String text) {
        allPostingList=new ArrayList<>();
        String url=Net.SearchPosting+"?content="+text;
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
                    JSONArray jsonArray=jsonObject.getJSONArray("SearchPosting");
                    JSONObject jsonObject1=jsonArray.getJSONObject(0);
                    String warning=jsonObject1.getString("warning");
                    if (warning.equals("1")){
                        Looper.prepare();
                        allFragment.InitAllPostingList(allPostingList);
                      //  Toast.makeText(MyApplication.getContext(),"没有相关动态",Toast.LENGTH_SHORT).show();
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
                            String userId=jsonObject1.getString("userId");
                            String commentNum=jsonObject1.getString("postingContentNum");
                            attentionInfo.setUserId(userId);
                            attentionInfo.setTime(time);
                            attentionInfo.setPostingId(postingId);
                            attentionInfo.setImage(image);
                            attentionInfo.setNickName(nickname);
                            attentionInfo.setSupportNum(supportNum);
                            attentionInfo.setCommentNum(commentNum);
                            attentionInfo.setPciture(picture);
                            attentionInfo.setContent(content);
                            allPostingList.add(attentionInfo);
                        }
                        allFragment.InitAllPostingList(allPostingList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
