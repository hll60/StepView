package com.hll.android.stepview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lin on 2016/12/3.
 */

public class HorizontalStepViewIndicator extends LinearLayout {


    private List<Step> mStepList;           // 存储所有的步骤内容

    private OnDrawListener mListener;

    /*************自定义属性*************/

    private int mIconHeight;                // 绘制的图标高度
    private int mIconWidth;                 // 绘制的图标宽度

    private int mLineHeight;                // 绘制的线的高度
    private int mLineWidth;                 // 绘制的线的宽度

    private int mLineSpacing;               // 线与图标之间的间距

    private int mUnCompletedLineColor;      // 绘制-还未做-的颜色
    private int mCompletedLineColor;        // 绘制-已完成-的颜色

    private int mIconBackgroundColor;       // 图标的背景颜色
    private Drawable mDoingIcon;            // 绘制-正在做-的图标
    private Drawable mUnDoIcon;             // 绘制-还未做-的图标
    private Drawable mCompletedIcon;        // 绘制-已完成-的图标

    private int mIconShape;                 // 图标的形状

    private int mTextSize;                  // 文字大小
    private int mTextColor;                 // 文字颜色

    /*************绘制需要*************/

    private Path mPath;                     // 绘制路径
    private PathEffect mEffect;             // 绘制的路径效果--实线/虚线

    private Paint mUnCompletedPaint;        // 绘制-还未做-的画笔
    private Paint mCompletedPaint;          // 绘制-已完成-的画笔
    private Paint mIconBackgroundPaint;     // 绘制-图标背景-的画笔
    private Paint mTextPaint;               // 绘制-文字-的画笔


    private int width;                      // view宽度
    private int height;                     // view高度
    private float mCenterY;                 //该view的Y轴中间位置     definition view centerY position
    private int diameter;                   // 直径


    public HorizontalStepViewIndicator(Context context) {
        this(context, null);
    }

    public HorizontalStepViewIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalStepViewIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.stepview_indicator);

        mIconHeight = ta.getDimensionPixelSize(R.styleable.stepview_indicator_iconHeight, 50);
        mIconWidth = ta.getDimensionPixelSize(R.styleable.stepview_indicator_iconWidth, 50);
        mLineHeight = ta.getDimensionPixelSize(R.styleable.stepview_indicator_lineHeight, 10);
        mLineWidth = ta.getDimensionPixelSize(R.styleable.stepview_indicator_lineWidth, 100);
        mLineSpacing = ta.getDimensionPixelSize(R.styleable.stepview_indicator_lineSpacing, 0);

        mUnCompletedLineColor = ta.getColor(R.styleable.stepview_indicator_lineUnCompletedColor, Color.GRAY);
        mCompletedLineColor = ta.getColor(R.styleable.stepview_indicator_lineCompletedColor, Color.RED);
        mIconBackgroundColor = ta.getColor(R.styleable.stepview_indicator_iconBackgroundColor, Color.WHITE);
        mDoingIcon = ta.getDrawable(R.styleable.stepview_indicator_iconDoing);
        mUnDoIcon = ta.getDrawable(R.styleable.stepview_indicator_iconUnDo);
        mCompletedIcon = ta.getDrawable(R.styleable.stepview_indicator_iconCompleted);
        mIconShape = ta.getInt(R.styleable.stepview_indicator_iconShape, 0);

        mTextSize = ta.getDimensionPixelSize(R.styleable.stepview_indicator_iconTextSize, 50);
        mTextColor = ta.getInt(R.styleable.stepview_indicator_iconTextColor, Color.WHITE);

        ta.recycle();

        this.setWillNotDraw(false);//必须
        init();
    }


    private void init(){
        mStepList = new ArrayList<>();

        mPath = new Path();
        mEffect = new DashPathEffect(new float[]{8, 8, 8, 8}, 1);

        mUnCompletedPaint = new Paint();
        mCompletedPaint = new Paint();
        mIconBackgroundPaint = new Paint();
        mTextPaint = new Paint();

        mUnCompletedPaint.setAntiAlias(true);
        mCompletedPaint.setAntiAlias(true);
        mIconBackgroundPaint.setAntiAlias(true);
        mTextPaint.setAntiAlias(true);

        mUnCompletedPaint.setColor(mUnCompletedLineColor);
        mCompletedPaint.setColor(mCompletedLineColor);
        mIconBackgroundPaint.setColor(mIconBackgroundColor);
        mTextPaint.setColor(mTextColor);

        mUnCompletedPaint.setStyle(Paint.Style.FILL);
        mCompletedPaint.setStyle(Paint.Style.FILL);
        mIconBackgroundPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setStyle(Paint.Style.FILL);

        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // 圆形
        if(mIconShape == 1){
            diameter = (int) Math.sqrt(Math.pow(mIconWidth, 2)+ Math.pow(mIconHeight, 2));
            height = diameter;
            width = mStepList.size() * (diameter + mLineWidth + mLineSpacing * 2);
        }else{
            width = mStepList.size() * (mIconWidth + mLineWidth + mLineSpacing * 2);
            height = mIconHeight;
        }

        mCenterY = 0.5f * height;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        canvas.drawPaint(paint);

        int startX = mLineWidth/2 + mLineSpacing;

        Paint temp = new Paint();
        temp.setColor(Color.WHITE);

        for(int i=0;i<mStepList.size();i++){

            Rect bounds;

            // 绘制图标背景
            if(mIconShape == 0){
                bounds = new Rect(startX, 0, startX + mIconWidth, mIconHeight);
                canvas.drawRect(bounds, mIconBackgroundPaint);
            }else{
                bounds = new Rect(startX+(diameter-mIconWidth)/2, 0+(diameter-mIconHeight)/2, startX+(diameter-mIconWidth)/2 + mIconWidth, mIconHeight+(diameter-mIconHeight)/2);
                canvas.drawCircle(startX+diameter/2, diameter/2, diameter/2, mIconBackgroundPaint);
            }

            // 绘制图标
            switch (mStepList.get(i).getStatus()){
                case UnDo:
                    if(mUnDoIcon != null){
                        mUnDoIcon.setBounds(bounds);
                        mUnDoIcon.draw(canvas);
                    }
                    break;
                case Doing:
                    if(mDoingIcon != null){
                        mDoingIcon.setBounds(bounds);
                        mDoingIcon.draw(canvas);
                    }
                    break;
                case Completed:
                    if(mCompletedIcon != null){
                        mCompletedIcon.setBounds(bounds);
                        mCompletedIcon.draw(canvas);
                    }
                    break;
            }

            Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
            float fontHeight = metrics.bottom - metrics.top;
            float textBaseY = height - (height - fontHeight)/2 - metrics.bottom;

            // 绘制文字

            if(!TextUtils.isEmpty(mStepList.get(i).getIconText())){
                if(mIconShape == 0){
                    canvas.drawText(mStepList.get(i).getIconText(), startX+mIconWidth/2, textBaseY, mTextPaint);
                }else{
                    canvas.drawText(mStepList.get(i).getIconText(), startX+diameter/2, textBaseY, mTextPaint);
                }
            }

            if(mIconShape == 0){
                startX = startX + mIconWidth + mLineSpacing;
            }else{
                startX = startX + diameter + mLineSpacing;
            }

            // 绘制线-最后一个图标，不绘制
            if(i == mStepList.size()-1) continue;

            mPath.moveTo(startX, mCenterY);
            mPath.lineTo(startX + mLineWidth, mCenterY);
            switch (mStepList.get(i).getStatus()){
                case UnDo:
                case Doing:
                    canvas.drawRect(startX, mCenterY-mLineHeight/2, startX + mLineWidth, mCenterY+mLineHeight/2, mUnCompletedPaint);
                    break;
                case Completed:
                    canvas.drawRect(startX, mCenterY-mLineHeight/2, startX + mLineWidth, mCenterY+mLineHeight/2, mCompletedPaint);
                    break;
            }

            if(mIconShape == 0){
                startX = startX + mLineWidth + mLineSpacing;
            }else{
                startX = startX + diameter + mLineSpacing;
            }

        }

        if(mListener != null){
            mListener.onDrawIndicator();
        }

    }

    public void setStepList(List<Step> steps){
        this.mStepList = steps;
        invalidate();
    }

    public int getIconCenterPositionX(int position){
        if(mIconShape == 0){
            return (mIconWidth+mLineWidth + mLineSpacing*2) * position + mLineWidth/2 + mLineSpacing + mIconWidth/2;
        }else{
            return (diameter+mLineWidth + mLineSpacing*2) * position + diameter/2 + mLineSpacing + diameter/2;
        }
    }

    public void setOnDrawListener(OnDrawListener listener){
        this.mListener = listener;
    }

    public interface OnDrawListener{
        void onDrawIndicator();
    }
}
