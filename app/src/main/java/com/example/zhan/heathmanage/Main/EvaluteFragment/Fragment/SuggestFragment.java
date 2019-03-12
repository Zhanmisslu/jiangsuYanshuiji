package com.example.zhan.heathmanage.Main.EvaluteFragment.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhan.heathmanage.Main.EvaluteFragment.Adapter.FoodAdapter;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Beans.Food;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Server.server.FoodServer;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Server.serverImp.FoodServerImp;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;

import java.util.ArrayList;
import java.util.List;

public class SuggestFragment extends Fragment {
    private FoodAdapter foodAdapter;
    private RecyclerView recyclerView;
    private FoodServer foodServer;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.suggest_fragment,container,false);
        recyclerView = view.findViewById(R.id.suggest_rv);
        initData();

        return view;
    }
    public void initData(){
        foodServer = new FoodServerImp(this);
        foodServer.GetEatSuggestion(MyApplication.getsBP());
    }
    public void CallBack(final List<Food> list){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                foodAdapter = new FoodAdapter(getContext(),list);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(foodAdapter);
            }
        });
    }
}
