package com.example.zhan.heathmanage.Main.TrendFragment.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.beiing.leafchart.LeafLineChart;
import com.beiing.leafchart.OutsideLineChart;
import com.beiing.leafchart.bean.Axis;
import com.beiing.leafchart.bean.AxisValue;
import com.beiing.leafchart.bean.Line;
import com.beiing.leafchart.bean.PointValue;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.Imp.LineChartServiceDaoImp;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.Imp.WeekLineChartServiceDaoImp;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.LineChartServiceDao;
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
    private LeafLineChart weekheartrate_graph;
    private LeafLineChart weekbloodpressure_graph;
    private LeafLineChart weekbloodoxygen_graph;
    private LeafLineChart weekbloodfat_graph;
    private View view;
    WeekLineChartServiceDao weekLineChartServiceDao;
    LineChartServiceDao lineChartServiceDao;
    Axis axisX = new Axis(getAxisValuesX());
    Axis axisY = new Axis(getAxisValuesY());
    private ViewPager viewPager;
    private ViewGroup viewGroup;

    private List<View> viewList = new ArrayList<>();
    private PagerAdapter pagerAdapter;
    private ImageView imageView;
    private ImageView[] imageViews;

    public WeekFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_week, container, false);
        ButterKnife.bind(this, view);

        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewGroup = (ViewGroup) view.findViewById(R.id.viewGroup);
        weekLineChartServiceDao = new WeekLineChartServiceDaoImp();
        lineChartServiceDao=new LineChartServiceDaoImp();
        axisX.setAxisColor(Color.parseColor("#33B5E5")).setTextColor(Color.DKGRAY).setHasLines(false).setShowText(true);
        axisY.setAxisColor(Color.parseColor("#33B5E5")).setTextColor(Color.DKGRAY).setHasLines(false).setShowText(true);

        InitPageAdapter();
        initPointer();
        initEvent();
        return view;
    }

    private void initEvent() {
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new GuidePageChangeListener());
    }

    //初始化ViewPager
    private void InitPageAdapter() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View week_bloodpressure_view = inflater.inflate(R.layout.week_bloodpressure, null);
        View week_bloodfat_view = inflater.inflate(R.layout.week_bloodfat, null);
        View week_bloodoxygen_view = inflater.inflate(R.layout.week_bloodoxygen, null);
        View week_heartrate_view = inflater.inflate(R.layout.week_heartrate, null);
        weekbloodfat_graph = week_bloodfat_view.findViewById(R.id.weekbloodfat_graph);
        weekbloodoxygen_graph = week_bloodoxygen_view.findViewById(R.id.weekbloodoxygen_graph);
        weekbloodpressure_graph = week_bloodpressure_view.findViewById(R.id.weekbloodpressure_graph);
        weekheartrate_graph = week_heartrate_view.findViewById(R.id.weekheartrate_graph);
        initHeartRate();
        initBloodPressure();
        initBloodOxygen();
        initBloodFat();
        viewList.add(week_bloodpressure_view);
        viewList.add(week_heartrate_view);
        viewList.add(week_bloodfat_view);
        viewList.add(week_bloodoxygen_view);
        pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewList.get(position));
            }

            //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = viewList.get(position);
                container.addView(view);
                return view;
            }
        };

    }

    //加载心率折线图
    private void initHeartRate() {

        weekheartrate_graph.setAxisX(axisX);
        weekheartrate_graph.setAxisY(axisY);
        List<Line> lines = new ArrayList<>();
        lines.add(lineChartServiceDao.getHeartRateLine());
        weekheartrate_graph.setChartData(lines);
        weekheartrate_graph.showWithAnimation(3000);
    }

    //加载血压折线图
    private void initBloodPressure() {
        weekbloodpressure_graph.setAxisX(axisX);
        weekbloodpressure_graph.setAxisY(axisY);
        List<Line> lines = new ArrayList<>();
        lines.add(lineChartServiceDao.getDiastolicBPLine());
        lines.add(lineChartServiceDao.getSystolicBPLine());
        weekbloodpressure_graph.setChartData(lines);
        weekbloodpressure_graph.showWithAnimation(3000);
    }

    //加载体温折线图
    private void initBloodOxygen() {
        weekbloodoxygen_graph.setAxisX(axisX);
        weekbloodoxygen_graph.setAxisY(axisY);
        List<Line> lines = new ArrayList<>();
        lines.add(lineChartServiceDao.getBloodOxygenLine());
        weekbloodoxygen_graph.setChartData(lines);
        weekbloodoxygen_graph.showWithAnimation(3000);
    }

    //加载血脂折线图
    private void initBloodFat() {
        weekbloodfat_graph.setAxisX(axisX);
        weekbloodfat_graph.setAxisY(axisY);
        List<Line> lines = new ArrayList<>();
        lines.add(lineChartServiceDao.getBloodFatLine());
        weekbloodfat_graph.setChartData(lines);
        weekbloodfat_graph.showWithAnimation(3000);
    }

    //横坐标轴
    private List<AxisValue> getAxisValuesX() {
        String week[] = {" ","周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        List<AxisValue> axisValues = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            AxisValue value = new AxisValue();
            value.setLabel(week[i - 1]);
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
    private List<AxisValue> getAxisValuesY() {
        List<AxisValue> axisValues = new ArrayList<>();
        for (int i = 0; i <= 20; i = i + 2) {
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
//ViewPager的onPageChangeListener监听事件，当ViewPager的page页发生变化的时候调用
    public class GuidePageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        //页面滑动完成后执行
        @Override
        public void onPageSelected(int position) {
            //判断当前是在那个page，就把对应下标的ImageView原点设置为选中状态的图片
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[position].setBackgroundResource(R.drawable.page_indicator_focused);
                if (position != i) {
                    imageViews[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
                }
            }
        }

        //监听页面的状态，0--静止  1--滑动   2--滑动完成
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void initPointer() {
        imageViews = new ImageView[viewList.size()];
        for (int i = 0; i < imageViews.length; i++) {
            imageView = new ImageView(getContext());
            //设置控件的宽高
            imageView.setLayoutParams(new ViewGroup.LayoutParams(15, 15));
            //设置控件的padding属性
            imageView.setPadding(20, 0, 20, 0);
            imageViews[i] = imageView;
            //初始化第一个页面的原点的图片为选中状态
            if (i == 0) {
                imageViews[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                imageViews[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
            viewGroup.addView(imageViews[i]);
        }
    }
}
