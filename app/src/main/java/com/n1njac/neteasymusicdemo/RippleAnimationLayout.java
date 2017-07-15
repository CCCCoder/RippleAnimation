package com.n1njac.neteasymusicdemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by N1njaC on 2017/7/13.
 */

public class RippleAnimationLayout extends RelativeLayout {


    private int rippleType;
    private int rippleColor;
    private int rippleDuration;
    private float rippleRadius;
    private float rippleScale;
    private int rippleAmount;
    public float rippleStrokeWidth;
    public Paint mPaint;

    private boolean isAnimationRunning = false;

    private AnimatorSet mAnimatorSet;


    private static int DEFAULT_TYPE = 0;
    private static int DEFAULT_AMOUNT = 5;
    private static int DEFAULT_DURATION = 2000;
    private static float DEFAULT_SCALE = 5.0f;


    private List<RippleCircleView> circleViewList = new ArrayList<>();


    public RippleAnimationLayout(Context context) {
        this(context, null);
    }

    public RippleAnimationLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleAnimationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (isInEditMode()){
            return;
        }

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleAnimationLayout);
        rippleType = typedArray.getInt(R.styleable.RippleAnimationLayout_rippleType, DEFAULT_TYPE);
        rippleColor = typedArray.getColor(R.styleable.RippleAnimationLayout_rippleColor, getResources().getColor(R.color.default_ripple_color));
        rippleDuration = typedArray.getInt(R.styleable.RippleAnimationLayout_rippleDuration, DEFAULT_DURATION);
        rippleRadius = typedArray.getDimension(R.styleable.RippleAnimationLayout_rippleRadius, getResources().getDimension(R.dimen.rippleRadius));
        rippleStrokeWidth = typedArray.getDimension(R.styleable.RippleAnimationLayout_rippleStokeWidth, getResources().getDimension(R.dimen.rippleRadius));
        rippleScale = typedArray.getFloat(R.styleable.RippleAnimationLayout_rippleScale, DEFAULT_SCALE);
        rippleAmount = typedArray.getInt(R.styleable.RippleAnimationLayout_rippleAmount, DEFAULT_AMOUNT);
        typedArray.recycle();

        //设置每个view的等待时间
        int delayTime = rippleDuration / rippleAmount;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        if (rippleType == 0) {
            rippleStrokeWidth = 0;
            mPaint.setStyle(Paint.Style.FILL);
        } else {

            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(rippleStrokeWidth);
        }
        mPaint.setColor(rippleColor);

        //设置StrokeWidth的宽度，圆环的宽高都需变化
        LayoutParams circleViewParams = new LayoutParams((int) (2 * (rippleRadius + rippleStrokeWidth / 2)), (int) (2 * (rippleRadius + rippleStrokeWidth / 2)));
        circleViewParams.addRule(CENTER_IN_PARENT, TRUE);

        ArrayList<Animator> animatorArrayList = new ArrayList<>();
        for (int i = 0; i < rippleAmount; i++) {
            RippleCircleView rippleCircleView = new RippleCircleView(this, context);
            this.addView(rippleCircleView, circleViewParams);
            circleViewList.add(rippleCircleView);
            ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(rippleCircleView, "scaleX", 1.0f, rippleScale);
            scaleXAnimator.setRepeatCount(ValueAnimator.INFINITE);
            scaleXAnimator.setRepeatMode(ObjectAnimator.RESTART);
            scaleXAnimator.setDuration(rippleDuration);
            scaleXAnimator.setStartDelay(i * delayTime);
            animatorArrayList.add(scaleXAnimator);

            ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(rippleCircleView, "scaleY", 1.0f, rippleScale);
            scaleYAnimator.setRepeatCount(ValueAnimator.INFINITE);
            scaleYAnimator.setRepeatMode(ObjectAnimator.RESTART);
            scaleYAnimator.setDuration(rippleDuration);
            scaleYAnimator.setStartDelay(i * delayTime);
            animatorArrayList.add(scaleYAnimator);

            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(rippleCircleView, "Alpha", 1.0f, 0.0f);
            alphaAnimator.setRepeatCount(ValueAnimator.INFINITE);
            alphaAnimator.setRepeatMode(ObjectAnimator.RESTART);
            alphaAnimator.setDuration(rippleDuration);
            alphaAnimator.setStartDelay(i * delayTime);
            animatorArrayList.add(alphaAnimator);
        }

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimatorSet.playTogether(animatorArrayList);

    }

    public void startAnimation() {

        if (!isAnimationRunning) {
            for (RippleCircleView circleView : circleViewList) {
                circleView.setVisibility(VISIBLE);
            }
            mAnimatorSet.start();
            isAnimationRunning = true;
        }
    }

    public void stopAnimation() {
        if (isAnimationRunning) {
            //反转list，目的是停止的时候，从最小环开始往外invisible
            Collections.reverse(circleViewList);
            for (RippleCircleView circleView : circleViewList) {
                circleView.setVisibility(INVISIBLE);
            }
            mAnimatorSet.end();
            isAnimationRunning = false;
        }
    }

    public boolean isRunning() {
        return isAnimationRunning;
    }
}
