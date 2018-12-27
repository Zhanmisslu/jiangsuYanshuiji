package com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.Imp;

import android.graphics.Color;

import com.beiing.leafchart.bean.Line;
import com.beiing.leafchart.bean.PointValue;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.WeekLineChartServiceDao;

import java.util.ArrayList;
import java.util.List;

public class WeekLineChartServiceDaoImp implements WeekLineChartServiceDao {
    //心率曲线
    @Override
    public Line getHeartRateLine() {
        List<PointValue> pointValues = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            PointValue pointValue = new PointValue();
            pointValue.setX( (i - 1) / 6f);
            int var = (int) (Math.random() * 100);
            pointValue.setLabel(String.valueOf(var));
            pointValue.setY(var / 190f);
            pointValue.setShowLabel(true);
            pointValues.add(pointValue);
        }
        Line line = new Line(pointValues);
        line.setLineColor(Color.parseColor("#33B5E5"))
                .setLineWidth(3)
                .setPointColor(Color.RED)//点的颜色
                .setPointRadius(3)//
                .setCubic(true)//设置是曲线还是折线
                .setHasPoints(true)
                .setFill(false)
                .setHasLabels(true)
                .setLabelColor(Color.parseColor("#33B5E5"));
        return line;
    }

    @Override
    public Line getDiastolicBPLine() {
        List<PointValue> pointValues = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            PointValue pointValue = new PointValue();
            pointValue.setX( (i - 1) / 6f);
            int var = (int) (Math.random() * 100);
            pointValue.setLabel(String.valueOf(var));
            pointValue.setY(var / 190f);
            pointValue.setShowLabel(true);
            pointValues.add(pointValue);
        }

        Line line = new Line(pointValues);
        line.setLineColor(Color.parseColor("#33B5E5"))
                .setLineWidth(3)
                .setPointColor(Color.YELLOW)
                .setCubic(true)
                .setPointRadius(3)
                .setFill(false)
                .setHasLabels(true)
                .setLabelColor(Color.parseColor("#33B5E5"));
        return line;
    }

    @Override
    public Line getSystolicBPLine() {
        List<PointValue> pointValues = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            PointValue pointValue = new PointValue();
            pointValue.setX( (i - 1) / 6f);
            int var = (int) (Math.random() * 100);
            pointValue.setLabel(String.valueOf(var));
            pointValue.setY(var / 190f);
            pointValue.setShowLabel(true);
            pointValues.add(pointValue);
        }

        Line line = new Line(pointValues);
        line.setLineColor(Color.MAGENTA)
                .setLineWidth(3)
                .setPointColor(Color.RED)//点的颜色
                .setPointRadius(3)//
                .setCubic(true)//设置是曲线还是折线
                .setHasPoints(true)
                .setFill(false)
                .setHasLabels(true)
                .setLabelColor(Color.parseColor("#33B5E5"));
        return line;
    }

    @Override
    public Line getBloodFatLine() {
        List<PointValue> pointValues = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            PointValue pointValue = new PointValue();
            pointValue.setX( (i - 1) / 6f);
            int var = (int) (Math.random() * 100);
            pointValue.setLabel(String.valueOf(var));
            pointValue.setY(var / 190f);
            pointValue.setShowLabel(true);
            pointValues.add(pointValue);
        }
        Line line = new Line(pointValues);
        line.setLineColor(Color.parseColor("#33B5E5"))
                .setLineWidth(3)
                .setPointColor(Color.RED)//点的颜色
                .setPointRadius(3)//
                .setCubic(true)//设置是曲线还是折线
                .setHasPoints(true)
                .setFill(false)
                .setHasLabels(true)
                .setLabelColor(Color.parseColor("#33B5E5"));
        return line;
    }

    @Override
    public Line getBloodOxygenLine() {
        List<PointValue> pointValues = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            PointValue pointValue = new PointValue();
            pointValue.setX( (i - 1) / 6f);
            int var = (int) (Math.random() * 100);
            pointValue.setLabel(String.valueOf(var));
            pointValue.setY(var / 190f);
            pointValue.setShowLabel(true);
            pointValues.add(pointValue);
        }
        Line line = new Line(pointValues);
        line.setLineColor(Color.parseColor("#33B5E5"))
                .setLineWidth(3)
                .setPointColor(Color.RED)//点的颜色
                .setPointRadius(3)//
                .setCubic(true)//设置是曲线还是折线
                .setHasPoints(true)
                .setFill(false)
                .setHasLabels(true)
                .setLabelColor(Color.parseColor("#33B5E5"));
        return line;
    }
//心率纵坐标
    @Override
    public void getHeartRateValuesY() {

    }
}
