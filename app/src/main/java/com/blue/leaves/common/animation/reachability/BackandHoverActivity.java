package com.blue.leaves.common.animation.reachability;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.blue.leaves.animation.reachability.Reachability;
import com.blue.leaves.common.R;


public class BackandHoverActivity extends Activity {

    private Reachability mReachability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_and_hover);
        mReachability = new Reachability(this);
        mReachability.makeHoverView(Reachability.Position.RIGHT);

        findViewById(R.id.switch_hover).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReachability.switchHover();
            }
        });
        findViewById(R.id.switch_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReachability.switchBack();
            }
        });

    }
}
