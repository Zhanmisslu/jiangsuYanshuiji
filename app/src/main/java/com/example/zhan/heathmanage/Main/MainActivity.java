package com.example.zhan.heathmanage.Main;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.BasicsTools.ChildViewPager;
import com.example.zhan.heathmanage.Main.EvaluteFragment.EvaluteFragment;
import com.example.zhan.heathmanage.Main.FindFragment.FindFragment;
import com.example.zhan.heathmanage.Main.Menu.ChangePasswordActivity;
import com.example.zhan.heathmanage.Main.Menu.EmergencyContactActivity;
import com.example.zhan.heathmanage.Main.Menu.SettingActivity;
import com.example.zhan.heathmanage.Main.Menu.UserActivity;
import com.example.zhan.heathmanage.Main.Service.MenuDao;
import com.example.zhan.heathmanage.Main.Service.MenuDaoImp;
import com.example.zhan.heathmanage.Main.TrendFragment.TrendFragment;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.qiantao.coordinatormenu.CoordinatorMenu;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.zhan.heathmanage.MyApplication.getContext;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    public static CoordinatorMenu mCoordinatorMenu;
    private EvaluteFragment evaluteFragment;
    private TrendFragment trendFragment;
    private FindFragment findFragment;
    @BindView(R.id.menu_temperature_tv)TextView menu_temperature_tv;
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
    @BindView(R.id.main_user_iv)
    RoundedImageView main_user_iv;
    @BindView(R.id.menu_setting_ll) LinearLayout setting_ll;
    public static MotionEvent ev;
    //@BindView(R.id.change_theme_ll)LinearLayout change_theme_ll;
    //@BindView(R.id.change_theme_iv)ImageView change_theme_iv;
    //@BindView(R.id.change_theme_tv) TextView change_theme_tv;
    @BindView(R.id.menu_nickName)
    TextView menu_nickName;
    @BindView(R.id.main_fan_tv)TextView main_fan_tv;
    @BindView(R.id.main_attention_tv)TextView main_attention_tv;
    @BindView(R.id.menu_weathertype_iv)ImageView menu_weathertype_iv;
    SharedPreferences preferences;
    MenuDao menuDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        menuDao=new MenuDaoImp(this);
        preferences = getSharedPreferences("UserList", MODE_PRIVATE);
        menuDao.GetFollowedNum(MyApplication.getUserId());
        menuDao.GetFollowNum(MyApplication.getUserId());
        menuDao.getWeather("101210501");
        inListener();
        mCoordinatorMenu=findViewById(R.id.mainactivity_menu);
        if (!MyApplication.getUserNickName().equals("null")){
            menu_nickName.setText(MyApplication.getUserNickName());
        }
        Glide.with(getContext())
                .load(MyApplication.getPhoto())
                .asBitmap()
                .error(R.drawable.head)
                .into(main_user_iv);
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
        }else if (NiceVideoPlayerManager.instance().onBackPressd()){
            return;
        }
            super.onBackPressed();
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
        tab_find_ib.setImageResource(R.drawable.newfind);

        //重置文字颜色
        tab_evalute_tv.setTextColor(Color.parseColor("#CACACA"));
        tab_find_tv.setTextColor(Color.parseColor("#CACACA"));
        tab_trend_tv.setTextColor(Color.parseColor("#CACACA"));
    }
    @Override
    public void onClick(View view) {
        resetImgs();
        switch (view.getId()){
            case R.id.tab_evalute_ll:
                tab_evalute_ib.setImageResource(R.drawable.evaluate_press);
                startShakeByPropertyAnim(tab_evalute_ib, 0.9f, 1.2f, 10f, 400);
                tab_evalute_tv.setTextColor(Color.parseColor("#FF000000"));
                setSelect(0);
                break;
            case R.id.tab_trend_ll:
                tab_trend_ib.setImageResource(R.drawable.trend_press);
                startShakeByPropertyAnim(tab_trend_ib,0.9f, 1.2f, 10f, 400);
                tab_trend_tv.setTextColor(Color.parseColor("#FF000000"));
                setSelect(1);
                break;
            case R.id.tab_find_ll:
                tab_find_ib.setImageResource(R.drawable.find_press);
                startShakeByPropertyAnim(tab_find_ib,0.9f, 1.2f, 10f, 400);
                tab_find_tv.setTextColor(Color.parseColor("#FF000000"));
                setSelect(2);
                break;
            default:break;
        }
    }
    /*
       以下是菜单栏控件的管理
     */

    //个人信息按钮
    @OnClick(R.id.menu_user_ll)
    public void menu_user_ll_OnClick(){
        Intent intent = new Intent(this, UserActivity.class);
        startActivityForResult(intent,2);
    }
    //设置按钮
    @OnClick(R.id.menu_setting_ll)
    public void setting_ll_Onclick(){
        Intent intent=new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);
    }
    //紧急联系人
    @OnClick(R.id.menu_emergencyuser_ll)
    public void menu_emergencyuser_ll_OnClick(){
        Intent intent=new Intent(MainActivity.this, EmergencyContactActivity.class);
        startActivity(intent);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2){
            menu_nickName.setText(MyApplication.getUserNickName());
//            main_user_iv.setImageBitmap(MyApplication.getUserPhoto());
            Glide.with(getContext())
                    .load(MyApplication.getPhoto())
                    .asBitmap()
                    .error(R.drawable.head)
                    .into(main_user_iv);
        }
    }

    public void InitfollowedNum(final String followedNum) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                main_fan_tv.setText(followedNum);
            }
        });
    }

    public void InitfollowNum(final String followedNum) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                main_attention_tv.setText(followedNum);
            }
        });
    }

    @OnClick(R.id.menu_changepsw_ll)
    public void menu_changepsw_ll_Click(){
        Intent intent=new Intent(MainActivity.this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    public void CallBack(final String wendu, final String type) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                menu_temperature_tv.setText(wendu);
                if(type.equals("晴")){
                    Glide.with(getContext())
                            .load(R.drawable.sunny)
                            .asBitmap()
                           // .error(R.drawable.head)
                            .into(menu_weathertype_iv);
                }
                if(type.equals("小雨")){
                    Glide.with(getContext())
                            .load(R.drawable.lightrain)
                            .asBitmap()
                            // .error(R.drawable.head)
                            .into(menu_weathertype_iv);
                }
                if(type.equals("多云")){
                    Glide.with(getContext())
                            .load(R.drawable.morecloudy)
                            .asBitmap()
                            // .error(R.drawable.head)
                            .into(menu_weathertype_iv);
                }
                if(type.equals("阴")){
                    Glide.with(getContext())
                            .load(R.drawable.cloudy)
                            .asBitmap()
                            // .error(R.drawable.head)
                            .into(menu_weathertype_iv);
                }
            }
        });

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
