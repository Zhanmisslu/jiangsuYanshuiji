package com.example.zhan.heathmanage.Main.TrendFragment.Fragment.ServiceDao;

public interface SuggestDao {
    void getWeekSuggestion(String userPhone,String startDay,String finalDay);
    void getMonthSuggestion(String userPhone,String year,String month);
}
