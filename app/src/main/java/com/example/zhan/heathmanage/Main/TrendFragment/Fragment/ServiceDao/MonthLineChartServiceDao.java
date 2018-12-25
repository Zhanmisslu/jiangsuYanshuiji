package com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao;

import com.beiing.leafchart.bean.Line;

public interface MonthLineChartServiceDao {
    public Line getHeartRateLine();//心率曲线
    public Line getDiastolicBPLine();//舒张压曲线
    public Line getSystolicBPLine();//收缩压曲线
    public Line getBloodFatLine();//血脂曲线
    public Line getBloodOxygenLine();//血氧曲线


}
