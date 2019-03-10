package com.example.zhan.heathmanage.Main.TrendFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhan.heathmanage.BasicsTools.AnimationUtils;
import com.example.zhan.heathmanage.BasicsTools.ChildViewPager;
import com.example.zhan.heathmanage.Main.MainActivity;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.DayFragment;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.MonthFragment;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.WeekFragment;
import com.example.zhan.heathmanage.R;
import com.necer.calendar.Miui10Calendar;
import com.necer.entity.NDate;
import com.necer.listener.OnCalendarChangedListener;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.zhan.heathmanage.Main.MainActivity.ev;


/**
 * A simple {@link Fragment} subclass.
 */
public class TrendFragment extends Fragment implements View.OnClickListener {


    public TrendFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.week_tv)
    TextView week_tv;
    @BindView(R.id.month_tv)
    TextView month_tv;
    @BindView(R.id.year_tv)
    TextView year_tv;
    //    @BindView(R.id.lunar_year_tv) TextView lunar_year_tv;
//    @BindView(R.id.lunar_day_tv) TextView lunar_day_tv;
    @BindView(R.id.back_today_ib)
    ImageButton back_today_ib;
//    @BindView(R.id.fragment_evaluate_down_ib)
//    ImageButton fragment_evaluate_down_ib;

    @BindView(R.id.trend_dayview_ll)
    LinearLayout trend_dayview_ll;
    @BindView(R.id.trend_dayview_ib)
    ImageButton trend_dayview_ib;
    @BindView(R.id.trend_dayview_tv)
    TextView trend_dayview_tv;
    @BindView(R.id.trend_weekview_ll)
    LinearLayout trend_weekview_ll;
    @BindView(R.id.trend_weekview_ib)
    ImageButton trend_weekview_ib;
    @BindView(R.id.trend_weekview_tv)
    TextView trend_weekview_tv;
    @BindView(R.id.trend_monthview_ll)
    LinearLayout trend_monthview_ll;
    @BindView(R.id.trend_monthview_ib)
    ImageButton trend_monthview_ib;
    @BindView(R.id.trend_monthview_tv)
    TextView trend_monthview_tv;
    private DayFragment dayFragment;
    private WeekFragment weekFragment;
    private MonthFragment monthFragment;
    private FragmentManager fragmentManager;//FragmentManager 是一个抽象类，它定义了对一个 Activity/Fragment 中 添加进来的 Fragment 列表、Fragment 回退栈的操作、管理
    private FragmentTransaction fragmentTransaction;

    @BindView(R.id.fragment_trend_fl)
    FrameLayout fragment_trend_fl;
    @BindView(R.id.fragment_trend_ib)
    ImageButton fragment_trend_ib;
    @BindView(R.id.fragment_trend_view_ll)
    LinearLayout fragment_trend_view_ll;
    @BindView(R.id.fragment_trend_ll)
    LinearLayout fragment_trend_ll;
    int flag = 0;
    int dayflag = 0;
    int fragmentflag = -1;
    public static String StartDay;
    public static String EndDay;
    public static String StartDay1;
    public static String EndDay1;
    public static String nDate;
    public static String Month;
    public static String Month1;
    public static String Day;
    int monthflag=-1;//月的标识
    int weekflag=-1;//周的标识
    private final String[] weeks = {"周一", "周二", "周三", "周四", "周五", "周六", "周日",};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trend, container, false);

        ButterKnife.bind(this, view);


        List<String> pointList = Arrays.asList("2018-10-01", "2018-11-19", "2018-11-20", "2018-05-23", "2019-01-01");
        final Miui10Calendar miui10Calendar = view.findViewById(R.id.miui10Calendar);
        miui10Calendar.setPointList(pointList);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
//获取当前时间
        final Date date1 = new Date(System.currentTimeMillis());
        // Toast.makeText(getActivity(),"Date获取当前日期时间"+simpleDateFormat.format(date1),Toast.LENGTH_SHORT).show();

        back_today_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                miui10Calendar.toToday();
            }
        });
        nDate=simpleDateFormat.format(date1);

        miui10Calendar.setOnCalendarChangedListener(new OnCalendarChangedListener() {
            @Override
            public void onCalendarDateChanged(NDate date) {
                Month1=month_tv.getText().toString().replace("月","");

                year_tv.setText(date.localDate.getYear() + "年");
                month_tv.setText(date.localDate.getMonthOfYear() + "月");
                week_tv.setText(weeks[date.localDate.getDayOfWeek() - 1]);
                nDate = date.localDate.toString();
                Month= String.valueOf(date.localDate.getMonthOfYear());

                if (simpleDateFormat.format(date1).equals(date.localDate.toString())) {
                    back_today_ib.setVisibility(View.GONE);
                } else {
                    back_today_ib.setVisibility(View.VISIBLE);
                }
                //monthFragment.judgeTime();
                dayflag=1;
                //monthFragment.initHeartRate();
                StartDay1=StartDay;
                EndDay1=EndDay;
                StartDay=getWeekStartTime(stringtoCalendar(nDate));
                EndDay=getWeekEndTime(stringtoCalendar(nDate));
                if(StartDay.equals(StartDay1)){
                    weekflag=0;
                }else {
                    weekflag=1;
                }
               // fragmentflag=0;
                //Toast.makeText(getActivity(), "你选中的是：" + date.localDate.toString(), Toast.LENGTH_SHORT).show();
                if(Month1.equals(Month)){
                    monthflag=0;//一样的月份不需要重画
                }else {
                    monthflag=1;//不一样的月份
                }
                InitData();

            }

            @Override
            public void onCalendarStateChanged(boolean isMonthSate) {
            }

        });
        inListener();
        setSelect(0);
        fragmentflag = 0;
        getActivity().dispatchTouchEvent(ev);
//        String a=Month;
//        String starttime=getWeekStartTime();
//        String endtime=getWeekEndTime();
//        Log.v("starttime=",starttime);
//        Log.v("endtime=",endtime);

        return view;
    }
//    public void aaa(){
//        getActivity().dispatchTouchEvent()
//    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_MOVE://如果是向下滑动，计算出每次滑动的距离与滑动的总距离，将每次滑动的距离作为layout(int l, int t, int r, int b)方法的参数，重新进行布局，达到布局滑动的效果。
//                break;
//            case MotionEvent.ACTION_DOWN://获取刚开始触碰的y坐标
//                ChildViewPager.mRollViewPagerTouching = true;
//                break;
//            case MotionEvent.ACTION_UP://将滑动的总距离作为layout(int l, int t, int r, int b)方法的参数，重新进行布局，达到布局自动回弹的效果。
//                ChildViewPager.mRollViewPagerTouching = false;
//                break;
//        }
//        return super.getActivity().dispatchTouchEvent(ev);
//    }
    @OnClick(R.id.fragment_trend_ll)
    public void fragment_trend_ll_Onclick() {
        if (flag == 0) {//0的时候是向下的箭头
            flag = 1;
            AnimationUtils.showAndHiddenAnimation(fragment_trend_view_ll, AnimationUtils.AnimationState.STATE_HIDDEN, 0);
            fragment_trend_ib.setBackgroundResource(R.drawable.upon);
            fragment_trend_view_ll.setVisibility(View.VISIBLE);
        } else {//1的时候是向上的箭头
            flag = 0;
            AnimationUtils.showAndHiddenAnimation(fragment_trend_view_ll, AnimationUtils.AnimationState.STATE_HIDDEN, 0);
            fragment_trend_ib.setBackgroundResource(R.drawable.down);
            fragment_trend_view_ll.setVisibility(View.GONE);
        }


    }

    public void InitData() {
        if (fragmentflag == 0&&dayflag==1) {
            dayFragment = new DayFragment();
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_trend_fl, dayFragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
        if (fragmentflag == 1&&weekflag==1) {
            weekFragment = new WeekFragment();
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();//开启一系列对fragment的操作
            fragmentTransaction.replace(R.id.fragment_trend_fl, weekFragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
        if (fragmentflag == 2 && monthflag==1) {
            monthFragment = new MonthFragment();
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_trend_fl, monthFragment);
            fragmentTransaction.commitAllowingStateLoss();
        }

    }

    public void inListener() {
        trend_dayview_ll.setOnClickListener(this);
        trend_weekview_ll.setOnClickListener(this);
        trend_monthview_ll.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        resetImgs();
        switch (view.getId()) {
            case R.id.trend_dayview_ll:
                trend_dayview_ib.setImageResource(R.drawable.day_press);
                trend_dayview_tv.setTextColor(Color.parseColor("#272727"));
                setSelect(0);
                fragmentflag = 0;
                InitData();

                break;
            case R.id.trend_weekview_ll:
                trend_weekview_ib.setImageResource(R.drawable.week_press);
                trend_weekview_tv.setTextColor(Color.parseColor("#272727"));
                setSelect(1);
                fragmentflag = 1;
                weekflag=1;
                InitData();

                break;
            case R.id.trend_monthview_ll:
                trend_monthview_ib.setImageResource(R.drawable.month_press);
                trend_monthview_tv.setTextColor(Color.parseColor("#272727"));
                setSelect(2);
                fragmentflag = 2;
                monthflag=1;
                InitData();

                break;
            default:
                break;
        }
    }

    public void setSelect(int i) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction tf = fm.beginTransaction();
        hideFragment(tf);
        switch (i) {
            case 0:
                if (dayFragment == null) {
                    dayFragment = new DayFragment();
                    tf.add(R.id.fragment_trend_fl, dayFragment);
                } else {
                    tf.show(dayFragment);
                }

                break;
            case 1:
                if (weekFragment == null) {
                    weekFragment = new WeekFragment();
                    tf.add(R.id.fragment_trend_fl, weekFragment);
                } else {
                    tf.show(weekFragment);

                }

                break;
            case 2:
                if (monthFragment == null) {
                    monthFragment = new MonthFragment();
                    tf.add(R.id.fragment_trend_fl, monthFragment);
                } else {
                    tf.show(monthFragment);

                }

                break;
        }
        tf.commit();
    }

    private void hideFragment(FragmentTransaction tf) {
        if (dayFragment != null) {
            tf.hide(dayFragment);
        }
        if (weekFragment != null) {
            tf.hide(weekFragment);
        }
        if (monthFragment != null) {
            tf.hide(monthFragment);
        }
    }

    private void resetImgs() {
        //重置icon图标
        trend_dayview_ib.setImageResource(R.drawable.day);
        trend_weekview_ib.setImageResource(R.drawable.week);
        trend_monthview_ib.setImageResource(R.drawable.month);

        //重置文字颜色
        trend_dayview_tv.setTextColor(Color.parseColor("#898989"));
        trend_weekview_tv.setTextColor(Color.parseColor("#898989"));
        trend_monthview_tv.setTextColor(Color.parseColor("#898989"));
    }
    public static Calendar dataToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Calendar stringtoCalendar(String Date){
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(Date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        return calendar;
    }


    /**
     * start
     * 本周开始时间戳
     */
    public static String getWeekStartTime(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd");
       // Calendar cal = calendar.getInstance();
        // 获取星期日开始时间戳
        calendar.set(calendar. DAY_OF_WEEK, calendar.SUNDAY);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * end
     * 本周结束时间戳
     */
    public static String getWeekEndTime(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd");

        //Calendar cal = calendar.getInstance();
        // 获取星期六结束时间戳
        calendar.set(calendar. DAY_OF_WEEK, calendar.SATURDAY );
        return simpleDateFormat.format(calendar.getTime());
    }

}
