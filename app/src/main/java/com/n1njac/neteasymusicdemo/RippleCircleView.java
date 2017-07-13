package com.n1njac.neteasymusicdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by N1njaC on 2017/7/13.
 */

public class RippleCircleView extends View {

    private RippleAnimationLayout animationLayout;

    public RippleCircleView(Context context) {
        super(context);
    }

    public RippleCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RippleCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RippleCircleView(RippleAnimationLayout animationLayout, Context context) {
        super(context);
        this.animationLayout = animationLayout;
        this.setVisibility(INVISIBLE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int radius = (Math.min(getWidth(), getHeight())) / 2;
        canvas.drawCircle(radius, radius, radius - animationLayout.rippleStrokeWidth, animationLayout.mPaint);
    }
}
