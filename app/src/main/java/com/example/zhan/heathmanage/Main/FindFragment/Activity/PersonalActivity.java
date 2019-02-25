package com.example.zhan.heathmanage.Main.FindFragment.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Adapter.PeopleListAdapter;
import com.example.zhan.heathmanage.Main.FindFragment.Adapter.PersonalAdapter;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.AttentionInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.PersonalInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Service.PeopleListDao;
import com.example.zhan.heathmanage.Main.FindFragment.Service.PersonalDao;
import com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp.PeopleListDaoImp;
import com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp.PersonalDaoImp;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.zhan.heathmanage.MyApplication.getContext;

public class PersonalActivity extends BaseActivity {
    @BindView(R.id.personal_attention_bt)
    Button personal_attention_bt;
    @BindView(R.id.personal_haveattention_bt)
    Button personal_haveattention_bt;
    @BindView(R.id.personal_attentionnum_tv)
    TextView personal_attentionnum_tv;
    @BindView(R.id.personal_fan_tv)
    TextView personal_fan_tv;
    @BindView(R.id.personal_nickname_tv)
    TextView personal_nickname_tv;
    @BindView(R.id.personal_image_riv)
    RoundedImageView personal_image_riv;
    @BindView(R.id.personal_sex_iv)
    ImageView personal_sex_iv;
    @BindView(R.id.personal_rv)
    RecyclerView personal_rv;
    public static String userId;
    PersonalDao personalDao;
    PersonalAdapter personalAdapter;
    PeopleListDao peopleListDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        userId = getIntent().getStringExtra("userId");
        personalDao = new PersonalDaoImp(this);
        peopleListDao=new PeopleListDaoImp(this);
        personal_rv.setLayoutManager(new LinearLayoutManager(this));

        personalDao.getPersonal(userId);
        personalDao.GetPersonalList(userId);
        personalDao.GetFollowStatus(MyApplication.getUserId(),userId);
        if (userId.equals(MyApplication.getUserId())){
            personal_attention_bt.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.personal_back_ib)
    public void personal_back_ib_Onclick() {
        finish();
    }
    @OnClick(R.id.personal_attention_tv)
    public void personal_attention_tv_Onclick(){
//        Intent intent=new Intent(PersonalActivity.this,AttentionActivity.class);
//        intent.putExtra("usrtId",userId);
//        startActivity(intent);
    }

    @OnClick(R.id.personal_attention_bt)
    public void personal_attention_bt_Onclick() {
        peopleListDao.attention(MyApplication.getUserId(),userId);
        personal_haveattention_bt.setVisibility(View.VISIBLE);
        personal_attention_bt.setVisibility(View.GONE);
    }

    @OnClick(R.id.personal_haveattention_bt)
    public void personal_haveattention_bt_Onclick() {
        peopleListDao.RemoveConcern(MyApplication.getUserId(),userId);
        personal_haveattention_bt.setVisibility(View.GONE);
        personal_attention_bt.setVisibility(View.VISIBLE);
    }
    public void InitPersonalInfo(final PersonalInfo personalInfo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (personalInfo.getUserSex().equals("女")) {
                    Glide.with(getContext())
                            .load(R.drawable.woman_click)
                            .asBitmap()
                            .error(R.drawable.head)
                            .into(personal_sex_iv);
                } else {
                    Glide.with(getContext())
                            .load(R.drawable.man_click)
                            .asBitmap()
                            .error(R.drawable.head)
                            .into(personal_sex_iv);
                }
                personal_attentionnum_tv.setText(personalInfo.getUserFollowNum());
                personal_nickname_tv.setText(personalInfo.getUserNickName());
                Glide.with(getContext())
                        .load(personalInfo.getUserPhono())
                        .asBitmap()
                        .error(R.drawable.head)
                        .into(personal_image_riv);
                personal_fan_tv.setText(personalInfo.getUserFollowedNum());
            }
        });

    }

    public void InitPersonalList(List<AttentionInfo> attentionInfoList) {
        personalAdapter=new PersonalAdapter(attentionInfoList,this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                personal_rv.setAdapter(personalAdapter);
            }
        });
    }
    //相互关注
    public void Concern() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                personal_haveattention_bt.setVisibility(View.VISIBLE);
                personal_attention_bt.setVisibility(View.GONE);
            }
        });

    }

    //没有关注
    public void NoConcern() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                personal_attention_bt.setVisibility(View.VISIBLE);
                personal_haveattention_bt.setVisibility(View.GONE);
            }
        });

    }

    public void Self() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                personal_attention_bt.setVisibility(View.GONE);
                personal_haveattention_bt.setVisibility(View.GONE);
            }
        });
    }
}
