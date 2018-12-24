package com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.Imp;

import android.graphics.Color;

import com.beiing.leafchart.bean.Line;
import com.beiing.leafchart.bean.PointValue;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.LineChartServiceDao;

import java.util.ArrayList;
import java.util.List;

public class LineChartServiceDaoImp implements LineChartServiceDao {
    @Override
    public Line getHeartRateLine() {
        List<PointValue> pointValues = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            PointValue pointValue = new PointValue();
            pointValue.setX((i - 1) / 30f);
            int var = (int) (Math.random() * 100);
            pointValue.setLabel(String.valueOf(var));
            pointValue.setY(var / 100f);
            pointValues.add(pointValue);
        }
        Line line = new Line(pointValues);
        line.setLineColor(Color.parseColor("#33B5E5"))
                .setLineWidth(3)
                .setPointColor(Color.RED)
                .setCubic(true)
                .setPointRadius(3)
                .setHasPoints(true)
                .setFill(true)
                .setHasLabels(true)
                .setLabelColor(Color.parseColor("#33B5E5"));
        return line;
    }
}
