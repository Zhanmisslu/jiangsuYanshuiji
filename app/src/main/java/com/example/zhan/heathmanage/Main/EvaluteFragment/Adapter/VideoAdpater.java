package com.example.zhan.heathmanage.Main.EvaluteFragment.Adapter;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Beans.Video;
import com.example.zhan.heathmanage.Main.EvaluteFragment.View.CreditScoreView;
import com.example.zhan.heathmanage.Main.EvaluteFragment.View.RingView;
import com.example.zhan.heathmanage.Main.EvaluteFragment.WeeklyActivity;
import com.example.zhan.heathmanage.Main.EvaluteFragment.activity.DetailActivity;
import com.example.zhan.heathmanage.MyApplication;
import com.example.zhan.heathmanage.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.john.waveview.WaveView;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;

public class VideoAdpater extends RecyclerView.Adapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Video> list;
    /*
     * Head 顶部展示图的布局
     * Evalute 个人评价数据展示的布局
     * Banner 周报轮播图的布局
     * Foods 推荐食物的布局
     * List  底部视频展示的布局
     */
    private static final int Tepy_Head = 0;
    private static final int Tepy_Evalute = 1;
    private static final int Tepy_Banner = 2;
    private static final int Tepy_Foods = 3;
    private static final int Tepy_List = 4;
    public VideoAdpater(Context context , List<Video> list){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }
    //根据position分配布局
    @Override
    public int getItemViewType(int position) {

        if (position == 0){
            return  Tepy_Head;
        }else if (position == 1 ){
            return Tepy_Evalute;
        }else if (position == 2){
            return Tepy_Banner;
        }else if (position ==3){
            return Tepy_Foods;
        }else{
            return Tepy_List;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i){
            case Tepy_Head :
                return new HeadViewHodler(inflater.inflate(R.layout.rv_view,viewGroup,false));
            case Tepy_Evalute:
                return new EvaluteViewHodler(inflater.inflate(R.layout.evaluation_score,viewGroup,false));
            case Tepy_Banner:
                return new BannerViewHodler(inflater.inflate(R.layout.evaluation_banner,viewGroup,false));
            case Tepy_Foods:
                return new ShowVideViewHodler(inflater.inflate(R.layout.evaluation_videolist,viewGroup,false));
            case Tepy_List:
                return new VideoViewHodler(inflater.inflate(R.layout.video_item,viewGroup,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof HeadViewHodler){
            setHeadItemValues((HeadViewHodler)viewHolder);
        }else if (viewHolder instanceof EvaluteViewHodler){
            setEvaluteItemValues((EvaluteViewHodler)viewHolder);
        }else if (viewHolder instanceof BannerViewHodler){
            setBannerItemValues((BannerViewHodler)viewHolder);
        }else if (viewHolder instanceof VideoViewHodler){
            setVideoItemValues((VideoViewHodler)viewHolder,i);
        }
    }
    //头布局的初始化
    private void setHeadItemValues(HeadViewHodler hodler){
        //float[] data = {12,13,18,19,20};
        if (!MyApplication.getsBP().equals("")){
           hodler.rv_view.setData(getadate());
           hodler.evalute_concentration.setText(MyApplication.getBloodFat());
           hodler.evalute_diastolic.setText(MyApplication.getdBP());
           hodler.evalute_systolic.setText(MyApplication.getsBP());
           hodler.evalute_oxygen.setText(MyApplication.getBloodOxygen());
           hodler.evalute_heartrate.setText(MyApplication.getHeartRate());
           hodler.evalute_eva.setText(MyApplication.getRanting());
           hodler.rv_ll.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(context, DetailActivity.class);
                   context.startActivity(intent);
               }
           });
       }
    }

    //个人数据显示的初始化
    private void setEvaluteItemValues(EvaluteViewHodler hodler){

    }
    //轮播图显示的初始化
    private void setBannerItemValues(BannerViewHodler hodler){
        hodler.mBanner.setAutoPlayAble(true);
        hodler. mBanner.setAdapter(new BGABanner.Adapter<CardView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, CardView itemView, String model, int position) {
                 //图片布局
                SimpleDraweeView simpleDraweeView = itemView.findViewById(R.id.sdv_item_fresco_content);
                simpleDraweeView.setImageURI(Uri.parse(model));
            }
        });
        List<String> img = new ArrayList<String>();
        img.add("http://bgashare.bingoogolapple.cn/banner/imgs/12.png");
        img.add("http://bgashare.bingoogolapple.cn/banner/imgs/13.png");
        List<String> tig = new ArrayList<String>();
        tig.add("欢迎");
        tig.add("欢迎");
        //设置Banner的布局，图片和文字
        hodler.mBanner.setData(R.layout.item_fresco,img,tig);

        hodler.mBanner.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onBannerItemClick(BGABanner banner, View itemView, @Nullable Object model, int position) {
                if (position==0){
                    Intent intent = new Intent(context, WeeklyActivity.class);
                    context.startActivity(intent);
                }
                Toast.makeText(banner.getContext(), "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
    //视频列表显示的初始化
    private void setVideoItemValues(VideoViewHodler hodler,int i){
        int postion = i-4;
        Video video = list.get(postion);
        TxVideoPlayerController controller = new TxVideoPlayerController(context);
        hodler.setController(controller);
        hodler.bindData(video);

    }
    @Override
    public int getItemCount() {
        return list.size()+4;
    }

    private float[] getadate(){
        float a,b,c,d,e;

        //判断收缩压评分
        if (Float.valueOf(MyApplication.getsBP()) < 90){
            a =(float)(20 - (90-Float.valueOf(MyApplication.getsBP())*0.2)) ;
        }else if (Float.valueOf(MyApplication.getsBP()) > 120){
            a = (float)(20 - (Float.valueOf(MyApplication.getsBP()) - 120)*0.2) ;
        }else {
            a = 20;
        }

        //判断舒张压评分
        if (Float.valueOf(MyApplication.getdBP())< 65){
            b =(float)(20 - (65-Float.valueOf(MyApplication.getdBP())*0.2)) ;
        }else if (Float.valueOf(MyApplication.getdBP()) > 90){
            b = (float)(20 - (Float.valueOf(MyApplication.getdBP()) - 90)*0.2) ;
        }else {
            b = 20;
        }

        //判断血氧评分
        if (Float.valueOf(MyApplication.getBloodOxygen()) < 90){
            c =(float)(20 - (90-Float.valueOf(MyApplication.getBloodOxygen())*0.2)) ;
        }else {
            c = 20;
        }
        //判断血浓度评分
        if (Float.valueOf(MyApplication.getBloodFat()) < 0.2){
            d =(float)(20 - (0.2-Float.valueOf(MyApplication.getBloodFat())*2)) ;
        }else if (Float.valueOf(MyApplication.getBloodFat()) > 0.6){
            d = (float)(20 - (Float.valueOf(MyApplication.getBloodFat()) - 0.6)*2) ;
        }else {
            d = 20;
        }
        //判断心率评分
        if (Float.valueOf(MyApplication.getHeartRate()) < 60){
            e =(float)(20 - (60-Float.valueOf(MyApplication.getHeartRate())*0.2)) ;
        }else if (Float.valueOf(MyApplication.getHeartRate()) > 100){
            e = (float)(20 - (Float.valueOf(MyApplication.getHeartRate()) - 100)*0.2) ;
        }else {
            e = 20;
        }

        float[] data ={a,b,c,d,e};
        return data;
    }
    //头布局hodler
    public class HeadViewHodler extends RecyclerView.ViewHolder{
        //主页数据显示
        private CreditScoreView rv_view;
        private TextView evalute_systolic;
        private TextView evalute_eva;
        private TextView evalute_diastolic;
        private TextView evalute_oxygen;
        private TextView evalute_heartrate;
        private TextView evalute_concentration;

        private LinearLayout rv_ll;
        private WaveView fragment_evaluate_waveview;
        public HeadViewHodler(View itemView) {
            super(itemView);
            rv_ll = itemView.findViewById(R.id.rv_ll);
            rv_view = itemView.findViewById(R.id.rv_view);
            evalute_systolic = itemView.findViewById(R.id.evalute_systolic);
            evalute_eva = itemView.findViewById(R.id.evalute_eva);
            evalute_diastolic = itemView.findViewById(R.id.evalute_diastolic);
            evalute_oxygen = itemView.findViewById(R.id.evalute_oxygen);
            evalute_heartrate = itemView.findViewById(R.id.evalute_heartrate);
            evalute_concentration = itemView.findViewById(R.id.evalute_concentration);
            fragment_evaluate_waveview = itemView.findViewById(R.id.fragment_evaluate_waveview);
            fragment_evaluate_waveview.setProgress(100);
            //initRingView();
        }
    }
    //个人数据显示布局
    public class EvaluteViewHodler extends RecyclerView.ViewHolder{

        public EvaluteViewHodler(@NonNull View itemView) {
            super(itemView);

        }
    }
    //轮播图的显示布局
    public class BannerViewHodler extends RecyclerView.ViewHolder{
        public BGABanner mBanner ;
        public BannerViewHodler(@NonNull View itemView) {
            super(itemView);

            mBanner = itemView.findViewById(R.id.ev_banner);

        }
    }
    //视频列表TextView的显示布局
    public class ShowVideViewHodler extends RecyclerView.ViewHolder{
        public ShowVideViewHodler(@NonNull View itemView) {
            super(itemView);
        }
    }
    //Video视频的VIewHodler
    public class VideoViewHodler extends RecyclerView.ViewHolder{
        public TxVideoPlayerController mController;
        public NiceVideoPlayer mVideoPlayer;
        public VideoViewHodler(@NonNull View itemView) {
            super(itemView);
            mVideoPlayer = (NiceVideoPlayer) itemView.findViewById(R.id.nice_video_player);
            // 将列表中的每个视频设置为默认16:9的比例
            ViewGroup.LayoutParams params = mVideoPlayer.getLayoutParams();
            params.width = itemView.getResources().getDisplayMetrics().widthPixels; // 宽度为屏幕宽度
            params.height = (int) (params.width * 9f / 16f);    // 高度为宽度的9/16
            mVideoPlayer.setLayoutParams(params);
        }
        public void setController(TxVideoPlayerController controller) {
            mController = controller;
            mVideoPlayer.setController(mController);
        }

        public void bindData(Video video) {
            mController.setTitle(video.getTitle());
            mController.setLenght(video.getLength());
            Glide.with(itemView.getContext())
                    .load(video.getImageUrl())
                    .placeholder(R.drawable.img_default)
                    .crossFade()
                    .into(mController.imageView());
            mVideoPlayer.setUp(video.getVideoUrl(), null);
        }
    }
}
