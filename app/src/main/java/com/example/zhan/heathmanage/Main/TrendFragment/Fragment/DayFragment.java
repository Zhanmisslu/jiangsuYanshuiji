package com.example.zhan.heathmanage.Main.TrendFragment.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.DayDataServiceDao;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.Imp.DayDataServiceDaoImp;
import com.example.zhan.heathmanage.Main.TrendFragment.TrendFragment;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayFragment extends Fragment {
    private View view;
    @BindView(R.id.heartrate_tv)
    TextView heartrate_tv;
    @BindView(R.id.bloodfat_tv)
    TextView bloodfat_tv;
    @BindView(R.id.bloodoxygen_tv)
    TextView bloodoxygen_tv;
    @BindView(R.id.bloodsystolic_tv)
    TextView bloodsystolic_tv;
    @BindView(R.id.blooddiastolic_tv)
    TextView blooddiastolic_tv;
    DayDataServiceDao dayDataServiceDao;
    public DayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_day, container, false);
        ButterKnife.bind(this,view);
        dayDataServiceDao=new DayDataServiceDaoImp(this);
        dayDataServiceDao.getDayData(MyApplication.getUserPhone(), TrendFragment.nDate);
        return view;
    }
    public void getDataByDaySuccessCallBack(String dayheartrate,String dayblooddiastolic,String daybloodsystolic,String daybloodoxygen,String  daybloodfat){
        heartrate_tv.setText(dayheartrate);
        bloodfat_tv.setText(daybloodfat);
        bloodoxygen_tv.setText(daybloodoxygen);
        bloodsystolic_tv.setText(daybloodsystolic);
        blooddiastolic_tv.setText(dayblooddiastolic);
    }

}
