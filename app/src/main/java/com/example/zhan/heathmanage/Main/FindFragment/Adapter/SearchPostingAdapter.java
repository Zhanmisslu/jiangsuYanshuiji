package com.example.zhan.heathmanage.Main.FindFragment.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import android.support.annotation.NonNull;


import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.ImageInfoActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.InvitationInfoActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.PersonalActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.AttentionInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.AttentionFragment;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.DynamicFragment;
import com.example.zhan.heathmanage.Main.FindFragment.Service.AttentionDao;
import com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp.AttentionDaoImp;
import com.example.zhan.heathmanage.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;
import static com.example.zhan.heathmanage.MyApplication.getContext;

public class SearchPostingAdapter extends RecyclerView.Adapter<SearchPostingAdapter.SearchPostingViewHolder> {
     DynamicFragment dynamicFragment;
    List<AttentionInfo> SearchPostingList;
    AttentionDao attentionDao;
    String num;
    Context mcontext;

    public SearchPostingAdapter(DynamicFragment dynamicFragment, List<AttentionInfo> searchPostingList, Context mcontext) {
        this.dynamicFragment = dynamicFragment;
        SearchPostingList = searchPostingList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public SearchPostingAdapter.SearchPostingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mcontext)
                .inflate(R.layout.searchposting_item, viewGroup, false);
        attentionDao = new AttentionDaoImp(SearchPostingList);
        return new SearchPostingAdapter.SearchPostingViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final SearchPostingAdapter.SearchPostingViewHolder searchPostingViewHolder, final int i) {
        searchPostingViewHolder.searchpostingitem_usernickname_tv.setText(SearchPostingList.get(i).getNickName());
        searchPostingViewHolder.searchpostingitem_commentnum_tv.setText(SearchPostingList.get(i).getCommentNum());
        searchPostingViewHolder.searchpostingitem_content_tv.setText(SearchPostingList.get(i).getContent());
        searchPostingViewHolder.searchpostingitem_supportnum_tv.setText(SearchPostingList.get(i).getSupportNum());
        searchPostingViewHolder.searchpostingitem_commentnum_tv.setText(SearchPostingList.get(i).getCommentNum());
        Glide.with(getContext())
                .load(SearchPostingList.get(i).getImage())
                .asBitmap()
                .error(R.drawable.welcome)
                .into(searchPostingViewHolder.searchpostingitem_image_riv);
        if(SearchPostingList.get(i).getPciture()==null){
            searchPostingViewHolder.searchpostingitem_picture1_iv.setVisibility(View.GONE);
        }else {
            Glide.with(getContext())
                    .load(SearchPostingList.get(i).getPciture())
                    .asBitmap()
                    .error(R.drawable.welcome)
                    .into(searchPostingViewHolder.searchpostingitem_picture1_iv);
        }
        searchPostingViewHolder.searchpostingitem_time_tv.setText(SearchPostingList.get(i).getTime());
        searchPostingViewHolder.searchpostingitem_commentnum_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dynamicFragment.getActivity(), InvitationInfoActivity.class);
                intent.putExtra("userimage", SearchPostingList.get(i).getImage());
                intent.putExtra("picture1", SearchPostingList.get(i).getPciture());
                intent.putExtra("nickname", SearchPostingList.get(i).getNickName());
                intent.putExtra("supportnum", SearchPostingList.get(i).getSupportNum());
                intent.putExtra("content", SearchPostingList.get(i).getContent());
                intent.putExtra("postingId", SearchPostingList.get(i).getPostingId());
                intent.putExtra("userId", SearchPostingList.get(i).getUserId());
                dynamicFragment.startActivity(intent);
            }
        });
//        attentionViewHolder.attentionitem_supportnum_ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                attentionViewHolder.attentionitem_supportnum_iv.setImageResource(R.drawable.support);
//                attentionDao.Support(AttentionList.get(i).getPostingId());
//            }
//        });
        searchPostingViewHolder.searchpostingitem_supportnum_sb.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                if (checked == true) {// 已经点赞
                    attentionDao.Support(SearchPostingList.get(i).getPostingId());
                    num = String.valueOf((Integer.valueOf(SearchPostingList.get(i).getSupportNum()) + 1));
                    searchPostingViewHolder.searchpostingitem_supportnum_tv.setText(num);
                } else {
                    attentionDao.CancelLikePosting(SearchPostingList.get(i).getPostingId());
                    num = String.valueOf((Integer.valueOf(SearchPostingList.get(i).getSupportNum())));
                    searchPostingViewHolder.searchpostingitem_supportnum_tv.setText(num);
                }
            }
        });
        searchPostingViewHolder.searchpostingitem_image_riv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dynamicFragment.getActivity(), PersonalActivity.class);
                intent.putExtra("userId", SearchPostingList.get(i).getUserId());
                startActivity(intent);
            }
        });
        searchPostingViewHolder.searchpostingitem_picture1_iv.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
//                view.getContext().startActivity(
//                        new Intent(view.getContext(), ImageInfoActivity.class),
//                        // 注意这里的sharedView
//                        // Content，View（动画作用view），String（和XML一样）
//                        ActivityOptions.makeSceneTransitionAnimation((Activity) view.getContext(), view, "sharedView").toBundle());
                Intent intent = new Intent(view.getContext(), ImageInfoActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation((Activity) view.getContext(),
                                view, "sharedView");
                intent.putExtra("image",SearchPostingList.get(i).getPciture());

                ((Activity) view.getContext()).startActivityForResult(intent,102,options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return SearchPostingList.size();
    }

    public class SearchPostingViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.searchpostingitem_usernickname_tv)
        TextView searchpostingitem_usernickname_tv;
        @BindView(R.id.searchpostingitem_content_tv)
        TextView searchpostingitem_content_tv;
        @BindView(R.id.searchpostingitem_image_riv)
        RoundedImageView searchpostingitem_image_riv;
        @BindView(R.id.searchpostingitem_time_tv)
        TextView searchpostingitem_time_tv;
        @BindView(R.id.searchpostingitem_picture1_iv)
        ImageView searchpostingitem_picture1_iv;
        @BindView(R.id.searchpostingitem_supportnum_tv)
        TextView searchpostingitem_supportnum_tv;
        @BindView(R.id.searchpostingitem_commentnum_tv)
        TextView searchpostingitem_commentnum_tv;
        @BindView(R.id.searchpostingitem_commentnum_ll)
        LinearLayout searchpostingitem_commentnum_ll;
        @BindView(R.id.searchpostingitem_supportnum_ll)
        LinearLayout searchpostingitem_supportnum_ll;
        @BindView(R.id.searchpostingitem_supportnum_sb)
        ShineButton searchpostingitem_supportnum_sb;

        public SearchPostingViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
