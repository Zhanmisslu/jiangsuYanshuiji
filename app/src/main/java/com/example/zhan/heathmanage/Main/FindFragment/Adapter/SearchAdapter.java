package com.example.zhan.heathmanage.Main.FindFragment.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhan.heathmanage.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private Context mContext;
    List<String>SearchList;

    public SearchAdapter(Context mContext, List<String> searchList) {
        this.mContext = mContext;
        SearchList = searchList;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.search_item,viewGroup,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, final int i) {
        searchViewHolder.Search_item_tv.setText(SearchList.get(i).toString());
        searchViewHolder.Search_item_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //搜索选中的词

            }
        });
    }

    @Override
    public int getItemCount() {
        return SearchList.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.Search_item_ll)LinearLayout Search_item_ll;
        @BindView(R.id.Search_item_tv)TextView Search_item_tv;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this.itemView);
        }
    }
}
