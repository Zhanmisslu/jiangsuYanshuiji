package com.example.zhan.heathmanage.Main.FindFragment.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhan.heathmanage.Main.FindFragment.Activity.InvitationInfoActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.PersonalActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Adapter.AttentionAdapter;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.AttentionInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Service.AttentionDao;
import com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp.AttentionDaoImp;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttentionFragment extends Fragment {
    @BindView(R.id.fragment_attention_rv)
    RecyclerView fragment_attention_rv;
    @BindView(R.id.fragment_attention_srl)
    SwipeRefreshLayout fragment_attention_srl;
    AttentionAdapter attentionAdapter;
    AttentionDao attentionDao;
    String userId;
    public AttentionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attention, container, false);
        ButterKnife.bind(this, view);
        attentionDao = new AttentionDaoImp(this);
        fragment_attention_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        attentionDao.getAttentionList(MyApplication.getUserId());
        fragment_attention_srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fragment_attention_srl.setRefreshing(false);
                attentionDao.getAttentionList(MyApplication.getUserId());

            }
        });
        return view;
    }



    @Override
    public void onResume() {

        super.onResume();
        //attentionDao.getAttentionList(MyApplication.getUserId());
    }

    public void InitAttentionList(List<AttentionInfo> attentionInfoList) {
        attentionAdapter = new AttentionAdapter(this, attentionInfoList);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fragment_attention_rv.setAdapter(attentionAdapter);
            }
        });
    }
}
