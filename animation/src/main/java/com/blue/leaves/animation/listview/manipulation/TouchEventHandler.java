package com.blue.leaves.animation.listview.manipulation;

import android.support.annotation.NonNull;
import android.view.MotionEvent;

public interface TouchEventHandler {

    boolean onTouchEvent(@NonNull MotionEvent event);

    boolean isInteracting();

}
