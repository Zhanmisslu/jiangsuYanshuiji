package com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.Imp;

import android.graphics.Color;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.beiing.leafchart.bean.Line;
import com.beiing.leafchart.bean.PointValue;
import com.example.zhan.heathmanage.Internet.Net;
import com.example.zhan.heathmanage.Internet.OKHttp;
import com.example.zhan.heathmanage.Main.TrendFragment.Bean.MonthInfo;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.MonthFragment;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.MonthLineChartServiceDao;
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

public class MonthLineChartServiceDaoImp implements MonthLineChartServiceDao {
    MonthInfo monthInfo;
    List<MonthInfo> monthInfoList;
    MonthFragment monthFragment;

    public MonthLineChartServiceDaoImp(MonthFragment monthFragment) {
        this.monthFragment = monthFragment;
    }

    //    List<String> HeartRateList;//心率
//    List<String> DateList;//时间
//    List<String> DiastolicBPList;//舒张压
//    List<String> SystolicBPList;//收缩压
//    List<String> BloodFatList;//血脂
//    List<String> BloodOxygenList;//血氧
    //心率曲线
    @Override
    public Line getHeartRateLine(List<MonthInfo> monthInfoList) {
        int length=monthInfoList.size();
        float a=length;
        List<PointValue> pointValues = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            PointValue pointValue = new PointValue();
            pointValue.setX((i)/ (a-1));
            float var = Float.parseFloat(monthInfoList.get(i).getHeartRate());
            pointValue.setLabel(String.valueOf(var));
            pointValue.setY(var / 200f);
            pointValue.setShowLabel(false);
            pointValues.add(pointValue);
        }
        Line line = new Line(pointValues);
        line.setLineColor(Color.parseColor("#FF18CC15"))
                .setLineWidth(3)
                .setPointColor(Color.parseColor("#FFFFFFFF"))
                .setCubic(true)
                .setPointRadius(3)
                .setHasPoints(true)
                .setFill(true)
                .setFillColor(Color.parseColor("#FF0AE906"))
                .setHasLabels(true)
                .setLabelColor(Color.parseColor("#00E9E9E9"));

        return line;
    }

    @Override
    public Line getDiastolicBPLine(List<MonthInfo> monthInfoList) {
        int length=monthInfoList.size();
        float a=length;
        List<PointValue> pointValues = new ArrayList<>();
        for (int i = 0; i <length; i++) {
            PointValue pointValue = new PointValue();
            pointValue.setX((i) / (a-1));
            float var = Float.parseFloat(monthInfoList.get(i).getDiastolicBP());
            pointValue.setLabel(String.valueOf(var));
            pointValue.setY(var / 100f);
            pointValue.setShowLabel(false);
            pointValues.add(pointValue);
        }

        Line line = new Line(pointValues);
        line.setLineColor(Color.parseColor("#33B5E5"))
                .setLineWidth(3)
                .setPointColor(Color.parseColor("#FFFFFFFF"))
                .setCubic(true)
                .setPointRadius(3)
                .setHasPoints(true)
                .setFill(true)
                .setHasLabels(true)
                .setLabelColor(Color.parseColor("#00E9E9E9"));

        return line;
    }

    @Override
    public Line getSystolicBPLine(List<MonthInfo> monthInfoList) {
        int length=monthInfoList.size();
        float a=length;
        Log.v("aaaa", String.valueOf(12f));
        List<PointValue> pointValues = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            PointValue pointValue = new PointValue();
            pointValue.setX((i)/ (a-1));
            float var = Float.parseFloat(monthInfoList.get(i).getSystolicBP());
            pointValue.setLabel(String.valueOf(var));
            pointValue.setY(var / 50f);
            pointValue.setShowLabel(false);
            pointValues.add(pointValue);
        }

        Line line = new Line(pointValues);
        line.setLineColor(Color.MAGENTA)
                .setLineWidth(3)
                .setPointColor(Color.parseColor("#FFFF0095"))
                .setCubic(true)
                .setPointRadius(3)
                .setHasPoints(true)
                .setFill(true)
                .setHasLabels(true)
                .setLabelColor(Color.parseColor("#00E9E9E9"));

        return line;
    }

    @Override
    public Line getBloodFatLine(List<MonthInfo> monthInfoList) {
        int length=monthInfoList.size();
        float a=length;
        List<PointValue> pointValues = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            PointValue pointValue = new PointValue();
            pointValue.setX((i)/ (a-1));
            float var = Float.parseFloat(monthInfoList.get(i).getBloodFat());
            pointValue.setLabel(String.valueOf(var));
            pointValue.setY(var / 200f);
            pointValue.setShowLabel(false);
            pointValues.add(pointValue);
        }

        Line line = new Line(pointValues);
        line.setLineColor(Color.parseColor("#FF18CC15"))
                .setLineWidth(3)
                .setPointColor(Color.parseColor("#FFFFFFFF"))
                .setCubic(true)
                .setPointRadius(3)
                .setHasPoints(true)
                .setFill(true)
                .setFillColor(Color.parseColor("#FF0AE906"))
                .setHasLabels(true)
                .setLabelColor(Color.parseColor("#00E9E9E9"));
        return line;
    }

    @Override
    public Line getBloodOxygenLine(List<MonthInfo> monthInfoList) {
        int length=monthInfoList.size();
        float a=length;
        List<PointValue> pointValues = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            PointValue pointValue = new PointValue();
            pointValue.setX(i/ (a-1));
            float var = Float.parseFloat(monthInfoList.get(i).getBloodOxygen());
            pointValue.setLabel(String.valueOf(var));
            pointValue.setY(var / 200f);
            pointValue.setShowLabel(false);
            pointValues.add(pointValue);
        }

        Line line = new Line(pointValues);
        line.setLineColor(Color.parseColor("#FF18CC15"))
                .setLineWidth(3)
                .setPointColor(Color.parseColor("#FFFFFFFF"))
                .setCubic(true)
                .setPointRadius(3)
                .setHasPoints(true)
                .setFill(true)
                .setFillColor(Color.parseColor("#FF0AE906"))
                .setHasLabels(true)
                .setLabelColor(Color.parseColor("#00E9E9E9"));
        return line;
    }

    @Override
    public void getSuggest(String userPhone, String year, final String month) {
        final String url= Net.GetSuggest+"?userPhone="+userPhone+"&year="+year+"&month="+month;
        Log.v("zjc",url);
//        DateList=new ArrayList<>();
//        HeartRateList=new ArrayList<>();
//        DiastolicBPList=new ArrayList<>();
//        SystolicBPList=new ArrayList<>();
//        BloodOxygenList=new ArrayList<>();
//        BloodFatList=new ArrayList<>();
        monthInfoList=new ArrayList<>();
        OKHttp.sendOkhttpGetRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(),"网络访问失败，请检查网络",Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ResponseData=response.body().string();
                Log.v("zjc",url);
                try {
                    JSONObject jsonObject=new JSONObject(ResponseData);
                    JSONArray jsonArray=jsonObject.getJSONArray("GetDataByMonth");
                    for(int i=0;i<jsonArray.length();i++){
                        monthInfo=new MonthInfo();
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String dayDataTime=jsonObject1.getString("dayDataTime");
                        String dayHeartRate=jsonObject1.getString("dayHeartRate");
                        String dayDbp=jsonObject1.getString("dayDbp");
                        String dayBloodOxygen=jsonObject1.getString("dayBloodOxygen");
                        String dayBloodFat=jsonObject1.getString("dayBloodFat");
                        String daySbp=jsonObject1.getString("daySbp");
                        monthInfo.setBloodFat(dayBloodFat);
                        monthInfo.setBloodOxygen(dayBloodOxygen);
                        monthInfo.setDate(dayDataTime.substring(5,dayDataTime.length()));
                        monthInfo.setHeartRate(dayHeartRate);
                        monthInfo.setDiastolicBP(dayDbp);
                        monthInfo.setSystolicBP(daySbp);
                        monthInfoList.add(monthInfo);
                    }
                    monthFragment.InitPageAdapter(monthInfoList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
