package com.example.zhan.heathmanage.Main.TrendFragment.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beiing.leafchart.LeafLineChart;
import com.beiing.leafchart.OutsideLineChart;
import com.beiing.leafchart.bean.Axis;
import com.beiing.leafchart.bean.AxisValue;
import com.beiing.leafchart.bean.Line;
import com.beiing.leafchart.bean.PointValue;
import com.example.zhan.heathmanage.Main.TrendFragment.TrendFragment;
import com.example.zhan.heathmanage.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonthFragment extends Fragment {
    public @BindView(R.id.heartrate_graph)
    OutsideLineChart heartrate_graph;
    @BindView(R.id.bloodpressure_graph)
    OutsideLineChart bloodpressure_graph;
    @BindView(R.id.bloodfat_graph)
    OutsideLineChart bloodfat_graph;
    @BindView(R.id.temperature_graph)
    OutsideLineChart temperature_graph;
    private View view;
    Axis axisX = new Axis(getAxisValuesX());
    Axis axisY = new Axis(getAxisValuesY());

    public MonthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_month, container, false);
        ButterKnife.bind(this, view);
        axisX.setAxisColor(Color.parseColor("#33B5E5")).setTextColor(Color.DKGRAY).setHasLines(false).setShowText(true);
        axisY.setAxisColor(Color.parseColor("#33B5E5")).setTextColor(Color.DKGRAY).setHasLines(false).setShowText(true);

        initHeartRate();
        initBloodPressure();
        initBloodFat();
        initTemperature();
        return view;
    }
    public void judgeTime(){
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        final Date date1 = new Date(System.currentTimeMillis());

        if(!simpleDateFormat.format(date1).equals(TrendFragment.nDate)){
            initHeartRate();
            initBloodPressure();
            initBloodFat();
            initTemperature();
        }
    }
    //加载心率折线图
    public void initHeartRate() {
//        Axis axisX = new Axis(getAxisValuesX());
//        Axis axisY = new Axis(getAxisValuesY());
//        axisX.setAxisColor(Color.parseColor("#00000000")).setTextColor(Color.DKGRAY).setHasLines(true);
//        axisY.setAxisColor(Color.parseColor("#33B5E5")).setTextColor(Color.DKGRAY).setHasLines(false).setShowText(true);
        //view = getLayoutInflater().inflate( R.layout.fragment_month, null);
        //view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_month,null);
//        heartrate_graph=view.findViewById(R.id.heartrate_graph);
        heartrate_graph.setAxisX(axisX);
        heartrate_graph.setAxisY(axisY);
        List<Line> lines = new ArrayList<>();
        lines.add(getHeartRateLine());
        heartrate_graph.setChartData(lines);
        heartrate_graph.showWithAnimation(3000);
    }

    //加载血压折线图
    public void initBloodPressure() {
        bloodpressure_graph.setAxisX(axisX);
        bloodpressure_graph.setAxisY(axisY);
        List<Line> lines = new ArrayList<>();
        lines.add(getDiastolicBPLine());
        lines.add(getSystolicBPLine());
        bloodpressure_graph.setChartData(lines);
        bloodpressure_graph.showWithAnimation(3000);
    }

    //加载血脂折线图
    public void initBloodFat() {
        bloodfat_graph.setAxisX(axisX);
        bloodfat_graph.setAxisY(axisY);
        List<Line> lines = new ArrayList<>();
        lines.add(getBloodFatLine());
        bloodfat_graph.setChartData(lines);
        bloodfat_graph.showWithAnimation(3000);
    }

    //加载体温折线图
    public void initTemperature() {
        temperature_graph.setAxisX(axisX);
        temperature_graph.setAxisY(axisY);
        List<Line> lines = new ArrayList<>();
        lines.add(getTemperatureLine());
        temperature_graph.setChartData(lines);
        temperature_graph.showWithAnimation(3000);
    }

    //横坐标轴
    public List<AxisValue> getAxisValuesX() {
        List<AxisValue> axisValues = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            AxisValue value = new AxisValue();
            value.setLabel(i + "日");
            axisValues.add(value);
        }
        return axisValues;
    }

    //纵坐标轴
    public List<AxisValue> getAxisValuesY() {
        List<AxisValue> axisValues = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            AxisValue value = new AxisValue();
            value.setLabel(String.valueOf(i * 10));
            axisValues.add(value);
        }
        return axisValues;
    }

    //心率曲线
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

    //舒张压曲线
    public Line getDiastolicBPLine() {
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
                .setLabelColor(Color.parseColor("#0033B5E5"));
        return line;
    }

    //收缩压曲线
    public Line getSystolicBPLine() {
        List<PointValue> pointValues = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            PointValue pointValue = new PointValue();
            pointValue.setX((i - 1) / 30f);
            int var = (int) (Math.random() * 100);
            pointValue.setLabel(String.valueOf(var));
            pointValue.setY(var / 100f);
            pointValue.setShowLabel(true);
            pointValues.add(pointValue);
        }

        Line line = new Line(pointValues);
        line.setLineColor(Color.MAGENTA)
                .setLineWidth(3)
                .setPointColor(Color.MAGENTA)
                .setCubic(true)
                .setPointRadius(3)
                .setFill(true)
                .setHasLabels(true);
        return line;
    }

    //体温曲线
    public Line getTemperatureLine() {
        List<PointValue> pointValues = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            PointValue pointValue = new PointValue();
            pointValue.setX((i - 1) / 30f);
            int var = (int) (Math.random() * 100);
            pointValue.setLabel(String.valueOf(var));
            pointValue.setY(var / 100f);
            pointValue.setShowLabel(true);
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

    //血脂曲线
    public Line getBloodFatLine() {
        List<PointValue> pointValues = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            PointValue pointValue = new PointValue();
            pointValue.setX((i - 1) / 30f);
            int var = (int) (Math.random() * 100);
            pointValue.setLabel(String.valueOf(var));
            pointValue.setY(var / 100f);
            pointValue.setShowLabel(true);
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
