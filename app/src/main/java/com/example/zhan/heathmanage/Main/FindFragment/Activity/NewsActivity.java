package com.example.zhan.heathmanage.Main.FindFragment.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chaek.android.widget.CaterpillarIndicator;
import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.AllFragment;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.DynamicFragment;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.FashionFragment;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.HeathFragment;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.UserFragment;
import com.example.zhan.heathmanage.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NewsActivity extends BaseActivity {
    @BindView(R.id.news_viewpage)ViewPager news_viewpage;
    @BindView(R.id.news_titlebar)CaterpillarIndicator news_titlebar;
    private List<Fragment> fragmentList = new ArrayList<>();
    public static List<CaterpillarIndicator.TitleInfo> titleList;
    static int healthflag=0;
    static int fashionflag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        healthflag=getIntent().getIntExtra("healthflag",0);
        fashionflag=getIntent().getIntExtra("fashionflag",0);
        InitViewPager();
    }
    public void InitViewPager(){
        fragmentList.add(new HeathFragment());
        fragmentList.add(new FashionFragment());
        BaseFragmentAdapter baseFragmentAdapter=new BaseFragmentAdapter(getSupportFragmentManager());
        news_viewpage.setAdapter(baseFragmentAdapter);
        titleList=new ArrayList<>();
        titleList.add(new CaterpillarIndicator.TitleInfo("健康新闻"));
        titleList.add(new CaterpillarIndicator.TitleInfo("时尚"));
        if (healthflag==1) {
            news_titlebar.init(0, titleList, news_viewpage);
        }if(fashionflag==1){
            news_titlebar.init(1, titleList, news_viewpage);
        }
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
    @OnClick(R.id.news_back_ib)
    public void news_back_ib_Onclick(){
        finish();
    }
}
