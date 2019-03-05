package com.example.zhan.heathmanage.Main.FindFragment.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.PersonalActivity;
import com.example.zhan.heathmanage.R;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.PeopleInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.UserFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.zhan.heathmanage.MyApplication.getContext;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.SearchUserViewHolder> {
    List<PeopleInfo> SearchUserList;
    UserFragment userFragment;
    private Context mContext;

    public SearchUserAdapter(List<PeopleInfo> searchUserList, UserFragment userFragment, Context mContext) {
        SearchUserList = searchUserList;
        this.userFragment = userFragment;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public SearchUserAdapter.SearchUserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.searchuser_item,viewGroup,false);

        return new SearchUserAdapter.SearchUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchUserAdapter.SearchUserViewHolder searchUserViewHolder, final int i) {
        searchUserViewHolder.searchuser_item_nickname_tv.setText(SearchUserList.get(i).getPeopleNickName());
        Glide.with(getContext())
                .load(SearchUserList.get(i).getPeopleImage())
                .asBitmap()
                .error(R.drawable.head)
                .into(searchUserViewHolder.searchuser_item_riv);
        searchUserViewHolder.searchuser_item_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(userFragment.getActivity(), PersonalActivity.class);
                intent.putExtra("userId",SearchUserList.get(i).getUserid());
                userFragment.getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return SearchUserList.size();
    }

    public class SearchUserViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.searchuser_item_riv)RoundedImageView searchuser_item_riv;
        @BindView(R.id.searchuser_item_nickname_tv)TextView searchuser_item_nickname_tv;
        @BindView(R.id.searchuser_item_ll)LinearLayout searchuser_item_ll;
        public SearchUserViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
