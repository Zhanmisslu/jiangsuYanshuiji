package com.example.zhan.heathmanage.Main.TrendFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhan.heathmanage.R;
import com.necer.calendar.Miui10Calendar;
import com.necer.entity.NDate;
import com.necer.listener.OnCalendarChangedListener;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class TrendFragment extends Fragment {


    public TrendFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.week_tv) TextView week_tv;
    @BindView(R.id.month_tv) TextView month_tv;
    @BindView(R.id.year_tv) TextView year_tv;
    @BindView(R.id.lunar_year_tv) TextView lunar_year_tv;
    @BindView(R.id.lunar_day_tv) TextView lunar_day_tv;
    @BindView(R.id.back_today_bt)
    Button back_today_bt;

    private final String[] weeks = {"周一", "周二", "周三", "周四", "周五", "周六", "周日",};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_trend, container, false);
        ButterKnife.bind(this,view);
        List<String> pointList = Arrays.asList("2018-10-01", "2018-11-19", "2018-11-20", "2018-05-23", "2019-01-01");
        final Miui10Calendar miui10Calendar = view.findViewById(R.id.miui10Calendar);
        miui10Calendar.setPointList(pointList);
        back_today_bt.setOnClickListener(new View.OnClickListener() {
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
                lunar_year_tv.setText("农历" + date.lunar.lunarYearStr + "年 ");
                lunar_day_tv.setText(date.lunar.lunarMonthStr + date.lunar.lunarDayStr + (TextUtils.isEmpty(date.lunarHoliday) ? "" : (" | " + date.lunarHoliday)) + (TextUtils.isEmpty(date.solarHoliday) ? "" : (" | " + date.solarHoliday)));

            }

            @Override
            public void onCalendarStateChanged(boolean isMonthSate) {

            }
        });
        return view;
    }

}
