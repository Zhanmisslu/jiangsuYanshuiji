package com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp;

import android.os.Looper;
import android.widget.Toast;

import com.example.zhan.heathmanage.Internet.Net;
import com.example.zhan.heathmanage.Internet.OKHttp;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.HotInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.PeopleInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.HotFragment;
import com.example.zhan.heathmanage.Main.FindFragment.Service.FindView;
import com.example.zhan.heathmanage.Main.FindFragment.Service.HotDao;
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

public class HotDaoImp implements HotDao {
    FindView findView;
    HotFragment hotFragment;
    HotInfo hotInfo;
    List<HotInfo> HotList;

    public HotDaoImp(FindView findView) {
        this.findView = findView;
    }

//  //  public HotDaoImp(HotFragment hotFragment) {
//        this.hotFragment = hotFragment;
//    }

    @Override
    public void getHotList() {
        HotList=new ArrayList<>();
        String url= Net.GetHotList;

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
                    for (int i=0;i<jsonArray.length();i++){
                        hotInfo=new HotInfo();
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String postingId=jsonObject1.getString("postingId");
                        String nickname = jsonObject1.getString("userNickName");
                        String image = jsonObject1.getString("userPhoto");
                        String supportNum=jsonObject1.getString("postingLike");
                        String content=jsonObject1.getString("postingContent");
                        String picture=jsonObject1.getString("postingImg");
                        String time=jsonObject1.getString("postingTime");
                        hotInfo.setTime(time);
                        hotInfo.setPostingId(postingId);
                        hotInfo.setImage(image);
                        hotInfo.setNickName(nickname);
                        hotInfo.setSupportNum(supportNum);
                        hotInfo.setPciture(picture);
                        hotInfo.setContent(content);
                        HotList.add(hotInfo);
                    }
                    findView.InitHotList(HotList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void GetPostList(String postingId) {
        //String url=Net.
    }
}
