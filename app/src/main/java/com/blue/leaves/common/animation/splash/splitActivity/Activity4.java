package com.blue.leaves.common.animation.splash.splitActivity;

import android.app.Activity;
import android.os.Bundle;

import com.blue.leaves.common.R;


public class Activity4 extends Activity {

	  @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        // Preparing the 2 images to be split
	        ActivitySplitAnimationUtil.prepareAnimation(this);

	        setContentView(R.layout.activity_splash_split_four);

	        // Animating the items to be open, revealing the new activity
	        ActivitySplitAnimationUtil.animate(this, 1000);
	    }

	    @Override
	    protected void onStop() {
	        // If we're currently running the entrance animation - cancel it
	        ActivitySplitAnimationUtil.cancel();

	        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
	    }
}
