package com.example.zhan.heathmanage.Main.EvaluteFragment.Server.serverImp;

import android.os.Looper;
import android.widget.Toast;

import com.example.zhan.heathmanage.Internet.Net;
import com.example.zhan.heathmanage.Internet.OKHttp;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Beans.Food;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Fragment.DontSuggestFragment;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Fragment.SuggestFragment;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Server.server.FoodServer;
import com.example.zhan.heathmanage.MyApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FoodServerImp implements FoodServer {
    SuggestFragment suggestFragment;
    DontSuggestFragment dontSuggestFragment;
    public FoodServerImp(SuggestFragment suggestFragment){
        this.suggestFragment = suggestFragment;
    }
    public FoodServerImp(DontSuggestFragment dontSuggestFragment){
        this.dontSuggestFragment = dontSuggestFragment;
    }

    @Override
    public void GetEatSuggestion(String sBP) {
        String URL = Net.GetEatSuggestion+"?sBP="+sBP;
        OKHttp.sendOkhttpGetRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(),"得到食物列表失败",Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res=response.body().string();
                try {
                    List<Food> list = new ArrayList();
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray jsonArray=jsonObject.getJSONArray("GetEatSuggestion");
                    for (int i=1;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        Food food =new Food();
                        food.setFoodPhoto(jsonObject1.getString("eatFoodImg"));
                        food.setFoodIntroduce(jsonObject1.getString("eatFoodSuggestion"));
                        food.setFoodName(jsonObject1.getString("eatFoodName"));
                        list.add(food);
                    }
                    suggestFragment.CallBack(list);
                }catch (Exception e){
                    Looper.prepare();
                    Toast.makeText(MyApplication.getContext(),"出现了一丝不同寻常的错误",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        });
    }

    @Override
    public void GetUnEatSuggestion(String sBP) {
        String URL = Net.GetUnEatSuggestion+"?sBP="+sBP;
        OKHttp.sendOkhttpGetRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(),"得到食物列表失败",Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res=response.body().string();
                try {
                    List<Food> list = new ArrayList();
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray jsonArray=jsonObject.getJSONArray("GetUnEatSuggestion");
                    for (int i=1;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        Food food =new Food();
                        food.setFoodPhoto(jsonObject1.getString("uneatFoodImg"));
                        food.setFoodIntroduce(jsonObject1.getString("uneatFoodSuggestion"));
                        food.setFoodName(jsonObject1.getString("uneatFoodName"));
                        list.add(food);
                    }
                    dontSuggestFragment.CallBack(list);
                }catch (Exception e){
                    Looper.prepare();
                    Toast.makeText(MyApplication.getContext(),"出现了一丝不同寻常的错误",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        });
    }
}
