package com.example.zhan.heathmanage.Main.FindFragment.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.InvitationInfoActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.HotInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.HotFragment;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;
import static com.example.zhan.heathmanage.MyApplication.getContext;

public class HotListAdapter extends RecyclerView.Adapter<HotListAdapter.HotListViewHolder> {
    HotFragment hotFragment;

    public HotListAdapter(HotFragment hotFragment) {
        this.hotFragment = hotFragment;
    }

    public HotListAdapter(List<HotInfo> hotList) {
        HotList = hotList;
    }

    public HotListAdapter(HotFragment hotFragment, List<HotInfo> hotList) {
        this.hotFragment = hotFragment;
        HotList = hotList;
    }

    private List<HotInfo> HotList=new ArrayList<>();
    @NonNull
    @Override
    public HotListAdapter.HotListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_hot_item, viewGroup, false);
        return new HotListAdapter.HotListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotListAdapter.HotListViewHolder hotListViewHolder, final int i) {
        hotListViewHolder.contenttitle_tv.setText(HotList.get(i).getContent());
        hotListViewHolder.nickname_tv.setText(HotList.get(i).getNickName());
        hotListViewHolder.supportNum_tv.setText(HotList.get(i).getSupportNum());
        Glide.with(getContext())
                .load(HotList.get(i).getImage())
                .asBitmap()
                .error(R.drawable.head)
                .into(hotListViewHolder.image_riv);
        Glide.with(getContext())
                .load(HotList.get(i).getPciture())
                .asBitmap()
                .error(R.drawable.welcome)
                .into(hotListViewHolder.picture_iv);
        hotListViewHolder.content_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(hotFragment.getActivity(), InvitationInfoActivity.class);
                intent .putExtra("userimage",HotList.get(i).getImage());
                intent.putExtra("picture1",HotList.get(i).getPciture());
                intent.putExtra("nickname",HotList.get(i).getNickName());
                intent.putExtra("supportnum",HotList.get(i).getSupportNum());
                intent.putExtra("content",HotList.get(i).getContent());
                intent.putExtra("time",HotList.get(i).getTime());
                intent.putExtra("postingId",HotList.get(i).getPostingId());
                intent.putExtra("userId",HotList.get(i).getUserId());
                hotFragment.startActivity(intent);
            }
        });
        if (i % 2==0) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(hotListViewHolder.picture_iv.getLayoutParams());
//            lp.setMargins(0,100,0,0);
            //LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) hotListViewHolder.picture_iv.getLayoutParams();
            lp.height=350;
            hotListViewHolder.picture_iv.setLayoutParams(lp);
            } else {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(hotListViewHolder.picture_iv.getLayoutParams());
//            lp.setMargins(0,0,0,0);
            lp.height=500;
            hotListViewHolder.picture_iv.setLayoutParams(lp);
        }

    }

    @Override
    public int getItemCount() {
        return HotList.size();
    }

    public class HotListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.contenttitle_tv)TextView contenttitle_tv;
        @BindView(R.id.picture_iv)ImageView picture_iv;
        @BindView(R.id.image_riv)RoundedImageView image_riv;
        @BindView(R.id.nickname_tv)TextView nickname_tv;
        @BindView(R.id.supportNum_tv)TextView supportNum_tv;
        @BindView(R.id.content_cv)CardView content_cv;
        public HotListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
