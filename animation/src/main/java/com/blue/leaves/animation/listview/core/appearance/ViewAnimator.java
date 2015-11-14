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

package com.blue.leaves.animation.listview.core.appearance;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;
import android.widget.GridView;

import com.blue.leaves.animation.listview.core.util.ListViewWrapper;
import com.blue.leaves.nineoldandroids.animation.Animator;
import com.blue.leaves.nineoldandroids.animation.AnimatorSet;
import com.blue.leaves.nineoldandroids.view.ViewHelper;

/**
 * A class which decides whether given Views should be animated based on their position: each View should only be animated once.
 * It also calculates proper animation delays for the views.
 * 用来决定一个给定的view是否需要动画(基于他们的位置)：每一个View只能动画一次
 * 也用来给这个view计算一个合适的动画延时
 */
public class ViewAnimator {

    /* Saved instance state keys */
    private static final String SAVEDINSTANCESTATE_FIRSTANIMATEDPOSITION = "savedinstancestate_firstanimatedposition";
    private static final String SAVEDINSTANCESTATE_LASTANIMATEDPOSITION = "savedinstancestate_lastanimatedposition";
    private static final String SAVEDINSTANCESTATE_SHOULDANIMATE = "savedinstancestate_shouldanimate";

    /* Default values */

    /**
     * The default delay in millis before the first animation starts.
     * 默认缺省的第一个动画显示之前的延时
     */
    private static final int INITIAL_DELAY_MILLIS = 150;

    /**
     * The default delay in millis between view animations.
     * 默认缺省的两个view动画的延时
     */
    private static final int DEFAULT_ANIMATION_DELAY_MILLIS = 100;

    /**
     * The default duration in millis of the animations.
     * 默认的动画演示(一个动画播放多久)
     */
    private static final int DEFAULT_ANIMATION_DURATION_MILLIS = 300;

    /* Fields */

    /**
     * The ListViewWrapper containing the ListView implementation.
     * 一个Listview的包裹，包含一个listview的实现
     */
    @NonNull
    private final ListViewWrapper mListViewWrapper;

    /**
     * The active Animators. Keys are hashcodes of the Views that are animated.
     * 活动的动画，key值是view的hash码
     */
    @NonNull
    private final SparseArray<Animator> mAnimators = new SparseArray<>();

    /**
     * The delay in millis before the first animation starts.
     * 第一个动画开始的延时
     */
    private int mInitialDelayMillis = INITIAL_DELAY_MILLIS;

    /**
     * The delay in millis between view animations.
     * 两个view动画的延时
     */
    private int mAnimationDelayMillis = DEFAULT_ANIMATION_DELAY_MILLIS;

    /**
     * The duration in millis of the animations.
     * 动画演示(一个动画播放多久)
     */
    private int mAnimationDurationMillis = DEFAULT_ANIMATION_DURATION_MILLIS;

    /**
     * The start timestamp of the first animation, as returned by {@link android.os.SystemClock#uptimeMillis()}.
     * 第一个动画开始的时间戳
     */
    private long mAnimationStartMillis;

    /**
     * The position of the item that is the first that was animated.
     * 第一个显示动画的item的位置
     */
    private int mFirstAnimatedPosition;

    /**
     * The position of the last item that was animated.
     * 最后一个显示动画的item的位置
     */
    private int mLastAnimatedPosition;

    /**
     * Whether animation is enabled. When this is set to false, no animation is applied to the views.
     * 是否是能动画，设置为false的时候，不会给view应用动画
     */
    private boolean mShouldAnimate = true;

    /**
     * Creates a new ViewAnimator, using the given {@link com.nhaarman.listviewanimations.util.ListViewWrapper}.
     * 使用给定的ListViewWrapper创建一个ViewAnimator
     *
     * @param listViewWrapper the {@code ListViewWrapper} which wraps the implementation of the ListView used.
     */
    public ViewAnimator(@NonNull final ListViewWrapper listViewWrapper) {
        mListViewWrapper = listViewWrapper;
        mAnimationStartMillis = -1;
        mFirstAnimatedPosition = -1;
        mLastAnimatedPosition = -1;
    }

    /**
     * Call this method to reset animation status on all views.
     * 重置所有view的动画
     */
    public void reset() {
        for (int i = 0; i < mAnimators.size(); i++) {
            mAnimators.get(mAnimators.keyAt(i)).cancel();
        }
        mAnimators.clear();
        mFirstAnimatedPosition = -1;
        mLastAnimatedPosition = -1;
        mAnimationStartMillis = -1;
        mShouldAnimate = true;
    }

    /**
     * Set the starting position for which items should animate. Given position will animate as well.
     * Will also call {@link #enableAnimations()}.
     * 设置开始动画的Item位置
     *
     * @param position the position.
     */
    public void setShouldAnimateFromPosition(final int position) {
        enableAnimations();
        mFirstAnimatedPosition = position - 1;
        mLastAnimatedPosition = position - 1;
    }

    /**
     * Set the starting position for which items should animate as the first position which isn't currently visible on screen. This call
     * is also valid when the {@link View}s
     * haven't been drawn yet. Will also call {@link #enableAnimations()}.
     * 设置当前当前不在屏幕上显示的Item的哪一个item应该第一个应用动画
     */
    public void setShouldAnimateNotVisible() {
        enableAnimations();
        mFirstAnimatedPosition = mListViewWrapper.getLastVisiblePosition();
        mLastAnimatedPosition = mListViewWrapper.getLastVisiblePosition();
    }

    /**
     * Sets the value of the last animated position. Views with positions smaller than or equal to given value will not be animated.
     * 设置最后应用动画的位置.小于或等于改位置的item的view将不会应用动画
     */
    void setLastAnimatedPosition(final int lastAnimatedPosition) {
        mLastAnimatedPosition = lastAnimatedPosition;
    }

    /**
     * Sets the delay in milliseconds before the first animation should start. Defaults to {@value #INITIAL_DELAY_MILLIS}.
     * 设置第一个动画显示之前的延时
     *
     * @param delayMillis the time in milliseconds.
     */
    public void setInitialDelayMillis(final int delayMillis) {
        mInitialDelayMillis = delayMillis;
    }

    /**
     * Sets the delay in milliseconds before an animation of a view should start. Defaults to {@value #DEFAULT_ANIMATION_DELAY_MILLIS}.
     * 设置一个动画显示之前的延时
     *
     * @param delayMillis the time in milliseconds.
     */
    public void setAnimationDelayMillis(final int delayMillis) {
        mAnimationDelayMillis = delayMillis;
    }

    /**
     * Sets the duration of the animation in milliseconds. Defaults to {@value #DEFAULT_ANIMATION_DURATION_MILLIS}.
     * 设置动画的显示时间
     *
     * @param durationMillis the time in milliseconds.
     */
    public void setAnimationDurationMillis(final int durationMillis) {
        mAnimationDurationMillis = durationMillis;
    }

    /**
     * Enables animating the Views. This is the default.
     * 使能动画
     */
    public void enableAnimations() {
        mShouldAnimate = true;
    }

    /**
     * Disables animating the Views. Enable them again using {@link #enableAnimations()}.
     * 不显示动画
     */
    public void disableAnimations() {
        mShouldAnimate = false;
    }

    /**
     * Cancels any existing animations for given View.
     * 取消一个view上面可能存在的所有动画
     */
    public void cancelExistingAnimation(@NonNull final View view) {
        int hashCode = view.hashCode();
        Animator animator = mAnimators.get(hashCode);
        if (animator != null) {
            animator.end();
            mAnimators.remove(hashCode);
        }
    }

    /**
     * Animates given View if necessary.
     * 如果需要，为一个view设置动画
     *
     * @param position the position of the item the View represents.
     * @param view     the View that should be animated.
     */
    public void animateViewIfNecessary(final int position, @NonNull final View view, @NonNull final Animator[] animators) {
        if (mShouldAnimate && position > mLastAnimatedPosition) {
            if (mFirstAnimatedPosition == -1) {
                mFirstAnimatedPosition = position;
            }

            animateView(position, view, animators);
            mLastAnimatedPosition = position;
        }
    }

    /**
     * Animates given View.
     * 为指定的view启动动画
     *
     * @param view the View that should be animated.
     */
    private void animateView(final int position, @NonNull final View view, @NonNull final Animator[] animators) {
        if (mAnimationStartMillis == -1) {
            mAnimationStartMillis = SystemClock.uptimeMillis();
        }

        ViewHelper.setAlpha(view, 0);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animators);
        set.setStartDelay(calculateAnimationDelay(position));
        set.setDuration(mAnimationDurationMillis);
        set.start();

        mAnimators.put(view.hashCode(), set);
    }

    /**
     * Returns the delay in milliseconds after which animation for View with position mLastAnimatedPosition + 1 should start.
     * 返回下一个启动动画的延时
     */
    @SuppressLint("NewApi")
    private int calculateAnimationDelay(final int position) {
        int delay;

        int lastVisiblePosition = mListViewWrapper.getLastVisiblePosition();
        int firstVisiblePosition = mListViewWrapper.getFirstVisiblePosition();

        int numberOfItemsOnScreen = lastVisiblePosition - firstVisiblePosition;
        int numberOfAnimatedItems = position - 1 - mFirstAnimatedPosition;

        if (numberOfItemsOnScreen + 1 < numberOfAnimatedItems) {
            delay = mAnimationDelayMillis;

            if (mListViewWrapper.getListView() instanceof GridView && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                int numColumns = ((GridView) mListViewWrapper.getListView()).getNumColumns();
                delay += mAnimationDelayMillis * (position % numColumns);
            }
        } else {
            int delaySinceStart = (position - mFirstAnimatedPosition) * mAnimationDelayMillis;
            delay = Math.max(0, (int) (-SystemClock.uptimeMillis() + mAnimationStartMillis + mInitialDelayMillis + delaySinceStart));
        }
        return delay;
    }

    /**
     * Returns a Parcelable object containing the AnimationAdapter's current dynamic state.
     * 返回一个Parcelable对象包含了AnimationAdapter当前的动态状态
     */
    @NonNull
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();

        bundle.putInt(SAVEDINSTANCESTATE_FIRSTANIMATEDPOSITION, mFirstAnimatedPosition);
        bundle.putInt(SAVEDINSTANCESTATE_LASTANIMATEDPOSITION, mLastAnimatedPosition);
        bundle.putBoolean(SAVEDINSTANCESTATE_SHOULDANIMATE, mShouldAnimate);

        return bundle;
    }

    /**
     * Restores this AnimationAdapter's state.
     * 恢复动画状态
     *
     * @param parcelable the Parcelable object previously returned by {@link #onSaveInstanceState()}.
     */
    public void onRestoreInstanceState(@Nullable final Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            mFirstAnimatedPosition = bundle.getInt(SAVEDINSTANCESTATE_FIRSTANIMATEDPOSITION);
            mLastAnimatedPosition = bundle.getInt(SAVEDINSTANCESTATE_LASTANIMATEDPOSITION);
            mShouldAnimate = bundle.getBoolean(SAVEDINSTANCESTATE_SHOULDANIMATE);
        }
    }
}
