package com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.Imp;

import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.zhan.heathmanage.Internet.Net;
import com.example.zhan.heathmanage.Internet.OKHttp;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.DayFragment;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.DayDataServiceDao;
import com.example.zhan.heathmanage.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class DayDataServiceDaoImp implements DayDataServiceDao {
    DayFragment dayFragment;

    public DayDataServiceDaoImp(DayFragment dayFragment) {
        this.dayFragment = dayFragment;
    }

    @Override
    public void getDayData(String UserPhone,String date) {
        final String Url= Net.GetDataByDay+"?UserPhone="+UserPhone+"&date="+date;
        OKHttp.sendOkhttpGetRequest(Url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(),"╮(╯▽╰)╭连接不上了",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ResponseData=response.body().string();
                Log.v("zjc",Url);
                try {
                    JSONObject jsonObject=new JSONObject(ResponseData);
                    JSONObject jsonObject1=jsonObject.getJSONObject("GetDataByDay");
                    String warning=jsonObject1.getString("warning");
                    if(warning.equals("1")){
                        Looper.prepare();
                        Toast.makeText(MyApplication.getContext(),"查找失败",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }else {
                        String dayheartrate=jsonObject1.getString("dayHeartRate");
                        String dayblooddiastolic=jsonObject1.getString("dayDbp");
                        String daybloodbloodsystolic=jsonObject1.getString("daySbp");
                        String daybloodoxygen=jsonObject1.getString("dayBloodOxygen");
                        String  daybloodfat=jsonObject1.getString("dayBloodFat");
                        dayFragment.getDataByDaySuccessCallBack(dayheartrate,dayblooddiastolic,daybloodbloodsystolic,daybloodoxygen,daybloodfat);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
