package com.example.zhan.heathmanage.Main.EvaluteFragment.Server.serverImp;

import android.os.Looper;
import android.widget.Toast;

import com.example.zhan.heathmanage.Internet.Net;
import com.example.zhan.heathmanage.Internet.OKHttp;
import com.example.zhan.heathmanage.Login.Beans.User;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Fragment.PersonFragment;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Server.server.UpdateUseServer;
import com.example.zhan.heathmanage.MyApplication;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UpdateUserServerImp implements UpdateUseServer {
    User user = new User();
    PersonFragment personFragment;
    public UpdateUserServerImp(PersonFragment personFragment){
        this.personFragment = personFragment;
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
                personFragment.UpdateCallBcak();
            }
        });
    }
}
