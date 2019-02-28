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
import com.example.zhan.heathmanage.Main.FindFragment.Activity.FanListActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.PeopleInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Service.PeopleListDao;
import com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp.PeopleListDaoImp;
import com.example.zhan.heathmanage.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.zhan.heathmanage.MyApplication.getContext;

//关注列表
public class FanListAdapter extends RecyclerView.Adapter<FanListAdapter.FanListViewHolder> {
    private Context mContext;
    private List<PeopleInfo> fanlistList;
    FanListActivity fanListActivity;
    PeopleListDao peopleListDao;


    public FanListAdapter(Context mContext, List<PeopleInfo> fanlistList, FanListActivity fanListActivity) {
        this.mContext = mContext;
        this.fanlistList = fanlistList;
        this.fanListActivity = fanListActivity;
    }

    @NonNull
    @Override
    public FanListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fanlist_item,viewGroup,false);
        peopleListDao=new PeopleListDaoImp();
        return new FanListAdapter.FanListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FanListViewHolder attentionListViewHolder, final int i) {
        attentionListViewHolder.fanlist_item_nickname_tv.setText(fanlistList.get(i).getPeopleNickName());
        Glide.with(getContext())
                .load(fanlistList.get(i).getPeopleImage())
                .asBitmap()
                .error(R.drawable.head)
                .into(attentionListViewHolder.fanlist_item_riv);
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
        return fanlistList.size();
    }

    public class FanListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fanlist_item_riv)RoundedImageView fanlist_item_riv;
        @BindView(R.id.fanlist_item_haveattention_bt)Button fanlist_item_haveattention_bt;
        @BindView(R.id.fanlist_item_nickname_tv)TextView fanlist_item_nickname_tv;
        public FanListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
