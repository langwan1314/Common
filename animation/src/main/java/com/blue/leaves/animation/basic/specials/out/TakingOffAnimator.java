package com.blue.leaves.animation.basic.specials.out;

import android.view.View;

import com.blue.leaves.animation.basic.BaseViewAnimator;
import com.blue.leaves.animation.easing.Glider;
import com.blue.leaves.animation.easing.Skill;
import com.blue.leaves.nineoldandroids.animation.ObjectAnimator;


public class TakingOffAnimator extends BaseViewAnimator {
    @Override
    protected void prepare(View target) {
        getAnimatorAgent().playTogether(
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "scaleX", 1f, 1.5f)),
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "scaleY", 1f, 1.5f)),
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "alpha", 1, 0))
        );
    }
}
