package com.n1njac.neteasymusicdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private RippleAnimationLayout mRippleAnimationLayout;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRippleAnimationLayout = (RippleAnimationLayout) findViewById(R.id.ripple_layout);
        mImageView = (ImageView) findViewById(R.id.netease_iv);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRippleAnimationLayout.isRunning()){
                    mRippleAnimationLayout.stopAnimation();
                }else {
                    mRippleAnimationLayout.startAnimation();
                }
            }
        });
    }
}
