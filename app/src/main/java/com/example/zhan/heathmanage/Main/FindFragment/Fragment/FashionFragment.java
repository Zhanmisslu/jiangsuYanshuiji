package com.example.zhan.heathmanage.Main.FindFragment.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhan.heathmanage.Main.FindFragment.Adapter.FanListAdapter;
import com.example.zhan.heathmanage.Main.FindFragment.Adapter.FashionAdapter;
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
public class FashionFragment extends Fragment {
    @BindView(R.id.fragment_fashion_rv)RecyclerView fragment_fashion_rv;
    FashionAdapter fashionAdapter;
    NewsDao newsDao;
    public FashionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fashion, container, false);
        ButterKnife.bind(this,view);
        newsDao=new NewsDaoImp(this);
        newsDao.getFashionapi();
        fragment_fashion_rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    public void InitFashionData(List<NewsBean> fashionList) {
        fashionAdapter=new FashionAdapter(fashionList,this);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fragment_fashion_rv.setAdapter(fashionAdapter);
            }
        });
    }
}
