package com.beiing.leafchart;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.beiing.leafchart.bean.AxisValue;
import com.beiing.leafchart.bean.Line;
import com.beiing.leafchart.bean.PointValue;
import com.beiing.leafchart.bean.SlidingLine;
import com.beiing.leafchart.renderer.OutsideLineRenderer;
import com.beiing.leafchart.support.LeafUtil;
import com.beiing.leafchart.support.Mode;
import com.beiing.leafchart.support.OnChartSelectedListener;
import com.beiing.leafchart.support.OnPointSelectListener;

import java.util.List;

/**
 * Created by chenliu on 2017/1/12.<br/>
 * 描述： to be continued
 * </br>
 */

public class OutsideLineChart extends AbsLeafChart {
    private List<Line> lines;

    private Line line;

    private OutsideLineRenderer outsideLineRenderer;


    int scaledTouchSlop;

    private OnChartSelectedListener mOnChartSelectedListener;

    /**
     * mMove为偏移量
     */
    private int mLastX, mMove;

    /**两个点之间间隔**/
    private int mStep;

    /**滑动到第一个点或最后一个点时，还能继续滑动的距离**/
    private int maxOverMove;

    private Scroller mScroller;

    private GestureDetectorCompat gestureDetector;



    public OutsideLineChart(Context context) {
        this(context, null, 0);
    }

    public OutsideLineChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }



    public void setmOnChartSelectedListener(OnChartSelectedListener mOnChartSelectedListener) {
        this.mOnChartSelectedListener = mOnChartSelectedListener;
    }

    public OutsideLineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(getContext());
        gestureDetector = new GestureDetectorCompat(getContext(), new SimpleGestureListener());
        maxOverMove = (int) LeafUtil.dp2px(mContext, 100);

        scaledTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
        gestureDetector= new GestureDetectorCompat(getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
//                setCanSelected(true);
//                if (null != mOnChartSelectedListener) {
//                    mOnChartSelectedListener.onChartSelected(true);
//                }
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }
        });
//        setOnLongClickListener(new OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                setCanSelected(true);
//                if (null != mOnChartSelectedListener) {
//                    mOnChartSelectedListener.onChartSelected(true);
//                }
//                return false;
//            }
//        });

    }
    @Override
    protected void initAttrs(AttributeSet attrs) {
        super.initAttrs(attrs);
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.OutsideLineChart);
        try{
            mStep = (int) ta.getDimension(R.styleable.OutsideLineChart_lc_step, LeafUtil.dp2px(mContext, 50));
        } finally {
            ta.recycle();
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void initRenderer() {
        outsideLineRenderer = new OutsideLineRenderer(mContext, this);
    }

    @Override
    protected void setRenderer() {
        super.setRenderer(outsideLineRenderer);
    }

    @Override
    protected void resetPointWeight() {
        if (lines != null) {
            for (int i = 0, size = lines.size(); i < size; i++) {
                super.resetPointWeight(lines.get(i));
            }
        }
    }

    @Override
    protected void resetAsixX() {
        if(axisX != null){
            List<AxisValue> values = axisX.getValues();
            int sizeX = values.size(); //几条y轴
            float xStep = mStep;
            for (int i = 0; i < sizeX; i++) {
                AxisValue axisValue = values.get(i);
                axisValue.setPointY(mHeight);
                if(i == 0){
                    axisValue.setPointX(leftPadding + startMarginX);
                } else {
                    axisValue.setPointX(leftPadding + startMarginX + xStep * i*2);
                }
            }
            switch (coordinateMode){
                case Mode.ACROSS:
                case Mode.X_ACROSS:
                    axisX.setStartX(leftPadding * 0.5f);
                    break;

                case Mode.INTERSECT:
                case Mode.Y_ACROSS:
                    axisX.setStartX(leftPadding);
                    break;
            }
            axisX.setStartY(mHeight - bottomPadding).setStopX(mWidth).setStopY(mHeight - bottomPadding);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        outsideLineRenderer.drawCoordinateLines(canvas, axisX, axisY);
        outsideLineRenderer.drawCoordinateText(canvas, axisX, axisY, mMove);
        if (lines != null && lines.size() > 0) {

            for (int i = 0, size = lines.size(); i < size; i++) {
                line = lines.get(i);
                if(line != null) {
//                    if(line.isCubic()) {
//                        outsideLineRenderer.drawCubicPath(canvas, line);
//                    } else {
//                        leafChartRenderer.drawLines(canvas, line);
//                    }
                    outsideLineRenderer.drawCubicPath(canvas, line, axisY, mMove);

                    if (line.isFill()) {
                        //填充
                        outsideLineRenderer.drawFillArea(canvas, line, axisX, mMove);
                    }
                }
                outsideLineRenderer.drawPoints(canvas, line, axisY, mMove);
                if (line != null && line.isHasLabels()) {
                    outsideLineRenderer.drawLabels(canvas, line, axisY, mMove);
                }
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        int xPosition = (int) event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mScroller.abortAnimation();
                mLastX = xPosition;
                return true;
            case MotionEvent.ACTION_MOVE:
                if(mMove >= 0 && mMove <= maxOverMove || mMove <= 0 && mMove >= -getMinMove()){
                    smoothScrollBy(xPosition - mLastX, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                if(mMove > 0){
                    smoothScrollTo(startMarginX, 0);
                } else if(mMove <= -getMinMove()) {
                    smoothScrollTo(-getMinMove(), 0);
                }
                break;
        }
        mLastX = xPosition;
        return true;
    }
    //调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx, int dy) {
        //设置mScroller的滚动偏移量
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    //调用此方法滚动到目标位置
    public void smoothScrollTo(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx, dy);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            //判断左右边界
            mMove = mScroller.getCurrX();
            postInvalidate();
        }
    }

    private class SimpleGestureListener extends
            GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            int minMove;
            minMove = getMinMove();
            mScroller.fling(mMove, 0, (int)velocityX, (int)velocityY, -minMove, startMarginX, 0, 0);
            return true;
        }
    }

    private int getMinMove() {
        int minMove = Integer.MIN_VALUE;

        if (line != null) {
            List<PointValue> values = line.getValues();
            if (values != null && values.size() > 0) {
                PointValue pointValue = values.get(values.size() - 1);
                minMove = (int) (pointValue.getOriginX() - mWidth + maxOverMove);
            }
        }
        return minMove;
    }

    /**
     * 带动画的绘制
     * @param duration
     */
    public void showWithAnimation(int duration){
        outsideLineRenderer.showWithAnimation(duration);
    }

    public void show(){
        showWithAnimation(0);
    }

//    public void setChartData(Line chartData) {
//        line = chartData;
//        resetPointWeight();
//    }
public void setChartData(List<Line> chartDatas) {
    lines = chartDatas;
    resetPointWeight();
}
    public List<Line> getChartData() {
        return lines;
    }


}
