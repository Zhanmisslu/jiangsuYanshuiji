package com.example.zhan.heathmanage.Main.FindFragment.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.zhan.heathmanage.Main.FindFragment.Activity.LocationListActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.LocationInfo;
import com.example.zhan.heathmanage.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocateViewHolder> {
    private Context mContext;
    private List<LocationInfo> mList;
    private List<Integer> positionList=new ArrayList<>();
    private int index=-1;
    private LocationListActivity locationListActivity;
    private int Location=0;

    public LocationAdapter(Context mContext, List<LocationInfo> mList, LocationListActivity locationListActivity) {
        this.mContext = mContext;
        this.mList = mList;
        this.locationListActivity = locationListActivity;
    }

    public LocationAdapter(Context context, List<LocationInfo> list) {
        mContext = context;
        mList = list;
    }
    @Override
    public LocationAdapter.LocateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.locate_info_item,parent,false);
        positionList.add(locationListActivity.pos);
        return new LocationAdapter.LocateViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final LocateViewHolder holder, final int position) {
        if(position==0){
            holder.mTextView.setText("不显示位置");
            holder.mTextView.setTextColor(R.color.colorPrimary);
            holder.mTextView.setTextSize(17);
            if(locationListActivity.pos==0) {
                holder.locate_info_rb.setVisibility(View.VISIBLE);
                holder.locate_info_rb.setChecked(true);
                positionList.add(position);
            }
        }else {
            holder.mTextView.setText(mList.get(position).getAddress());
        }

        holder.location_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionList.clear();
                positionList.add(position);
                locationListActivity.addresses=holder.mTextView.getText().toString();
                Intent intent=new Intent();
                intent.putExtra("address",holder.mTextView.getText().toString());
                intent.putExtra("position",position);
                locationListActivity.setResult(Location,intent);
                locationListActivity.finish();


            }
        });
        holder.locate_info_rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    index = position;
                    //
                }
            }
        });
        if (positionList.get(0) == position) {
            holder.locate_info_rb.setVisibility(View.VISIBLE);
            holder.locate_info_rb.setChecked(true);

        } else {
            holder.locate_info_rb.setVisibility(View.GONE);
            holder.locate_info_rb.setChecked(false);

        }
    }



    @Override
    public int getItemCount() {
        return mList.size();
    }
    public static class LocateViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.locate_info_rb)RadioButton locate_info_rb;
        @BindView(R.id.locate_info_adress)
        TextView mTextView;
        @BindView(R.id.location_ll)LinearLayout location_ll;
        public LocateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }

}
