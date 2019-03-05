package com.example.zhan.heathmanage.Main.TrendFragment.Fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
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
import com.beiing.leafchart.bean.SlidingLine;
import com.example.zhan.heathmanage.BasicsTools.ChildViewPager;
import com.example.zhan.heathmanage.Main.TrendFragment.Bean.MonthInfo;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.Imp.MonthLineChartServiceDaoImp;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.LineChartServiceDao;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.MonthLineChartServiceDao;
import com.example.zhan.heathmanage.Main.TrendFragment.TrendFragment;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;
import com.zyao89.view.zloading.ZLoadingDialog;


import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.zhan.heathmanage.Main.MainActivity.ev;
import static com.zyao89.view.zloading.Z_TYPE.DOUBLE_CIRCLE;
import static com.zyao89.view.zloading.Z_TYPE.STAR_LOADING;

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
    Handler handler;
    Handler handler1;
    View month_bloodpressure_view;
    View month_bloodfat_view;
    View month_bloodoxygen_view;
    View month_heartrate_view;
    Axis axisX;
    Axis axisY;
    List<MonthInfo> monthInfoList;
    MonthLineChartServiceDao monthLineChartServiceDao;
    String Date;
    String month;
    String year;
    ZLoadingDialog dialog;
    List<Line> lines;
    public MonthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_month, container, false);
        ButterKnife.bind(this, view);
        handler = new Handler();
        handler1=new Handler();
        month_viewPager = (ChildViewPager) view.findViewById(R.id.month_viewPager);
        month_viewGroup = (ViewGroup) view.findViewById(R.id.month_viewGroup);
        monthLineChartServiceDao = new MonthLineChartServiceDaoImp(this);
        Date = TrendFragment.nDate;
        year = Date.substring(0, 4);
        month = TrendFragment.Month;

        dialog = new ZLoadingDialog(getActivity());

        Init();
        InitView();
        initPointer();
        getActivity().dispatchTouchEvent(ev);
        initEvent();
        monthLineChartServiceDao.getSuggest(MyApplication.getUserPhone(), year, month);
        //InitPageAdapter();
        //InitView();


//
//        String str = "2007-01-18";
//        String a;
//        a = str.substring(5, str.length());
//        Log.v("zjc", a);
        return view;
    }

    public void Init() {
        dialog.setLoadingBuilder(STAR_LOADING)
                .setLoadingColor(Color.parseColor("#ff5305"))
                .setHintText("正在加载中...")
//                                .setHintTextSize(16) // 设置字体大小
                .setHintTextColor(Color.GRAY)  // 设置字体颜色
//                                .setDurationTime(0.5) // 设置动画时间百分比

                .setDialogBackgroundColor(Color.parseColor("#cc111111")) // 设置背景色
                .show();
    }

    public void InitView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        month_bloodpressure_view = inflater.inflate(R.layout.month_bloodpressure, null);
        month_bloodfat_view = inflater.inflate(R.layout.month_bloodfat, null);
        month_bloodoxygen_view = inflater.inflate(R.layout.month_bloodoxygen, null);
        month_heartrate_view = inflater.inflate(R.layout.month_heartrate, null);
        bloodpressure_graph = month_bloodpressure_view.findViewById(R.id.bloodpressure_graph);
        heartrate_graph = month_heartrate_view.findViewById(R.id.heartrate_graph);
        bloodfat_graph = month_bloodfat_view.findViewById(R.id.bloodfat_graph);
        bloodoxygen_graph = month_bloodoxygen_view.findViewById(R.id.bloodoxygen_graph);
       // month_heartrate_ll = month_heartrate_view.findViewById(R.id.month_heartrate_ll);
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

    private void initEvent() {
        month_viewPager.setAdapter(pagerAdapter);
        month_viewPager.addOnPageChangeListener(new GuidePageChangeListener());
    }

    Runnable runnableUi = new Runnable() {
        @Override
        public void run() {
            //更新界面
            bloodpressure_graph.setAxisX(axisX);
            bloodpressure_graph.setAxisY(axisY);
            heartrate_graph.setAxisX(axisX);
            heartrate_graph.setAxisY(axisY);
            bloodfat_graph.setAxisX(axisX);
            bloodfat_graph.setAxisY(axisY);
            bloodoxygen_graph.setAxisX(axisX);
            bloodoxygen_graph.setAxisY(axisY);
        }

    };
    Runnable runnableui = new Runnable() {
        @Override
        public void run() {

            //更新界面
            bloodpressure_graph.showWithAnimation(3000);
            heartrate_graph.showWithAnimation(3000);
            bloodfat_graph.showWithAnimation(3000);
            bloodoxygen_graph.showWithAnimation(3000);

        }

    };
    //初始化ViewPager
    public void InitPageAdapter(final List<MonthInfo> monthInfoList) {

      //  this.monthInfoList= monthInfoList;
        axisX = new Axis(getAxisValuesX(monthInfoList));
        axisY = new Axis(getAxisValuesY());
        axisX.setAxisColor(Color.parseColor("#33B5E5")).setTextColor(Color.DKGRAY).setHasLines(false).setShowText(true);
        axisY.setAxisColor(Color.parseColor("#e9e9e9")).setTextColor(Color.DKGRAY).setHasLines(false).setShowText(false);
        handler.post(runnableUi);

//        initHeartRate(monthInfoList);
//        initBloodPressure(monthInfoList);
//        initBloodOxygen(monthInfoList);
//        initBloodFat(monthInfoList);
        lines = new ArrayList<>();
        lines.add(monthLineChartServiceDao.getDiastolicBPLine(monthInfoList));
        lines.add(monthLineChartServiceDao.getSystolicBPLine(monthInfoList));
        bloodpressure_graph.setChartData(lines);
        lines = new ArrayList<>();
        lines.add(monthLineChartServiceDao.getHeartRateLine(monthInfoList));
        heartrate_graph.setChartData(lines);

        lines = new ArrayList<>();
        lines.add(monthLineChartServiceDao.getBloodFatLine(monthInfoList));
        bloodfat_graph.setChartData(lines);
        lines = new ArrayList<>();
        lines.add(monthLineChartServiceDao.getBloodOxygenLine(monthInfoList));
        bloodoxygen_graph.setChartData(lines);
        handler.post(runnableui);
        dialog.dismiss();

    }

    //加载心率折线图
    public void initHeartRate(final List<MonthInfo> monthInfoList) {

        List<Line> lines = new ArrayList<>();
        lines.add(monthLineChartServiceDao.getHeartRateLine(monthInfoList));
        heartrate_graph.setChartData(lines);
        //  heartrate_graph.setSlidingLine(getSlideingLine());
//                 heartrate_graph.showWithAnimation(3000);

    }

    //加载血压折线图
    public void initBloodPressure(final List<MonthInfo> monthInfoList) {
        List<Line> lines = new ArrayList<>();
        lines.add(monthLineChartServiceDao.getDiastolicBPLine(monthInfoList));
        lines.add(monthLineChartServiceDao.getSystolicBPLine(monthInfoList));
        bloodpressure_graph.setChartData(lines);



        //  bloodpressure_graph.setSlidingLine(getSlideingLine());
//         bloodpressure_graph.showWithAnimation(3000);
    }

    //加载血脂折线图
    public void initBloodFat(final List<MonthInfo> monthInfoList) {


        List<Line> lines = new ArrayList<>();
        lines.add(monthLineChartServiceDao.getBloodFatLine(monthInfoList));
        bloodfat_graph.setChartData(lines);


        //  bloodfat_graph.setSlidingLine(getSlideingLine());
        // bloodfat_graph.showWithAnimation(3000);
    }

    //加载血氧折线图
    public void initBloodOxygen(final List<MonthInfo> monthInfoList) {


        List<Line> lines = new ArrayList<>();
        lines.add(monthLineChartServiceDao.getBloodOxygenLine(monthInfoList));
        bloodoxygen_graph.setChartData(lines);


        //   bloodoxygen_graph.setSlidingLine(getSlideingLine());
        // bloodoxygen_graph.showWithAnimation(3000);
    }

    //横坐标轴
    public List<AxisValue> getAxisValuesX(List<MonthInfo> monthInfoList) {
        int length = monthInfoList.size();
        List<AxisValue> axisValues = new ArrayList<>();
        AxisValue value;

        for (int i = 0; i < length; i++) {
            value = new AxisValue();
            value.setLabel(" "+" "+monthInfoList.get(i).getDate()+" "+" ");
            axisValues.add(value);
        }
        return axisValues;
    }

    //纵坐标轴
    public List<AxisValue> getAxisValuesY() {
        List<AxisValue> axisValues = new ArrayList<>();
        for (int i = 0; i <= 20; i = i + 2) {
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
            if (state == 0) {
                Log.v("页面状态:静止", String.valueOf(state));
            } else if (state == 1) {
                Log.v("页面状态:滑动", String.valueOf(state));
            } else if (state == 2) {
                Log.v("页面状态:滑动完成", String.valueOf(state));
            }
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
//    private SlidingLine getSlideingLine(){
//        SlidingLine slidingLine = new SlidingLine();
//        slidingLine.setSlideLineColor(Color.parseColor("#00000000"))
//                .setSlidePointColor(getResources().getColor(R.color.colorAccent))
//                .setSlidePointRadius(3);
//        return slidingLine;
//    }
//
//    //心率曲线
    public Line getHeartRateLine(final List<MonthInfo> monthInfoList) {
        int length=monthInfoList.size();
        float a=length;
        List<PointValue> pointValues = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            PointValue pointValue = new PointValue();
            pointValue.setX((i)/ (a-1));
            float var = Float.parseFloat(monthInfoList.get(i).getHeartRate());
            pointValue.setLabel(String.valueOf(var));
            pointValue.setY(var / 100f);
            pointValue.setShowLabel(false);
            pointValues.add(pointValue);
        }
        Line line = new Line(pointValues);
        line.setLineColor(Color.parseColor("#FF18CC15"))
                .setLineWidth(3)
                .setPointColor(Color.parseColor("#FFFFFFFF"))
                .setCubic(true)
                .setPointRadius(3)
                .setHasPoints(true)
                .setFill(true)
                .setFillColor(Color.parseColor("#FF0AE906"))
                .setHasLabels(true)
                .setLabelColor(Color.parseColor("#00E9E9E9"));

        return line;
    }
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
