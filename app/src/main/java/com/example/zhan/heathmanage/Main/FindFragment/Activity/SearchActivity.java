package com.example.zhan.heathmanage.Main.FindFragment.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.R;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {
    @BindView(R.id.activity_search_et)
    EditText activity_search_et;
    @BindView(R.id.activity_cancel_ib)
    ImageButton activity_cancel_ib;
    InputMethodManager manager;//输入法管理器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        activity_search_et.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        activity_search_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                {
//do something;
                    //先隐藏键盘
                    if (manager.isActive()) {
                        manager.hideSoftInputFromWindow(activity_search_et.getApplicationWindowToken(), 0);
                    }

                    Toast.makeText(SearchActivity.this, "hello 2017!", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
        activity_search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            //每输入一个字，就去访问后台拿数据
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i2 > 0) {
                    activity_cancel_ib.setVisibility(View.VISIBLE);
                } else {
                    activity_cancel_ib.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @OnClick(R.id.activity_cancel_ib)
    public void activity_cancel_ib_Onclick() {
        activity_search_et.setText(" ");
    }

    @OnClick(R.id.activity_back_tv)
    public void activity_back_tv_Onclick() {
        finish();
    }
}
