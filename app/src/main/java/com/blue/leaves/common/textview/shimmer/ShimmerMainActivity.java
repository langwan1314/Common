package com.blue.leaves.common.textview.shimmer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.blue.leaves.common.R;
import com.blue.leaves.textview.shimmer.Shimmer;
import com.blue.leaves.textview.shimmer.ShimmerTextView;


public class ShimmerMainActivity extends Activity {

    ShimmerTextView tv;
    Shimmer shimmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shimmer_main);

        tv = (ShimmerTextView) findViewById(R.id.shimmer_tv);
    }

    public void toggleAnimation(View target) {
        if (shimmer != null && shimmer.isAnimating()) {
            shimmer.cancel();
        } else {
            shimmer = new Shimmer();
            shimmer.start(tv);
        }
    }
}
