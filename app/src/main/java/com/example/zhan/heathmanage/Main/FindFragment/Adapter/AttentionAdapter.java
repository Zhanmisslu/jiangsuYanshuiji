package com.example.zhan.heathmanage.Main.FindFragment.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
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

public class AttentionAdapter extends RecyclerView.Adapter<AttentionAdapter.AttentionViewHolder> {
    AttentionFragment attentionFragment;
    List<AttentionInfo> AttentionList;
    AttentionDao attentionDao;
    String num;

    public AttentionAdapter(AttentionFragment attentionFragment, List<AttentionInfo> attentionList) {
        this.attentionFragment = attentionFragment;
        AttentionList = attentionList;
    }

    @NonNull
    @Override
    public AttentionAdapter.AttentionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_attention_item, viewGroup, false);
        attentionDao = new AttentionDaoImp(AttentionList);
        return new AttentionAdapter.AttentionViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final AttentionAdapter.AttentionViewHolder attentionViewHolder, final int i) {
        attentionViewHolder.attentionitem_nickname_tv.setText(AttentionList.get(i).getNickName());
        attentionViewHolder.attentionitem_commentnum_tv.setText(AttentionList.get(i).getCommentNum());
        attentionViewHolder.attentionitem_content_tv.setText(AttentionList.get(i).getContent());
        attentionViewHolder.attentionitem_supportnum_tv.setText(AttentionList.get(i).getSupportNum());
        attentionViewHolder.attentionitem_commentnum_tv.setText(AttentionList.get(i).getCommentNum());
        Glide.with(getContext())
                .load(AttentionList.get(i).getImage())
                .asBitmap()
                .error(R.drawable.welcome)
                .into(attentionViewHolder.attentionitem_image_riv);
        if(AttentionList.get(i).getPciture()==null){
            attentionViewHolder.attentionitem_picture1_iv.setVisibility(View.GONE);
        }else {
            Glide.with(getContext())
                    .load(AttentionList.get(i).getPciture())
                    .asBitmap()
                    .error(R.drawable.welcome)
                    .into(attentionViewHolder.attentionitem_picture1_iv);
        }
        attentionViewHolder.attentionitem_time_tv.setText(AttentionList.get(i).getTime());
        attentionViewHolder.attentionitem_commentnum_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(attentionFragment.getActivity(), InvitationInfoActivity.class);
                intent.putExtra("userimage", AttentionList.get(i).getImage());
                intent.putExtra("picture1", AttentionList.get(i).getPciture());
                intent.putExtra("nickname", AttentionList.get(i).getNickName());
                intent.putExtra("supportnum", AttentionList.get(i).getSupportNum());
                intent.putExtra("content", AttentionList.get(i).getContent());
                intent.putExtra("postingId", AttentionList.get(i).getPostingId());
                intent.putExtra("userId", AttentionList.get(i).getUserId());
                attentionFragment.startActivity(intent);
            }
        });
//        attentionViewHolder.attentionitem_supportnum_ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                attentionViewHolder.attentionitem_supportnum_iv.setImageResource(R.drawable.support);
//                attentionDao.Support(AttentionList.get(i).getPostingId());
//            }
//        });
        attentionViewHolder.attentionitem_supportnum_sb.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                if (checked == true) {// 已经点赞
                    attentionDao.Support(AttentionList.get(i).getPostingId());
                    num = String.valueOf((Integer.valueOf(AttentionList.get(i).getSupportNum()) + 1));
                    attentionViewHolder.attentionitem_supportnum_tv.setText(num);
                } else {
                    attentionDao.CancelLikePosting(AttentionList.get(i).getPostingId());
                    num = String.valueOf((Integer.valueOf(AttentionList.get(i).getSupportNum())));
                    attentionViewHolder.attentionitem_supportnum_tv.setText(num);
                }
            }
        });
        attentionViewHolder.attentionitem_image_riv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(attentionFragment.getActivity(), PersonalActivity.class);
                intent.putExtra("userId", AttentionList.get(i).getUserId());
                startActivity(intent);
            }
        });
        attentionViewHolder.attentionitem_picture1_iv.setOnClickListener(new View.OnClickListener() {
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
                intent.putExtra("image",AttentionList.get(i).getPciture());
                view.getContext().startActivity(intent,options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return AttentionList.size();
    }

    public class AttentionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.attentionitem_nickname_tv)
        TextView attentionitem_nickname_tv;
        @BindView(R.id.attentionitem_content_tv)
        TextView attentionitem_content_tv;
        @BindView(R.id.attentionitem_image_riv)
        RoundedImageView attentionitem_image_riv;
        @BindView(R.id.attentionitem_time_tv)
        TextView attentionitem_time_tv;
        @BindView(R.id.attentionitem_picture1_iv)
        ImageView attentionitem_picture1_iv;
        @BindView(R.id.attentionitem_supportnum_tv)
        TextView attentionitem_supportnum_tv;
        @BindView(R.id.attentionitem_commentnum_tv)
        TextView attentionitem_commentnum_tv;
        @BindView(R.id.attentionitem_commentnum_ll)
        LinearLayout attentionitem_commentnum_ll;
        @BindView(R.id.attentionitem_supportnum_ll)
        LinearLayout attentionitem_supportnum_ll;
        @BindView(R.id.attentionitem_supportnum_sb)
        ShineButton attentionitem_supportnum_sb;

        public AttentionViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
