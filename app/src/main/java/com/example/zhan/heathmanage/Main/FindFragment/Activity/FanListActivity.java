package com.example.zhan.heathmanage.Main.FindFragment.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Adapter.AttentionListAdapter;
import com.example.zhan.heathmanage.Main.FindFragment.Adapter.FanListAdapter;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.PeopleInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Service.AttentionDao;
import com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp.AttentionDaoImp;
import com.example.zhan.heathmanage.R;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FanListActivity extends BaseActivity {
    @BindView(R.id.fanlist_num_tv)TextView fanlist_num_tv;
    @BindView(R.id.fanlist_rv)RecyclerView fanlist_rv;
    FanListAdapter fanListAdapter;
    AttentionDao attentionDao;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan_list);
        userId=getIntent().getStringExtra("userId");
        attentionDao=new AttentionDaoImp(this);
        attentionDao.GetFollowedUserList(userId);
    }
    @OnClick(R.id.fanlist_back_ib)
    public void fanlist_back_ib_Onclick(){
        finish();
    }

    public void InitFanList(final List<PeopleInfo> fanList) {
        fanListAdapter=new FanListAdapter(this,fanList,this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fanlist_num_tv.setText(fanList.size()+"个粉丝");
                fanlist_rv.setLayoutManager(layoutManager);
                fanlist_rv.setAdapter(fanListAdapter);
            }
        });
    }

    public void noData(final List<PeopleInfo> fanList) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fanlist_num_tv.setText(fanList.size()+"个粉丝");
            }
        });
    }
}
