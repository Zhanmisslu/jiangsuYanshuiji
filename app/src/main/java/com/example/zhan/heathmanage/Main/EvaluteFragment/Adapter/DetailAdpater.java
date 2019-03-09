package com.example.zhan.heathmanage.Main.EvaluteFragment.Adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhan.heathmanage.Main.EvaluteFragment.Beans.Details;
import com.example.zhan.heathmanage.R;

import org.w3c.dom.Text;

import java.util.List;

public class DetailAdpater extends RecyclerView.Adapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Details> list;
    public DetailAdpater(Context context,List<Details> list){
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DetailViewHodler(inflater.inflate(R.layout.item_detail,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof DetailViewHodler){
            setDetailViewItem((DetailViewHodler)viewHolder);
        }
    }
    private void setDetailViewItem(final DetailViewHodler viewHolder){
       viewHolder.item_detail_title_ll.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (viewHolder.item_detail_content_ll.getVisibility() == View.GONE){
                   viewHolder.animateOpen(viewHolder.item_detail_content_ll);
                   viewHolder.animationIvOpen();
               }else {
                   viewHolder.animateClose(viewHolder.item_detail_content_ll);
                   viewHolder.animationIvClose();
               }
           }
       });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    private class DetailViewHodler extends RecyclerView.ViewHolder {
        private LinearLayout item_detail_title_ll;
        private LinearLayout item_detail_content_ll;
        /* 测量值的
         * 图片
         * 内容
         * 测量值介绍
         * 显示是否正常
         * 下拉按钮
         */
        private ImageView item_detail_img;
        private TextView item_detail_title;
        private TextView item_detail_title_content;
        private TextView item_detail_math;
        private Button item_detail_bt;
        private ImageView item_detail_dowm;


        private TextView item_detail_low1;
        private TextView item_detail_low1_tv;
        private TextView item_detail_high1;
        private TextView item_detail_high1_tv;
        private TextView item_detail_high2;
        private TextView item_detail_high2_tv;
        /*
           测量值内容
        */
        private TextView item_detail_low;
        private TextView item_detail_low_tv;
        private TextView item_detail_normal;
        private TextView item_detail_normal_tv;
        private TextView item_detail_high;
        private TextView item_detail_high_tv;
        private TextView item_detail_content;
        //测量隐藏布局的高度
        private int mHiddenViewMeasuredHeight;
        public DetailViewHodler(@NonNull View itemView) {
            super(itemView);
            item_detail_title_ll = itemView.findViewById(R.id.item_detail_title_ll);
            item_detail_content_ll = itemView.findViewById(R.id.item_detail_content_ll);
//            item_detail_img = itemView.findViewById(R.id.item_detail_img);
//            item_detail_title = itemView.findViewById(R.id.item_detail_title);
//            item_detail_title_content = itemView.findViewById(R.id.item_detail_title_content);
            item_detail_bt = itemView.findViewById(R.id.item_detail_bt);
            item_detail_dowm = itemView.findViewById(R.id.item_detail_dowm);
            item_detail_low = itemView.findViewById(R.id.item_detail_low);
            item_detail_low_tv = itemView.findViewById(R.id.item_detail_low_tv);
            item_detail_normal = itemView.findViewById(R.id.item_detail_normal);
            item_detail_normal_tv = itemView.findViewById(R.id.item_detail_normal_tv);
            item_detail_high = itemView.findViewById(R.id.item_detail_high);
            item_detail_high_tv = itemView.findViewById(R.id.item_detail_high_tv);
            item_detail_content = itemView.findViewById(R.id.item_detail_content);
        }
        /*
          以下是有关于隐藏页面的动画
         */
        private void animateOpen(View v) {
            v.setVisibility(View.VISIBLE);
            int w = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
            v.measure(w, h);
            mHiddenViewMeasuredHeight =(int) v.getMeasuredHeight();;
            ValueAnimator animator = createDropAnimator(v, 0,
                    mHiddenViewMeasuredHeight);
            animator.start();
        }
        private void animationIvOpen() {
            //旋转动画
            RotateAnimation animation = new RotateAnimation(0, 180,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                    0.5f);
            animation.setFillAfter(true);
            animation.setDuration(100);
            item_detail_dowm.startAnimation(animation);
        }

        private void animationIvClose() {
            RotateAnimation animation = new RotateAnimation(180, 0,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                    0.5f);
            animation.setFillAfter(true);
            animation.setDuration(100);
            item_detail_dowm.startAnimation(animation);
        }

        private void animateClose(final View view) {
            int origHeight = view.getHeight();
            ValueAnimator animator = createDropAnimator(view, origHeight, 0);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.GONE);
                }

            });
            animator.start();
        }
        private ValueAnimator createDropAnimator(final View v, int start, int end) {
            ValueAnimator animator = ValueAnimator.ofInt(start, end);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator arg0) {
                    int value = (int) arg0.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                    layoutParams.height = value;
                    v.setLayoutParams(layoutParams);

                }
            });
            return animator;
        }
    }
}
