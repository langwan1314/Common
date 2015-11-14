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

package com.blue.leaves.animation.easing;

import com.blue.leaves.animation.easing.back.BackEaseIn;
import com.blue.leaves.animation.easing.back.BackEaseInOut;
import com.blue.leaves.animation.easing.back.BackEaseOut;
import com.blue.leaves.animation.easing.bounce.BounceEaseIn;
import com.blue.leaves.animation.easing.bounce.BounceEaseInOut;
import com.blue.leaves.animation.easing.bounce.BounceEaseOut;
import com.blue.leaves.animation.easing.circ.CircEaseIn;
import com.blue.leaves.animation.easing.circ.CircEaseInOut;
import com.blue.leaves.animation.easing.circ.CircEaseOut;
import com.blue.leaves.animation.easing.cubic.CubicEaseIn;
import com.blue.leaves.animation.easing.cubic.CubicEaseInOut;
import com.blue.leaves.animation.easing.cubic.CubicEaseOut;
import com.blue.leaves.animation.easing.elastic.ElasticEaseIn;
import com.blue.leaves.animation.easing.elastic.ElasticEaseOut;
import com.blue.leaves.animation.easing.expo.ExpoEaseIn;
import com.blue.leaves.animation.easing.expo.ExpoEaseInOut;
import com.blue.leaves.animation.easing.expo.ExpoEaseOut;
import com.blue.leaves.animation.easing.quad.QuadEaseIn;
import com.blue.leaves.animation.easing.quad.QuadEaseInOut;
import com.blue.leaves.animation.easing.quad.QuadEaseOut;
import com.blue.leaves.animation.easing.quint.QuintEaseIn;
import com.blue.leaves.animation.easing.quint.QuintEaseInOut;
import com.blue.leaves.animation.easing.quint.QuintEaseOut;
import com.blue.leaves.animation.easing.sine.SineEaseIn;
import com.blue.leaves.animation.easing.sine.SineEaseInOut;
import com.blue.leaves.animation.easing.sine.SineEaseOut;
import com.blue.leaves.animation.easing.linear.Linear;


public enum  Skill {

    BackEaseIn(BackEaseIn.class),
    BackEaseOut(BackEaseOut.class),
    BackEaseInOut(BackEaseInOut.class),

    BounceEaseIn(BounceEaseIn.class),
    BounceEaseOut(BounceEaseOut.class),
    BounceEaseInOut(BounceEaseInOut.class),

    CircEaseIn(CircEaseIn.class),
    CircEaseOut(CircEaseOut.class),
    CircEaseInOut(CircEaseInOut.class),

    CubicEaseIn(CubicEaseIn.class),
    CubicEaseOut(CubicEaseOut.class),
    CubicEaseInOut(CubicEaseInOut.class),

    ElasticEaseIn(ElasticEaseIn.class),
    ElasticEaseOut(ElasticEaseOut.class),

    ExpoEaseIn(ExpoEaseIn.class),
    ExpoEaseOut(ExpoEaseOut.class),
    ExpoEaseInOut(ExpoEaseInOut.class),

    QuadEaseIn(QuadEaseIn.class),
    QuadEaseOut(QuadEaseOut.class),
    QuadEaseInOut(QuadEaseInOut.class),

    QuintEaseIn(QuintEaseIn.class),
    QuintEaseOut(QuintEaseOut.class),
    QuintEaseInOut(QuintEaseInOut.class),

    SineEaseIn(SineEaseIn.class),
    SineEaseOut(SineEaseOut.class),
    SineEaseInOut(SineEaseInOut.class),

    Linear(Linear.class);


    private Class easingMethod;

    private Skill(Class clazz) {
        easingMethod = clazz;
    }

    public BaseEasingMethod getMethod(float duration) {
        try {
            return (BaseEasingMethod)easingMethod.getConstructor(float.class).newInstance(duration);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error("Can not init easingMethod instance");
        }
    }
}
