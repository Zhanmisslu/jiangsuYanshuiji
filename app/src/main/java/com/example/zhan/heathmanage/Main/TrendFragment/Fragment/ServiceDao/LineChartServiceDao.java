package com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao;

import com.beiing.leafchart.bean.AxisValue;
import com.beiing.leafchart.bean.Line;
import com.example.zhan.heathmanage.Main.TrendFragment.Bean.MonthInfo;

import java.util.List;

public interface LineChartServiceDao {
    public Line getHeartRateLine(List<MonthInfo> monthInfoList);//心率曲线
    public Line getDiastolicBPLine(List<MonthInfo> monthInfoList);//舒张压曲线
    public Line getSystolicBPLine(List<MonthInfo> monthInfoList);//收缩压曲线
    public Line getBloodFatLine(List<MonthInfo> monthInfoList);//血脂曲线
    public Line getBloodOxygenLine(List<MonthInfo> monthInfoList);//血氧曲线
    public List<AxisValue> getBloodPressursValuesY();//血压纵坐标
    public List<AxisValue> getBloodOxygenValuesY();//血氧的纵坐标
    public List<AxisValue> getBloodFatValuesY();//血脂的纵坐标
    public List<AxisValue> getHeartRateValuesY();//心率纵坐标轴
    public List<AxisValue> getWeekAxisValuesX();//周视图的横坐标
    public List<AxisValue> getMonthAxisValueX();//月视图的横坐标
    public void GetWeekData(String UserPhone,String StartDay,String EndDay);

}
