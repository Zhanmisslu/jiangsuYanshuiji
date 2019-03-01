package com.example.zhan.heathmanage.Main.FindFragment.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhan.heathmanage.Main.FindFragment.Adapter.NewsAdapter;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.NewsBean;
import com.example.zhan.heathmanage.Main.FindFragment.Service.DryCarGoDao;
import com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp.DryCarGoDaoImp;
import com.example.zhan.heathmanage.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class DryCargoFragment extends Fragment {
    @BindView(R.id.health_rv)RecyclerView health_rv;
    @BindView(R.id.fashion_rv)RecyclerView fashion_rv;
    @BindView(R.id.health_more_tv)TextView health_more_tv;
    NewsAdapter healthAdapter;
    NewsAdapter fashionAdapter;
    DryCarGoDao dryCarGoDao;
    List<NewsBean> HeathList;
    List<NewsBean> FahsionList;
    public DryCargoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dry_cargo, container, false);
        ButterKnife.bind(this,view);
        dryCarGoDao=new DryCarGoDaoImp(this);
        health_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        fashion_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        dryCarGoDao.getHealthapi();
        dryCarGoDao.getFashionapi();
//        health_more_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent();
//                intent.putCharSequenceArrayListExtra("heathList",HeathList)
//            }
//        });
        return view;
    }

    public void InitHealthData(List<NewsBean> newsBeanList) {
        this.HeathList=newsBeanList;
        healthAdapter=new NewsAdapter(newsBeanList,this);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                health_rv.setAdapter(healthAdapter);
            }
        });
    }

    public void InitFashionData(List<NewsBean> fashionList) {
        this.FahsionList=fashionList;
        fashionAdapter=new NewsAdapter(fashionList,this);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fashion_rv.setAdapter(fashionAdapter);
            }
        });
    }
    @OnClick(R.id.health_more_tv)
    public void health_more_tv_Onclick(){
//        Intent intent=new Intent();
//        Intent intent1 = intent.putCharSequenceArrayListExtra("healthList", HeathList);

    }
    @OnClick(R.id.fashion_more_tv)
    public void fashion_more_tv_Onclick(){

    }
}
