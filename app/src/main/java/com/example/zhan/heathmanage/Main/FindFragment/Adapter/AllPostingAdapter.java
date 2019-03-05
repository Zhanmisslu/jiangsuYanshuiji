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
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.AllFragment;
import com.example.zhan.heathmanage.Main.FindFragment.Service.AttentionDao;
import com.example.zhan.heathmanage.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;
import static com.example.zhan.heathmanage.MyApplication.getContext;

public class AllPostingAdapter extends RecyclerView.Adapter<AllPostingAdapter.AllPostViewHolder>{
    List<AttentionInfo>AllPostingList;
    AllFragment allFragment;
    Context mcontext;
    AttentionDao attentionDao;
    String num;
    public AllPostingAdapter(List<AttentionInfo> allPostingList, AllFragment allFragment, Context mcontext) {
        AllPostingList = allPostingList;
        this.allFragment = allFragment;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public AllPostingAdapter.AllPostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.searchposting_item,viewGroup,false);
        return new AllPostingAdapter.AllPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AllPostingAdapter.AllPostViewHolder allPostViewHolder, final int i) {
        allPostViewHolder.searchpostingitem_usernickname_tv.setText(AllPostingList.get(i).getNickName());
        allPostViewHolder.searchpostingitem_commentnum_tv.setText(AllPostingList.get(i).getCommentNum());
        allPostViewHolder.searchpostingitem_content_tv.setText(AllPostingList.get(i).getContent());
        allPostViewHolder.searchpostingitem_supportnum_tv.setText(AllPostingList.get(i).getSupportNum());
        allPostViewHolder.searchpostingitem_commentnum_tv.setText(AllPostingList.get(i).getCommentNum());
        Glide.with(getContext())
                .load(AllPostingList.get(i).getImage())
                .asBitmap()
                .error(R.drawable.welcome)
                .into(allPostViewHolder.searchpostingitem_image_riv);
        if(AllPostingList.get(i).getPciture()==null){
            allPostViewHolder.searchpostingitem_picture1_iv.setVisibility(View.GONE);
        }else {
            Glide.with(getContext())
                    .load(AllPostingList.get(i).getPciture())
                    .asBitmap()
                    .error(R.drawable.welcome)
                    .into(allPostViewHolder.searchpostingitem_picture1_iv);
        }
        allPostViewHolder.searchpostingitem_time_tv.setText(AllPostingList.get(i).getTime());
        allPostViewHolder.searchpostingitem_commentnum_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(allFragment.getActivity(), InvitationInfoActivity.class);
                intent.putExtra("userimage", AllPostingList.get(i).getImage());
                intent.putExtra("picture1", AllPostingList.get(i).getPciture());
                intent.putExtra("nickname", AllPostingList.get(i).getNickName());
                intent.putExtra("supportnum", AllPostingList.get(i).getSupportNum());
                intent.putExtra("content", AllPostingList.get(i).getContent());
                intent.putExtra("postingId", AllPostingList.get(i).getPostingId());
                intent.putExtra("userId", AllPostingList.get(i).getUserId());
                allFragment.startActivity(intent);
            }
        });
//        attentionViewHolder.attentionitem_supportnum_ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                attentionViewHolder.attentionitem_supportnum_iv.setImageResource(R.drawable.support);
//                attentionDao.Support(AttentionList.get(i).getPostingId());
//            }
//        });
        allPostViewHolder.searchpostingitem_supportnum_sb.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                if (checked == true) {// 已经点赞
                    attentionDao.Support(AllPostingList.get(i).getPostingId());
                    num = String.valueOf((Integer.valueOf(AllPostingList.get(i).getSupportNum()) + 1));
                    allPostViewHolder.searchpostingitem_supportnum_tv.setText(num);
                } else {
                    attentionDao.CancelLikePosting(AllPostingList.get(i).getPostingId());
                    num = String.valueOf((Integer.valueOf(AllPostingList.get(i).getSupportNum())));
                    allPostViewHolder.searchpostingitem_supportnum_tv.setText(num);
                }
            }
        });
        allPostViewHolder.searchpostingitem_image_riv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(allFragment.getActivity(), PersonalActivity.class);
                intent.putExtra("userId", AllPostingList.get(i).getUserId());
                startActivity(intent);
            }
        });
        allPostViewHolder.searchpostingitem_picture1_iv.setOnClickListener(new View.OnClickListener() {
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
                intent.putExtra("image",AllPostingList.get(i).getPciture());

                ((Activity) view.getContext()).startActivityForResult(intent,102,options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(AllPostingList.size()>3){
            return 3;
        }else {
            return AllPostingList.size();
        }
    }

    public class AllPostViewHolder extends RecyclerView.ViewHolder {
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
        public AllPostViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
