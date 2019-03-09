package com.example.zhan.heathmanage.Main.EvaluteFragment.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Adapter.DetailAdpater;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Beans.Details;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.item_blood_math)
    TextView item_blood_math;
    @BindView(R.id.item_oxygen_math)
    TextView item_oxygen_math;
    @BindView(R.id.item_concentration_math)
    TextView item_concentration_math;
    @BindView(R.id.item_Diastolic_math)
    TextView item_Diastolic_math;
    @BindView(R.id.item_Systolic_math)
    TextView item_Systolic_math;
    private int mHiddenViewMeasuredHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initOnClick();//点击显示隐藏布局
        initSetData();
    }
    public void initSetData(){
        item_blood_math.setText(MyApplication.getHeartRate());
        item_oxygen_math.setText(MyApplication.getBloodOxygen());
        item_concentration_math.setText(MyApplication.getBloodFat());
        item_Diastolic_math.setText(MyApplication.getdBP());
        item_Systolic_math.setText(MyApplication.getsBP());
    }
    //点击事件
    public void initOnClick(){
        item_blood_title_ll.setOnClickListener(this);
        item_oxygen_title_ll.setOnClickListener(this);
        item_concentration_title_ll.setOnClickListener(this);
        item_Diastolic_title_ll.setOnClickListener(this);
        item_Systolic_title_ll.setOnClickListener(this);
    }
    //返回上个页面
    @OnClick(R.id.details_back)
    public void details_back_OnClick() {
        finish();
    }
    //动画
    private void animateOpen(View v) {
        v.setVisibility(View.VISIBLE);
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(w, h);
        mHiddenViewMeasuredHeight = (int) v.getMeasuredHeight();
        ;
        ValueAnimator animator = createDropAnimator(v, 0,
                mHiddenViewMeasuredHeight);
        animator.start();
    }

    private void animationIvOpen(ImageView view) {
        //旋转动画
        RotateAnimation animation = new RotateAnimation(0, 180,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animation.setFillAfter(true);
        animation.setDuration(100);
        view.startAnimation(animation);
    }

    private void animationIvClose(ImageView view) {
        RotateAnimation animation = new RotateAnimation(180, 0,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animation.setFillAfter(true);
        animation.setDuration(100);
        view.startAnimation(animation);
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

    //上方布局点击后得到下方布局
    @BindView(R.id.item_blood_title_ll)
    LinearLayout item_blood_title_ll;
    @BindView(R.id.item_oxygen_title_ll)
    LinearLayout item_oxygen_title_ll;
    @BindView(R.id.item_concentration_title_ll)
    LinearLayout item_concentration_title_ll;
    @BindView(R.id.item_Diastolic_title_ll)
    LinearLayout item_Diastolic_title_ll;
    @BindView(R.id.item_Systolic_title_ll)
    LinearLayout item_Systolic_title_ll;
    //下方隐藏的布局
    @BindView(R.id.item_blood_content_ll)
    LinearLayout item_blood_content_ll;
    @BindView(R.id.item_oxygen_content_ll)
    LinearLayout item_oxygen_content_ll;
    @BindView(R.id.item_concentration_content_ll)
    LinearLayout item_concentration_content_ll;
    @BindView(R.id.item_Diastolic_content_ll)
    LinearLayout item_Diastolic_content_ll;
    @BindView(R.id.item_Systolic_content_ll)
    LinearLayout item_Systolic_content_ll;
    //转动的按钮
    @BindView(R.id.item_blood_dowm)
    ImageView item_blood_dowm;
    @BindView(R.id.item_oxygen_dowm)
    ImageView item_oxygen_dowm;
    @BindView(R.id.item_concentration_dowm)
    ImageView item_concentration_dowm;
    @BindView(R.id.item_Diastolic_dowm)
    ImageView item_Diastolic_dowm;
    @BindView(R.id.item_Systolic_dowm)
    ImageView item_Systolic_dowm;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_blood_title_ll:
                if (item_blood_content_ll.getVisibility() == View.GONE){
                    animateOpen(item_blood_content_ll);
                    animationIvOpen(item_blood_dowm);
                }else {
                    animateClose(item_blood_content_ll);
                    animationIvClose(item_blood_dowm);
                }
                break;
            case R.id.item_oxygen_title_ll:
                if (item_oxygen_content_ll.getVisibility() == View.GONE){
                    animateOpen(item_oxygen_content_ll);
                    animationIvOpen(item_oxygen_dowm);
                }else {
                    animateClose(item_oxygen_content_ll);
                    animationIvClose(item_oxygen_dowm);
                }
                break;
            case R.id.item_concentration_title_ll:
                if (item_concentration_content_ll.getVisibility() == View.GONE){
                    animateOpen(item_concentration_content_ll);
                    animationIvOpen(item_concentration_dowm);
                }else {
                    animateClose(item_concentration_content_ll);
                    animationIvClose(item_concentration_dowm);
                }
                break;
            case R.id.item_Diastolic_title_ll:
                if (item_Diastolic_content_ll.getVisibility() == View.GONE){
                    animateOpen(item_Diastolic_content_ll);
                    animationIvOpen(item_Diastolic_dowm);
                }else {
                    animateClose(item_Diastolic_content_ll);
                    animationIvClose(item_Diastolic_dowm);
                }
                break;
            case R.id.item_Systolic_title_ll:
                if (item_Systolic_content_ll.getVisibility() == View.GONE){
                    animateOpen(item_Systolic_content_ll);
                    animationIvOpen(item_Systolic_dowm);
                }else {
                    animateClose(item_Systolic_content_ll);
                    animationIvClose(item_Systolic_dowm);
                }
                break;
        }
    }
}
