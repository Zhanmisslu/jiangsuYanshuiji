package com.beiing.leafchart;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.beiing.leafchart.bean.AxisValue;
import com.beiing.leafchart.bean.Line;
import com.beiing.leafchart.bean.PointValue;
import com.beiing.leafchart.bean.SlidingLine;
import com.beiing.leafchart.renderer.LeafLineRenderer;
import com.beiing.leafchart.support.LeafUtil;
import com.beiing.leafchart.support.OnChartSelectedListener;
import com.beiing.leafchart.support.OnPointSelectListener;

import java.util.List;

/**
 * Created by chenliu on 2016/7/15.<br/>
 * 描述：折线图
 * </br>
 */
public class LeafLineChart extends AbsLeafChart {

    private List<Line> lines;


    private LeafLineRenderer leafChartRenderer;
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

    public LeafLineChart(Context context) {
        this(context, null, 0);
    }

    public LeafLineChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void setSlidingLine(SlidingLine slidingLine) {
        this.slidingLine = slidingLine;
    }

    public LeafLineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initDefaultSlidingLine();

        scaledTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setCanSelected(true);
                if (null != mOnChartSelectedListener) {
                    mOnChartSelectedListener.onChartSelected(true);
                }
                return false;
            }
        });

    }


    public void setOnPointSelectListener(OnPointSelectListener onPointSelectListener) {
        this.onPointSelectListener = onPointSelectListener;
    }

    @Override
    protected void initRenderer() {
        leafChartRenderer = new LeafLineRenderer(mContext, this);
    }

    @Override
    protected void setRenderer() {
         super.setRenderer(leafChartRenderer);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * 设置点所占比重
     */
    @Override
    protected void resetPointWeight() {
        if (lines != null) {
            for (int i = 0, size = lines.size(); i < size; i++) {
                super.resetPointWeight(lines.get(i));
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (lines != null && lines.size() > 0) {
            Line line;
            for (int i = 0, size = lines.size(); i < size; i++) {
                line = lines.get(i);
                if(line != null){
                    if(line.isCubic()) {
                        leafChartRenderer.drawCubicPath(canvas, line);
                    } else {
                        leafChartRenderer.drawLines(canvas, line);
                    }
                    if(line.isFill()){
                        //填充
                        leafChartRenderer.drawFillArea(canvas, line, axisX);
                    }

                    leafChartRenderer.drawPoints(canvas, line);
                }

                if (line != null && line.isHasLabels()) {
                    leafChartRenderer.drawLabels(canvas, line, axisY);
                }
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isCanSelected)
            return super.onTouchEvent(event);

        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (downX - x != 0 && Math.abs(y - downY) < scaledTouchSlop) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                isDrawMoveLine = false;
                isCanSelected = false;
                if (null != mOnChartSelectedListener) {
                    mOnChartSelectedListener.onChartSelected(false);
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
        invalidate();

        if (slidingLine != null) {
            if (slidingLine.isOpenSlideSelect()) {
                return true;
            }
        }
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
    /**
     * 带动画的绘制
     * @param duration
     */
    public void showWithAnimation(int duration){
        leafChartRenderer.showWithAnimation(duration);
    }

    public void show(){
        showWithAnimation(0);
    }

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