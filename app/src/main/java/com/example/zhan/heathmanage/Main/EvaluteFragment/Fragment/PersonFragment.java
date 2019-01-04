package com.example.zhan.heathmanage.Main.EvaluteFragment.Fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.zhan.heathmanage.Main.EvaluteFragment.Server.server.UpdateUseServer;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Server.serverImp.UpdateUserServerImp;
import com.example.zhan.heathmanage.R;
import com.makeramen.roundedimageview.RoundedImageView;

public class PersonFragment extends DialogFragment {
    private EditText person_age,person_weight,person_high;
    private RoundedImageView person_man,person_woman;
    private Button person_finish;

    private static String Sex="";

    private UpdateUseServer updateUseServer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.person_message);
        dialog.setCanceledOnTouchOutside(false); // 外部点击取消
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//设置fragment的背景为透明
        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);//动画
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 2/3;
        window.setAttributes(lp);

        updateUseServer = new UpdateUserServerImp(0,this);
        initView(dialog);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK){
                    Toast.makeText(getActivity(),"请填写完信息再走",Toast.LENGTH_SHORT).show();
                    return true;
                }else {
                    return false;
                }
            }
        });
        return dialog;
    }

    private void  initView(Dialog dialog){
        person_age = (EditText) dialog.findViewById(R.id.person_age);
        person_weight = (EditText) dialog.findViewById(R.id.person_weight);
        person_high = (EditText) dialog.findViewById(R.id.person_high);
        person_man = (RoundedImageView) dialog.findViewById(R.id.person_man);
        person_man.setOnClickListener(listener);
        person_woman = (RoundedImageView) dialog.findViewById(R.id.person_woman);
        person_woman.setOnClickListener(listener);
        person_finish = (Button) dialog.findViewById(R.id.person_finish);
        person_finish.setOnClickListener(listener);
    }
    View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.person_finish://
                    if (Sex.equals("")){
                        Toast.makeText(getActivity(),"请填写性别",Toast.LENGTH_SHORT).show();
                    }else if (person_age.getText().toString().equals("")){
                        Toast.makeText(getActivity(),"请填写年龄",Toast.LENGTH_SHORT).show();
                    }else if (person_high.getText().toString().equals("")){
                        Toast.makeText(getActivity(),"请填写高度",Toast.LENGTH_SHORT).show();
                    }else if (person_weight.getText().toString().equals("")){
                        Toast.makeText(getActivity(),"请填写重量",Toast.LENGTH_SHORT).show();
                    }else{
                        updateUseServer.UpdateUser(person_high.getText().toString(),person_weight.getText().toString(),Sex,person_age.getText().toString());
                    }
                    break;
                case R.id.person_man:
                    person_man.setImageResource(R.drawable.man_click);
                    person_woman.setImageResource(R.drawable.woman);
                    Sex="男";
                    break;
                case R.id.person_woman:
                    person_man.setImageResource(R.drawable.man);
                    person_woman.setImageResource(R.drawable.woman_click);
                    Sex="女";
                    break;
                default:
                    break;
            }

        }
    };
    public void UpdateCallBcak(){
        getDialog().dismiss();
    }


}
