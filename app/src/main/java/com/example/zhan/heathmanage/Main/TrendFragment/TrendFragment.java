package com.example.zhan.heathmanage.Main.TrendFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhan.heathmanage.BasicsTools.AnimationUtils;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.DayFragment;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.MonthFragment;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.WeekFragment;
import com.example.zhan.heathmanage.R;
import com.necer.calendar.Miui10Calendar;
import com.necer.entity.NDate;
import com.necer.listener.OnCalendarChangedListener;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class TrendFragment extends Fragment implements View.OnClickListener{


    public TrendFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.week_tv) TextView week_tv;
    @BindView(R.id.month_tv) TextView month_tv;
    @BindView(R.id.year_tv) TextView year_tv;
//    @BindView(R.id.lunar_year_tv) TextView lunar_year_tv;
//    @BindView(R.id.lunar_day_tv) TextView lunar_day_tv;
    @BindView(R.id.back_today_ib)
    ImageButton back_today_ib;
//    @BindView(R.id.fragment_evaluate_down_ib)
//    ImageButton fragment_evaluate_down_ib;

    @BindView(R.id.trend_dayview_ll)LinearLayout trend_dayview_ll;
    @BindView(R.id.trend_dayview_ib)ImageButton trend_dayview_ib;
    @BindView(R.id.trend_dayview_tv) TextView trend_dayview_tv;
    @BindView(R.id.trend_weekview_ll)LinearLayout trend_weekview_ll;
    @BindView(R.id.trend_weekview_ib)ImageButton trend_weekview_ib;
    @BindView(R.id.trend_weekview_tv) TextView trend_weekview_tv;
    @BindView(R.id.trend_monthview_ll)LinearLayout trend_monthview_ll;
    @BindView(R.id.trend_monthview_ib)ImageButton trend_monthview_ib;
    @BindView(R.id.trend_monthview_tv) TextView trend_monthview_tv;
    private DayFragment dayFragment;
    private WeekFragment weekFragment;
    private MonthFragment monthFragment;
    @BindView(R.id.fragment_trend_fl)FrameLayout fragment_trend_fl;
    @BindView(R.id.fragment_trend_ib)
    ImageButton fragment_trend_ib;
    @BindView(R.id.fragment_trend_view_ll)LinearLayout fragment_trend_view_ll;
    @BindView(R.id.fragment_trend_ll)LinearLayout fragment_trend_ll;
    int flag=0;
    int dayflag=0;
    private final String[] weeks = {"周一", "周二", "周三", "周四", "周五", "周六", "周日",};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_trend, container, false);
        ButterKnife.bind(this,view);
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

        miui10Calendar.setOnCalendarChangedListener(new OnCalendarChangedListener() {
            @Override
            public void onCalendarDateChanged(NDate date) {

                year_tv.setText(date.localDate.getYear() + "年");
                month_tv.setText(date.localDate.getMonthOfYear() + "月");
                week_tv.setText(weeks[date.localDate.getDayOfWeek() - 1]);
                if(simpleDateFormat.format(date1).equals(date.localDate.toString())){
                    back_today_ib.setVisibility(View.GONE);
                }else {
                    back_today_ib.setVisibility(View.VISIBLE);
                }

                  //  Toast.makeText(getActivity(),"你选中的是："+date.localDate.toString(),Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCalendarStateChanged(boolean isMonthSate) {
            }

        });
        inListener();
        setSelect(0);
        return view;
    }
    @OnClick(R.id.fragment_trend_ll)
    public void fragment_trend_ll_Onclick(){
        if(flag==0){//0的时候是向下的箭头
            flag=1;
            AnimationUtils.showAndHiddenAnimation(fragment_trend_view_ll, AnimationUtils.AnimationState.STATE_HIDDEN,0);
            fragment_trend_ib.setBackgroundResource(R.drawable.upon);
            fragment_trend_view_ll.setVisibility(View.VISIBLE);
        }
        else{//1的时候是向上的箭头
            flag=0;
            AnimationUtils.showAndHiddenAnimation(fragment_trend_view_ll, AnimationUtils.AnimationState.STATE_HIDDEN,0);
            fragment_trend_ib.setBackgroundResource(R.drawable.down);
            fragment_trend_view_ll.setVisibility(View.GONE);
        }


    }


    public void inListener(){
        trend_dayview_ll.setOnClickListener(this);
        trend_weekview_ll.setOnClickListener(this);
        trend_monthview_ll.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        resetImgs();
        switch (view.getId()){
            case R.id.trend_dayview_ll:
                trend_dayview_ib.setImageResource(R.drawable.day_press);
                trend_dayview_tv.setTextColor(Color.parseColor("#272727"));
                setSelect(0);
                break;
            case R.id.trend_weekview_ll:
                trend_weekview_ib.setImageResource(R.drawable.week_press);
                trend_weekview_tv.setTextColor(Color.parseColor("#272727"));
                setSelect(1);
                break;
            case R.id.trend_monthview_ll:
                trend_monthview_ib.setImageResource(R.drawable.month_press);
                trend_monthview_tv.setTextColor(Color.parseColor("#272727"));
                setSelect(2);
                break;
            default:break;
        }
    }
    public void setSelect(int i){
        FragmentManager fm=getFragmentManager();
        FragmentTransaction tf=fm.beginTransaction();
        hideFragment(tf);
        switch (i){
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
    private void hideFragment(FragmentTransaction tf){
        if(dayFragment!=null){
            tf.hide(dayFragment);
        }
        if (weekFragment!=null){
            tf.hide(weekFragment);
        }
        if (monthFragment!=null){
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
}
