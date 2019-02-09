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
import com.example.zhan.heathmanage.Main.FindFragment.Activity.AddFriendActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.PeopleInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Service.PeopleListDao;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import static com.example.zhan.heathmanage.MyApplication.getContext;

public class PeopleListAdapter extends RecyclerView.Adapter<PeopleListAdapter.PeopleListViewHolder> {
    PeopleListDao peopleListDao;
    private Context mContext;
    private List<PeopleInfo> peopleInfoList;
    private AddFriendActivity addFriendActivity;
    public PeopleListAdapter(Context mContext, List<PeopleInfo> peopleInfoList) {
        this.mContext = mContext;
        this.peopleInfoList = peopleInfoList;
    }

    public PeopleListAdapter(Context mContext, List<PeopleInfo> peopleInfoList, AddFriendActivity addFriendActivity) {
        this.mContext = mContext;
        this.peopleInfoList = peopleInfoList;
        this.addFriendActivity = addFriendActivity;
    }

    @NonNull
    @Override
    public PeopleListAdapter.PeopleListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.peoplelist_item,viewGroup,false);
        return new PeopleListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PeopleListAdapter.PeopleListViewHolder peopleListViewHolder, int position) {
        peopleListViewHolder.nickname_tv.setText(peopleInfoList.get(position).getPeopleNickName());
        Glide.with(getContext())
                .load(peopleInfoList.get(position).getPeopleImage())
                .asBitmap()
                .error(R.drawable.head)
                .into(peopleListViewHolder.people_riv);
        peopleListViewHolder.attention_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                peopleListViewHolder.attention_bt.setVisibility(View.GONE);
                peopleListViewHolder.haveattention_bt.setVisibility(View.VISIBLE);
                peopleListDao.attention();
            }
        });
        peopleListViewHolder.haveattention_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                peopleListViewHolder.attention_bt.setVisibility(View.VISIBLE);
                peopleListViewHolder.haveattention_bt.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return peopleInfoList.size();
    }

    public class PeopleListViewHolder extends RecyclerView.ViewHolder{
        Button attention_bt;
        Button haveattention_bt;
        TextView nickname_tv;
        RoundedImageView people_riv;
        public PeopleListViewHolder(@NonNull View itemView) {
            super(itemView);
            attention_bt=itemView.findViewById(R.id.attention_bt);
            haveattention_bt=itemView.findViewById(R.id.haveattention_bt);
            nickname_tv=itemView.findViewById(R.id.nickname_tv);
            people_riv=itemView.findViewById(R.id.people_riv);
        }

    }
}
