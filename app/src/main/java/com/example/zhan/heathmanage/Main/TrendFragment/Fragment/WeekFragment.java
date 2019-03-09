package com.example.zhan.heathmanage.Main.TrendFragment.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.beiing.leafchart.LeafLineChart;
import com.beiing.leafchart.OutsideLineChart;
import com.beiing.leafchart.bean.Axis;
import com.beiing.leafchart.bean.AxisValue;
import com.beiing.leafchart.bean.Line;
import com.beiing.leafchart.bean.PointValue;
import com.beiing.leafchart.bean.SlidingLine;
import com.example.zhan.heathmanage.Main.TrendFragment.Bean.MonthInfo;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.Imp.LineChartServiceDaoImp;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.Imp.WeekLineChartServiceDaoImp;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.LineChartServiceDao;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.WeekLineChartServiceDao;
import com.example.zhan.heathmanage.Main.TrendFragment.TrendFragment;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.zhan.heathmanage.Main.MainActivity.ev;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeekFragment extends Fragment {
    private LeafLineChart weekheartrate_graph;
    private LeafLineChart weekbloodpressure_graph;
    private LeafLineChart weekbloodoxygen_graph;
    //private LeafLineChart weekbloodfat_graph;
    @BindView(R.id.weeknodata_ll)
    LinearLayout weeknodata_ll;
    @BindView(R.id.fragment_week_ll)LinearLayout fragment_week_ll;
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

    private List<View> viewList = new ArrayList<>();
    private PagerAdapter pagerAdapter;
    private ImageView imageView;
    private ImageView[] imageViews;
    Handler handler;
    List<Line> lines;
    List<MonthInfo>weekInfoList;
    public WeekFragment() {
        // Required empty public constructor
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

        lineChartServiceDao = new LineChartServiceDaoImp(this);
        InitView();
        //InitPageAdapter();
        initPointer();
       // getActivity().dispatchTouchEvent(ev);
        initEvent();

        lineChartServiceDao.GetWeekData(MyApplication.getUserPhone(), TrendFragment.StartDay,TrendFragment.EndDay);

        return view;
    }

    private void initEvent() {
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new GuidePageChangeListener());
    }

    public void InitView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
         week_bloodpressure_view = inflater.inflate(R.layout.week_bloodpressure, null);
        // week_bloodfat_view = inflater.inflate(R.layout.week_bloodfat, null);
         week_bloodoxygen_view = inflater.inflate(R.layout.week_bloodoxygen, null);
         week_heartrate_view = inflater.inflate(R.layout.week_heartrate, null);
       // weekbloodfat_graph = week_bloodfat_view.findViewById(R.id.weekbloodfat_graph);
        weekbloodoxygen_graph = week_bloodoxygen_view.findViewById(R.id.weekbloodoxygen_graph);
        weekbloodpressure_graph = week_bloodpressure_view.findViewById(R.id.weekbloodpressure_graph);
        weekheartrate_graph = week_heartrate_view.findViewById(R.id.weekheartrate_graph);
        viewList.add(week_bloodpressure_view);
        viewList.add(week_heartrate_view);
      //  viewList.add(week_bloodfat_view);
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
        this.weekInfoList=weekInfoList;
        axisX = new Axis(getAxisValuesX(weekInfoList));
        axisY = new Axis(getAxisValuesY());
        axisX.setAxisColor(Color.parseColor("#FF000000")).setTextColor(Color.DKGRAY).setHasLines(false).setShowText(true);
        axisY.setAxisColor(Color.parseColor("#e9e9e9")).setTextColor(Color.DKGRAY).setHasLines(false).setShowText(false);
        handler.post(runnableUi);
    }

    Runnable runnableUi = new Runnable() {
        @Override
        public void run() {
            //更新界面
            weekheartrate_graph.setAxisX(axisX);
            weekheartrate_graph.setAxisY(axisY);
            weekbloodpressure_graph.setAxisX(axisX);
            weekbloodpressure_graph.setAxisY(axisY);
            weekbloodoxygen_graph.setAxisX(axisX);
            weekbloodoxygen_graph.setAxisY(axisY);
//            weekbloodfat_graph.setAxisX(axisX);
//            weekbloodfat_graph.setAxisY(axisY);
            lines = new ArrayList<>();
            lines.add(lineChartServiceDao.getHeartRateLine(weekInfoList));
            weekheartrate_graph.setSlidingLine(getSlideingLine());
            weekheartrate_graph.setChartData(lines);
            //
            lines = new ArrayList<>();
            lines.add(lineChartServiceDao.getDiastolicBPLine(weekInfoList));
            lines.add(lineChartServiceDao.getSystolicBPLine(weekInfoList));
            weekbloodpressure_graph.setSlidingLine(getSlideingLine());
            weekbloodpressure_graph.setChartData(lines);
            //
            lines = new ArrayList<>();
            lines.add(lineChartServiceDao.getBloodOxygenLine(weekInfoList));
            weekbloodoxygen_graph.setChartData(lines);
            weekbloodoxygen_graph.setSlidingLine(getSlideingLine());
            lines = new ArrayList<>();
            lines.add(lineChartServiceDao.getBloodFatLine(weekInfoList));
            weekheartrate_graph.show();
            weekbloodpressure_graph.showWithAnimation(3000);
            weekheartrate_graph.showWithAnimation(3000);
            weekbloodoxygen_graph.showWithAnimation(3000);
        }

    };

    //横坐标轴
    private List<AxisValue> getAxisValuesX(List<MonthInfo> monthInfoList) {
        int length = monthInfoList.size();
        List<AxisValue> axisValues = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            AxisValue value = new AxisValue();
            value.setLabel(monthInfoList.get(i).getDate());
            axisValues.add(value);
        }
        return axisValues;
    }

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

    public void getWeekDataByWeekSuccessCallBack(String dayheartrate, String dayblooddiastolic, String daybloodsystolic, String daybloodoxygen, String daybloodfat) {

    }

    public void InitNoData() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fragment_week_ll.setVisibility(View.GONE);
                weeknodata_ll.setVisibility(View.VISIBLE);
            }
        });
    }

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

    public SlidingLine getSlideingLine() {
        SlidingLine slidingLine = new SlidingLine();
        slidingLine.setSlideLineColor(Color.parseColor("#00000000"))
                .setSlidePointColor(R.color.colorAccent)
                .setSlidePointRadius(3);
        return slidingLine;
    }
}
