package com.example.zhan.heathmanage.Main.FindFragment.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Adapter.LocationAdapter;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.LocationInfo;
import com.example.zhan.heathmanage.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class LocationListActivity extends BaseActivity implements AMapLocationListener, PoiSearch.OnPoiSearchListener  {
    public AMapLocationClient mlocationClient = null;
    @BindView(R.id.locate_recycler)
    RecyclerView mLocateRecycler;
    @BindView(R.id.locate_cancel)
    ImageButton mLocateCancel;
    @BindView(R.id.locate_refresh)
    TextView mLocateRefresh;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();
    private List<LocationInfo> mList;
    private LocationAdapter mAdapter;
    public static  int pos=0;
    public static String addresses="不显示位置";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);
        pos=getIntent().getIntExtra("pos",-1);
//        String b=getIntent().getStringExtra("pos");
//        pos= Integer.parseInt(getIntent().getStringExtra("pos"));
        initLocate();
        mList = new ArrayList<>();
        mAdapter = new LocationAdapter(this, mList,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLocateRecycler.setLayoutManager(layoutManager);
        mLocateRecycler.setAdapter(mAdapter);
        //mAdapter.setLocationItemClick(this);
        String a=sHA1(getApplicationContext());
        Log.v("zjc",a);
    }
    private void initLocate() {
        //声明mLocationOption对象
        AMapLocationClientOption mLocationOption = null;
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000000);
        mLocationOption.setOnceLocationLatest(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        intent.putExtra("address",addresses);
        intent.putExtra("position",pos);
        setResult(0,intent);
        super.onBackPressed();


    }
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                double latitude = amapLocation.getLatitude();//获取纬度
                double longitude = amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                Log.d("haha", amapLocation.getAddress());
                LocationInfo locationInfo = new LocationInfo();
                locationInfo.setAddress(amapLocation.getAddress());
                locationInfo.setLatitude(latitude);
                locationInfo.setLonTitude(longitude);
                mList.clear();
                mList.add(locationInfo);
                mList.remove("");
                mAdapter.notifyDataSetChanged();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                //参数“query”为搜索的关键字，“ctgr”为搜索类型（类型参照表从相关下载处获取）、“city”为搜索城市，是必填参数，关键字和类型至少输入一个。
                PoiSearch.Query query = new PoiSearch.Query("", "生活服务", "");
                query.setPageSize(20);
                PoiSearch search = new PoiSearch(this, query);
                //设置周边搜索的中心点以及半径
                search.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), 10000));
                search.setOnPoiSearchListener(this);
                search.searchPOIAsyn();

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }
    //解析result获取POI信息
    @Override
    public void onPoiSearched(PoiResult result, int i) {
        PoiSearch.Query query = result.getQuery();
        ArrayList<PoiItem> pois = result.getPois();
        for (PoiItem poi : pois) {
            String name = poi.getCityName();
            String snippet = poi.getSnippet();
            String address=poi.getTitle();
            LocationInfo info = new LocationInfo();
            info.setAddress(address);
            LatLonPoint point = poi.getLatLonPoint();

            info.setLatitude(point.getLatitude());
            info.setLonTitude(point.getLongitude());
            mList.add(info);
            Log.d("haha", "poi" + snippet);

        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPoiItemSearched(com.amap.api.services.core.PoiItem poiItem, int i) {

    }



    @OnClick({R.id.locate_cancel, R.id.locate_refresh})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.locate_cancel:
                Intent intent=new Intent();
                intent.putExtra("address",addresses);
                intent.putExtra("position",pos);
                setResult(0,intent);
                finish();
                break;
            case R.id.locate_refresh:
                initLocate();
                Toast.makeText(this, "正在重定位", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result=hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==100){
            pos=getIntent().getIntExtra("pos",-1);
        }
    }
}
