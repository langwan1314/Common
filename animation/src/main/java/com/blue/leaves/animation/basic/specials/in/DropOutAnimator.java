package com.blue.leaves.animation.basic.specials.in;

import android.view.View;

import com.blue.leaves.animation.basic.BaseViewAnimator;
import com.blue.leaves.animation.easing.Glider;
import com.blue.leaves.animation.easing.Skill;
import com.blue.leaves.nineoldandroids.animation.ObjectAnimator;


public class DropOutAnimator extends BaseViewAnimator {
    @Override
    protected void prepare(View target) {
        int distance = target.getTop() + target.getHeight();
        getAnimatorAgent().playTogether(
                ObjectAnimator.ofFloat(target, "alpha", 0, 1),
                Glider.glide(Skill.BounceEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "translationY", -distance, 0))
        );
    }
}
