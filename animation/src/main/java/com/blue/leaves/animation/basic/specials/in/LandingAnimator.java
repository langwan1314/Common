package com.blue.leaves.animation.basic.specials.in;

import android.view.View;

import com.blue.leaves.animation.basic.BaseViewAnimator;
import com.blue.leaves.animation.easing.Glider;
import com.blue.leaves.animation.easing.Skill;
import com.blue.leaves.nineoldandroids.animation.ObjectAnimator;


public class LandingAnimator extends BaseViewAnimator {
    @Override
    protected void prepare(View target) {
        getAnimatorAgent().playTogether(
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "scaleX", 1.5f, 1f)),
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "scaleY", 1.5f, 1f)),
                Glider.glide(Skill.QuintEaseOut, getDuration(), ObjectAnimator.ofFloat(target, "alpha", 0, 1f))
        );
    }
}
