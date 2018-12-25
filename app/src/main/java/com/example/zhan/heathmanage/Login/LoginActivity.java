package com.example.zhan.heathmanage.Login;
/*
  account 登录的账号
  password 登录的密码
  login_recycle  已经登录过的用户列表
  hide_ll   隐藏下方的布局文件
  asdasdas
 */
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.Login.Adapters.UsersAdapter;
import com.example.zhan.heathmanage.Login.Beans.User;
import com.example.zhan.heathmanage.Main.MainActivity;
import com.example.zhan.heathmanage.R;
import com.example.zhan.heathmanage.Register.RegisterActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    //登录的账号
    @BindView(R.id.login_account)
    EditText account;
    //登录的密码
    @BindView(R.id.login_password)
    EditText password;
    //已经登录过的用户列表
    @BindView(R.id.login_account_list)
    RecyclerView login_recycle;

    private List<User> userList = new ArrayList<User>();;//用户的数据源
    private UsersAdapter adapter;//适配器
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = getSharedPreferences("UserList", MODE_PRIVATE);
        editor = preferences.edit();
        getData();
        if (userList.size()!=0){
            account.setText(preferences.getString("Login_User",null));
            Log.d("Login_User", preferences.getString("Login_User",null));
            password.setText(preferences.getString("Login_Password",null));
        }
        //创建适配器
        adapter = new UsersAdapter(this,this,userList);
        login_recycle.setLayoutManager(new LinearLayoutManager(this));
        login_recycle.setAdapter(adapter);

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
           editor.putString("Login_User",account.getText().toString());
           editor.putString("Login_Password",password.getText().toString());
           editor.commit();
           Intent intent=new Intent(LoginActivity.this, MainActivity.class);
           startActivity(intent);
           Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
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
}
