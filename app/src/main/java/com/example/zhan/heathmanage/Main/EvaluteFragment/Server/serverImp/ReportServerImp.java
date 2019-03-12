package com.example.zhan.heathmanage.Main.EvaluteFragment.Server.serverImp;

import android.os.Looper;
import android.widget.Toast;

import com.example.zhan.heathmanage.Internet.Net;
import com.example.zhan.heathmanage.Internet.OKHttp;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Fragment.ReportFragment;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Server.server.ReportServer;
import com.example.zhan.heathmanage.MyApplication;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ReportServerImp implements ReportServer {
    public ReportFragment reportFragment;
    public ReportServerImp(ReportFragment reportFragment){
        this.reportFragment = reportFragment;
    }
    @Override
    public void getReport(String sBP, String dBP, String heartRate, String bloodFat, String bloodOxygen) {
       String URL = Net.ClickToReport+"?sBP="+sBP+"&dBp="+dBP+"&heartRate="+heartRate+"&bloodFat="+bloodFat+"&bloodOxygen="+bloodOxygen;
        OKHttp.sendOkhttpGetRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(),"生成失败",Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res=response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("ClickToReport");
                    String img = jsonObject1.getString("imgPath");
                    reportFragment.callBack(img);
                }catch (Exception e){
                    Looper.prepare();
                    Toast.makeText(MyApplication.getContext(),"出现了一丝不同寻常的错误",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        });
    }
}
