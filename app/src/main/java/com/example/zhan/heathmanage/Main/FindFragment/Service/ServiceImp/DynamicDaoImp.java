package com.example.zhan.heathmanage.Main.FindFragment.Service.ServiceImp;

import android.os.Looper;
import android.widget.Toast;

import com.example.zhan.heathmanage.Internet.Net;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.DynamicActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.PublishPostingActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Activity.PublishTextActivity;
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
    PublishPostingActivity publishPostingActivity;
    PublishTextActivity publishTextActivity;

    public DynamicDaoImp(PublishPostingActivity publishPostingActivity) {
        this.publishPostingActivity = publishPostingActivity;
    }

    public DynamicDaoImp(PublishTextActivity publishTextActivity) {
        this.publishTextActivity = publishTextActivity;
    }

    @Override
    public void PublishPosting(final String userPhone, final String Image, final String content, final String location) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient mOkHttpClient = new OkHttpClient();
                    RequestBody formBody = new FormBody.Builder()
                            .add("userPhone", userPhone)
                            .add("postingContent", content)
                            .add("postingImg", Image)
                            .add("location", location)
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
                            Toast.makeText(MyApplication.getContext(), "修改失败，请检查网络", Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String res = response.body().string();
                            try {
                                JSONObject jsonObject = new JSONObject(res);
                                JSONObject jsonObject1 = jsonObject.getJSONObject("PublishPosting");
                                String waring = jsonObject1.getString("warning");
                                if (waring.equals("上传不带图片帖子成功")) {
                                    Looper.prepare();
                                    Toast.makeText(MyApplication.getContext(), "发帖成功", Toast.LENGTH_LONG).show();
                                    publishTextActivity.callback();
                                    Looper.loop();
                                }
                                if (waring.equals("上传带图片帖子成功")) {
                                    Looper.prepare();
                                    Toast.makeText(MyApplication.getContext(), "发帖成功", Toast.LENGTH_LONG).show();
                                    publishPostingActivity.callback();
                                    Looper.loop();
                                }
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
