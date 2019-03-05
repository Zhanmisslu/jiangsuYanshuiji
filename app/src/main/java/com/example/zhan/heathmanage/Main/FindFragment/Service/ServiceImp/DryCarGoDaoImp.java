package com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp;

import android.os.Looper;
import android.widget.Toast;

import com.example.zhan.heathmanage.Internet.OKHttp;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.NewsBean;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.DryCargoFragment;
import com.example.zhan.heathmanage.Main.FindFragment.Service.DryCarGoDao;
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

public class DryCarGoDaoImp implements DryCarGoDao {
    NewsBean newsBean;
    List<NewsBean> newsBeanList;
    List<NewsBean> fashionList;
    DryCargoFragment dryCargoFragment;

    public DryCarGoDaoImp(DryCargoFragment dryCargoFragment) {
        this.dryCargoFragment = dryCargoFragment;
    }

    @Override
    public void getHealthapi() {
        newsBeanList=new ArrayList<>();
        String url="http://v.juhe.cn/toutiao/index?type=jiankang&key=b438f0de31bb0107abe79a4140defbf8";
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
                    JSONObject jsonObject2=jsonObject.getJSONObject("result");
                    JSONArray jsonArray=jsonObject2.getJSONArray("data");
                    for(int i=0;i<jsonArray.length();i++){
                        newsBean=new NewsBean();
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String title=jsonObject1.getString("title");
                        String date=jsonObject1.getString("date");
                        String anthorname=jsonObject1.getString("author_name");
                        String Url=jsonObject1.getString("url");
                        String picture=jsonObject1.getString("thumbnail_pic_s");
                        newsBean.setAuthor_name(anthorname);
                        newsBean.setDate(date);
                        newsBean.setUrl(Url);
                        newsBean.setPhotourl(picture);
                        newsBean.setTitle(title);
                        newsBeanList.add(newsBean);
                    }
                    dryCargoFragment.InitHealthData(newsBeanList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getFashionapi() {
        String url="http://v.juhe.cn/toutiao/index?type=shishang&key=b438f0de31bb0107abe79a4140defbf8";
        fashionList=new ArrayList<>();
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
                    JSONObject jsonObject2=jsonObject.getJSONObject("result");
                    JSONArray jsonArray=jsonObject2.getJSONArray("data");
                    for(int i=0;i<jsonArray.length();i++){
                        newsBean=new NewsBean();
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String title=jsonObject1.getString("title");
                        String date=jsonObject1.getString("date");
                        String anthorname=jsonObject1.getString("author_name");
                        String Url=jsonObject1.getString("url");
                        String picture=jsonObject1.getString("thumbnail_pic_s");
                        newsBean.setAuthor_name(anthorname);
                        newsBean.setDate(date);
                        newsBean.setUrl(Url);
                        newsBean.setPhotourl(picture);
                        newsBean.setTitle(title);
                        fashionList.add(newsBean);
                    }
                    dryCargoFragment.InitFashionData(fashionList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
