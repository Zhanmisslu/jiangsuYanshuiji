package com.example.zhan.heathmanage.Main.FindFragment.Adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.NewsInfoActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.NewsBean;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.FashionFragment;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.HeathFragment;
import com.example.zhan.heathmanage.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.zhan.heathmanage.MyApplication.getContext;

public class FashionAdapter extends RecyclerView.Adapter<FashionAdapter.NewsViewHolder> {
    List<NewsBean> newsBeanList;

    FashionFragment fashionFragment;



    public FashionAdapter(List<NewsBean> newsBeanList, FashionFragment fashionFragment) {
        this.newsBeanList = newsBeanList;
        this.fashionFragment = fashionFragment;
    }

    @NonNull
    @Override
    public FashionAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.news_item, viewGroup, false);
       // attentionDao = new AttentionDaoImp(AttentionList);
        return new FashionAdapter.NewsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FashionAdapter.NewsViewHolder newsViewHolder, final int i) {
        newsViewHolder.newsitem_authorname_tv.setText(newsBeanList.get(i).getAuthor_name());
        newsViewHolder.newsitem_time_tv.setText(newsBeanList.get(i).getDate());
        newsViewHolder.newsitem_title_tv.setText(newsBeanList.get(i).getTitle());
        Glide.with(getContext())
                .load(newsBeanList.get(i).getPhotourl())
                .asBitmap()
                .error(R.drawable.welcome)
                .into(newsViewHolder.newsitem_image_tiv);
        newsViewHolder.newsitem_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri= Uri.parse(newsBeanList.get(i).getUrl());
                String url= String.valueOf(uri);
                Intent intent=new Intent(fashionFragment.getActivity(), NewsInfoActivity.class);
                intent.putExtra("URL",url);
                fashionFragment.getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsBeanList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.newsitem_authorname_tv)TextView newsitem_authorname_tv;
        @BindView(R.id.newsitem_time_tv)TextView newsitem_time_tv;
        @BindView(R.id.newsitem_image_iv)ImageView newsitem_image_tiv;
        @BindView(R.id.newsitem_title_tv)TextView newsitem_title_tv;
        @BindView(R.id.newsitem_ll)LinearLayout newsitem_ll;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
