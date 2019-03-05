package com.example.zhan.heathmanage.Main.EvaluteFragment.Adapter;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.zhan.heathmanage.Main.EvaluteFragment.Beans.Video;
import com.example.zhan.heathmanage.Main.EvaluteFragment.View.RingView;
import com.example.zhan.heathmanage.R;
import com.john.waveview.WaveView;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import java.util.ArrayList;
import java.util.List;

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
//        }else if (position == 2){
//            return Tepy_Banner;
//        }else if (position ==3){
//            return Tepy_Foods;
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
        }else if (viewHolder instanceof VideoViewHodler){
            setVideoItemValues((VideoViewHodler)viewHolder,i);
        }
    }
    //头布局的初始化
    private void setHeadItemValues(HeadViewHodler hodler){

    }

    //个人数据显示的初始化
    private void setEvaluteItemValues(EvaluteViewHodler hodler){

    }

    //视频列表显示的初始化
    private void setVideoItemValues(VideoViewHodler hodler,int i){
        int postion = i-2;
        Video video = list.get(postion);
        TxVideoPlayerController controller = new TxVideoPlayerController(context);
        hodler.setController(controller);
        hodler.bindData(video);

    }
    @Override
    public int getItemCount() {
        return list.size()+2;
    }

    /*
      顶部动态圆圈的视图
     */
    private RingView rv_view;//圆圈视图
    private ArgbEvaluator evaluator;
    private int startColor = 0XFFfb5338;
    private int centerColor = 0XFF00ff00;
    private int endColor = 0XFF008dfc;
    private int endUseColor = 0;

    List<Integer> valueList = new ArrayList<>();
    List<String> valueNameList = new ArrayList<>();
    private int animDuration = 2500;
    public void initRingView(){
        evaluator = new ArgbEvaluator();
        valueList.add(350);
        valueList.add(450);
        valueList.add(550);
        valueList.add(650);
        valueList.add(750);
        valueList.add(850);
        rv_view.setValueList(valueList);
        valueNameList.add("较差");
        valueNameList.add("中等");
//        valueNameList.add("良好");
        valueNameList.add("合格");
        valueNameList.add("优秀");
        rv_view.setValueNameList(valueNameList);
//        rv_view.setPointer(true);
        rv_view.setPointer(false);
//        ly_content.setBackgroundColor((Integer) evaluator.evaluate(0f, startColor, endColor));
        start((int) (350 + Math.random() * 500));
    }
    private void start(int value) {
        float f = (value - valueList.get(0)) * 1.0f / (valueList.get(valueList.size() - 1) - valueList.get(0));
        if (f <= 0.5f) {
            endUseColor = (Integer) evaluator.evaluate(f, startColor, centerColor);

        }
        else
        {
            endUseColor = (Integer) evaluator.evaluate(f, centerColor, endColor);

        }

        rv_view.setValue(value, new RingView.OnProgerssChange() {
            @Override
            public void OnProgerssChange(float interpolatedTime) {
                int evaluate = 0;
//
//                if (interpolatedTime <= 0.5f) {
//
//                    evaluate = (Integer) evaluator.evaluate(interpolatedTime, startColor, endUseColor);
//
//                } else {
//                    evaluate = (Integer) evaluator.evaluate(interpolatedTime, centerColor, endUseColor);
//                }
//                ly_content.setBackgroundColor(evaluate);
            }
        },(int)(f*animDuration));
    }
    //头布局hodler
    public class HeadViewHodler extends RecyclerView.ViewHolder{

        private WaveView fragment_evaluate_waveview;
        public HeadViewHodler(View itemView) {
            super(itemView);
            rv_view = itemView.findViewById(R.id.rv_view);
            fragment_evaluate_waveview = itemView.findViewById(R.id.fragment_evaluate_waveview);
            fragment_evaluate_waveview.setProgress(100);
            initRingView();
        }
    }
    //个人数据显示布局
    public class EvaluteViewHodler extends RecyclerView.ViewHolder{

        public EvaluteViewHodler(@NonNull View itemView) {
            super(itemView);
        }
    }
    //Video的VIewHodler
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
