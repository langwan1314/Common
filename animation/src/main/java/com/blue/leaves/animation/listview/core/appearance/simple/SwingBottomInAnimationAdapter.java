/*
 * Copyright 2014 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blue.leaves.animation.listview.core.appearance.simple;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.blue.leaves.animation.listview.core.appearance.SingleAnimationAdapter;
import com.blue.leaves.nineoldandroids.animation.Animator;
import com.blue.leaves.nineoldandroids.animation.ObjectAnimator;

/**
 * An implementation of the AnimationAdapter class which applies a
 * swing-in-from-bottom-animation to views.
 * 一个AnimationAdapter的实现，对view应用一个从底部滑入的动画
 */
public class SwingBottomInAnimationAdapter extends SingleAnimationAdapter {

    private static final String TRANSLATION_Y = "translationY";

    public SwingBottomInAnimationAdapter(@NonNull final BaseAdapter baseAdapter) {
        super(baseAdapter);
    }

    @Override
    @NonNull
    protected Animator getAnimator(@NonNull final ViewGroup parent, @NonNull final View view) {//具体的动画，
        return ObjectAnimator.ofFloat(view, TRANSLATION_Y, parent.getMeasuredHeight() >> 1, 0);//y值改变的动画，y值减少，一个上移的动画
    }

}
