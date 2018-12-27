package com.example.zhan.heathmanage.Main;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.BasicsTools.ChildViewPager;
import com.example.zhan.heathmanage.Main.EvaluteFragment.EvaluteFragment;
import com.example.zhan.heathmanage.Main.FindFragment.FindFragment;
import com.example.zhan.heathmanage.Main.Menu.SettingActivity;
import com.example.zhan.heathmanage.Main.TrendFragment.TrendFragment;
import com.example.zhan.heathmanage.R;
import com.qiantao.coordinatormenu.CoordinatorMenu;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    public static CoordinatorMenu mCoordinatorMenu;
    private EvaluteFragment evaluteFragment;
    private TrendFragment trendFragment;
    private FindFragment findFragment;
    @BindView(R.id.tab_evalute_ib)
    ImageView tab_evalute_ib;
    @BindView(R.id.tab_evalute_ll)
    LinearLayout tab_evalute_ll;
    @BindView(R.id.tab_evalute_tv)
    TextView tab_evalute_tv;
    @BindView(R.id.main_fl)
    FrameLayout main_fl;
    @BindView(R.id.tab_find_ib) ImageView tab_find_ib;
    @BindView(R.id.tab_find_ll)
    LinearLayout tab_find_ll;
    @BindView(R.id.tab_find_tv)
    TextView tab_find_tv;
    @BindView(R.id.tab_trend_ib) ImageView tab_trend_ib;
    @BindView(R.id.tab_trend_ll)
    LinearLayout tab_trend_ll;
    @BindView(R.id.tab_trend_tv)
    TextView tab_trend_tv;
    @BindView(R.id.setting_ll) LinearLayout setting_ll;
    public static MotionEvent ev;
    //@BindView(R.id.change_theme_ll)LinearLayout change_theme_ll;
    //@BindView(R.id.change_theme_iv)ImageView change_theme_iv;
    //@BindView(R.id.change_theme_tv) TextView change_theme_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        inListener();

        mCoordinatorMenu=findViewById(R.id.mainactivity_menu);
        setSelect(0);

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        this.ev=ev;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE://如果是向下滑动，计算出每次滑动的距离与滑动的总距离，将每次滑动的距离作为layout(int l, int t, int r, int b)方法的参数，重新进行布局，达到布局滑动的效果。
                break;
            case MotionEvent.ACTION_DOWN://获取刚开始触碰的y坐标
                mCoordinatorMenu.mRollViewPagerTouching = true;
                ChildViewPager.mRollViewPagerTouching = true;
                break;
            case MotionEvent.ACTION_UP://将滑动的总距离作为layout(int l, int t, int r, int b)方法的参数，重新进行布局，达到布局自动回弹的效果。
                mCoordinatorMenu.mRollViewPagerTouching = false;
                ChildViewPager.mRollViewPagerTouching = true;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
    @Override
    public void onBackPressed() {
        if (mCoordinatorMenu.isOpened()) {
            mCoordinatorMenu.closeMenu();
        }  else {
            super.onBackPressed();
        }

    }


    public void inListener(){
        tab_evalute_ll.setOnClickListener(this);
        tab_find_ll.setOnClickListener(this);
        tab_trend_ll.setOnClickListener(this);
    }
    public void setSelect(int i){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction tf=fm.beginTransaction();
        hideFragment(tf);
        switch (i){
            case 0:
                if (evaluteFragment == null) {
                    evaluteFragment = new EvaluteFragment();
                    tf.add(R.id.main_fl, evaluteFragment);
                } else {
                    tf.show(evaluteFragment);
                }

                break;
            case 1:
                if (trendFragment == null) {
                    trendFragment = new TrendFragment();
                    tf.add(R.id.main_fl, trendFragment);
                } else {
                    tf.show(trendFragment);

                }

                break;
            case 2:
                if (findFragment == null) {
                    findFragment = new FindFragment();
                    tf.add(R.id.main_fl, findFragment);
                } else {
                    tf.show(findFragment);

                }

                break;
        }
        tf.commit();
    }
    private void hideFragment(FragmentTransaction tf){
        if(trendFragment!=null){
            tf.hide(trendFragment);
        }
        if (findFragment!=null){
            tf.hide(findFragment);
        }
        if (evaluteFragment!=null){
            tf.hide(evaluteFragment);
        }
    }
    private void startShakeByPropertyAnim(View view, float scaleSmall, float scaleLarge, float shakeDegrees, long duration) {
        if (view == null) {
            return;
        }
        //TODO 验证参数的有效性

        //先变小后变大
        PropertyValuesHolder scaleXValuesHolder = PropertyValuesHolder.ofKeyframe(View.SCALE_X,
                Keyframe.ofFloat(0f, 1.0f),
                Keyframe.ofFloat(0.25f, scaleSmall),
                Keyframe.ofFloat(0.5f, scaleLarge),
                Keyframe.ofFloat(0.75f, scaleLarge),
                Keyframe.ofFloat(1.0f, 1.0f)
        );
        PropertyValuesHolder scaleYValuesHolder = PropertyValuesHolder.ofKeyframe(View.SCALE_Y,
                Keyframe.ofFloat(0f, 1.0f),
                Keyframe.ofFloat(0.25f, scaleSmall),
                Keyframe.ofFloat(0.5f, scaleLarge),
                Keyframe.ofFloat(0.75f, scaleLarge),
                Keyframe.ofFloat(1.0f, 1.0f)
        );

        //先往左再往右
        PropertyValuesHolder rotateValuesHolder = PropertyValuesHolder.ofKeyframe(View.ROTATION,
                Keyframe.ofFloat(0f, 0f),
                Keyframe.ofFloat(0.3f, -shakeDegrees),
                Keyframe.ofFloat(0.6f, shakeDegrees),
                Keyframe.ofFloat(0.9f, -shakeDegrees),
                Keyframe.ofFloat(1.0f, 0f)
        );

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, scaleXValuesHolder, scaleYValuesHolder, rotateValuesHolder);
        objectAnimator.setDuration(duration);
        objectAnimator.start();
    }
    private void resetImgs() {
        //重置icon图标
        tab_evalute_ib.setImageResource(R.drawable.evalute);
        tab_trend_ib.setImageResource(R.drawable.trend);
        tab_find_ib.setImageResource(R.drawable.find);

        //重置文字颜色
        tab_evalute_tv.setTextColor(Color.parseColor("#272727"));
        tab_find_tv.setTextColor(Color.parseColor("#272727"));
        tab_trend_tv.setTextColor(Color.parseColor("#272727"));
    }
    @Override
    public void onClick(View view) {
        resetImgs();
        switch (view.getId()){
            case R.id.tab_evalute_ll:
                tab_evalute_ib.setImageResource(R.drawable.evalute_press);
                startShakeByPropertyAnim(tab_evalute_ib, 0.9f, 1.2f, 10f, 400);
                tab_evalute_tv.setTextColor(Color.parseColor("#FF4081"));
                setSelect(0);
                break;
            case R.id.tab_trend_ll:
                tab_trend_ib.setImageResource(R.drawable.trend_press);
                startShakeByPropertyAnim(tab_trend_ib,0.9f, 1.2f, 10f, 400);
                tab_trend_tv.setTextColor(Color.parseColor("#FF4081"));
                setSelect(1);
                break;
            case R.id.tab_find_ll:
                tab_find_ib.setImageResource(R.drawable.find_press);
                startShakeByPropertyAnim(tab_find_ib,0.9f, 1.2f, 10f, 400);
                tab_find_tv.setTextColor(Color.parseColor("#FF4081"));
                setSelect(2);
                break;
            default:break;
        }
    }
    @OnClick(R.id.setting_ll)
    public void setting_ll_Onclick(){
        Intent intent=new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);
        Toast.makeText(this,"啦啦啦啦啦",Toast.LENGTH_SHORT).show();
    }
//    public interface MyTouchListener {
//        public void onTouchEvent(MotionEvent event);
//    }
//
//    // 保存MyTouchListener接口的列表
//    private ArrayList<MyTouchListener> myTouchListeners = new ArrayList<MainActivity.MyTouchListener>();
//
//    /**
//     * 提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法
//     * @param listener
//     */
//    public void registerMyTouchListener(MyTouchListener listener) {
//        myTouchListeners.add(listener);
//    }
//
//    /**
//     * 提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法
//     * @param listener
//     */
//    public void unRegisterMyTouchListener(MyTouchListener listener) {
//        myTouchListeners.remove( listener );
//    }
//
//    /**
//     * 分发触摸事件给所有注册了MyTouchListener的接口
//     */
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        for (MyTouchListener listener : myTouchListeners) {
//            listener.onTouchEvent(ev);
//        }
//        return super.dispatchTouchEvent(ev);
//    }
}
