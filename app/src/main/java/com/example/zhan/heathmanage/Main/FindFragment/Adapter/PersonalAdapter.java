package com.example.zhan.heathmanage.Main.FindFragment.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.ImageInfoActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.InvitationInfoActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.PersonalActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.AttentionInfo;
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

public class PersonalAdapter extends RecyclerView.Adapter<PersonalAdapter.PersonalViewHolder> {
    List<AttentionInfo>attentionInfoList;
    PersonalActivity personalActivity;
    AttentionDao attentionDao;

    public PersonalAdapter(List<AttentionInfo> attentionInfoList, PersonalActivity personalActivity) {
        this.attentionInfoList = attentionInfoList;
        this.personalActivity = personalActivity;
    }

    @NonNull
    @Override
    public PersonalAdapter.PersonalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_personal_item, viewGroup, false);
        attentionDao=new AttentionDaoImp(attentionInfoList);
        return new PersonalAdapter.PersonalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PersonalAdapter.PersonalViewHolder personalViewHolder, final int i) {
        personalViewHolder.personalitem_nickname_tv.setText(attentionInfoList.get(i).getNickName());
        personalViewHolder.personalitem_commentnum_tv.setText(attentionInfoList.get(i).getCommentNum());
        personalViewHolder.personalitem_content_tv.setText(attentionInfoList.get(i).getContent());
        personalViewHolder.personalitem_supportnum_tv.setText(attentionInfoList.get(i).getSupportNum());
        Glide.with(getContext())
                .load(attentionInfoList.get(i).getImage())
                .asBitmap()
                .error(R.drawable.welcome)
                .into(personalViewHolder.personalitem_image_riv);
        if(attentionInfoList.get(i).getPciture().equals("null")|| attentionInfoList.get(i).getPciture()==null){

        }else {
            Glide.with(getContext())
                    .load(attentionInfoList.get(i).getPciture())
                    .asBitmap()
                    .error(R.drawable.welcome)
                    .into(personalViewHolder.personalitem_picture1_iv);
        }

        if(attentionInfoList.get(i).getLocation().equals("null")){
            personalViewHolder.personalitem_location_tv.setText("");
        }else {
            personalViewHolder.personalitem_location_tv.setText(attentionInfoList.get(i).getLocation());
        }
        personalViewHolder.personalitem_time_tv.setText(attentionInfoList.get(i).getTime());
        personalViewHolder.personalitem_commentnum_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(personalActivity, InvitationInfoActivity.class);
                intent .putExtra("userimage",attentionInfoList.get(i).getImage());
                intent.putExtra("picture1",attentionInfoList.get(i).getPciture());
                intent.putExtra("nickname",attentionInfoList.get(i).getNickName());
                intent.putExtra("supportnum",attentionInfoList.get(i).getSupportNum());
                intent.putExtra("content",attentionInfoList.get(i).getContent());
                intent.putExtra("postingId",attentionInfoList.get(i).getPostingId());
                intent.putExtra("userId",InvitationInfoActivity.userId);
                personalActivity.startActivity(intent);
            }
        });
//        personalViewHolder.personalitem_supportnum_ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                personalViewHolder.personalitem_supportnum_iv.setImageResource(R.drawable.support);
//                attentionDao.Support(attentionInfoList.get(i).getPostingId());
//                if(personalViewHolder.personalitem_supportnum_iv.getTag().equals("nosupport")){
//                    personalViewHolder.personalitem_supportnum_iv.setTag("support");
//                    personalViewHolder.personalitem_supportnum_iv.setImageResource(R.drawable.support);
//                    attentionDao.Support(attentionInfoList.get(i).getPostingId());
//                }else {
//                    personalViewHolder.personalitem_supportnum_iv.setTag("nosupport");
//                    personalViewHolder.personalitem_supportnum_iv.setImageResource(R.drawable.support_nopress);
//                    attentionDao.CancelLikePosting(attentionInfoList.get(i).getPostingId());
//                }
                personalViewHolder.personalitem_supportnum_sb.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(View view, boolean checked) {
                        if(checked==true){
                            attentionDao.Support(attentionInfoList.get(i).getPostingId());
                        }else {
                            attentionDao.CancelLikePosting(attentionInfoList.get(i).getPostingId());
                        }
                    }
                });
//            }
//        });
        personalViewHolder.personalitem_picture1_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ImageInfoActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation((Activity) view.getContext(),
                                view, "sharedView");
                intent.putExtra("image",attentionInfoList.get(i).getPciture());
                startActivity(intent,options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return attentionInfoList.size();
    }

    public class PersonalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.personalitem_nickname_tv)TextView personalitem_nickname_tv;
        @BindView(R.id.personalitem_content_tv)TextView personalitem_content_tv;
        @BindView(R.id.personalitem_image_riv)RoundedImageView personalitem_image_riv;
        @BindView(R.id.personalitem_time_tv)TextView personalitem_time_tv;
        @BindView(R.id.personalitem_picture1_iv)ImageView personalitem_picture1_iv;
        @BindView(R.id.personalitem_supportnum_tv)TextView personalitem_supportnum_tv;
        @BindView(R.id.personalitem_commentnum_tv)TextView personalitem_commentnum_tv;
        @BindView(R.id.personalitem_commentnum_ll)LinearLayout personalitem_commentnum_ll;
        @BindView(R.id.personalitem_supportnum_ll)LinearLayout personalitem_supportnum_ll;
        @BindView(R.id.personalitem_supportnum_sb)ShineButton personalitem_supportnum_sb;
        @BindView(R.id.personalitem_location_tv)TextView personalitem_location_tv;
        @BindView(R.id.personalitem_down_ib)ImageButton personalitem_down_ib;
        public PersonalViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
