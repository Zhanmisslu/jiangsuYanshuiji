package com.example.zhan.heathmanage.Login.Servers.serverImp;

import android.os.Looper;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import com.example.zhan.heathmanage.Internet.Net;
import com.example.zhan.heathmanage.Internet.OKHttp;
import com.example.zhan.heathmanage.Login.Beans.User;
import com.example.zhan.heathmanage.Login.LoginActivity;
import com.example.zhan.heathmanage.Login.Servers.server.UserServer;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.Register.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UserServerImp implements UserServer {
    User user= new User();
    private LoginActivity loginActivity;
    private RegisterActivity registerActivity;
    public UserServerImp(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }
    public UserServerImp(RegisterActivity registerActivity) {
        this.registerActivity = registerActivity;
    }
    @Override
    public void Register(String UserPhone, String UserPassword) {
      String URL = Net.Register;
        OKHttp.sendOkhttpGetRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(),"注册失败",Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(),"注册成功",Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        });
    }

    @Override
    public void FirstLogin(final int i,final String UserPhone, final String UserPassword) {
        final String url=Net.PasswordLogin+"?userPhone="+UserPhone+"&userPassword="+ UserPassword;
        OKHttp.sendOkhttpGetRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
               // Toast.makeText(MyApplication.getContext(),"登陆失败，请检查网络连接",Toast.LENGTH_SHORT).show();
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(),"╮(╯▽╰)╭连接不上了",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ResponseData=response.body().string();
                try {
                    Log.v("zjc",url);
                    JSONObject jsonObject=new JSONObject(ResponseData);
                    JSONObject jsonObject1=jsonObject.getJSONObject("PasswordLogin");
                    String warning=jsonObject1.getString("warning");
                    if(warning.equals("0")){
                        user.setPhoneNumber(UserPhone);
                        user.setPassword(UserPassword);
                        String weight = jsonObject1.getString("userWeight");
                        String userHeight = jsonObject1.getString("userHeight");
                        String userSex = jsonObject1.getString("userSex");
                        String userNickName = jsonObject1.getString("userNickName");
                        String userAge =  jsonObject1.getString("userAge");
                        user.setUserWeight(weight);
                        user.setUserNickName(userNickName);
                        user.setUserHeight(userHeight);
                        user.setUserSex(userSex);
                        user.setUserAge(userAge);
                        if (i == 0){
                            loginActivity.LoginCallBack(user);
                        }
                        Looper.prepare();
                        Toast.makeText(MyApplication.getContext(),"登录成功",Toast.LENGTH_SHORT).show();
                        Looper.loop();

                    }else if(warning.equals("1")){
                        if (i==0){
                            Looper.prepare();
                            Toast.makeText(MyApplication.getContext(),"密码错误",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }else if (i==1){
                            Looper.prepare();
                            Toast.makeText(MyApplication.getContext(),"该账号已经注册过",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }else if(warning.equals("2")){
                        if (i==0){
                            Looper.prepare();
                            Toast.makeText(MyApplication.getContext(),"该账号未注册",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }else if (i==1){
                            registerActivity.registerBack();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("TAG", e.getMessage(), e);
                }
            }
        });
    }
}
