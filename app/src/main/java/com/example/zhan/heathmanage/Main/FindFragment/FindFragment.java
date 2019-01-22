package com.example.zhan.heathmanage.Main.FindFragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.chaek.android.widget.CaterpillarIndicator;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.AttentionFragment;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.DryCargoFragment;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.HotFragment;
import com.example.zhan.heathmanage.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends Fragment {
    @BindView(R.id.fragment_find_viewpage)ViewPager fragment_find_viewpage;
    @BindView(R.id.fragment_find_titlebar)CaterpillarIndicator fragment_find_titlebar;
    @BindView(R.id.fragment_find_search_et)EditText fragment_find_search_et;
    private List<Fragment> fragmentList = new ArrayList<>();
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
       return  view;
    }
    public void InitViewPager(){
        fragmentList.add(new AttentionFragment());
        fragmentList.add(new HotFragment());
        fragmentList.add(new DryCargoFragment());
        BaseFragmentAdapter baseFragmentAdapter=new BaseFragmentAdapter(getFragmentManager());
        fragment_find_viewpage.setAdapter(baseFragmentAdapter);
        List<CaterpillarIndicator.TitleInfo> titleList=new ArrayList<>();
        titleList.add(new CaterpillarIndicator.TitleInfo("关注"));
        titleList.add(new CaterpillarIndicator.TitleInfo("热门"));
        titleList.add(new CaterpillarIndicator.TitleInfo("干货"));
        fragment_find_titlebar.init(0,titleList,fragment_find_viewpage);
    }
    private class BaseFragmentAdapter extends FragmentStatePagerAdapter {

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
}
