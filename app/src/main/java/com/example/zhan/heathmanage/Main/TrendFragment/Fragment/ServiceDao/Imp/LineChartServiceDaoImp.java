package com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.Imp;

import android.graphics.Color;

import com.beiing.leafchart.bean.Axis;
import com.beiing.leafchart.bean.AxisValue;
import com.beiing.leafchart.bean.Line;
import com.beiing.leafchart.bean.PointValue;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.LineChartServiceDao;

import java.util.ArrayList;
import java.util.List;

public class LineChartServiceDaoImp implements LineChartServiceDao {
    @Override
    public Line getHeartRateLine() {
        List<PointValue> pointValues = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            PointValue pointValue = new PointValue();
            pointValue.setX( i / 7f);
            int var = (int) (Math.random() * 100);
            pointValue.setLabel(String.valueOf(var));
            pointValue.setY(var / 200f);
            pointValue.setShowLabel(true);
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
    public Line getDiastolicBPLine() {
        List<PointValue> pointValues = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            PointValue pointValue = new PointValue();
            pointValue.setX( i / 7f);
            int var = (int) (Math.random() * 100);
            pointValue.setLabel(String.valueOf(var));
            pointValue.setY(var / 200f);
            pointValue.setShowLabel(true);
            pointValues.add(pointValue);
        }

        Line line = new Line(pointValues);
        line.setLineColor(Color.parseColor("#33B5E5"))
                .setLineWidth(3)
                .setPointColor(Color.parseColor("#FF00BAFF"))
                .setCubic(true)
                .setPointRadius(3)
                .setFill(false)
                .setHasPoints(true)
                .setHasLabels(true)
                .setLabelColor(Color.parseColor("#FF000000"));
        return line;
    }

    @Override
    public Line getSystolicBPLine() {
        List<PointValue> pointValues = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            PointValue pointValue = new PointValue();
            pointValue.setX( i  / 7f);
            int var = (int) (Math.random() * 100);
            pointValue.setLabel(String.valueOf(var));
            pointValue.setY(var / 200f);
            pointValue.setShowLabel(true);
            pointValues.add(pointValue);
        }

        Line line = new Line(pointValues);
        line.setLineColor(Color.MAGENTA)
                .setLineWidth(3)
                .setPointColor(Color.parseColor("#FFFF0095"))//点的颜色
                .setPointRadius(3)//
                .setCubic(true)//设置是曲线还是折线
                .setHasPoints(true)
                .setFill(false)
                .setHasLabels(true)
                .setLabelColor(Color.parseColor("#FF000000"));
        return line;
    }

    @Override
    public Line getBloodFatLine() {
        List<PointValue> pointValues = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            PointValue pointValue = new PointValue();
            pointValue.setX( i / 7f);
            int var = (int) (Math.random() * 100);
            pointValue.setLabel(String.valueOf(var));
            pointValue.setY(var / 200f);
            pointValue.setShowLabel(true);
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
    public Line getBloodOxygenLine() {

        List<PointValue> pointValues = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            PointValue pointValue = new PointValue();
            pointValue.setX( i  / 7f);
            int var = (int) (Math.random() * 100);
            pointValue.setLabel(String.valueOf(var));
            pointValue.setY(var / 200f);
            pointValue.setShowLabel(true);
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
}
