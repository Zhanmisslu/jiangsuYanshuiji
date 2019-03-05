package com.example.zhan.heathmanage.Main.FindFragment.Activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaek.android.widget.CaterpillarIndicator;
import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.PeopleInfo;
import com.example.zhan.heathmanage.Main.FindFragment.FindFragment;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.AllFragment;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.AttentionFragment;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.DryCargoFragment;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.DynamicFragment;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.HotFragment;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.UserFragment;
import com.example.zhan.heathmanage.Main.FindFragment.Service.SearchDao;
import com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp.SearchDaoImp;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;

import static android.view.View.GONE;

public class SearchActivity extends BaseActivity {
//    @BindView(R.id.activity_search_et)
//    EditText activity_search_et;
//    @BindView(R.id.activity_cancel_ib)
//    ImageButton activity_cancel_ib;
    @BindView(R.id.viewpage_ll)LinearLayout viewpage_ll;
    @BindView(R.id.search_viewpage)ViewPager search_viewpage;
    public @BindView(R.id.search_titlebar)CaterpillarIndicator search_titlebar;
    private List<Fragment> fragmentList = new ArrayList<>();
    public static List<CaterpillarIndicator.TitleInfo> titleList;
    //@BindView(R.id.Search_rv)RecyclerView Search_rv;
    //InputMethodManager manager;//输入法管理器
    public @BindView(R.id.search_view)
    SearchView search_view;
    public static String text;
    SearchDao searchDao;
    public static List<PeopleInfo> peopleInfoList;

    public SearchActivity() {
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchDao=new SearchDaoImp(this);
        //manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        search_view.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });
        search_view.et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(search_view.et_search.getText().toString().equals("")){
                    viewpage_ll.setVisibility(GONE);
                }
            }
        });
        search_view.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                viewpage_ll.setVisibility(View.VISIBLE);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                String name = textView.getText().toString();
                search_view.et_search.setText(name);
                search_view.listView.setVisibility(GONE);
                search_view.tv_clear.setVisibility(GONE);
                search_view.et_search.setSelection(name.length());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                text=name;
                InitViewPager();
               // searchDao.SearchUser(name);
            }
        });
        search_view.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                //调用搜索接口
                if(search_view.flag==0){
                    viewpage_ll.setVisibility(View.VISIBLE);
                }else {
                    viewpage_ll.setVisibility(GONE);
                }
                if(TextUtils.isEmpty(string)){
                    Toast.makeText(MyApplication.getContext(),"请输入输入内容",Toast.LENGTH_SHORT).show();
                }else {
                    //搜索接口
                    //searchDao.SearchUser(string);
                    text=string;
                    InitViewPager();
                }
            }
        });

//        activity_search_et.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
//        activity_search_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//
//                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
//                {
////do something;
//                    //先隐藏键盘
//                    if (manager.isActive()) {
//                        manager.hideSoftInputFromWindow(activity_search_et.getApplicationWindowToken(), 0);
//                    }
//
//                    Toast.makeText(SearchActivity.this, "hello 2017!", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//                return false;
//            }
//        });
//        activity_search_et.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//            //每输入一个字，就去访问后台拿数据
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (i2 > 0) {
//                    activity_cancel_ib.setVisibility(View.VISIBLE);
//                } else {
//                    activity_cancel_ib.setVisibility(View.GONE);
//                }
//
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

    }
    public void InitViewPager(){
        fragmentList.add(new AllFragment());
        fragmentList.add(new UserFragment());
        fragmentList.add(new DynamicFragment());
        BaseFragmentAdapter baseFragmentAdapter=new BaseFragmentAdapter(getSupportFragmentManager());
        search_viewpage.setAdapter(baseFragmentAdapter);
        titleList=new ArrayList<>();
        titleList.add(new CaterpillarIndicator.TitleInfo("全部"));
        titleList.add(new CaterpillarIndicator.TitleInfo("用户"));
        titleList.add(new CaterpillarIndicator.TitleInfo("动态"));
        search_titlebar.init(0,titleList,search_viewpage);
    }



    public  void changeUersViewpage(){
        search_titlebar.init(1,titleList,search_viewpage);
    }
    public  void changePostingViewpage(){
        search_titlebar.init(2,titleList,search_viewpage);
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
//    @OnClick(R.id.activity_cancel_ib)
//    public void activity_cancel_ib_Onclick() {
//        activity_search_et.setText(" ");
//    }
//
//    @OnClick(R.id.activity_back_tv)
//    public void activity_back_tv_Onclick() {
//        finish();
//    }
}
