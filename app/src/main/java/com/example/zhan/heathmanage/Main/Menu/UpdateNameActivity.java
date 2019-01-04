package com.example.zhan.heathmanage.Main.Menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Server.server.UpdateUseServer;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Server.serverImp.UpdateUserServerImp;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdateNameActivity extends BaseActivity {
    @BindView(R.id.nick_et)
    EditText nick_et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_name);
        Intent intent = getIntent();
        String nick = intent.getStringExtra("nick");
        nick_et.setText(nick);
    }
    @OnClick(R.id.nick_back)
    public void ni(){
        finish();
    }
    @OnClick(R.id.nick_button)
    public void buto(){
        if (!nick_et.getText().toString().isEmpty()){
            UpdateUseServer  updateUseServer = new UpdateUserServerImp(UpdateNameActivity.this);
            updateUseServer.UpdateNickName(nick_et.getText().toString());
        }else {
            Toast.makeText(this,"昵称不能为空",Toast.LENGTH_SHORT).show();
        }

    }
    public void callback(){
       new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.putExtra("Nick",nick_et.getText().toString());
                setResult(1,intent);
                finish();
            }
        }).start();

    }
}
