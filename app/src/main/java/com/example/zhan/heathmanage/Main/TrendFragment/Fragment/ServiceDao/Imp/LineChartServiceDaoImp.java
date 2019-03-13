package com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.Imp;

import android.graphics.Color;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.beiing.leafchart.bean.Axis;
import com.beiing.leafchart.bean.AxisValue;
import com.beiing.leafchart.bean.Line;
import com.beiing.leafchart.bean.PointValue;
import com.example.zhan.heathmanage.Internet.Net;
import com.example.zhan.heathmanage.Internet.OKHttp;
import com.example.zhan.heathmanage.Main.TrendFragment.Bean.MonthInfo;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.LineChartServiceDao;
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

public class LineChartServiceDaoImp implements LineChartServiceDao {
    MonthInfo monthInfo;
    List<MonthInfo> monthInfoList;
    WeekFragment weekFragment;

    public LineChartServiceDaoImp(WeekFragment weekFragment) {
        this.weekFragment = weekFragment;
    }

    @Override
    public Line getHeartRateLine(List<MonthInfo> monthInfoList) {
        int length=monthInfoList.size();
        float a=length;
        List<PointValue> pointValues = new ArrayList<>();
        for (int i = 0; i <length; i++) {
            PointValue pointValue = new PointValue();
            pointValue.setX( (11*i)/ (a-1));
            float var = Float.parseFloat(monthInfoList.get(i).getHeartRate());
            pointValue.setLabel(String.valueOf(var));
            pointValue.setY(var / 25f);
            pointValue.setShowLabel(false);
            pointValues.add(pointValue);
        }
        Line line = new Line(pointValues);
        line.setLineColor(Color.parseColor("#ba2323"))
                .setLineWidth(3)
                .setPointColor(Color.parseColor("#fa2323"))//点的颜色
                .setPointRadius(3)//
                .setCubic(true)//设置是曲线还是折线
                .setHasPoints(true)
                .setFill(false)
                .setHasLabels(true)
                .setLabelColor(Color.parseColor("#FF000000"));
        return line;
    }

    @Override
    public Line getDiastolicBPLine(List<MonthInfo> monthInfoList) {
        int length=monthInfoList.size();
        float a=length;
        List<PointValue> pointValues = new ArrayList<>();
        for (int i = 1; i <length; i++) {
            PointValue pointValue = new PointValue();
            pointValue.setX( 11*i/ (a-1));
            float var = Float.parseFloat(monthInfoList.get(i).getDiastolicBP());
            pointValue.setLabel(String.valueOf(var));
            pointValue.setY(var / 30f);
            pointValue.setShowLabel(false);
            pointValues.add(pointValue);
        }

        Line line = new Line(pointValues);
        line.setLineColor(Color.parseColor("#23fa47"))
                .setLineWidth(3)
                .setPointColor(Color.parseColor("#FF23fa47"))
                .setCubic(true)
                .setPointRadius(3)
                .setFill(false)
                .setHasPoints(true)
                .setHasLabels(true)
                .setLabelColor(Color.parseColor("#FF000000"));
        return line;
    }

    @Override
    public Line getSystolicBPLine(List<MonthInfo> monthInfoList) {
        int length=monthInfoList.size();
        float a=length;
        List<PointValue> pointValues = new ArrayList<>();
        for (int i = 0; i <length; i++) {
            PointValue pointValue = new PointValue();
            pointValue.setX( 11*i/ (a-1) );
            float var = Float.parseFloat(monthInfoList.get(i).getSystolicBP());
            pointValue.setLabel(String.valueOf(var));
            pointValue.setY(var / 25f);
            pointValue.setShowLabel(false);
            pointValues.add(pointValue);
        }

        Line line = new Line(pointValues);
        line.setLineColor(Color.parseColor("#23bafa"))
                .setLineWidth(3)
                .setPointColor(Color.parseColor("#23bafa"))//点的颜色
                .setPointRadius(3)//
                .setCubic(true)//设置是曲线还是折线
                .setHasPoints(true)
                .setFill(false)
                .setHasLabels(true)
                .setLabelColor(Color.parseColor("#FF000000"));
        return line;
    }

    @Override
    public Line getBloodFatLine(List<MonthInfo> monthInfoList) {
        int length=monthInfoList.size();
        float a=length;
        List<PointValue> pointValues = new ArrayList<>();
        for (int i = 0; i <length; i++) {
            PointValue pointValue = new PointValue();
            pointValue.setX( i/ (a-1) );
            float var = Float.parseFloat(monthInfoList.get(i).getBloodFat());
            pointValue.setLabel(String.valueOf(var));
            pointValue.setY(var / 200f);
            pointValue.setShowLabel(false);
            pointValues.add(pointValue);
        }
        Line line = new Line(pointValues);
        line.setLineColor(Color.parseColor("#33B5E5"))
                .setLineWidth(3)
                .setPointColor(Color.parseColor("#FF00BAFF"))//点的颜色
                .setPointRadius(3)//
                .setCubic(true)//设置是曲线还是折线
                .setHasPoints(true)
                .setFill(false)
                .setHasLabels(true)
                .setLabelColor(Color.parseColor("#FF000000"));
        return line;
    }

    @Override
    public Line getBloodOxygenLine(List<MonthInfo> monthInfoList) {
        int length=monthInfoList.size();
        float a=length;
        List<PointValue> pointValues = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            PointValue pointValue = new PointValue();
            pointValue.setX( i  / (a-1));
            float var = Float.parseFloat(monthInfoList.get(i).getBloodOxygen());
            pointValue.setLabel(String.valueOf(var));
            pointValue.setY(var / 200f);
            pointValue.setShowLabel(false);
            pointValues.add(pointValue);
        }
        Line line = new Line(pointValues);
        line.setLineColor(Color.parseColor("#9523ba"))
                .setLineWidth(3)
                .setPointColor(Color.parseColor("#FF9523ba"))//点的颜色
                .setPointRadius(3)//
                .setCubic(true)//设置是曲线还是折线
                .setHasPoints(true)
                .setFill(false)
                .setHasLabels(true)
                .setLabelColor(Color.parseColor("#FF000000"));
        return line;
    }

    @Override
    public List<AxisValue> getBloodPressursValuesY() {
        List<AxisValue> axisValues = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            AxisValue value = new AxisValue();
            value.setLabel(String.valueOf(i * 10));
            axisValues.add(value);
        }
        return axisValues;
    }

    @Override
    public List<AxisValue> getBloodOxygenValuesY() {
        List<AxisValue> axisValues = new ArrayList<>();
        for (int i = 0; i < 20; i = i + 2) {
            AxisValue value = new AxisValue();
            value.setLabel(String.valueOf(i * 10));
            axisValues.add(value);
        }
        return axisValues;
    }

    @Override
    public List<AxisValue> getBloodFatValuesY() {
        List<AxisValue> axisValues=new ArrayList<>();

        for(int i = 0 ; i < 20 ; i ++){
            AxisValue value=new AxisValue();
            value.setLabel(String.valueOf(i * 10));
            axisValues.add(value);
        }
        return axisValues;
    }

    @Override
    public List<AxisValue> getHeartRateValuesY() {
        List<AxisValue> axisValues = new ArrayList<>();
        for (int i = 0; i < 20; i = i + 2) {
            AxisValue value = new AxisValue();
            value.setLabel(String.valueOf(i * 10));
            axisValues.add(value);
        }
        return axisValues;
    }

    @Override
    public List<AxisValue> getWeekAxisValuesX() {
        String week[] = {"  ","周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        List<AxisValue> axisValues = new ArrayList<>();

        for (int i = 1; i <= 8; i++) {
            AxisValue value = new AxisValue();
            value.setLabel(week[i - 1]);
            axisValues.add(value);
        }
        return axisValues;
    }

    @Override
    public List<AxisValue> getMonthAxisValueX() {
        List<AxisValue> axisValues = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            AxisValue value = new AxisValue();
            value.setLabel(i + "日");
            axisValues.add(value);
        }
        return axisValues;
    }

    @Override
    public void GetWeekData(String UserPhone, String StartDay, String EndDay) {
        monthInfoList=new ArrayList<>();
        final String url= Net.GetDataByWeek+"?userPhone="+UserPhone+"&startDay="+StartDay+"&finalDay="+EndDay;
        Log.v("zjc",url);
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
                    JSONArray jsonArray=jsonObject.getJSONArray("GetDataByWeek");
                    JSONObject jsonObject2=jsonArray.getJSONObject(0);
                    String warning=jsonObject2.getString("warning");
                    if (warning.equals("该星期无任何数据")){
                        weekFragment.InitNoData();
                    }else {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            monthInfo = new MonthInfo();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String dayDataTime = jsonObject1.getString("dayDataTime");
                            String dayHeartRate = jsonObject1.getString("dayHeartRate");
                            String dayDbp = jsonObject1.getString("dayDbp");
                            String dayBloodOxygen = jsonObject1.getString("dayBloodOxygen");
                            String dayBloodFat = jsonObject1.getString("dayBloodFat");
                            String daySbp = jsonObject1.getString("daySbp");
                            monthInfo.setBloodFat(dayBloodFat);
                            monthInfo.setBloodOxygen(dayBloodOxygen);
                            monthInfo.setDate(dayDataTime);
                            monthInfo.setHeartRate(dayHeartRate);
                            monthInfo.setDiastolicBP(dayDbp);
                            monthInfo.setSystolicBP(daySbp);
                            monthInfoList.add(monthInfo);
                        }
                        weekFragment.InitData(monthInfoList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
