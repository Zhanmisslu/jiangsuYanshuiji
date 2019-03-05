package com.example.zhan.heathmanage.Main.FindFragment.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhan.heathmanage.Main.FindFragment.Adapter.NewsAdapter;
import com.example.zhan.heathmanage.Main.FindFragment.Adapter.NewsInfoAdapter;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.NewsBean;
import com.example.zhan.heathmanage.Main.FindFragment.Service.NewsDao;
import com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp.NewsDaoImp;
import com.example.zhan.heathmanage.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeathFragment extends Fragment {
    @BindView(R.id.fragment_health_rv)RecyclerView fragment_health_rv;
    NewsDao newsDao;
    NewsInfoAdapter newsInfoAdapter;
    public HeathFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_heath, container, false);
        ButterKnife.bind(this,view);
        newsDao= new NewsDaoImp(this);
        newsDao.getHealthapi();
        fragment_health_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    public void InitHealthData(List<NewsBean> newsBeanList) {
        newsInfoAdapter=new NewsInfoAdapter(newsBeanList,this);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fragment_health_rv.setAdapter(newsInfoAdapter);
            }
        });
    }
}
