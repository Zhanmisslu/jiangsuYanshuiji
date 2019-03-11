package com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.Imp;

import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.zhan.heathmanage.Internet.Net;
import com.example.zhan.heathmanage.Internet.OKHttp;
import com.example.zhan.heathmanage.Main.TrendFragment.Bean.MonthSuggestBean;
import com.example.zhan.heathmanage.Main.TrendFragment.Bean.WeekSuggestBean;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.MonthFragment;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.SuggestDao;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.WeekFragment;
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

public class SuggestDaoImp implements SuggestDao {
    WeekFragment weekFragment;
    WeekSuggestBean weekSuggestBean;
    List<WeekSuggestBean> weekSuggestBeanList;
    public SuggestDaoImp(WeekFragment weekFragment) {
        this.weekFragment = weekFragment;
    }
    MonthFragment monthFragment;
    MonthSuggestBean monthSuggestBean;
    List<MonthSuggestBean>monthSuggestBeanList;
    public SuggestDaoImp(MonthFragment monthFragment) {
        this.monthFragment = monthFragment;
    }

    @Override
    public void getWeekSuggestion(String userPhone, String startDay, String finalDay) {
        String url= Net.getWeekSuggestion+"?userPhone="+userPhone+"&startDay="+startDay+"&finalDay="+finalDay;
        weekSuggestBeanList=new ArrayList<>();
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
                    JSONArray jsonArray=jsonObject.getJSONArray("WeekSuggestion");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        weekSuggestBean=new WeekSuggestBean();
                        String referenceType=jsonObject1.getString("referenceType");
                        String indexId=jsonObject1.getString("indexId");
                        String suggestion=jsonObject1.getString("suggestion");
                        weekSuggestBean.setIndexId(indexId);
                        weekSuggestBean.setReferenceType(referenceType);
                        weekSuggestBean.setSuggestion(suggestion);
                        weekSuggestBeanList.add(weekSuggestBean);
                    }
                    weekFragment.InitSuggest(weekSuggestBeanList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void getMonthSuggestion(String userPhone, String year, String month) {
        String url= Net.getMonthSuggestion+"?userPhone="+userPhone+"&year="+year+"&month="+month;
        monthSuggestBeanList=new ArrayList<>();
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
                    JSONArray jsonArray=jsonObject.getJSONArray("MonthSuggestion");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        monthSuggestBean=new MonthSuggestBean();
                        String referenceType=jsonObject1.getString("referenceType");
                        String indexId=jsonObject1.getString("indexId");
                        String suggestion=jsonObject1.getString("suggestion");
                        monthSuggestBean.setIndexId(indexId);
                        monthSuggestBean.setReferenceType(referenceType);
                        monthSuggestBean.setSuggestion(suggestion);
                        monthSuggestBeanList.add(monthSuggestBean);
                    }
                    monthFragment.InitSuggest(monthSuggestBeanList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
