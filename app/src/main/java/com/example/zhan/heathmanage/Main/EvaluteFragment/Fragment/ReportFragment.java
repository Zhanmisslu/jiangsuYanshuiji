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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Server.server.ReportServer;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Server.serverImp.ReportServerImp;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Server.serverImp.UpdateUserServerImp;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;
import com.zyao89.view.zloading.ZLoadingDialog;

import static com.zyao89.view.zloading.Z_TYPE.DOUBLE_CIRCLE;

public class ReportFragment extends DialogFragment {
    private ImageView report_img;
    private ImageButton report_back;
    private ReportServer reportServer;
    //加载框
    ZLoadingDialog zLoadingDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.report);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//设置fragment的背景为透明
        initView(dialog);
        // 设置宽度为屏宽, 靠近屏幕中央。
        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);//动画
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER; // 中部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 2/3;
        window.setAttributes(lp);


        return dialog;
    }
    public void initView(final Dialog dialog){
        reportServer = new ReportServerImp(this);
        reportServer.getReport(MyApplication.getsBP(),MyApplication.getdBP(),MyApplication.getHeartRate(),MyApplication.getBloodFat(),MyApplication.getBloodOxygen());
        report_img = dialog.findViewById(R.id.report_img);
        report_back = dialog.findViewById(R.id.report_back);
        report_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //加载框初始化
        zLoadingDialog = new ZLoadingDialog(getActivity());
        zLoadingDialog.setLoadingBuilder(DOUBLE_CIRCLE)
                .setLoadingColor(Color.parseColor("#ff5305"))
                .setHintText("正在加载中...")
//                                .setHintTextSize(16) // 设置字体大小
                .setHintTextColor(Color.GRAY)  // 设置字体颜色
//                                .setDurationTime(0.5) // 设置动画时间百分比

                .setDialogBackgroundColor(Color.parseColor("#cc111111")) // 设置背景色
                .show();

    }
    public void callBack(final String imgPath){

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                zLoadingDialog.dismiss();
                Glide.with(getContext())
                        .load(imgPath)
                        .asBitmap()
                        .error(R.drawable.login_backgroud)
                        .into(report_img);
            }
        });
    }
}
