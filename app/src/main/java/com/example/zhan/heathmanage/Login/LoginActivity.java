package com.example.zhan.heathmanage.Login;
/*
  account 登录的账号
  password 登录的密码
  login_recycle  已经登录过的用户列表
  hide_ll   隐藏下方的布局文件
  asdasdas
 */
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.Login.Adapters.UsersAdapter;
import com.example.zhan.heathmanage.Login.Beans.User;
import com.example.zhan.heathmanage.Login.Servers.server.UserServer;
import com.example.zhan.heathmanage.Login.Servers.serverImp.UserServerImp;
import com.example.zhan.heathmanage.Login.utils.TimeCountUtil;
import com.example.zhan.heathmanage.Main.MainActivity;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;
import com.example.zhan.heathmanage.Register.RegisterActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginActivity extends BaseActivity {
    //登录的账号
    @BindView(R.id.login_account)
    EditText account;
    //登录的密码
    @BindView(R.id.login_password)
    EditText password;
    //已经登录过的用户列表TimeCountUtil
    @BindView(R.id.login_account_list)
    RecyclerView login_recycle;
    private TimeCountUtil mTimeCountUtil; //按钮倒计时
    @BindView(R.id.login_verification_send)
    Button login_verification_send;
    private static  int code=-1;
    private List<User> userList = new ArrayList<User>();;//用户的数据源
    private UsersAdapter adapter;//适配器
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    UserServer userServer;
    String phone;
    @BindView(R.id.login_verification_password)EditText login_verification_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userServer=new UserServerImp(this);
        preferences = getSharedPreferences("UserList", MODE_PRIVATE);
        //preferences= PreferenceManager.getDefaultSharedPreferences(getApplication());
        initView();
        mTimeCountUtil = new TimeCountUtil(login_verification_send, 60000, 1000);

        editor = preferences.edit();
        getData();
        if (userList.size()!=0){
            account.setText(preferences.getString("Login_User",null));
            password.setText(preferences.getString("Login_Password",null));
        }
        //创建适配器
        adapter = new UsersAdapter(this,this,userList);
        login_recycle.setLayoutManager(new LinearLayoutManager(this));
        login_recycle.setAdapter(adapter);

    }
    public void  initView(){
        login_verification_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SMSSDK.getVerificationCode("86", account.getText().toString());
                phone = account.getText().toString();
            }
        });


        EventHandler eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                mHandler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eh);
    }

    //TODO 查看UserList 和 每项的账号Item_Phone和密码 Item_Password;
    private void  setData(){
        int count = 0;
        //判断是否有重复的账号
        if(userList!=null&&userList.size()>0){
            for (int i=0; i<userList.size();i++){
                if (userList.get(i).getPhoneNumber().equals(account.getText().toString())){
                    count =1;
                    break;
                }
            }
        }
        //如果没有，则添加该账号
        if (count ==0){
            User user = new User();
            user.setPhoneNumber(account.getText().toString());
            user.setPassword(password.getText().toString());
            userList.add(user);

            editor.putInt("UserListSize",userList.size());
            for (int i = 0 ; i<userList.size();i++){
                editor.putString("Item_Phone"+i,userList.get(i).getPhoneNumber());
                editor.putString("Item_Password"+i,userList.get(i).getPassword());
            }
            editor.commit();
        }
    }
    private List<User> getData(){
        int UserListSize = preferences.getInt("UserListSize",0);
        User user ;
        for (int i = 0 ; i<UserListSize;i++){
            user = new User();
            String Item_Phone = preferences.getString("Item_Phone"+i,null);
            String Item_Password = preferences.getString("Item_Password"+i,null);
            user.setPhoneNumber(Item_Phone);
            user.setPassword(Item_Password);
            userList.add(user);
        }
        return userList;
    }

    //TODO 登录中的各种布局的隐藏和显示
    //包裹RecyclerVIew的布局
    @BindView(R.id.login_recyckview_ll)
    LinearLayout login_recyckview_ll;
    //隐藏下方的布局文件
    @BindView(R.id.login_hide_ll)
    LinearLayout hide_ll;
    //上拉按钮
    @BindView(R.id.login_up_IB)
    ImageButton login_up_IB;
    //下拉按钮
    @BindView(R.id.login_drop_IB)
    ImageButton login_drop_IB;

    //下拉按钮的点击事件
   @OnClick(R.id.login_drop_IB)
   public void login_drop_IB_OnClick(){
       //隐藏其他布局只显示recycle布局
        hide_ll.setVisibility(View.GONE);
        login_recyckview_ll.setVisibility(View.VISIBLE);
        login_verification_ll.setVisibility(View.GONE);
        //上下拉状态改变
        login_drop_IB.setVisibility(View.GONE);
        login_up_IB.setVisibility(View.VISIBLE);

   }
   //验证码登录
    @OnClick(R.id.login_buttonByVerification)
    public void login_buttonByVerification_Onclick(){
        boolean flag=false;
        SMSSDK.submitVerificationCode("86",account.getText().toString(),login_verification_password.getText().toString());

        if(TextUtils.isEmpty(account.getText().toString())){
            flag=true;
        }
        if(TextUtils.isEmpty(login_verification_password.getText().toString())){
            flag=true;
        }
        if(flag){
            Toast.makeText(this,"请填写账号/验证码后再进行登陆",Toast.LENGTH_SHORT).show();
        }

    }


   //上拉按钮点击事件
    @OnClick(R.id.login_up_IB)
    public void login_up_IB_OnClick(){
        //隐藏其他布局只显示密码布局
        hide_ll.setVisibility(View.VISIBLE);
        login_recyckview_ll.setVisibility(View.GONE);
        login_verification_ll.setVisibility(View.GONE);
        //上下拉状态改变
        login_drop_IB.setVisibility(View.VISIBLE);
        login_up_IB.setVisibility(View.GONE);
    }

   //通过验证码登录textView
   @BindView(R.id.loginByverification)
    TextView loginByverification;

    //通过密码登录textView
   @BindView(R.id.loginBypassword)
    TextView loginBypassword;

    //密码登录的布局
   @BindView(R.id.login_password_ll)
    LinearLayout login_password_ll;

    //验证码登录的布局
   @BindView(R.id.login_verification_ll)
    LinearLayout login_verification_ll;

   //验证码TextView登录点击事件（转换成验证码登录）
   @OnClick(R.id.loginByverification)
   public void loginByverification_OnClick(){
       //显示验证码登录布局
       login_verification_ll.setVisibility(View.VISIBLE);
       //隐藏密码登录布局
       hide_ll.setVisibility(View.GONE);
   }

    //密码登录TextView点击事件（转换成密码登录）
   @OnClick(R.id.loginBypassword)
   public void loginBypassword_OnClick(){
       //隐藏验证码登录布局
        login_verification_ll.setVisibility(View.GONE);
       //显示密码登录布局
       hide_ll.setVisibility(View.VISIBLE);

    }
    //历史登录列表的点击事件
    @OnClick(R.id.login_recyckview_ll)
    public void  login_recyckview_ll_OnClick(){
        //回到原来布局
        login_recyckview_ll.setVisibility(View.GONE);
        hide_ll.setVisibility(View.VISIBLE);
        login_drop_IB.setVisibility(View.VISIBLE);
        login_up_IB.setVisibility(View.GONE);
        login_verification_ll.setVisibility(View.GONE);
    }

    //TODO 登录按钮的点击事件处理 和 注册按钮的点击事件处理
   //密码登录Button点击事件
   @OnClick(R.id.login_buttonByPassword)
    public void login_buttonByPassword_OnClick() {
       Log.d("account",account.getText().toString());
       Log.d("password",password.getText().toString());
       if (account.getText().toString().equals("")){
           Toast.makeText(this,"账号不能为空",Toast.LENGTH_SHORT).show();
       }else if (password.getText().toString().equals("")){
           Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
       }else {
           setData();
//           editor.putString("Login_User",account.getText().toString());
//           editor.putString("Login_Password",password.getText().toString());
//           editor.commit();
           userServer.FirstLogin(0,account.getText().toString(),password.getText().toString());
           userServer.UserEvalute(account.getText().toString());
           //Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
       }
   }

   //新用户注册按钮
    @OnClick(R.id.login_newUser)
    public void login_newUser_OnClick(){
       Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
       startActivity(intent);
    }

   //adpter回调函数，点击adpter的item项返回账号密码值
   public void ChangeUser(final String Phone , final String Password){
       //回到原来布局
        login_recyckview_ll.setVisibility(View.GONE);
        hide_ll.setVisibility(View.VISIBLE);
        login_drop_IB.setVisibility(View.VISIBLE);
        login_up_IB.setVisibility(View.GONE);
        login_verification_ll.setVisibility(View.GONE);
        //输入框填入账号密码
        account.setText(Phone);
        password.setText(Password);
   }

   //adpter回调函数，点击删除按钮更新adpter
    public void  freshAdapter(){
       adapter.notifyDataSetChanged();
    }

    public void EvaluteCallBack(){
        editor.putString("BloodFat",MyApplication.getBloodFat());
        editor.putString("BloodOxygen",MyApplication.getBloodOxygen());
        editor.putString("DataTime",MyApplication.getDataTime());
        editor.putString("dBP",MyApplication.getdBP());
        editor.putString("sBP",MyApplication.getsBP());
        editor.putString("HeartRate",MyApplication.getHeartRate());
        editor.commit();
    }
    //登录成功的回调
    public void LoginCallBack(User user){
        MyApplication.setUserPhone(user.getPhoneNumber());
        MyApplication.setUserPassword(user.getPassword());
        MyApplication.setUserWeight(user.getUserWeight());
        MyApplication.setUserAge(user.getUserAge());
        MyApplication.setUserHigh(user.getUserHeight());
        MyApplication.setUserNickName(user.getUserNickName());
        MyApplication.setUserSex(user.getUserSex());
        MyApplication.setPhoto(user.getPhoto());
        MyApplication.setEmergencyPhone(user.getEmergencyPhone());
        MyApplication.setEmergencyName(user.getEmergencyName());
        MyApplication.setUserId(user.getUserId());
        editor.putString("Login_Id",user.getUserId());
        editor.putString("Login_Weight",user.getUserWeight());
        editor.putString("Login_User",user.getPhoneNumber());
        editor.putString("Login_Age",user.getUserAge());
        editor.putString("Login_Height",user.getUserHeight());
        editor.putString("Login_NickName",user.getUserNickName());
        editor.putString("Login_Sex",user.getUserSex());
        editor.putString("UserPhoto",user.getPhoto());
        editor.putString("EmergencyPhone",user.getEmergencyPhone());
        editor.putString("EmergencyName",user.getEmergencyName());
        editor.commit();
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) { // TODO Auto-generated method stub
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event=" + event);
            if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理成功得到验证码的结果
                    mTimeCountUtil.start();
                    Toast.makeText(getApplicationContext(),"发送验证码成功",Toast.LENGTH_LONG).show();

                } else {
                    // TODO 处理错误的结果
                    ((Throwable) data).printStackTrace();

                    Toast.makeText(getApplicationContext(),"请输入正确的手机号码",Toast.LENGTH_LONG).show();
                }
            } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理验证码验证通过的结果
                    Toast.makeText(getApplicationContext(),"验证成功",Toast.LENGTH_LONG).show();

                    userServer.TextLogin(account.getText().toString());
                } else {
                    // TODO 处理错误的结果

                    ((Throwable) data).printStackTrace();
                    Toast.makeText(getApplicationContext(),"验证失败",Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}
