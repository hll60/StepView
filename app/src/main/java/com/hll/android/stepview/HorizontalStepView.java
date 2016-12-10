package com.hll.android.stepview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;


/**
 * Created by lin on 2016/12/3.
 */

public class HorizontalStepView extends LinearLayout implements HorizontalStepViewIndicator.OnDrawListener{


    private List<Step> mStepList;           // 存储所有的步骤内容

    private HorizontalStepViewIndicator mStepViewIndicator;
    private RelativeLayout mTextContainerRL;

    private int mTextUnDoColor;
    private int mTextDoingColor;
    private int mTextCompletedColor;
    private int mTextSize;

    public HorizontalStepView(Context context) {
        this(context, null);
    }

    public HorizontalStepView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalStepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.setWillNotDraw(false);//必须

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.stepview);

        mTextUnDoColor = ta.getColor(R.styleable.stepview_textUnDoColor, Color.WHITE);
        mTextDoingColor = ta.getColor(R.styleable.stepview_textDoingColor, Color.WHITE);
        mTextCompletedColor = ta.getColor(R.styleable.stepview_textCompletedColor, Color.WHITE);
        mTextSize = ta.getDimensionPixelSize(R.styleable.stepview_textSize, 13);

        ta.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mStepViewIndicator = (HorizontalStepViewIndicator) findViewById(R.id.stepViewIndicator);
        mTextContainerRL = (RelativeLayout) findViewById(R.id.textContainerRL);
        mStepViewIndicator.setOnDrawListener(this);
    }

    public void setStepList(List<Step> steps){
        this.mStepList = steps;
        mStepViewIndicator.setStepList(steps);
    }


    @Override
    public void onDrawIndicator() {
        if(mTextContainerRL != null)
        {
            mTextContainerRL.removeAllViews();

            if(mStepList != null && mStepList.size() != 0)
            {
                for(int i = 0; i < mStepList.size(); i++)
                {
                    TextView mTextView = new TextView(getContext());
                    mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
                    mTextView.setText(mStepList.get(i).getName());
                    int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                    mTextView.measure(spec, spec);
                    // getMeasuredWidth
                    int measuredWidth = mTextView.getMeasuredWidth();
                    mTextView.setX(mStepViewIndicator.getIconCenterPositionX(i) - measuredWidth / 2);
                    mTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                    switch (mStepList.get(i).getStatus()){
                        case UnDo:
                            mTextView.setTextColor(mTextUnDoColor);
                            break;
                        case Doing:
                            mTextView.setTextColor(mTextDoingColor);
                            break;
                        case Completed:
                            mTextView.setTextColor(mTextCompletedColor);
                            break;
                    }

                    mTextContainerRL.addView(mTextView);
                }
            }
        }
    }
}
