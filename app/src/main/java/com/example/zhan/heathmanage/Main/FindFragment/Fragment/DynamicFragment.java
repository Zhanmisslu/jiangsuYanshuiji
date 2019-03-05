package com.example.zhan.heathmanage.Main.FindFragment.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.zhan.heathmanage.Main.FindFragment.Activity.SearchActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Adapter.SearchPostingAdapter;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.AttentionInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Service.SearchDao;
import com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp.SearchDaoImp;
import com.example.zhan.heathmanage.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DynamicFragment extends Fragment {
    @BindView(R.id.fragment_dynamic_rv)RecyclerView fragment_dynamic_rv;
    @BindView(R.id.fragment_dynamic_empty_iv)ImageView fragment_dynamic_empty_iv;
    SearchPostingAdapter searchPostingAdapter;
    SearchDao searchDao;

    public DynamicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_dynamic, container, false);
        ButterKnife.bind(this,view);
        fragment_dynamic_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchDao=new SearchDaoImp(this);
        searchDao.SearchPosting(SearchActivity.text);
        return  view;

    }

    public void InitList(List<AttentionInfo> SearchPostingList) {
        searchPostingAdapter=new SearchPostingAdapter(this,SearchPostingList,getContext());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fragment_dynamic_rv.setAdapter(searchPostingAdapter);
            }
        });
    }

    public void InitImage() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fragment_dynamic_empty_iv.setVisibility(View.VISIBLE);
                fragment_dynamic_rv.setVisibility(View.GONE);
            }
        });
    }
}
