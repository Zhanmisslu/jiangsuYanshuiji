package com.example.zhan.heathmanage.Main.TrendFragment.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.DayDataServiceDao;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao.Imp.DayDataServiceDaoImp;
import com.example.zhan.heathmanage.Main.TrendFragment.TrendFragment;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;
import com.zyao89.view.zloading.ZLoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zyao89.view.zloading.Z_TYPE.STAR_LOADING;

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
    @BindView(R.id.fragment_day_nodata_ll)
    LinearLayout fragment_day_nodata_ll;
    @BindView(R.id.fragment_day_ll)
    LinearLayout fragment_day_ll;
    Handler handler;
    String dayheartrate;
    String dayblooddiastolic;
    String daybloodsystolic;
    String daybloodoxygen;
    String  daybloodfat;
    ZLoadingDialog dialog;
    public DayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_day, container, false);
        ButterKnife.bind(this,view);
        handler=new Handler();
        dialog = new ZLoadingDialog(getActivity());
        Init();
        dayDataServiceDao=new DayDataServiceDaoImp(this);
        dayDataServiceDao.getDayData(MyApplication.getUserPhone(), TrendFragment.nDate);

        return view;
    }
    public void getDataByDaySuccessCallBack(String dayheartrate,String dayblooddiastolic,String daybloodsystolic,String daybloodoxygen,String  daybloodfat){
        this.dayblooddiastolic=dayblooddiastolic;
        this.daybloodfat=daybloodfat;
        this.daybloodoxygen=daybloodoxygen;
        this.daybloodsystolic=daybloodsystolic;
        this.dayheartrate=dayheartrate;
        handler.post(runnable1);

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
    public void InitNoData() {
        handler.post(runnable);
    }
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            fragment_day_nodata_ll.setVisibility(View.VISIBLE);
            fragment_day_ll.setVisibility(View.GONE);
            dialog.dismiss();
        }
    };
    Runnable runnable1=new Runnable() {
        @Override
        public void run() {
            fragment_day_ll.setVisibility(View.VISIBLE);
            heartrate_tv.setText(dayheartrate);
            bloodfat_tv.setText(daybloodfat);
            bloodoxygen_tv.setText(daybloodoxygen);
            bloodsystolic_tv.setText(daybloodsystolic);
            blooddiastolic_tv.setText(dayblooddiastolic);
            dialog.dismiss();
        }
    };
}
