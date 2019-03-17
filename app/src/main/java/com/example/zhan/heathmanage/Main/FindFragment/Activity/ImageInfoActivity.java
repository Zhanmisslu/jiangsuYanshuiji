package com.example.zhan.heathmanage.Main.FindFragment.Activity;

import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.zhan.heathmanage.BasicsTools.BaseActivity;
import com.example.zhan.heathmanage.Main.FindFragment.Fragment.AttentionFragment;
import com.example.zhan.heathmanage.R;

import butterknife.BindView;

import static com.example.zhan.heathmanage.MyApplication.getContext;

public class ImageInfoActivity extends BaseActivity {
    @BindView(R.id.imageView2)
    ImageView imageView2;
    String image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_info);
        image=getIntent().getStringExtra("image");
        Glide.with(getContext())
                .load(image)
                .asBitmap()
                .error(R.drawable.weclome2)
                .into(imageView2);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(102);
                AttentionFragment.flag=1;
                ActivityCompat.finishAfterTransition(ImageInfoActivity.this);

            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(102);
        AttentionFragment.flag=1;
        ActivityCompat.finishAfterTransition(ImageInfoActivity.this);
        super.onBackPressed();
    }
}
