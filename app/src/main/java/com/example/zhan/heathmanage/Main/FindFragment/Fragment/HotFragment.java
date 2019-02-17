package com.example.zhan.heathmanage.Main.FindFragment.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhan.heathmanage.Main.FindFragment.Adapter.HotListAdapter;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.HotInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Service.FindView;
import com.example.zhan.heathmanage.Main.FindFragment.Service.HotDao;
import com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp.HotDaoImp;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;
import com.zyao89.view.zloading.ZLoadingDialog;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.os.Looper.getMainLooper;
import static com.zyao89.view.zloading.Z_TYPE.DOUBLE_CIRCLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotFragment extends Fragment implements FindView {
    @BindView(R.id.fragment_hot_rv)
    RecyclerView fragment_hot_rv;
    @BindView(R.id.fragment_hot_srl)
    SwipeRefreshLayout fragment_hot_srl;
    HotDao hotDao;
    HotListAdapter hotListAdapter;
    ZLoadingDialog dialog;
    //private static List<HotInfo> HotList;
    public HotFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        ButterKnife.bind(this, view);
        // HotList=new ArrayList<>();
        dialog = new ZLoadingDialog(getActivity());
        Init();
        hotDao = new HotDaoImp(this);
        hotDao.getHotList();
        fragment_hot_srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // HotList=new ArrayList<>();
                Init();
                hotDao.getHotList();
                fragment_hot_srl.setRefreshing(false);
            }
        });
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);//定义瀑布流管理器，第一个参数是列数，第二个是方向。
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//不设置的话，图片闪烁错位，有可能有整列错位的情况。
        fragment_hot_rv.setLayoutManager(layoutManager);//设置瀑布流管理器
        fragment_hot_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                layoutManager.invalidateSpanAssignments();//这行主要解决了当加载更多数据时，底部需要重绘，否则布局可能衔接不上。
            }
        });

        return view;
    }
    public void Init(){


        dialog.setLoadingBuilder(DOUBLE_CIRCLE)
                .setLoadingColor(Color.parseColor("#ff5305"))
                .setHintText("正在加载中...")
//                                .setHintTextSize(16) // 设置字体大小
                .setHintTextColor(Color.GRAY)  // 设置字体颜色
//                                .setDurationTime(0.5) // 设置动画时间百分比

                .setDialogBackgroundColor(Color.parseColor("#cc111111")) // 设置背景色
                .show();
    }

    @Override
    public void InitHotList(final List<HotInfo> HotList) {
        hotListAdapter = new HotListAdapter(this,HotList);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fragment_hot_rv.setAdapter(hotListAdapter);
                dialog.dismiss();
            }
        });

    }

//    public void InitHotList(final List<HotInfo> HotList) {
//       // this.HotList=HotList;
//
////                final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);//定义瀑布流管理器，第一个参数是列数，第二个是方向。
////                layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//不设置的话，图片闪烁错位，有可能有整列错位的情况。
////                fragment_hot_rv.setLayoutManager(layoutManager);//设置瀑布流管理器
//                HotListAdapter hotListAdapter = new HotListAdapter(HotList);
//                //fragment_hot_rv.addItemDecoration(new GridSpacingItemDecoration(40));//边距和分割线，需要自己定义
//                //设置适配器
//                fragment_hot_rv.setAdapter(hotListAdapter);
//            }
//
//        });
//    }

}
