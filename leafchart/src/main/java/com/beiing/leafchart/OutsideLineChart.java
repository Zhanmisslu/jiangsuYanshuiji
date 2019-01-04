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
    private SlidingLine slidingLine;

    private float moveX;
    private float moveY;
    private boolean isDrawMoveLine;
    float downX;
    float downY;
    int scaledTouchSlop;

    private boolean isCanSelected;
    private OnPointSelectListener onPointSelectListener;
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

    public void setSlidingLine(SlidingLine slidingLine) {
        this.slidingLine = slidingLine;
    }

    public OutsideLineChart(Context context) {
        this(context, null, 0);
    }

    public OutsideLineChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void setOnPointSelectListener(OnPointSelectListener onPointSelectListener) {
        this.onPointSelectListener = onPointSelectListener;
    }

    public void setmOnChartSelectedListener(OnChartSelectedListener mOnChartSelectedListener) {
        this.mOnChartSelectedListener = mOnChartSelectedListener;
    }

    public OutsideLineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(getContext());
        gestureDetector = new GestureDetectorCompat(getContext(), new SimpleGestureListener());
        maxOverMove = (int) LeafUtil.dp2px(mContext, 50);
        initDefaultSlidingLine();

        scaledTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
        gestureDetector= new GestureDetectorCompat(getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                setCanSelected(true);
                if (null != mOnChartSelectedListener) {
                    mOnChartSelectedListener.onChartSelected(true);
                }
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
            mStep = (int) ta.getDimension(R.styleable.OutsideLineChart_lc_step, LeafUtil.dp2px(mContext, 30));
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
                    axisValue.setPointX(leftPadding + startMarginX + xStep * i);
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
//        if (!isCanSelected) {
//            return super.onTouchEvent(event);
//        }
        gestureDetector.onTouchEvent(event);
        int xPosition = (int) event.getX();
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mScroller.abortAnimation();
                mLastX = xPosition;
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (downX - x != 0 && Math.abs(y - downY) < scaledTouchSlop) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                if(mMove >= 0 && mMove <= maxOverMove || mMove <= 0 && mMove >= -getMinMove()){
                    smoothScrollBy(xPosition - mLastX, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                isDrawMoveLine = false;
                isCanSelected = false;
                if (null != mOnChartSelectedListener) {
                    mOnChartSelectedListener.onChartSelected(false);
                }
                if(mMove > 0){
                    smoothScrollTo(startMarginX, 0);
                } else if(mMove <= -getMinMove()) {
                    smoothScrollTo(-getMinMove(), 0);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                isCanSelected = false;
                if (null != mOnChartSelectedListener) {
                    mOnChartSelectedListener.onChartSelected(false);
                }
                break;
        }
        countRoundPoint(x);

        if (slidingLine != null) {
            if (slidingLine.isOpenSlideSelect()) {
                return true;
            }
        }
        mLastX = xPosition;
        return false;
    }
    /**
     * 计算最接近的点
     *
     * @param x
     */
    private void countRoundPoint(float x) {
        int y=0;
        if (lines != null && lines.size() > 0) {
            Line line;
            for (int j = 0, sizes = lines.size(); j < sizes; j++) {
                line = lines.get(j);
                if (line != null) {
                    List<AxisValue> axisXValues = axisX.getValues();
                    int sizeX = axisXValues.size(); //几条y轴
                    float xStep = (mWidth - leftPadding - startMarginX) / sizeX;
                    int loc = Math.round((x - leftPadding - startMarginX) / xStep);
                    List<PointValue> values = line.getValues();
                    for (int i = 0, size = values.size(); i < size; i++) {
                        PointValue pointValue = values.get(i);
                        pointValue.setShowLabel(false);
                        int ploc = Math.round(pointValue.getDiffX() / xStep);
                        if (ploc == loc) {

                            pointValue.setShowLabel(true);
                            moveX = pointValue.getOriginX();
                            moveY = pointValue.getOriginY() + LeafUtil.dp2px(mContext, line.getPointRadius());
                            isDrawMoveLine = true;
                            y = 0;
                            if (onPointSelectListener != null) {
                                onPointSelectListener.onPointSelect(loc, axisXValues.get(loc).getLabel(), pointValue.getLabel());
                            }
//                    break;
                        }
                    }
                }
            }
        }
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

    private void initDefaultSlidingLine() {
        slidingLine = new SlidingLine();
        slidingLine.setDash(true).setSlideLineWidth(1).setSlidePointRadius(3);
    }
    @SuppressLint("MissingPermission")
    public void setCanSelected(boolean canSelected) {
        isCanSelected = canSelected;
        Vibrator vib = (Vibrator) getContext().getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(40);
    }
}
