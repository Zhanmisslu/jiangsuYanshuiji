package com.example.zhan.heathmanage.Main.EvaluteFragment.Server.serverImp;

import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.zhan.heathmanage.Internet.Net;
import com.example.zhan.heathmanage.Internet.OKHttp;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Server.server.EmergencyContactDao;
import com.example.zhan.heathmanage.Main.Menu.EmergencyContactActivity;
import com.example.zhan.heathmanage.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class EmergencyContactDaoImp implements EmergencyContactDao {
    EmergencyContactActivity emergencyContactActivity;

    public EmergencyContactDaoImp(EmergencyContactActivity emergencyContactActivity) {
        this.emergencyContactActivity = emergencyContactActivity;
    }

    @Override
    public void ChangeUserEmergency(String Userphone, String UserName) {
        String URL = Net.ChangeUserEmergency+"?userPhone="+ Userphone+"&userEmergency="+UserName;
        Log.v("zjc",URL);

        OKHttp.sendOkhttpGetRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(),"修改失败，请检查网络",Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res=response.body().string();
                try {
                    JSONObject jsonObject=new JSONObject(res);
                    JSONObject jsonObject1=jsonObject.getJSONObject("ChangeUserEmergency");
                    String waring=jsonObject1.getString("waring");
                    if(waring.equals("0")) {
                        emergencyContactActivity.callback();
                    }else {
                        Looper.prepare();
                        Toast.makeText(MyApplication.getContext(),"修改失败",Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                    }catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
