package com.example.zhan.heathmanage.Main.EvaluteFragment.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhan.heathmanage.Main.EvaluteFragment.Adapter.FoodAdapter;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Beans.Food;
import com.example.zhan.heathmanage.R;

import java.util.ArrayList;
import java.util.List;

public class DontSuggestFragment extends Fragment {
    private FoodAdapter foodAdapter;
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.suggest_fragment,container,false);
        recyclerView = view.findViewById(R.id.suggest_rv);
        initData();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(foodAdapter);
        return view;
    }
    public void initData(){
        List<Food> list = new ArrayList<>();
        Food food = new Food();
        food.setFoodName("蛋炒饭");
        food.setFoodIntroduce("蛋炒饭，好吃好吃好吃");
        list.add(food);
        food = new Food();
        food.setFoodName("蛋炒饭");
        food.setFoodIntroduce("蛋炒饭，好吃好吃好吃");
        list.add(food);
        food = new Food();
        food.setFoodName("蛋炒饭");
        food.setFoodIntroduce("蛋炒饭，好吃好吃好吃");
        list.add(food);
        food = new Food();
        food.setFoodName("蛋炒饭");
        food.setFoodIntroduce("蛋炒饭，好吃好吃好吃");
        list.add(food);
        food = new Food();
        food.setFoodName("蛋炒饭");
        food.setFoodIntroduce("蛋炒饭，好吃好吃好吃");
        list.add(food);
        food = new Food();
        food.setFoodName("蛋炒饭");
        food.setFoodIntroduce("蛋炒饭，好吃好吃好吃");
        list.add(food);
        foodAdapter = new FoodAdapter(getContext(),list);
    }
}
