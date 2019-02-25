package com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp;

import android.os.Looper;
import android.widget.Toast;

import com.example.zhan.heathmanage.Internet.Net;
import com.example.zhan.heathmanage.Main.FindFragment.Bean.DynamicInfo;
import com.example.zhan.heathmanage.Main.FindFragment.Service.DynamicDao;
import com.example.zhan.heathmanage.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DynamicDaoImp implements DynamicDao {

    @Override
    public void PublishPosting(final String userPhone, final String Image, final String content, final String location) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient mOkHttpClient=new OkHttpClient();
                    RequestBody formBody = new FormBody.Builder()
                            .add("userPhone",userPhone)
                            .add("postingContent",content)
                            .add("userPhoto",Image)
                            .add("location",location)
                            .build();
                    Request request = new Request.Builder()
                            .url(Net.PublishPosting)
                            .post(formBody)
                            .build();
                    Call call = mOkHttpClient.newCall(request);
                    call.enqueue(new Callback() {
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
                                JSONObject jsonObject1=jsonObject.getJSONObject("PublishPosting");
                                String waring=jsonObject1.getString("warning");
//                                if(waring.equals("0")){
//                                    Looper.prepare();
//                                    String image=jsonObject1.getString("userPhoto");
//                                    MyApplication.setPhoto(image);
//
//                                    Toast.makeText(MyApplication.getContext(),"成功修改头像",Toast.LENGTH_LONG).show();
//                                    Looper.loop();
//                                }
//                                if(waring.equals("1")){
//                                    Looper.prepare();
//                                    Toast.makeText(MyApplication.getContext(),"修改头像失败",Toast.LENGTH_LONG).show();
//                                    Looper.loop();
//                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
