package com.example.zhan.heathmanage.Main.EvaluteFragment.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Beans.Food;
import com.example.zhan.heathmanage.R;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.SuggestViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Food> foodList;
    public FoodAdapter(Context context,List<Food> list){
        this.context =context;
        this.foodList = list;
        this.inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public SuggestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =  LayoutInflater.from(context).inflate(R.layout.item_suggest,viewGroup,false);
        return new SuggestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestViewHolder suggestViewHolder, int i) {
        suggestViewHolder.food_name.setText(foodList.get(i).getFoodName());
        suggestViewHolder.food_introduce.setText(foodList.get(i).getFoodIntroduce());
        Glide.with(context)
                .load(foodList.get(i).getFoodPhoto())
                .asBitmap()
                .error(R.drawable.login_backgroud)
                .into(suggestViewHolder.food_img);
    }


    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class  SuggestViewHolder extends RecyclerView.ViewHolder {
        private TextView food_name;
        private TextView food_introduce;
        private ImageView food_img;
        public SuggestViewHolder(@NonNull View itemView) {
            super(itemView);
            food_img = itemView.findViewById(R.id.food_img);
            food_introduce = itemView.findViewById(R.id.food_introduce);
            food_name = itemView.findViewById(R.id.food_name);
        }
    }
}
