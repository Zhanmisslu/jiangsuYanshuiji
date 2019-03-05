package com.example.zhan.heathmanage.Main.FindFragment;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chaek.android.widget.CaterpillarIndicator;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.AddFriendActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.DynamicActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.PublishPostingActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.PublishTextActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.ReportActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.SearchActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.AttentionFragment;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.DryCargoFragment;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.HotFragment;
import com.example.zhan.heathmanage.Main.TrendFragment.Fragment.DayFragment;
import com.example.zhan.heathmanage.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends Fragment {
   // @BindView(R.id.button)Button button;
    @BindView(R.id.fragment_find_viewpage)ViewPager fragment_find_viewpage;
    @BindView(R.id.fragment_find_titlebar)CaterpillarIndicator fragment_find_titlebar;
    @BindView(R.id.fragment_find_search_et)EditText fragment_find_search_et;
    private List<Fragment> fragmentList = new ArrayList<>();
     @BindView(R.id.fragment_find_FAM)
     FloatingActionMenu fragment_find_FAM;
    private List<FloatingActionMenu> menus = new ArrayList<>();
    @BindView(R.id.dynamic_FAB)FloatingActionButton dynamic_FAB;
    @BindView(R.id.report_FAB)FloatingActionButton report_FAB;
    //@BindView(R.id.fab3)FloatingActionButton fab3;
    private Handler mUiHandler = new Handler();
    private FragmentManager fragmentManager;//FragmentManager 是一个抽象类，它定义了对一个 Activity/Fragment 中 添加进来的 Fragment 列表、Fragment 回退栈的操作、管理
    private FragmentTransaction fragmentTransaction;
    public static List<CaterpillarIndicator.TitleInfo> titleList;
    public FindFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_find, container, false);
       ButterKnife.bind(this,view);
        InitViewPager();
        showSoftInputFromWindow(getActivity(),fragment_find_search_et);
     //   fragment_find_FAM = (FloatingActionMenu) view.findViewById(R.id.fragment_find_FAM);
        InitFloatIngActionButton();
      // button.setBackgroundColor(0x66000000);
        dynamic_FAB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent=new Intent(getActivity(), PublishTextActivity.class);
                getActivity().startActivity(intent);
                return false;
            }
        });
       return  view;
    }
    public void InitViewPager(){
        fragmentList.add(new AttentionFragment());
        fragmentList.add(new HotFragment());
        fragmentList.add(new DryCargoFragment());
        BaseFragmentAdapter baseFragmentAdapter=new BaseFragmentAdapter(getFragmentManager());
        fragment_find_viewpage.setAdapter(baseFragmentAdapter);
        titleList=new ArrayList<>();
        titleList.add(new CaterpillarIndicator.TitleInfo("关注"));
        titleList.add(new CaterpillarIndicator.TitleInfo("热门"));
        titleList.add(new CaterpillarIndicator.TitleInfo("干货"));
        fragment_find_titlebar.init(1,titleList,fragment_find_viewpage);
    }
    public class BaseFragmentAdapter extends FragmentStatePagerAdapter {

        public BaseFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList != null ? fragmentList.size() : 0;
        }
    }
    //编辑框不可编辑
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }
    @OnClick(R.id.fragment_find_search_et)
    public void fragment_find_search_et_Onclick(){
        Intent intent=new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.addfriend_ib)
    public void addfriend_ib_Onclick(){
        Intent intent=new Intent(getActivity(), AddFriendActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //fragment_find_titlebar.init(0,titleList,fragment_find_viewpage);
        if (requestCode==101){
            if(data.getIntExtra("flag",0)==1) {
                // fragment_find_FAM.hideMenu(true);
                fragment_find_titlebar.init(0, titleList, fragment_find_viewpage);
                AttentionFragment.flag=0;
            }

        }
    }

    public void InitFloatIngActionButton(){
        fragment_find_FAM.hideMenuButton(false);

//        final FloatingActionButton programFab1 = new FloatingActionButton(getActivity());
//        programFab1.setButtonSize(FloatingActionButton.SIZE_MINI);
//        programFab1.setLabelText(getString(R.string.lorem_ipsum));
//        programFab1.setImageResource(R.drawable.ic_edit);
//        fragment_find_FAM.addMenuButton(programFab1);
//        programFab1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                programFab1.setLabelColors(ContextCompat.getColor(getActivity(), R.color.grey),
//                        ContextCompat.getColor(getActivity(), R.color.light_grey),
//                        ContextCompat.getColor(getActivity(), R.color.white_transparent));
//                programFab1.setLabelTextColor(ContextCompat.getColor(getActivity(), R.color.black));
//            }
//        });
        fragment_find_FAM.setClosedOnTouchOutside(true);
        fragment_find_FAM.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (fragment_find_FAM.isOpened()) {
//                    //Toast.makeText(getActivity(), fragment_find_FAM.getMenuButtonLabelText(), Toast.LENGTH_SHORT).show();
//                }
                fragment_find_FAM.toggle(true);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        menus.add(fragment_find_FAM);
        dynamic_FAB.setOnClickListener(clickListener);
        report_FAB.setOnClickListener(clickListener);
        //fab3.setOnClickListener(clickListener);
        int delay = 400;
        for (final FloatingActionMenu menu : menus) {
            mUiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    menu.showMenuButton(true);
                }
            }, delay);
            delay += 150;
        }
    }
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.dynamic_FAB:
                    RxPermissions.getInstance(getActivity())
                            .request(Manifest.permission.CAMERA,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)//多个权限用","隔开
                            .subscribe(new Action1<Boolean>() {
                                @Override
                                public void call(Boolean aBoolean) {
                                    if (aBoolean) {
                                        //当所有权限都允许之后，返回true
                                        Intent intent=new Intent(getActivity(),PublishPostingActivity.class);
                                        fragment_find_FAM.close(true);
                                        intent.putExtra("flag",1);
                                        startActivityForResult(intent,101);
                                    } else {
                                        //只要有一个权限禁止，返回false，
                                        //下一次申请只申请没通过申请的权限
                                        getActivity().finish();
                                    }
                                }
                            });
                    break;
                case R.id.report_FAB:
                    RxPermissions.getInstance(getActivity())
                            .request(Manifest.permission.CAMERA,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)//多个权限用","隔开
                            .subscribe(new Action1<Boolean>() {
                                @Override
                                public void call(Boolean aBoolean) {
                                    if (aBoolean) {
                                        //当所有权限都允许之后，返回true
                                        Intent intent=new Intent(getActivity(),ReportActivity.class);
                                        fragment_find_FAM.close(true);
                                        startActivity(intent);
                                    } else {
                                        //只要有一个权限禁止，返回false，
                                        //下一次申请只申请没通过申请的权限
                                        getActivity().finish();
                                    }
                                }
                            });
                    break;

            }
        }
    };

}
