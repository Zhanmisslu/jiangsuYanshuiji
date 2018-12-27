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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.beiing.leafchart.OutsideLineChart;
import com.beiing.leafchart.bean.Axis;
import com.beiing.leafchart.bean.AxisValue;
import com.beiing.leafchart.bean.Line;
import com.beiing.leafchart.bean.PointValue;
import com.example.zhan.heathmanage.BasicsTools.ChildViewPager;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.Imp.MonthLineChartServiceDaoImp;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.LineChartServiceDao;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.MonthLineChartServiceDao;
import com.example.zhan.heathmanage.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.zhan.heathmanage.Main.MainActivity.ev;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonthFragment extends Fragment {
    private OutsideLineChart heartrate_graph;
    private OutsideLineChart bloodpressure_graph;
    private OutsideLineChart bloodfat_graph;
    private OutsideLineChart bloodoxygen_graph;
    private ViewGroup month_viewGroup;
    private ChildViewPager month_viewPager;
    private LinearLayout month_heartrate_ll;
    private List<View> viewList = new ArrayList<>();
    private PagerAdapter pagerAdapter;
    private ImageView imageView;
    private ImageView[] imageViews;
    private View view;
    Axis axisX = new Axis(getAxisValuesX());
    Axis axisY = new Axis(getAxisValuesY());
    MonthLineChartServiceDao monthLineChartServiceDao;


    public MonthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_month, container, false);
        ButterKnife.bind(this, view);
        month_viewPager = (ChildViewPager) view.findViewById(R.id.month_viewPager);
        month_viewGroup = (ViewGroup) view.findViewById(R.id.month_viewGroup);
        axisX.setAxisColor(Color.parseColor("#33B5E5")).setTextColor(Color.DKGRAY).setHasLines(false).setShowText(true);
        axisY.setAxisColor(Color.parseColor("#33B5E5")).setTextColor(Color.DKGRAY).setHasLines(false).setShowText(true);
        monthLineChartServiceDao = new MonthLineChartServiceDaoImp();
        InitPageAdapter();
        initPointer();
        initEvent();
        getActivity().dispatchTouchEvent(ev);
        return view;
    }
    private void initEvent() {
        month_viewPager.setAdapter(pagerAdapter);
        month_viewPager.addOnPageChangeListener(new GuidePageChangeListener());
    }

    //初始化ViewPager
    private void InitPageAdapter() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View month_bloodpressure_view = inflater.inflate(R.layout.month_bloodpressure, null);
        View month_bloodfat_view = inflater.inflate(R.layout.month_bloodfat, null);
        View month_bloodoxygen_view = inflater.inflate(R.layout.month_bloodoxygen, null);
        View month_heartrate_view = inflater.inflate(R.layout.month_heartrate, null);
        bloodfat_graph = month_bloodfat_view.findViewById(R.id.bloodfat_graph);
        bloodoxygen_graph = month_bloodoxygen_view.findViewById(R.id.bloodoxygen_graph);
        bloodpressure_graph = month_bloodpressure_view.findViewById(R.id.bloodpressure_graph);
        heartrate_graph = month_heartrate_view.findViewById(R.id.heartrate_graph);
        month_heartrate_ll=month_heartrate_view.findViewById(R.id.month_heartrate_ll);

        initHeartRate();
        initBloodPressure();
        initBloodOxygen();
        initBloodFat();
        viewList.add(month_bloodpressure_view);
        viewList.add(month_heartrate_view);
        viewList.add(month_bloodfat_view);
        viewList.add(month_bloodoxygen_view);
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
    public void initHeartRate() {
        heartrate_graph.setAxisX(axisX);
        heartrate_graph.setAxisY(axisY);
        List<Line> lines = new ArrayList<>();
        lines.add(monthLineChartServiceDao.getHeartRateLine());
        heartrate_graph.setChartData(lines);
        heartrate_graph.showWithAnimation(3000);
    }

    //加载血压折线图
    public void initBloodPressure() {
        bloodpressure_graph.setAxisX(axisX);
        bloodpressure_graph.setAxisY(axisY);
        List<Line> lines = new ArrayList<>();
        lines.add(monthLineChartServiceDao.getDiastolicBPLine());
        lines.add(monthLineChartServiceDao.getSystolicBPLine());
        bloodpressure_graph.setChartData(lines);
        bloodpressure_graph.showWithAnimation(3000);
    }

    //加载血脂折线图
    public void initBloodFat() {
        bloodfat_graph.setAxisX(axisX);
        bloodfat_graph.setAxisY(axisY);
        List<Line> lines = new ArrayList<>();
        lines.add(monthLineChartServiceDao.getBloodFatLine());
        bloodfat_graph.setChartData(lines);
        bloodfat_graph.showWithAnimation(3000);
    }

    //加载血氧折线图
    public void initBloodOxygen() {
        bloodoxygen_graph.setAxisX(axisX);
        bloodoxygen_graph.setAxisY(axisY);
        List<Line> lines = new ArrayList<>();
        lines.add(monthLineChartServiceDao.getBloodOxygenLine());
        bloodoxygen_graph.setChartData(lines);
        bloodoxygen_graph.showWithAnimation(3000);
    }

    //横坐标轴
    public List<AxisValue> getAxisValuesX() {
        List<AxisValue> axisValues = new ArrayList<>();
        AxisValue value = new AxisValue();
        value.setLabel(" ");
        axisValues.add(value);
        for (int i = 1; i <= 31; i++) {
            value = new AxisValue();
            value.setLabel(i + "日");
            axisValues.add(value);
        }
        return axisValues;
    }

    //纵坐标轴
    public List<AxisValue> getAxisValuesY() {
        List<AxisValue> axisValues = new ArrayList<>();
        for (int i = 0; i <=20; i=i+2) {
            AxisValue value = new AxisValue();
            value.setLabel(String.valueOf(i * 10));
            axisValues.add(value);
        }
        return axisValues;
    }
    //ViewPager的onPageChangeListener监听事件，当ViewPager的page页发生变化的时候调用
    public class GuidePageChangeListener implements ChildViewPager.OnPageChangeListener {
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
            month_viewGroup.addView(imageViews[i]);
        }
    }
//
//    //心率曲线
//    public Line getHeartRateLine() {
//        List<PointValue> pointValues = new ArrayList<>();
//        for (int i = 1; i <= 31; i++) {
//            PointValue pointValue = new PointValue();
//            pointValue.setX((i - 1) / 30f);
//            int var = (int) (Math.random() * 100);
//            pointValue.setLabel(String.valueOf(var));
//            pointValue.setY(var / 100f);
//            pointValues.add(pointValue);
//        }
//        Line line = new Line(pointValues);
//        line.setLineColor(Color.parseColor("#33B5E5"))
//                .setLineWidth(3)
//                .setPointColor(Color.RED)
//                .setCubic(true)
//                .setPointRadius(3)
//                .setHasPoints(true)
//                .setFill(true)
//                .setHasLabels(true)
//                .setLabelColor(Color.parseColor("#33B5E5"));
//        return line;
//    }
//
//    //舒张压曲线
//    public Line getDiastolicBPLine() {
//        List<PointValue> pointValues = new ArrayList<>();
//        for (int i = 1; i <= 31; i++) {
//            PointValue pointValue = new PointValue();
//            pointValue.setX((i - 1) / 30f);
//            int var = (int) (Math.random() * 100);
//            pointValue.setLabel(String.valueOf(var));
//            pointValue.setY(var / 100f);
//            pointValues.add(pointValue);
//        }
//
//        Line line = new Line(pointValues);
//        line.setLineColor(Color.parseColor("#33B5E5"))
//                .setLineWidth(3)
//                .setPointColor(Color.RED)
//                .setCubic(true)
//                .setPointRadius(3)
//                .setHasPoints(true)
//                .setFill(true)
//                .setHasLabels(true)
//                .setLabelColor(Color.parseColor("#0033B5E5"));
//        return line;
//    }
//
//    //收缩压曲线
//    public Line getSystolicBPLine() {
//        List<PointValue> pointValues = new ArrayList<>();
//        for (int i = 1; i <= 31; i++) {
//            PointValue pointValue = new PointValue();
//            pointValue.setX((i - 1) / 30f);
//            int var = (int) (Math.random() * 100);
//            pointValue.setLabel(String.valueOf(var));
//            pointValue.setY(var / 100f);
//            pointValue.setShowLabel(true);
//            pointValues.add(pointValue);
//        }
//
//        Line line = new Line(pointValues);
//        line.setLineColor(Color.MAGENTA)
//                .setLineWidth(3)
//                .setPointColor(Color.MAGENTA)
//                .setCubic(true)
//                .setPointRadius(3)
//                .setFill(true)
//                .setHasLabels(true);
//        return line;
//    }
//
//    //体温曲线
//    public Line getTemperatureLine() {
//        List<PointValue> pointValues = new ArrayList<>();
//        for (int i = 1; i <= 31; i++) {
//            PointValue pointValue = new PointValue();
//            pointValue.setX((i - 1) / 30f);
//            int var = (int) (Math.random() * 100);
//            pointValue.setLabel(String.valueOf(var));
//            pointValue.setY(var / 100f);
//            pointValue.setShowLabel(true);
//            pointValues.add(pointValue);
//        }
//
//        Line line = new Line(pointValues);
//        line.setLineColor(Color.parseColor("#33B5E5"))
//                .setLineWidth(3)
//                .setPointColor(Color.RED)
//                .setCubic(true)
//                .setPointRadius(3)
//                .setHasPoints(true)
//                .setFill(true)
//                .setHasLabels(true)
//                .setLabelColor(Color.parseColor("#33B5E5"));
//        return line;
//    }
//
//    //血脂曲线
//    public Line getBloodFatLine() {
//        List<PointValue> pointValues = new ArrayList<>();
//        for (int i = 1; i <= 31; i++) {
//            PointValue pointValue = new PointValue();
//            pointValue.setX((i - 1) / 30f);
//            int var = (int) (Math.random() * 100);
//            pointValue.setLabel(String.valueOf(var));
//            pointValue.setY(var / 100f);
//            pointValue.setShowLabel(true);
//            pointValues.add(pointValue);
//        }
//
//        Line line = new Line(pointValues);
//        line.setLineColor(Color.parseColor("#33B5E5"))
//                .setLineWidth(3)
//                .setPointColor(Color.RED)
//                .setCubic(true)
//                .setPointRadius(3)
//                .setHasPoints(true)
//                .setFill(true)
//                .setHasLabels(true)
//                .setLabelColor(Color.parseColor("#33B5E5"));
//        return line;
//    }
}
