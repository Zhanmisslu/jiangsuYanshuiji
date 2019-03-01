package com.example.zhan.heathmanage.Main.FindFragment.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
    public static int flag=0;
    Handler handler=new Handler();
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
        handler=new Handler();
       // attentionDao.getAttentionList(MyApplication.getUserId());
        fragment_attention_srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fragment_attention_srl.setRefreshing(false);
                attentionDao.getAttentionList(MyApplication.getUserId());

            }
        });
        return view;
    }


    Runnable runnableUi = new Runnable() {
        @Override
        public void run() {
            //更新界面
            fragment_attention_rv.setAdapter(attentionAdapter);

        }

    };

    @Override
    public void onResume() {
        super.onResume();
        if(flag==0) {
            attentionDao.getAttentionList(MyApplication.getUserId());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==102){
            flag=0;
        }
    }

    public void InitAttentionList(List<AttentionInfo> attentionInfoList) {
        attentionAdapter = new AttentionAdapter(this, attentionInfoList);
        handler.post(runnableUi);
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                fragment_attention_rv.setAdapter(attentionAdapter);
//            }
//        });
    }
}
