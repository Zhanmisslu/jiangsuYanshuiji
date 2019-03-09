package com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao;

import com.beiing.leafchart.bean.Line;
import com.example.zhan.heathmanage.Main.TrendFragment.Bean.MonthInfo;

import java.util.List;

public interface MonthLineChartServiceDao {
//    public Line getHeartRateLine(List<MonthInfo> monthInfoList);//心率曲线
//    public Line getDiastolicBPLine(List<MonthInfo> monthInfoList);//舒张压曲线
//    public Line getSystolicBPLine(List<MonthInfo> monthInfoList);//收缩压曲线
//    public Line getBloodFatLine(List<MonthInfo> monthInfoList);//血脂曲线
//    public Line getBloodOxygenLine(List<MonthInfo> monthInfoList);//血氧曲线
    public void getSuggest(String userPhone,String year,String month);

}
