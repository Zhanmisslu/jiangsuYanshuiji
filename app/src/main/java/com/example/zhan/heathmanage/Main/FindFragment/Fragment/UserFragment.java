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
import com.example.zhan.heathmanage.Main.FindFragment.Adapter.AttentionListAdapter;
import com.example.zhan.heathmanage.Main.FindFragment.Adapter.SearchUserAdapter;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.PeopleInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Service.SearchDao;
import com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp.SearchDaoImp;
import com.example.zhan.heathmanage.R;
import com.yuyh.library.imgsel.bean.Image;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {
    @BindView(R.id.fragment_user_rv)RecyclerView fragment_user_rv;
    SearchDao searchDao;
    @BindView(R.id.fragment_user_empty_iv)ImageView fragment_user_empty_iv;
    SearchUserAdapter searchUserAdapter;
    List<PeopleInfo> SearchUserList;
    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this,view);
        fragment_user_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        //this.SearchUserList=SearchActivity.peopleInfoList;
        searchDao=new SearchDaoImp(this);
        searchDao.SearchUser(SearchActivity.text);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void InitUserData(List<PeopleInfo> searchUserList) {
        searchUserAdapter=new SearchUserAdapter(searchUserList,this,getContext());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fragment_user_rv.setAdapter(searchUserAdapter);
            }
        });
    }

    public void InitImage() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fragment_user_empty_iv.setVisibility(View.VISIBLE);
                fragment_user_rv.setVisibility(View.GONE);
            }
        });

    }
}
