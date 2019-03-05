package com.example.zhan.heathmanage.Main.FindFragment.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.zhan.heathmanage.Main.FindFragment.Activity.SearchActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Adapter.AllPostingAdapter;
import com.example.zhan.heathmanage.Main.FindFragment.Adapter.AllUserAdapter;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.AttentionInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.PeopleInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Service.SearchDao;
import com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp.SearchDaoImp;
import com.example.zhan.heathmanage.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllFragment extends Fragment {

    @BindView(R.id.fragment_all_posting_rv)RecyclerView fragment_all_posting_rv;
    @BindView(R.id.fragment_all_user_rv)RecyclerView fragment_all_user_rv;
    @BindView(R.id.fragment_all_showmoreuser_ll)LinearLayout fragment_all_showmoreuser_ll;
    @BindView(R.id.fragment_all_showmoreposting_ll)LinearLayout fragment_all_showmoreposting_ll;
    @BindView(R.id.fragment_all_user_ll)LinearLayout fragment_all_user_ll;
    @BindView(R.id.fragment_all_posting_ll)LinearLayout fragment_all_posting_ll;
    SearchDao searchDao;
    AllUserAdapter allUserAdapter;
    AllPostingAdapter allPostingAdapter;
    public AllFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_all, container, false);
        ButterKnife.bind(this,view);
        searchDao=new SearchDaoImp(this);
        searchDao.SearchAllUser(SearchActivity.text);
        searchDao.SearchAllPosting(SearchActivity.text);
        fragment_all_user_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragment_all_posting_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        return  view;
    }
    @OnClick(R.id.fragment_all_showmoreuser_ll)
    public void fragment_all_showmoreuser_ll_Onclick(){
        ((SearchActivity)getActivity()).changeUersViewpage();
    }
    @OnClick(R.id.fragment_all_showmoreposting_ll)
    public void fragment_all_showmoreposting_ll_Onlick(){
        ((SearchActivity)getActivity()).changePostingViewpage();
    }

    public void InitAllUserData(final List<PeopleInfo> allUserList) {
        allUserAdapter=new AllUserAdapter(allUserList,this,getContext());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fragment_all_user_rv.setAdapter(allUserAdapter);
                if(allUserList.size()<3){
                    fragment_all_showmoreuser_ll.setVisibility(View.GONE);
                }if (allUserList.size()==0){
                    fragment_all_user_ll.setVisibility(View.GONE);
                }
            }
        });
    }

    public void InitAllPostingList(final List<AttentionInfo> allPostingList) {
        allPostingAdapter=new AllPostingAdapter(allPostingList,this,getContext());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fragment_all_posting_rv.setAdapter(allPostingAdapter);
                if (allPostingList.size()<3){
                    fragment_all_showmoreposting_ll.setVisibility(View.GONE);
                }if (allPostingList.size()==0){
                    fragment_all_posting_ll.setVisibility(View.GONE);
                }
            }
        });
    }
}
