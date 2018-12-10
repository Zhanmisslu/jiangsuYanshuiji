package com.example.zhan.heathmanage.Login.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhan.heathmanage.Login.Beans.User;
import com.example.zhan.heathmanage.Login.LoginActivity;
import com.example.zhan.heathmanage.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter {
    private Context context;
    private LayoutInflater inflater;
    private List<User> list;
    private LoginActivity loginActivity;
    public UsersAdapter(LoginActivity loginActivity,Context context, List<User> list){
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        this.loginActivity = loginActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.login_droplist_item,viewGroup,false);
        return new UsersViewHolder(view);
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder{
        ImageView login_drop_deleter;//删除账号视图
        TextView login_drop_account;//显示账号
        RoundedImageView login_drop_photo;//显示头像
        LinearLayout login_drop_ll;
        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            login_drop_account = itemView.findViewById(R.id.login_drop_account);
            login_drop_deleter = itemView.findViewById(R.id.login_drop_deleter);
            login_drop_photo = itemView.findViewById(R.id.login_drop_photo);
            login_drop_ll = itemView.findViewById(R.id.login_drop_ll);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof UsersViewHolder){
            setUserItemValues((UsersViewHolder) viewHolder,i);
        }
    }
    //设置每个Item的具体事件
    public  void  setUserItemValues(UsersViewHolder viewHolder, final int i){
        viewHolder.login_drop_account.setText(list.get(i).getPhoneNumber());
        viewHolder.login_drop_deleter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"你点击了删除按钮",Toast.LENGTH_LONG).show();
            }
        });
        viewHolder.login_drop_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginActivity.ChangeUser(list.get(i).getPhoneNumber(),list.get(i).getPassword());
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
