package com.example.zhan.heathmanage.Main.EvaluteFragment.Server.serverImp;

import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.zhan.heathmanage.Internet.Net;
import com.example.zhan.heathmanage.Internet.OKHttp;
import com.example.zhan.heathmanage.Login.Beans.User;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Fragment.PersonFragment;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Server.server.UpdateUseServer;
import com.example.zhan.heathmanage.Main.Menu.UpdateNameActivity;
import com.example.zhan.heathmanage.Main.Menu.UserActivity;
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

public class UpdateUserServerImp implements UpdateUseServer {
    User user = new User();
    PersonFragment personFragment;
    UserActivity userActivity;
    UpdateNameActivity updateNameActivity;
    int i ;
    public UpdateUserServerImp(){}
    public UpdateUserServerImp(int i,PersonFragment personFragment){
        this.personFragment = personFragment;
        this.i = i;
    }
    public UpdateUserServerImp(int i,UserActivity userActivity){
        this.userActivity = userActivity;
        this.i = i;
    }
    public UpdateUserServerImp(UpdateNameActivity updateNameActivity){
        this.updateNameActivity=updateNameActivity;
    }

    public UpdateUserServerImp(UserActivity userActivity) {
        this.userActivity = userActivity;
    }

    @Override
    public void UpdateUser(String userHeight, String userWeight, String userSex, String userAge) {
       String URL = Net.ChangeUserInfo +"?userPhone="+ MyApplication.getUserPhone()
               +"&userHeight="+userHeight+"&userWeight="+userWeight+"&userSex="+userSex+"&userAge="+userAge;
        OKHttp.sendOkhttpGetRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(),"修改失败",Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (i==0){
                    personFragment.UpdateCallBcak();
                }else if (i ==1){
                    userActivity.callback();
                }
            }
        });
    }

    @Override
    public void UpdateNickName(final String NickName) {
       String URL = Net.ChangeUserNickName+"?userPhone="+MyApplication.getUserPhone()+"&userNickName="+NickName;
       OKHttp.sendOkhttpGetRequest(URL, new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {
               Looper.prepare();
               Toast.makeText(MyApplication.getContext(),"修改失败，请检查网络",Toast.LENGTH_LONG).show();
               Looper.loop();
           }

           @Override
           public void onResponse(Call call, Response response) throws IOException {

               updateNameActivity.callback();
           }
       });
    }

    @Override
    public void UpdateImage(final String UsePhone, final String UsePhoto) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient mOkHttpClient=new OkHttpClient();
                    RequestBody formBody = new FormBody.Builder()
                            .add("userPhone", UsePhone)
                            .add("userPhoto",UsePhoto)
                            .build();
                    Request request = new Request.Builder()
                            .url(Net.ChangeUserImage)
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
                                JSONObject jsonObject1=jsonObject.getJSONObject("ChangeUserPhoto");
                                String waring=jsonObject1.getString("warning");
                                if(waring.equals("0")){
                                    Looper.prepare();
                                    String image=jsonObject1.getString("userPhoto");
                                    MyApplication.setPhoto(image);
                                    userActivity.imagecallback(image);
                                    Toast.makeText(MyApplication.getContext(),"成功修改头像",Toast.LENGTH_LONG).show();
                                    Looper.loop();
                                }
                                if(waring.equals("1")){
                                    Looper.prepare();
                                    Toast.makeText(MyApplication.getContext(),"修改头像失败",Toast.LENGTH_LONG).show();
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

    @Override
    public void GetUserMessage() {
        String URL = Net.ShowUserInfo+"?userPhone="+MyApplication.getUserPhone();
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
                    JSONObject jsonObject1=jsonObject.getJSONObject("ShowUserInfo");
                    String waring=jsonObject1.getString("warning");
                    if(waring.equals("0")){
                        String NickName=jsonObject1.getString("userNickName");
                        String Height=jsonObject1.getString("userHeight");
                        String Weight=jsonObject1.getString("userWeight");
                        String Age=jsonObject1.getString("userAge");
                        String Sex=jsonObject1.getString("userSex");
                        String Image=jsonObject1.getString("userPhoto");

                        userActivity.getUserMessageCallBack(NickName,Height,Weight,Age,Sex,Image);
                        Looper.prepare();
                        Toast.makeText(MyApplication.getContext(),"成功加载信息",Toast.LENGTH_LONG).show();
                        Looper.loop();

                    }
                    if(waring.equals("1")){
                        Looper.prepare();
                        Toast.makeText(MyApplication.getContext(),"没有此用户",Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
