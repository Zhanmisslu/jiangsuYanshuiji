package com.example.zhan.heathmanage.Main.EvaluteFragment.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chaek.android.widget.CaterpillarIndicator;
import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Fragment.DontSuggestFragment;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Fragment.SuggestFragment;
import com.example.zhan.heathmanage.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FoodSuggestActivity extends BaseActivity {
    @BindView(R.id.food_suggest_titlebar)
    CaterpillarIndicator food_suggest_titlebar;
    @BindView(R.id.food_suggest_viewpage)
    ViewPager food_suggest_viewpage;
    List<Fragment> fragmentList = new ArrayList<>();
    List titlelist ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_suggest);
        initFragment();
    }
    public void initFragment(){
        fragmentList.add(new SuggestFragment());
        fragmentList.add(new DontSuggestFragment());
        BaseFragmentAdapter baseFragmentAdapter=new BaseFragmentAdapter(getSupportFragmentManager());
        food_suggest_viewpage.setAdapter(baseFragmentAdapter);
        titlelist = new ArrayList<>();
        titlelist.add(new CaterpillarIndicator.TitleInfo("建议吃的食物"));
        titlelist.add(new CaterpillarIndicator.TitleInfo("不建议吃的食物"));
        food_suggest_titlebar.init(0,titlelist,food_suggest_viewpage);
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

    @OnClick(R.id.food_back)
    public void food_back_Onclick(){
        finish();
    }
}
