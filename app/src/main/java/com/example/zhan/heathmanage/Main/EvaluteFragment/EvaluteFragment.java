package com.example.zhan.heathmanage.Main.EvaluteFragment;


import android.animation.ArgbEvaluator;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zhan.heathmanage.BasicsTools.HintPopupWindow;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Adapter.VideoAdpater;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Beans.DataUtil;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Fragment.CompatHomeKeyFragment;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Fragment.PersonFragment;
import com.example.zhan.heathmanage.Main.EvaluteFragment.View.RingView;
import com.example.zhan.heathmanage.Main.MainActivity;
import com.example.zhan.heathmanage.Main.Menu.UserActivity;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;
import com.john.waveview.WaveView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.content.Context.MODE_PRIVATE;
import static com.example.zhan.heathmanage.MyApplication.getContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class EvaluteFragment extends CompatHomeKeyFragment {
    private RoundedImageView evalutefragment_iv;
    private View view;
    private MainActivity mainActivity;
    private HintPopupWindow hintPopupWindow;
    private SeekBar fragment_evaluate_seekbar;
    private RecyclerView recycler_view;
    private SharedPreferences preferences;

    public EvaluteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_evalute, container, false);
        evalutefragment_iv = view.findViewById(R.id.evalutefragment_iv);
        recycler_view = view.findViewById(R.id.recycler_view);
        preferences = getActivity().getSharedPreferences("UserList", MODE_PRIVATE);


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
                Log.v("ZJC", "新增用户！！！");
                return true;
            }
        });
        //ReycleView的初始化
        initRevcycleView();
        //头像点击视图初始化
        initView();
        //填写个人信息视图初始化
        initPessonView();
        return view;
    }

    //页面重置时加载头像
    @Override
    public void onResume() {
        super.onResume();
        Glide.with(getContext())
                .load(preferences.getString("UserPhoto", ""))
                .asBitmap()
                .error(R.drawable.head)
                .into(evalutefragment_iv);
        //evalutefragment_iv.setImageBitmap(MyApplication.getUserPhoto());
    }
    //视频列表的初始化
    public void initRevcycleView(){
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_view.setHasFixedSize(true);
        VideoAdpater adapter = new VideoAdpater(getActivity(), DataUtil.getVideoListData());
        recycler_view.setAdapter(adapter);
        recycler_view.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                if (holder instanceof VideoAdpater.VideoViewHodler){
                    NiceVideoPlayer niceVideoPlayer = ((VideoAdpater.VideoViewHodler) holder).mVideoPlayer;
                    if (niceVideoPlayer == NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
                        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                    }
                }
            }
        });

    }
    //头像视图
    public void initView() {

        //下面的操作是初始化弹出数据
        ArrayList<String> strList = new ArrayList<>();
        SharedPreferences preferences = getActivity().getSharedPreferences("UserList", MODE_PRIVATE);
        int UserListSize = preferences.getInt("UserListSize", 0);
        for (int i = 0; i < UserListSize; i++) {
            String UserNumber = preferences.getString("Item_Phone" + i, null);
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

    //新用户登录时弹窗填写信息
    public void initPessonView() {
        if (preferences.getString("Login_Weight", null).equals(null) || preferences.getString("Login_Weight", null).equals("null")) {
            PersonFragment personFragment = new PersonFragment();
            personFragment.show(getActivity().getSupportFragmentManager(), "personFragment");
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }
}



