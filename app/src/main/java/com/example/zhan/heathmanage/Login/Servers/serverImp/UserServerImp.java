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

    //注册接口
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
    //第一次登陆
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
                        String userPhoto=jsonObject1.getString("userPhoto");
                        String emergencyPhone=jsonObject1.getString("userEmergency");
                        String emergencyName=jsonObject1.getString("userEmergencyName");
                        String userid=jsonObject1.getString("userId");
                        user.setUserId(userid);
                        user.setEmergencyName(emergencyName);
                        user.setEmergencyPhone(emergencyPhone);
                        user.setUserWeight(weight);
                        user.setUserNickName(userNickName);
                        user.setUserHeight(userHeight);
                        user.setUserSex(userSex);
                        user.setUserAge(userAge);
                        user.setPhoto(userPhoto);
                        if (i == 0){
                            loginActivity.LoginCallBack(user);
                        }

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

    //得到个人信息的接口
    @Override
    public void UserEvalute(String UserPhone) {
       String URL = Net.ShowLatestData+"?userPhone="+UserPhone;
        user= new User();
       OKHttp.sendOkhttpGetRequest(URL, new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {
               Looper.prepare();
               Toast.makeText(MyApplication.getContext(),"获取信息失败",Toast.LENGTH_LONG).show();
               Looper.loop();
           }

           @Override
           public void onResponse(Call call, Response response) throws IOException {
               String ResponseData=response.body().string();
               try {
                   JSONObject jsonObject=new JSONObject(ResponseData);
                   JSONObject jsonObject1=jsonObject.getJSONObject("ShowLatestData");
                   String warning=jsonObject1.getString("warning");
                   if (warning.equals("2")){
                       Looper.prepare();
                       Toast.makeText(MyApplication.getContext(),"该用户昨天没开车哦",Toast.LENGTH_LONG).show();
                       Looper.loop();
                   }else {
                       MyApplication.setBloodFat(jsonObject1.getString("bloodFat"));
                       MyApplication.setBloodOxygen(jsonObject1.getString("bloodOxygen"));
                       MyApplication.setDataTime(jsonObject1.getString("dataTime"));
                       MyApplication.setdBP(jsonObject1.getString("dBP"));
                       MyApplication.setsBP(jsonObject1.getString("sBP"));
                       MyApplication.setHeartRate(jsonObject1.getString("heartRate"));
                       MyApplication.setRanting(jsonObject1.getString("ranting"));
                       loginActivity.EvaluteCallBack();
                   }
               }catch (Exception e){
                   Looper.prepare();
                   Toast.makeText(MyApplication.getContext(),"网络炸了",Toast.LENGTH_LONG).show();
                   Looper.loop();
               }

           }
       });
    }

    @Override
    public void TextLogin(final String UserPhone) {
        String url=Net.CaptchaLogin+"?userPhone="+UserPhone;
        OKHttp.sendOkhttpGetRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(),"╮(╯▽╰)╭连接不上了",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ResponseData=response.body().string();
                try {
                    JSONObject jsonObject=new JSONObject(ResponseData);
                    JSONObject jsonObject1=jsonObject.getJSONObject("CaptchaLogin");
                    String warning=jsonObject1.getString("warning");
                    if(warning.equals("0")) {
                        user.setPhoneNumber(UserPhone);
                        String weight = jsonObject1.getString("userWeight");
                        String userHeight = jsonObject1.getString("userHeight");
                        String userSex = jsonObject1.getString("userSex");
                        String userNickName = jsonObject1.getString("userNickName");
                        String userAge = jsonObject1.getString("userAge");
                        String userPhoto = jsonObject1.getString("userPhoto");
                        String emergencyPhone = jsonObject1.getString("userEmergency");
                        String emergencyName = jsonObject1.getString("userEmergencyName");
                        String userid = jsonObject1.getString("userId");
                        user.setUserId(userid);
                        user.setEmergencyName(emergencyName);
                        user.setEmergencyPhone(emergencyPhone);
                        user.setUserWeight(weight);
                        user.setUserNickName(userNickName);
                        user.setUserHeight(userHeight);
                        user.setUserSex(userSex);
                        user.setUserAge(userAge);
                        user.setPhoto(userPhoto);
                        loginActivity.LoginCallBack(user);
                    }else {
                        Looper.prepare();
                        Toast.makeText(MyApplication.getContext(),"该用户未注册",Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
