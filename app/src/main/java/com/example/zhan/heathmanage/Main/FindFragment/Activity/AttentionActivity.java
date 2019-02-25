package com.example.zhan.heathmanage.Main.FindFragment.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Adapter.AttentionAdapter;
import com.example.zhan.heathmanage.Main.FindFragment.Adapter.AttentionListAdapter;
import com.example.zhan.heathmanage.Main.FindFragment.Adapter.PeopleListAdapter;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.PeopleInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Service.AttentionDao;
import com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp.AttentionDaoImp;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AttentionActivity extends BaseActivity {
    @BindView(R.id.attention_num_tv)TextView attention_num_tv;
    @BindView(R.id.attention_rv)RecyclerView attention_rv;
    AttentionListAdapter attentionListAdapter;
    AttentionDao attentionDao;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention);
        userId=getIntent().getStringExtra("userId");
        attentionDao=new AttentionDaoImp(this);
        attentionDao.GetFollowUserList(userId);
    }
    @OnClick(R.id.attention_back_ib)
    public void attention_back_ib_Onlick(){
        finish();
    }

    public void InitList(List<PeopleInfo> attentionList) {
        attentionListAdapter=new AttentionListAdapter(this,attentionList,this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                attention_rv.setLayoutManager(layoutManager);
                attention_rv.setAdapter(attentionListAdapter);
            }
        });
    }
}
