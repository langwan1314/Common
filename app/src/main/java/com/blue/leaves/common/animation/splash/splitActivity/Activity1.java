package com.blue.leaves.common.animation.splash.splitActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blue.leaves.common.R;

/**
 * 
 * 中心打开方式
 * @author Administrator
 *
 */
public class Activity1 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        setContentView(R.layout.activity_splash_split_one);

        findViewById(R.id.one).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ActivitySplitAnimationUtil.startActivity(Activity1.this, new Intent(Activity1.this, Activity2.class));
            }
        });
        
    }
}
