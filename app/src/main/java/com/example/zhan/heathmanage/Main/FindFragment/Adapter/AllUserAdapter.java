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

import com.bumptech.glide.Glide;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.PersonalActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.PeopleInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.AllFragment;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.UserFragment;
import com.makeramen.roundedimageview.RoundedImageView;
import com.example.zhan.heathmanage.R;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.zhan.heathmanage.MyApplication.getContext;

public class AllUserAdapter extends RecyclerView.Adapter<AllUserAdapter.AllUserViewHolder> {
    List<PeopleInfo> AllUserList;
    AllFragment allFragment;
    private Context mContext;

    public AllUserAdapter(List<PeopleInfo> AllUserList, AllFragment allFragment, Context mContext) {
        this.AllUserList = AllUserList;
        this.allFragment = allFragment;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public AllUserAdapter.AllUserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.alluser_item,viewGroup,false);
        return new AllUserAdapter.AllUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllUserAdapter.AllUserViewHolder allUserViewHolder, final int i) {
        allUserViewHolder.alluser_item_nickname_tv.setText(AllUserList.get(i).getPeopleNickName());
        Glide.with(getContext())
                .load(AllUserList.get(i).getPeopleImage())
                .asBitmap()
                .error(R.drawable.head)
                .into(allUserViewHolder.alluser_item_riv);
        allUserViewHolder.alluser_item_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(allFragment.getActivity(), PersonalActivity.class);
                intent.putExtra("userId",AllUserList.get(i).getUserid());
                allFragment.getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(AllUserList.size()>3) {
            return 3;
        }else {
            return AllUserList.size();
        }
    }

    public class AllUserViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.alluser_item_ll)LinearLayout alluser_item_ll;
        @BindView(R.id.alluser_item_riv)RoundedImageView alluser_item_riv;
        @BindView(R.id.alluser_item_nickname_tv)TextView alluser_item_nickname_tv;
        public AllUserViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
