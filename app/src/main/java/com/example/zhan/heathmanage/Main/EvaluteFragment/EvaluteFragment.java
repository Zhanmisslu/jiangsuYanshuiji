package com.example.zhan.heathmanage.Main.EvaluteFragment;


import android.animation.ArgbEvaluator;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zhan.heathmanage.BasicsTools.HintPopupWindow;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Fragment.PersonFragment;
import com.example.zhan.heathmanage.Main.EvaluteFragment.View.RingView;
import com.example.zhan.heathmanage.Main.MainActivity;
import com.example.zhan.heathmanage.Main.Menu.UserActivity;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;
import com.john.waveview.WaveView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.content.Context.MODE_PRIVATE;
import static com.example.zhan.heathmanage.MyApplication.getContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class EvaluteFragment extends Fragment {
    private RoundedImageView evalutefragment_iv;
    private View view;
    private MainActivity mainActivity;
    private HintPopupWindow hintPopupWindow;
    private SeekBar fragment_evaluate_seekbar;
    private WaveView fragment_evaluate_waveview;
    private SharedPreferences preferences;
    public EvaluteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_evalute, container, false);
        evalutefragment_iv=view.findViewById(R.id.evalutefragment_iv);
        preferences = getActivity().getSharedPreferences("UserList", MODE_PRIVATE);
        /*
         rv_view
         */
        rv_view = view.findViewById(R.id.rv_view);
            evalutefragment_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mainActivity.mCoordinatorMenu.isOpened()) {
                        mainActivity.mCoordinatorMenu.closeMenu();
                    } else {
                        mainActivity.mCoordinatorMenu.openMenu();
                    }
                }
            });
        Glide.with(getContext())
                .load(MyApplication.getPhoto())
                .asBitmap()
                .error(R.drawable.head)
                .into(evalutefragment_iv);
        evalutefragment_iv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                hintPopupWindow.showPopupWindow(view);
                Log.v("ZJC","新增用户！！！");
                return true;
            }
        });
        //头像点击视图初始化
        InitView();
        //水波视图初始化
        InitWaveView();
        //评价圆圈视图初始化
        initRingView();
        //填写个人信息视图初始化
        initPessonView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(getContext())
                .load(preferences.getString("UserPhoto",""))
                .asBitmap()
                .error(R.drawable.head)
                .into(evalutefragment_iv);
        //evalutefragment_iv.setImageBitmap(MyApplication.getUserPhoto());
    }

    //头像视图
    public void InitView() {

        //下面的操作是初始化弹出数据
        ArrayList<String> strList = new ArrayList<>();
        SharedPreferences preferences = getActivity().getSharedPreferences("UserList",MODE_PRIVATE);
        int UserListSize = preferences.getInt("UserListSize",0);
        for (int i = 0;i<UserListSize;i++){
            String UserNumber = preferences.getString("Item_Phone"+i,null);
            strList.add(UserNumber);
        }
        ArrayList<String> imageList = new ArrayList<>();
        ArrayList<View.OnClickListener> clickList = new ArrayList<>();
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "点击事件触发", Toast.LENGTH_SHORT).show();
            }
        };
        for (int i = 0; i < strList.size(); i++) {
            clickList.add(clickListener);
        }
        //hintPopupWindow.setBackgroundAlpha(getActivity(),1);
        //具体初始化逻辑看下面的图
        hintPopupWindow = new HintPopupWindow(getActivity(), strList, clickList);
    }
    //水波视图
    public void InitWaveView(){
        fragment_evaluate_waveview=view.findViewById(R.id.fragment_evaluate_waveview);
        fragment_evaluate_waveview.setProgress(100);
    }
    //圆圈视图
    RingView rv_view;
    ArgbEvaluator evaluator;
    private int startColor = 0XFFfb5338;
    private int centerColor = 0XFF00ff00;
    private int endColor = 0XFF008dfc;
    private int endUseColor = 0;

    List<Integer> valueList = new ArrayList<>();
    List<String> valueNameList = new ArrayList<>();
    private int animDuration = 2500;
    public void initRingView(){
        evaluator = new ArgbEvaluator();
        valueList.add(350);
        valueList.add(450);
        valueList.add(550);
        valueList.add(650);
        valueList.add(750);
        valueList.add(850);
        rv_view.setValueList(valueList);
        valueNameList.add("较差");
        valueNameList.add("中等");
//        valueNameList.add("良好");
        valueNameList.add("合格");
        valueNameList.add("优秀");
        rv_view.setValueNameList(valueNameList);
//        rv_view.setPointer(true);
        rv_view.setPointer(false);
//        ly_content.setBackgroundColor((Integer) evaluator.evaluate(0f, startColor, endColor));
        start((int) (350 + Math.random() * 500));
    }
    private void start(int value) {
        float f = (value - valueList.get(0)) * 1.0f / (valueList.get(valueList.size() - 1) - valueList.get(0));
        if (f <= 0.5f) {
            endUseColor = (Integer) evaluator.evaluate(f, startColor, centerColor);

        }
        else
        {
            endUseColor = (Integer) evaluator.evaluate(f, centerColor, endColor);

        }

        rv_view.setValue(value, new RingView.OnProgerssChange() {
            @Override
            public void OnProgerssChange(float interpolatedTime) {
                int evaluate = 0;
//
//                if (interpolatedTime <= 0.5f) {
//
//                    evaluate = (Integer) evaluator.evaluate(interpolatedTime, startColor, endUseColor);
//
//                } else {
//                    evaluate = (Integer) evaluator.evaluate(interpolatedTime, centerColor, endUseColor);
//                }
//                ly_content.setBackgroundColor(evaluate);


            }
        },(int)(f*animDuration));
    }

    public void initPessonView(){
       if (preferences.getString("Login_Weight",null).equals(null)||preferences.getString("Login_Weight",null).equals("null")){
           PersonFragment personFragment = new PersonFragment();
           personFragment.show(getActivity().getSupportFragmentManager(),"personFragment");
       }
    }
}



