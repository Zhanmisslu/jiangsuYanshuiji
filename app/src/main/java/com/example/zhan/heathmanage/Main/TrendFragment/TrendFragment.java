package com.example.zhan.heathmanage.Main.TrendFragment;


import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhan.heathmanage.R;
import com.necer.calendar.Miui10Calendar;
import com.necer.entity.NDate;
import com.necer.listener.OnCalendarChangedListener;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class TrendFragment extends Fragment {


    public TrendFragment() {
        // Required empty public constructor
    }

//    @BindView(R.id.week_tv) TextView week_tv;
//    @BindView(R.id.month_tv) TextView month_tv;
//    @BindView(R.id.year_tv) TextView year_tv;
//    @BindView(R.id.lunar_year_tv) TextView lunar_year_tv;
//    @BindView(R.id.lunar_day_tv) TextView lunar_day_tv;
//    @BindView(R.id.back_today_bt)
//    Button back_today_bt;
//    @BindView(R.id.fragment_evaluate_down_ib)
//    ImageButton fragment_evaluate_down_ib;
    @BindView(R.id.fragment_evaluate_ib)
    ImageButton fragment_evaluate_ib;
    @BindView(R.id.fragment_evaluate_view_ll)LinearLayout fragment_evaluate_view_ll;
    @BindView(R.id.fragment_evaluate_ll)LinearLayout fragment_evaluate_ll;
    int flag=0;
    private final String[] weeks = {"周一", "周二", "周三", "周四", "周五", "周六", "周日",};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_trend, container, false);
        ButterKnife.bind(this,view);

        return view;
    }
    @OnClick(R.id.fragment_evaluate_ll)
    public void fragment_evaluate_ll_Onclick(){
        if(flag==0){//0的时候是向上的箭头
            flag=1;
            fragment_evaluate_ib.setBackgroundResource(R.drawable.down);
            fragment_evaluate_view_ll.setVisibility(View.VISIBLE);
        }
        else{//1的时候是向下的箭头
            flag=0;
            fragment_evaluate_ib.setBackgroundResource(R.drawable.upon);
            fragment_evaluate_view_ll.setVisibility(View.GONE);
        }


    }


}
