package com.example.zhan.heathmanage.Main.TrendFragment.Fragment;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
import com.example.zhan.heathmanage.Main.TrendFragment.manage.LineChartManager;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.zyao89.view.zloading.ZLoadingDialog;



import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.zhan.heathmanage.Main.MainActivity.ev;

import static com.zyao89.view.zloading.Z_TYPE.STAR_LOADING;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonthFragment extends Fragment {
    @BindView(R.id.fragment_month_ll) LinearLayout fragment_month_ll;
    @BindView(R.id.monthnodata_ll)LinearLayout monthnodata_ll;
    private LineChart heartrate_graph;
    private LineChart bloodpressure_graph;

    private LineChart bloodoxygen_graph;
    private ViewGroup month_viewGroup;
    private ChildViewPager month_viewPager;

    private List<View> viewList = new ArrayList<>();
    private PagerAdapter pagerAdapter;
    private ImageView imageView;
    private ImageView[] imageViews;
    private View view;
    Handler handler;
    private LineChartManager lineChartManager;
    private LineChartManager BloodOxygenlineChartManager;
    private LineChartManager HeartratelineChartManager;
    View month_bloodpressure_view;
    View month_bloodoxygen_view;
    View month_heartrate_view;
    private Legend legend;              //图例
    List<MonthInfo> monthInfoList;
    MonthLineChartServiceDao monthLineChartServiceDao;
    String Date;
    String month;
    String year;
    ZLoadingDialog dialog;

    Drawable drawable;
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

        month_viewPager = (ChildViewPager) view.findViewById(R.id.month_viewPager);
        month_viewGroup = (ViewGroup) view.findViewById(R.id.month_viewGroup);
        monthLineChartServiceDao = new MonthLineChartServiceDaoImp(this);
        Date = TrendFragment.nDate;
        year = Date.substring(0, 4);
        month = TrendFragment.Month;
        drawable = getResources().getDrawable(R.drawable.fade_blue);
        dialog = new ZLoadingDialog(getActivity());

        Init();
        InitView();
        initPointer();
        getActivity().dispatchTouchEvent(ev);
        initEvent();
        monthLineChartServiceDao.getSuggest(MyApplication.getUserPhone(), year, month);
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
        month_bloodoxygen_view = inflater.inflate(R.layout.month_bloodoxygen, null);
        month_heartrate_view = inflater.inflate(R.layout.month_heartrate, null);
        bloodpressure_graph = month_bloodpressure_view.findViewById(R.id.bloodpressure_graph);
        lineChartManager=new LineChartManager(bloodpressure_graph);
        bloodoxygen_graph = month_bloodoxygen_view.findViewById(R.id.bloodoxygen_graph);
        BloodOxygenlineChartManager=new LineChartManager(bloodoxygen_graph);
        heartrate_graph = month_heartrate_view.findViewById(R.id.heartrate_graph);
        HeartratelineChartManager=new LineChartManager(heartrate_graph);
        viewList.add(month_bloodpressure_view);
        viewList.add(month_heartrate_view);
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
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void run() {
            //更新界面
            bloodpressure_graph.setDrawGridBackground(false);
            bloodpressure_graph.setBackgroundColor(Color.WHITE);
            //是否显示边界
            bloodpressure_graph.setDrawBorders(false);
            bloodpressure_graph.setDoubleTapToZoomEnabled(false);
            Description description = new Description();
            description.setEnabled(false);
            bloodpressure_graph.setDescription(description);
            bloodpressure_graph.getAxisLeft().setEnabled(false);
            bloodpressure_graph.getAxisLeft().setAxisMinimum(50f);
            bloodpressure_graph.getAxisLeft().setAxisMaximum(150f);
            bloodpressure_graph.getAxisLeft().setDrawGridLines(false);
            bloodpressure_graph.getAxisRight().setDrawAxisLine(false);
            bloodpressure_graph.getAxisRight().setDrawGridLines(false);
            //设置x轴在底部显示
            bloodpressure_graph.getXAxis().setDrawAxisLine(true);
            bloodpressure_graph.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            bloodpressure_graph.getAxisLeft().setDrawGridLines(false);
            bloodpressure_graph.getAxisLeft().setDrawLabels(false);
            bloodpressure_graph.getXAxis().setDrawGridLines(false);
            bloodpressure_graph.getAxisRight().setDrawLabels(false);
            /***折线图例 标签 设置***/
            legend = bloodpressure_graph.getLegend();
            //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
            legend.setForm(Legend.LegendForm.LINE);
            legend.setTextSize(12f);
            //显示位置 左下方
            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            //是否绘制在图表里面
            legend.setDrawInside(false);
            //是否显示
            legend.setEnabled(false);
            //展示图表
            lineChartManager.showLineChart(monthInfoList, "收缩压", MyApplication.getContext().getColor(R.color.color1));
            lineChartManager.addLine(monthInfoList, "舒张压",  MyApplication.getContext().getColor(R.color.color1));
            //设置曲线填充色 以及 MarkerView
            drawable = MyApplication.getContext().getDrawable(R.drawable.fade_green);
            lineChartManager.setChartFillDrawable(drawable);
            lineChartManager.setMarkerView(MyApplication.getContext());


            bloodoxygen_graph.setDrawGridBackground(false);
            bloodoxygen_graph.setBackgroundColor(Color.WHITE);
            //是否显示边界
            bloodoxygen_graph.setDrawBorders(false);
            bloodoxygen_graph.setDoubleTapToZoomEnabled(false);
            description = new Description();
            description.setEnabled(false);
            bloodoxygen_graph.setDescription(description);
            bloodoxygen_graph.getAxisLeft().setEnabled(false);
            bloodoxygen_graph.getAxisLeft().setAxisMinimum(50f);
            bloodoxygen_graph.getAxisLeft().setAxisMaximum(150f);
            bloodoxygen_graph.getAxisLeft().setDrawGridLines(false);
            bloodoxygen_graph.getAxisRight().setDrawAxisLine(false);
            bloodoxygen_graph.getAxisRight().setDrawGridLines(false);
            //设置x轴在底部显示
            bloodoxygen_graph.getXAxis().setDrawAxisLine(true);
            bloodoxygen_graph.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            bloodoxygen_graph.getAxisLeft().setDrawGridLines(false);
            bloodoxygen_graph.getAxisLeft().setDrawLabels(false);
            bloodoxygen_graph.getXAxis().setDrawGridLines(false);
            bloodoxygen_graph.getAxisRight().setDrawLabels(false);
            /***折线图例 标签 设置***/
            legend = bloodoxygen_graph.getLegend();
            //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
            legend.setForm(Legend.LegendForm.LINE);
            legend.setTextSize(12f);
            //显示位置 左下方
            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            //是否绘制在图表里面
            legend.setDrawInside(false);
            //是否显示
            legend.setEnabled(false);
            //展示图表
            BloodOxygenlineChartManager.showBloodOxygenLineChart(monthInfoList, "血氧",  MyApplication.getContext().getColor(R.color.color2));
            //lineChartManager.addLine(monthInfoList, "收缩压", R.color.orange);
            //设置曲线填充色 以及 MarkerView
            drawable =MyApplication.getContext().getDrawable(R.drawable.fade_blue);
            BloodOxygenlineChartManager.setChartFillDrawable(drawable);
            BloodOxygenlineChartManager.setMarkerView(MyApplication.getContext());

            heartrate_graph.setDrawGridBackground(false);
            heartrate_graph.setBackgroundColor(Color.WHITE);
            //是否显示边界
            heartrate_graph.setDrawBorders(false);
            heartrate_graph.setDoubleTapToZoomEnabled(false);
            description = new Description();
            description.setEnabled(false);
            heartrate_graph.setDescription(description);
            heartrate_graph.getAxisLeft().setEnabled(false);
            heartrate_graph.getAxisLeft().setAxisMinimum(50f);
            heartrate_graph.getAxisLeft().setAxisMaximum(150f);
            heartrate_graph.getAxisLeft().setDrawGridLines(false);
            heartrate_graph.getAxisRight().setDrawAxisLine(false);
            heartrate_graph.getAxisRight().setDrawGridLines(false);
            //设置x轴在底部显示
            heartrate_graph.getXAxis().setDrawAxisLine(true);
            heartrate_graph.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            heartrate_graph.getAxisLeft().setDrawGridLines(false);
            heartrate_graph.getAxisLeft().setDrawLabels(false);
            heartrate_graph.getXAxis().setDrawGridLines(false);
            heartrate_graph.getAxisRight().setDrawLabels(false);
            /***折线图例 标签 设置***/
            legend = heartrate_graph.getLegend();
            //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
            legend.setForm(Legend.LegendForm.LINE);
            legend.setTextSize(12f);
            //显示位置 左下方
            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            //是否绘制在图表里面
            legend.setDrawInside(false);
            //是否显示
            legend.setEnabled(false);
            //展示图表
            HeartratelineChartManager.showHeartRateLineChart(monthInfoList, "心率",  MyApplication.getContext().getColor(R.color.color3));
            //lineChartManager.addLine(monthInfoList, "收缩压", R.color.orange);
            //设置曲线填充色 以及 MarkerView
            drawable =MyApplication.getContext().getDrawable(R.drawable.fade_red);
            HeartratelineChartManager.setChartFillDrawable(drawable);
            HeartratelineChartManager.setMarkerView(MyApplication.getContext());
        }

    };

    //初始化ViewPager
    public void InitPageAdapter(final List<MonthInfo> monthInfoList) {

        this.monthInfoList= monthInfoList;

        handler.post(runnableUi);
        dialog.dismiss();
    }



    public void InitNoDate() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fragment_month_ll.setVisibility(View.GONE);
                monthnodata_ll.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        });

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

}
