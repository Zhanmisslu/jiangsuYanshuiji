package com.example.zhan.heathmanage.Main.EvaluteFragment.Server.serverImp;

import android.os.Looper;
import android.widget.Toast;

import com.example.zhan.heathmanage.Internet.Net;
import com.example.zhan.heathmanage.Internet.OKHttp;
import com.example.zhan.heathmanage.Login.Beans.User;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Fragment.PersonFragment;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Server.server.UpdateUseServer;
import com.example.zhan.heathmanage.Main.Menu.UpdateNameActivity;
import com.example.zhan.heathmanage.Main.Menu.UserActivity;
import com.example.zhan.heathmanage.MyApplication;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
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
}
