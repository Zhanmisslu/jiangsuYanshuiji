package com.example.zhan.heathmanage.Main.TrendFragment.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhan.heathmanage.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayFragment extends Fragment {
    private View view;

    public DayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
                view=inflater.inflate(R.layout.fragment_day, container, false);

                return view;
    }

}
