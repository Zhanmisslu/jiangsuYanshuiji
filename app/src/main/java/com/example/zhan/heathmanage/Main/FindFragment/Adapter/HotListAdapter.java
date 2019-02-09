package com.example.zhan.heathmanage.Main.FindFragment.Adapter;

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
import com.example.zhan.heathmanage.Main.FindFragment.Bean.HotInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.HotFragment;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    public void onBindViewHolder(@NonNull HotListAdapter.HotListViewHolder hotListViewHolder, int i) {
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

            }
        });
        if (i % 2==0) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(hotListViewHolder.picture_iv.getLayoutParams());
//            lp.setMargins(0,100,0,0);
            //LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) hotListViewHolder.picture_iv.getLayoutParams();
            lp.height=450;
            hotListViewHolder.picture_iv.setLayoutParams(lp);
            } else {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(hotListViewHolder.picture_iv.getLayoutParams());
//            lp.setMargins(0,0,0,0);
            lp.height=700;
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
