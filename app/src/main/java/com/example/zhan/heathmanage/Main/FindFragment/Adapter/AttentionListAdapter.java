package com.example.zhan.heathmanage.Main.FindFragment.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.AttentionActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.PeopleInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.UserFragment;
import com.example.zhan.heathmanage.Main.FindFragment.Service.PeopleListDao;
import com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp.PeopleListDaoImp;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.zhan.heathmanage.MyApplication.getContext;

//关注列表
public class AttentionListAdapter extends RecyclerView.Adapter<AttentionListAdapter.AttentionListViewHolder> {
    private Context mContext;
    private List<PeopleInfo> attentionList;
    AttentionActivity attentionActivity;
    PeopleListDao peopleListDao;
    UserFragment userFragment;

    public AttentionListAdapter(List<PeopleInfo> attentionList, UserFragment userFragment) {
        this.attentionList = attentionList;
        this.userFragment = userFragment;
    }

    public AttentionListAdapter(Context mContext, List<PeopleInfo> attentionList) {
        this.mContext = mContext;
        this.attentionList = attentionList;
    }

    public AttentionListAdapter(Context mContext, List<PeopleInfo> attentionList, UserFragment userFragment) {
        this.mContext = mContext;
        this.attentionList = attentionList;
        this.userFragment = userFragment;
    }

    public AttentionListAdapter(Context mContext, List<PeopleInfo> attentionList, AttentionActivity attentionActivity) {
        this.mContext = mContext;
        this.attentionList = attentionList;
        this.attentionActivity = attentionActivity;
    }

    @NonNull
    @Override
    public AttentionListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.attentionlist_item,viewGroup,false);
        peopleListDao=new PeopleListDaoImp();
        return new AttentionListAdapter.AttentionListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AttentionListViewHolder attentionListViewHolder, final int i) {
        attentionListViewHolder.attentionlist_item_nickname_tv.setText(attentionList.get(i).getPeopleNickName());
        Glide.with(getContext())
                .load(attentionList.get(i).getPeopleImage())
                .asBitmap()
                .error(R.drawable.head)
                .into(attentionListViewHolder.attentionlist_item_riv);
//        attentionListViewHolder.attentionlist_item_haveattention_bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                peopleListDao.RemoveConcern(MyApplication.getUserId(),attentionList.get(i).getUserid());
//                notifyDataSetChanged();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return attentionList.size();
    }

    public class AttentionListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.attentionlist_item_riv)RoundedImageView attentionlist_item_riv;
        @BindView(R.id.attentionlist_item_haveattention_bt)Button attentionlist_item_haveattention_bt;
        @BindView(R.id.attentionlist_item_nickname_tv)TextView attentionlist_item_nickname_tv;
        public AttentionListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
