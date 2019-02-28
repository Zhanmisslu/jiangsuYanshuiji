package com.example.zhan.heathmanage.Main.FindFragment.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Adapter.PeopleListAdapter;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.PeopleInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Service.PeopleListDao;
import com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp.PeopleListDaoImp;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddFriendActivity extends BaseActivity {
    @BindView(R.id.peoplelist_rv)RecyclerView peoplelist_rv;
    PeopleListAdapter peopleListAdapter;
    PeopleListDao peopleListDao;
  //  List<PeopleInfo> peopleInfoList =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        peopleListDao=new PeopleListDaoImp(this);
        peopleListDao.getPeopleList();

    }
    public void InitPeopleList(final List<PeopleInfo> peopleInfoList){
        peopleListAdapter=new PeopleListAdapter(this,peopleInfoList,this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                peoplelist_rv.setLayoutManager(layoutManager);
                peoplelist_rv.setAdapter(peopleListAdapter);
            }
        });


    }

    @OnClick(R.id.addfriend_back_ib)
    public void addfriend_back_ib_Onclick(){
        finish();
    }
}
