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
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.Imp.WeekLineChartServiceDaoImp;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.WeekLineChartServiceDao;
import com.example.zhan.heathmanage.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeekFragment extends Fragment {
    @BindView(R.id.weekheartrate_graph)
    LeafLineChart weekheartrate_graph;
    @BindView(R.id.weekbloodpressure_graph)
    LeafLineChart weekbloodpressure_graph;
    @BindView(R.id.weektemperature_graph)
    LeafLineChart weektemperature_graph;
    @BindView(R.id.weekbloodfat_graph)
    LeafLineChart weekbloodfat_graph;
    private View view;
    WeekLineChartServiceDao weekLineChartServiceDao;
    Axis axisX = new Axis(getAxisValuesX());
    Axis axisY = new Axis(getAxisValuesY());

    public WeekFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view=inflater.inflate(R.layout.fragment_week, container, false);
        ButterKnife.bind(this,view);
        weekLineChartServiceDao=new WeekLineChartServiceDaoImp();
        axisX.setAxisColor(Color.parseColor("#33B5E5")).setTextColor(Color.DKGRAY).setHasLines(false).setShowText(true);
        axisY.setAxisColor(Color.parseColor("#33B5E5")).setTextColor(Color.DKGRAY).setHasLines(false).setShowText(true);
        initHeartRate();
        initBloodPressure();
        initBloodOxygen();
        initBloodFat();
       return view;
    }
    //加载心率折线图
    private void initHeartRate(){

        weekheartrate_graph.setAxisX(axisX);
        weekheartrate_graph.setAxisY(axisY);
        List<Line> lines = new ArrayList<>();
        lines.add(weekLineChartServiceDao.getHeartRateLine());
        weekheartrate_graph.setChartData(lines);
        weekheartrate_graph.showWithAnimation(3000);
    }
    //加载血压折线图
    private void initBloodPressure(){
        weekbloodpressure_graph.setAxisX(axisX);
        weekbloodpressure_graph.setAxisY(axisY);
        List<Line> lines = new ArrayList<>();
        lines.add(weekLineChartServiceDao.getDiastolicBPLine());
        lines.add(weekLineChartServiceDao.getSystolicBPLine());
        weekbloodpressure_graph.setChartData(lines);
        weekbloodpressure_graph.showWithAnimation(3000);
    }
    //加载体温折线图
    private void initBloodOxygen(){
        weektemperature_graph.setAxisX(axisX);
        weektemperature_graph.setAxisY(axisY);
        List<Line> lines = new ArrayList<>();
        lines.add(weekLineChartServiceDao.getBloodOxygenLine());
        weektemperature_graph.setChartData(lines);
        weektemperature_graph.showWithAnimation(3000);
    }
    //加载血脂折线图
    private void initBloodFat(){
        weekbloodfat_graph.setAxisX(axisX);
        weekbloodfat_graph.setAxisY(axisY);
        List<Line> lines = new ArrayList<>();
        lines.add(weekLineChartServiceDao.getBloodFatLine());
        weekbloodfat_graph.setChartData(lines);
        weekbloodfat_graph.showWithAnimation(3000);
    }
    //横坐标轴
    private List<AxisValue> getAxisValuesX(){
        String week[]={"周日","周一","周二","周三","周四","周五","周六"};
        List<AxisValue> axisValues = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            AxisValue value = new AxisValue();
            value.setLabel(week[i-1]);
            axisValues.add(value);
        }
        return axisValues;
    }
//    //心率纵坐标轴
//    private List<AxisValue> getHeartRateAxisValuesY(){
//        List<AxisValue> axisValues = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            AxisValue value = new AxisValue();
//            value.setLabel(String.valueOf(i * 10));
//            axisValues.add(value);
//        }
//        return axisValues;
//    }
    //心率纵坐标轴
    private List<AxisValue> getAxisValuesY(){
        List<AxisValue> axisValues = new ArrayList<>();
        for (int i = 0; i <20; i=i+2) {
            AxisValue value = new AxisValue();
            value.setLabel(String.valueOf(i * 10));
            axisValues.add(value);
        }
        return axisValues;
    }
//    //心率曲线
//    private Line getHeartRateLine(){
//        List<PointValue> pointValues = new ArrayList<>();
//        for (int i = 1; i <= 7; i++) {
//            PointValue pointValue = new PointValue();
//            pointValue.setX( (i - 1) / 6f);
//            int var = (int) (Math.random() * 100);
//            pointValue.setLabel(String.valueOf(var));
//            pointValue.setY(var / 190f);
//            pointValue.setShowLabel(true);
//            pointValues.add(pointValue);
//        }
//        Line line = new Line(pointValues);
//        line.setLineColor(Color.parseColor("#33B5E5"))
//                .setLineWidth(3)
//                .setPointColor(Color.RED)//点的颜色
//                .setPointRadius(3)//
//                .setCubic(true)//设置是曲线还是折线
//                .setHasPoints(true)
//                .setFill(false)
//                .setHasLabels(true)
//                .setLabelColor(Color.parseColor("#33B5E5"));
//        return line;
//    }
//    //舒张压曲线
//    private Line getDiastolicBPLine(){
//        List<PointValue> pointValues = new ArrayList<>();
//        for (int i = 1; i <= 7; i++) {
//            PointValue pointValue = new PointValue();
//            pointValue.setX( (i - 1) / 6f);
//            int var = (int) (Math.random() * 100);
//            pointValue.setLabel(String.valueOf(var));
//            pointValue.setY(var / 190f);
//            pointValue.setShowLabel(true);
//            pointValues.add(pointValue);
//        }
//
//        Line line = new Line(pointValues);
//        line.setLineColor(Color.parseColor("#33B5E5"))
//                .setLineWidth(3)
//                .setPointColor(Color.YELLOW)
//                .setCubic(true)
//                .setPointRadius(3)
//                .setFill(false)
//                .setHasLabels(true)
//                .setLabelColor(Color.parseColor("#33B5E5"));
//        return line;
//    }
//    //收缩压曲线
//    private Line getSystolicBPLine(){
//        List<PointValue> pointValues = new ArrayList<>();
//        for (int i = 1; i <= 7; i++) {
//            PointValue pointValue = new PointValue();
//            pointValue.setX( (i - 1) / 6f);
//            int var = (int) (Math.random() * 100);
//            pointValue.setLabel(String.valueOf(var));
//            pointValue.setY(var / 190f);
//            pointValue.setShowLabel(true);
//            pointValues.add(pointValue);
//        }
//
//        Line line = new Line(pointValues);
//        line.setLineColor(Color.MAGENTA)
//                .setLineWidth(3)
//                .setPointColor(Color.RED)//点的颜色
//                .setPointRadius(3)//
//                .setCubic(true)//设置是曲线还是折线
//                .setHasPoints(true)
//                .setFill(false)
//                .setHasLabels(true)
//                .setLabelColor(Color.parseColor("#33B5E5"));
//        return line;
//    }
//    //体温曲线
//    private Line getTemperatureLine(){
//        List<PointValue> pointValues = new ArrayList<>();
//        for (int i = 1; i <= 7; i++) {
//            PointValue pointValue = new PointValue();
//            pointValue.setX( (i - 1) / 6f);
//            int var = (int) (Math.random() * 100);
//            pointValue.setLabel(String.valueOf(var));
//            pointValue.setY(var / 190f);
//            pointValue.setShowLabel(true);
//            pointValues.add(pointValue);
//        }
//        Line line = new Line(pointValues);
//        line.setLineColor(Color.parseColor("#33B5E5"))
//                .setLineWidth(3)
//                .setPointColor(Color.RED)//点的颜色
//                .setPointRadius(3)//
//                .setCubic(true)//设置是曲线还是折线
//                .setHasPoints(true)
//                .setFill(false)
//                .setHasLabels(true)
//                .setLabelColor(Color.parseColor("#33B5E5"));
//        return line;
//    }
//    //血脂曲线
//    private Line getBloodFatLine(){
//        List<PointValue> pointValues = new ArrayList<>();
//        for (int i = 1; i <= 7; i++) {
//            PointValue pointValue = new PointValue();
//            pointValue.setX( (i - 1) / 6f);
//            int var = (int) (Math.random() * 100);
//            pointValue.setLabel(String.valueOf(var));
//            pointValue.setY(var / 190f);
//            pointValue.setShowLabel(true);
//            pointValues.add(pointValue);
//        }
//        Line line = new Line(pointValues);
//        line.setLineColor(Color.parseColor("#33B5E5"))
//                .setLineWidth(3)
//                .setPointColor(Color.RED)//点的颜色
//                .setPointRadius(3)//
//                .setCubic(true)//设置是曲线还是折线
//                .setHasPoints(true)
//                .setFill(false)
//                .setHasLabels(true)
//                .setLabelColor(Color.parseColor("#33B5E5"));
//        return line;
//    }
}
