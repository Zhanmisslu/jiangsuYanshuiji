package com.example.zhan.heathmanage.Main.TrendFragment.Fragment;


import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beiing.leafchart.LeafLineChart;
import com.beiing.leafchart.OutsideLineChart;
import com.beiing.leafchart.bean.Axis;
import com.beiing.leafchart.bean.AxisValue;
import com.beiing.leafchart.bean.Line;
import com.beiing.leafchart.bean.PointValue;
import com.beiing.leafchart.bean.SlidingLine;
import com.example.zhan.heathmanage.Main.TrendFragment.Bean.MonthInfo;
import com.example.zhan.heathmanage.Main.TrendFragment.Bean.WeekSuggestBean;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.Imp.LineChartServiceDaoImp;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.Imp.SuggestDaoImp;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.Imp.WeekLineChartServiceDaoImp;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.LineChartServiceDao;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.SuggestDao;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.WeekLineChartServiceDao;
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
public class WeekFragment extends Fragment {
    private LineChart weekheartrate_graph;
    private LineChart weekbloodpressure_graph;
    private LineChart weekbloodoxygen_graph;
    //private LeafLineChart weekbloodfat_graph;
    @BindView(R.id.weeknodata_ll)
    LinearLayout weeknodata_ll;
    @BindView(R.id.fragment_week_ll)
    LinearLayout fragment_week_ll;
    @BindView(R.id.frament_week_suggest_tv)
    TextView frament_week_suggest_tv;
    @BindView(R.id.weekpressuse_ll)
    LinearLayout weekpressuse_ll;
    @BindView(R.id.weekbloooxygen_ll)
    LinearLayout weekbloooxygen_ll;
    @BindView(R.id.weekheart_ll)
    LinearLayout weekheart_ll;
    @BindView(R.id.weekDiastolicBP_tv)
    TextView weekDiastolicBP_tv;
    @BindView(R.id.weekDiastolicBPtype_tv)
    TextView weekDiastolicBPtype_tv;
    @BindView(R.id.weekSystolicBP_tv)
    TextView weekSystolicBP_tv;
    @BindView(R.id.weekSystolicBPtype_tv)
    TextView weekSystolicBPtype_tv;
    @BindView(R.id.frament_week_suggest1_tv)
    TextView frament_week_suggest1_tv;
    View week_bloodpressure_view;
    //  View week_bloodfat_view;
    View week_bloodoxygen_view;
    View week_heartrate_view;
    private View view;
    WeekLineChartServiceDao weekLineChartServiceDao;
    LineChartServiceDao lineChartServiceDao;
    static Axis axisX;
    static Axis axisY;
    private ViewPager viewPager;
    private ViewGroup viewGroup;
    Drawable drawable;
    private List<View> viewList = new ArrayList<>();
    private PagerAdapter pagerAdapter;
    private ImageView imageView;
    private ImageView[] imageViews;
    Handler handler;
    List<Line> lines;
    List<MonthInfo> weekInfoList;
    static List<WeekSuggestBean> weekSuggestBeanList = new ArrayList<>();
    SuggestDao suggestDao;
    private LineChartManager lineChartManager;
    private LineChartManager BloodOxygenlineChartManager;
    private LineChartManager HeartratelineChartManager;
    private Legend legend;              //图例
    ZLoadingDialog dialog;
    public WeekFragment() {
        // Required empty public constructor
    }

    public void InitSuggest(List<WeekSuggestBean> weekSuggestBeanList) {
        this.weekSuggestBeanList = weekSuggestBeanList;
        handler.post(runnable1);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_week, container, false);
        ButterKnife.bind(this, view);
        handler = new Handler();
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewGroup = (ViewGroup) view.findViewById(R.id.viewGroup);
        suggestDao = new SuggestDaoImp(this);
        lineChartServiceDao = new LineChartServiceDaoImp(this);
        dialog = new ZLoadingDialog(getActivity());
        Init();
        InitView();
        //InitPageAdapter();
        initPointer();
        // getActivity().dispatchTouchEvent(ev);
        initEvent();

        lineChartServiceDao.GetWeekData(MyApplication.getUserPhone(), TrendFragment.StartDay, TrendFragment.EndDay);
        suggestDao.getWeekSuggestion(MyApplication.getUserPhone(), TrendFragment.StartDay, TrendFragment.EndDay);

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
    private void initEvent() {
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new GuidePageChangeListener());
    }

    Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
//            Typeface font = Typeface.createFromAsset(MyApplication.getContext().getAssets(),"bitcheese10.ttf");
//            Typeface font1 = Typeface.createFromAsset(MyApplication.getContext().getAssets(),"华文琥珀.ttf");
//
//            frament_week_suggest_tv.setTypeface(font1);
//            weekDiastolicBPtype_tv.setTypeface(font1);
//            weekSystolicBPtype_tv.setTypeface(font);
//            frament_week_suggest1_tv.setTypeface(font);
            frament_week_suggest_tv.setText(weekSuggestBeanList.get(0).getReferenceType());
            weekDiastolicBPtype_tv.setText(weekSuggestBeanList.get(0).getSuggestion());
            weekSystolicBPtype_tv.setText(weekSuggestBeanList.get(1).getSuggestion());
            frament_week_suggest1_tv.setText(weekSuggestBeanList.get(1).getReferenceType());
        }
    };

    public void InitView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        week_bloodpressure_view = inflater.inflate(R.layout.week_bloodpressure, null);
        week_bloodoxygen_view = inflater.inflate(R.layout.week_bloodoxygen, null);
        week_heartrate_view = inflater.inflate(R.layout.week_heartrate, null);
        weekbloodoxygen_graph = week_bloodoxygen_view.findViewById(R.id.weekbloodoxygen_graph);
        weekbloodpressure_graph = week_bloodpressure_view.findViewById(R.id.weekbloodpressure_graph);
        weekheartrate_graph = week_heartrate_view.findViewById(R.id.weekheartrate_graph);
        HeartratelineChartManager = new LineChartManager(weekheartrate_graph);
        lineChartManager = new LineChartManager(weekbloodpressure_graph);
        BloodOxygenlineChartManager = new LineChartManager(weekbloodoxygen_graph);
        viewList.add(week_bloodpressure_view);
        viewList.add(week_heartrate_view);
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

    //初始化ViewPager
    public void InitData(final List<MonthInfo> weekInfoList) {
        this.weekInfoList = weekInfoList;

        handler.post(runnableUi);
    }

    Runnable runnableUi = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            fragment_week_ll.setVisibility(View.VISIBLE);
            //更新界面
            weekbloodpressure_graph.setDrawGridBackground(false);
            weekbloodpressure_graph.setBackgroundColor(Color.WHITE);
            //是否显示边界
            weekbloodpressure_graph.setDrawBorders(false);
            weekbloodpressure_graph.setDoubleTapToZoomEnabled(false);
            Description description = new Description();
            description.setEnabled(false);
            weekbloodpressure_graph.setDescription(description);
            weekbloodpressure_graph.getAxisLeft().setEnabled(false);
            weekbloodpressure_graph.getAxisLeft().setAxisMinimum(50f);
            weekbloodpressure_graph.getAxisLeft().setAxisMaximum(150f);
            weekbloodpressure_graph.getAxisLeft().setDrawGridLines(false);
            weekbloodpressure_graph.getAxisRight().setDrawAxisLine(false);
            weekbloodpressure_graph.getAxisRight().setDrawGridLines(false);
            //设置x轴在底部显示
            weekbloodpressure_graph.getXAxis().setDrawAxisLine(true);
            weekbloodpressure_graph.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            weekbloodpressure_graph.getAxisLeft().setDrawGridLines(false);
            weekbloodpressure_graph.getAxisLeft().setDrawLabels(false);
            weekbloodpressure_graph.getXAxis().setDrawGridLines(false);
            weekbloodpressure_graph.getAxisRight().setDrawLabels(false);
            /***折线图例 标签 设置***/
            legend = weekbloodpressure_graph.getLegend();
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
            lineChartManager.showLineChart(weekInfoList, "收缩压", MyApplication.getContext().getColor(R.color.color1));
            lineChartManager.addLine(weekInfoList, "舒张压", MyApplication.getContext().getColor(R.color.color1));
            //设置曲线填充色 以及 MarkerView
            drawable = MyApplication.getContext().getDrawable(R.drawable.fade_green);
            lineChartManager.setChartFillDrawable(drawable);
            lineChartManager.setMarkerView(MyApplication.getContext());


            weekbloodoxygen_graph.setDrawGridBackground(false);
            weekbloodoxygen_graph.setBackgroundColor(Color.WHITE);
            //是否显示边界
            weekbloodoxygen_graph.setDrawBorders(false);
            weekbloodoxygen_graph.setDoubleTapToZoomEnabled(false);
            description = new Description();
            description.setEnabled(false);
            weekbloodoxygen_graph.setDescription(description);
            weekbloodoxygen_graph.getAxisLeft().setEnabled(false);
            weekbloodoxygen_graph.getAxisLeft().setAxisMinimum(50f);
            weekbloodoxygen_graph.getAxisLeft().setAxisMaximum(150f);
            weekbloodoxygen_graph.getAxisLeft().setDrawGridLines(false);
            weekbloodoxygen_graph.getAxisRight().setDrawAxisLine(false);
            weekbloodoxygen_graph.getAxisRight().setDrawGridLines(false);
            //设置x轴在底部显示
            weekbloodoxygen_graph.getXAxis().setDrawAxisLine(true);
            weekbloodoxygen_graph.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            weekbloodoxygen_graph.getAxisLeft().setDrawGridLines(false);
            weekbloodoxygen_graph.getAxisLeft().setDrawLabels(false);
            weekbloodoxygen_graph.getXAxis().setDrawGridLines(false);
            weekbloodoxygen_graph.getAxisRight().setDrawLabels(false);
            /***折线图例 标签 设置***/
            legend = weekbloodoxygen_graph.getLegend();
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
            BloodOxygenlineChartManager.showBloodOxygenLineChart(weekInfoList, "血氧", MyApplication.getContext().getColor(R.color.color2));
            //lineChartManager.addLine(monthInfoList, "收缩压", R.color.orange);
            //设置曲线填充色 以及 MarkerView
            drawable = MyApplication.getContext().getDrawable(R.drawable.fade_blue);
            BloodOxygenlineChartManager.setChartFillDrawable(drawable);
            BloodOxygenlineChartManager.setMarkerView(MyApplication.getContext());

            weekheartrate_graph.setDrawGridBackground(false);
            weekheartrate_graph.setBackgroundColor(Color.WHITE);
            //是否显示边界
            weekheartrate_graph.setDrawBorders(false);
            weekheartrate_graph.setDoubleTapToZoomEnabled(false);
            description = new Description();
            description.setEnabled(false);
            weekheartrate_graph.setDescription(description);
            weekheartrate_graph.getAxisLeft().setEnabled(false);
            weekheartrate_graph.getAxisLeft().setAxisMinimum(50f);
            weekheartrate_graph.getAxisLeft().setAxisMaximum(150f);
            weekheartrate_graph.getAxisLeft().setDrawGridLines(false);
            weekheartrate_graph.getAxisRight().setDrawAxisLine(false);
            weekheartrate_graph.getAxisRight().setDrawGridLines(false);
            //设置x轴在底部显示
            weekheartrate_graph.getXAxis().setDrawAxisLine(true);
            weekheartrate_graph.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            weekheartrate_graph.getAxisLeft().setDrawGridLines(false);
            weekheartrate_graph.getAxisLeft().setDrawLabels(false);
            weekheartrate_graph.getXAxis().setDrawGridLines(false);
            weekheartrate_graph.getAxisRight().setDrawLabels(false);
            /***折线图例 标签 设置***/
            legend = weekheartrate_graph.getLegend();
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
            HeartratelineChartManager.showHeartRateLineChart(weekInfoList, "心率", MyApplication.getContext().getColor(R.color.color3));
            //lineChartManager.addLine(monthInfoList, "收缩压", R.color.orange);
            //设置曲线填充色 以及 MarkerView
            drawable = MyApplication.getContext().getDrawable(R.drawable.fade_red);
            HeartratelineChartManager.setChartFillDrawable(drawable);
            HeartratelineChartManager.setMarkerView(MyApplication.getContext());
            dialog.dismiss();
        }

    };


    public void getWeekDataByWeekSuccessCallBack(String dayheartrate, String dayblooddiastolic, String daybloodsystolic, String daybloodoxygen, String daybloodfat) {

    }

    public void InitNoData() {
        handler.post(runnable);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            fragment_week_ll.setVisibility(View.GONE);
            weeknodata_ll.setVisibility(View.VISIBLE);
            dialog.dismiss();
        }
    };

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
                if (position == 0) {
                    weekheart_ll.setVisibility(View.GONE);
                    weekpressuse_ll.setVisibility(View.VISIBLE);
                    weekbloooxygen_ll.setVisibility(View.GONE);
                    frament_week_suggest_tv.setText(weekSuggestBeanList.get(0).getReferenceType());
                    frament_week_suggest1_tv.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    weekheart_ll.setVisibility(View.VISIBLE);
                    weekpressuse_ll.setVisibility(View.GONE);
                    weekbloooxygen_ll.setVisibility(View.GONE);
                    frament_week_suggest_tv.setText(weekSuggestBeanList.get(2).getReferenceType());
                    frament_week_suggest1_tv.setVisibility(View.GONE);
                } else {
                    weekheart_ll.setVisibility(View.GONE);
                    weekpressuse_ll.setVisibility(View.GONE);
                    weekbloooxygen_ll.setVisibility(View.VISIBLE);
                    frament_week_suggest_tv.setText(weekSuggestBeanList.get(4).getReferenceType());
                    frament_week_suggest1_tv.setVisibility(View.GONE);
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
