
/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 daimajia
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.blue.leaves.animation.basic;

import com.blue.leaves.animation.basic.attention.BounceAnimator;
import com.blue.leaves.animation.basic.attention.FlashAnimator;
import com.blue.leaves.animation.basic.attention.PulseAnimator;
import com.blue.leaves.animation.basic.attention.RubberBandAnimator;
import com.blue.leaves.animation.basic.attention.ShakeAnimator;
import com.blue.leaves.animation.basic.attention.StandUpAnimator;
import com.blue.leaves.animation.basic.attention.SwingAnimator;
import com.blue.leaves.animation.basic.attention.TadaAnimator;
import com.blue.leaves.animation.basic.attention.WaveAnimator;
import com.blue.leaves.animation.basic.attention.WobbleAnimator;
import com.blue.leaves.animation.basic.bouncing_entrances.BounceInAnimator;
import com.blue.leaves.animation.basic.bouncing_entrances.BounceInDownAnimator;
import com.blue.leaves.animation.basic.bouncing_entrances.BounceInLeftAnimator;
import com.blue.leaves.animation.basic.bouncing_entrances.BounceInRightAnimator;
import com.blue.leaves.animation.basic.bouncing_entrances.BounceInUpAnimator;
import com.blue.leaves.animation.basic.fading_entrances.FadeInAnimator;
import com.blue.leaves.animation.basic.fading_entrances.FadeInDownAnimator;
import com.blue.leaves.animation.basic.fading_entrances.FadeInLeftAnimator;
import com.blue.leaves.animation.basic.fading_entrances.FadeInRightAnimator;
import com.blue.leaves.animation.basic.fading_entrances.FadeInUpAnimator;
import com.blue.leaves.animation.basic.fading_exits.FadeOutAnimator;
import com.blue.leaves.animation.basic.fading_exits.FadeOutDownAnimator;
import com.blue.leaves.animation.basic.fading_exits.FadeOutLeftAnimator;
import com.blue.leaves.animation.basic.fading_exits.FadeOutRightAnimator;
import com.blue.leaves.animation.basic.fading_exits.FadeOutUpAnimator;
import com.blue.leaves.animation.basic.flippers.FlipInXAnimator;
import com.blue.leaves.animation.basic.flippers.FlipInYAnimator;
import com.blue.leaves.animation.basic.flippers.FlipOutXAnimator;
import com.blue.leaves.animation.basic.flippers.FlipOutYAnimator;
import com.blue.leaves.animation.basic.rotating_entrances.RotateInAnimator;
import com.blue.leaves.animation.basic.rotating_entrances.RotateInDownLeftAnimator;
import com.blue.leaves.animation.basic.rotating_entrances.RotateInDownRightAnimator;
import com.blue.leaves.animation.basic.rotating_entrances.RotateInUpLeftAnimator;
import com.blue.leaves.animation.basic.rotating_entrances.RotateInUpRightAnimator;
import com.blue.leaves.animation.basic.rotating_exits.RotateOutAnimator;
import com.blue.leaves.animation.basic.rotating_exits.RotateOutDownLeftAnimator;
import com.blue.leaves.animation.basic.rotating_exits.RotateOutDownRightAnimator;
import com.blue.leaves.animation.basic.rotating_exits.RotateOutUpLeftAnimator;
import com.blue.leaves.animation.basic.rotating_exits.RotateOutUpRightAnimator;
import com.blue.leaves.animation.basic.sliders.SlideInDownAnimator;
import com.blue.leaves.animation.basic.sliders.SlideInLeftAnimator;
import com.blue.leaves.animation.basic.sliders.SlideInRightAnimator;
import com.blue.leaves.animation.basic.sliders.SlideInUpAnimator;
import com.blue.leaves.animation.basic.sliders.SlideOutDownAnimator;
import com.blue.leaves.animation.basic.sliders.SlideOutLeftAnimator;
import com.blue.leaves.animation.basic.sliders.SlideOutRightAnimator;
import com.blue.leaves.animation.basic.sliders.SlideOutUpAnimator;
import com.blue.leaves.animation.basic.specials.HingeAnimator;
import com.blue.leaves.animation.basic.specials.RollInAnimator;
import com.blue.leaves.animation.basic.specials.RollOutAnimator;
import com.blue.leaves.animation.basic.specials.in.DropOutAnimator;
import com.blue.leaves.animation.basic.specials.in.LandingAnimator;
import com.blue.leaves.animation.basic.specials.out.TakingOffAnimator;
import com.blue.leaves.animation.basic.zooming_entrances.ZoomInAnimator;
import com.blue.leaves.animation.basic.zooming_entrances.ZoomInDownAnimator;
import com.blue.leaves.animation.basic.zooming_entrances.ZoomInLeftAnimator;
import com.blue.leaves.animation.basic.zooming_entrances.ZoomInRightAnimator;
import com.blue.leaves.animation.basic.zooming_entrances.ZoomInUpAnimator;
import com.blue.leaves.animation.basic.zooming_exits.ZoomOutAnimator;
import com.blue.leaves.animation.basic.zooming_exits.ZoomOutDownAnimator;
import com.blue.leaves.animation.basic.zooming_exits.ZoomOutLeftAnimator;
import com.blue.leaves.animation.basic.zooming_exits.ZoomOutRightAnimator;
import com.blue.leaves.animation.basic.zooming_exits.ZoomOutUpAnimator;

public enum Techniques {

    DropOut(DropOutAnimator.class),
    Landing(LandingAnimator.class),
    TakingOff(TakingOffAnimator.class),

    Flash(FlashAnimator.class),
    Pulse(PulseAnimator.class),
    RubberBand(RubberBandAnimator.class),
    Shake(ShakeAnimator.class),
    Swing(SwingAnimator.class),
    Wobble(WobbleAnimator.class),
    Bounce(BounceAnimator.class),
    Tada(TadaAnimator.class),
    StandUp(StandUpAnimator.class),
    Wave(WaveAnimator.class),

    Hinge(HingeAnimator.class),
    RollIn(RollInAnimator.class),
    RollOut(RollOutAnimator.class),

    BounceIn(BounceInAnimator.class),
    BounceInDown(BounceInDownAnimator.class),
    BounceInLeft(BounceInLeftAnimator.class),
    BounceInRight(BounceInRightAnimator.class),
    BounceInUp(BounceInUpAnimator.class),

    FadeIn(FadeInAnimator.class),
    FadeInUp(FadeInUpAnimator.class),
    FadeInDown(FadeInDownAnimator.class),
    FadeInLeft(FadeInLeftAnimator.class),
    FadeInRight(FadeInRightAnimator.class),

    FadeOut(FadeOutAnimator.class),
    FadeOutDown(FadeOutDownAnimator.class),
    FadeOutLeft(FadeOutLeftAnimator.class),
    FadeOutRight(FadeOutRightAnimator.class),
    FadeOutUp(FadeOutUpAnimator.class),

    FlipInX(FlipInXAnimator.class),
    FlipOutX(FlipOutXAnimator.class),
    FlipInY(FlipInYAnimator.class),
    FlipOutY(FlipOutYAnimator.class),
    RotateIn(RotateInAnimator.class),
    RotateInDownLeft(RotateInDownLeftAnimator.class),
    RotateInDownRight(RotateInDownRightAnimator.class),
    RotateInUpLeft(RotateInUpLeftAnimator.class),
    RotateInUpRight(RotateInUpRightAnimator.class),

    RotateOut(RotateOutAnimator.class),
    RotateOutDownLeft(RotateOutDownLeftAnimator.class),
    RotateOutDownRight(RotateOutDownRightAnimator.class),
    RotateOutUpLeft(RotateOutUpLeftAnimator.class),
    RotateOutUpRight(RotateOutUpRightAnimator.class),

    SlideInLeft(SlideInLeftAnimator.class),
    SlideInRight(SlideInRightAnimator.class),
    SlideInUp(SlideInUpAnimator.class),
    SlideInDown(SlideInDownAnimator.class),

    SlideOutLeft(SlideOutLeftAnimator.class),
    SlideOutRight(SlideOutRightAnimator.class),
    SlideOutUp(SlideOutUpAnimator.class),
    SlideOutDown(SlideOutDownAnimator.class),

    ZoomIn(ZoomInAnimator.class),
    ZoomInDown(ZoomInDownAnimator.class),
    ZoomInLeft(ZoomInLeftAnimator.class),
    ZoomInRight(ZoomInRightAnimator.class),
    ZoomInUp(ZoomInUpAnimator.class),

    ZoomOut(ZoomOutAnimator.class),
    ZoomOutDown(ZoomOutDownAnimator.class),
    ZoomOutLeft(ZoomOutLeftAnimator.class),
    ZoomOutRight(ZoomOutRightAnimator.class),
    ZoomOutUp(ZoomOutUpAnimator.class);



    private Class animatorClazz;

    private Techniques(Class clazz) {
        animatorClazz = clazz;
    }

    public BaseViewAnimator getAnimator() {
        try {
            return (BaseViewAnimator) animatorClazz.newInstance();
        } catch (Exception e) {
            throw new Error("Can not init animatorClazz instance");
        }
    }
}
