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

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.blue.leaves.animation.listview.core.BaseAdapterDecorator;
import com.blue.leaves.animation.listview.core.util.AnimatorUtil;
import com.blue.leaves.animation.listview.core.util.ListViewWrapper;
import com.blue.leaves.nineoldandroids.animation.Animator;
import com.blue.leaves.nineoldandroids.animation.ObjectAnimator;

/**
 * A {@link BaseAdapterDecorator} class which applies multiple {@link Animator}s at once to views when they are first shown. The
 * Animators applied include the animations specified
 * in {@link #getAnimators(ViewGroup, View)}, plus an alpha transition.
 * 可以一次应用多个动画的一个类。应用给view的动画包括getAnimators获取的动画外加一个alpha动画
 */
public abstract class AnimationAdapter extends BaseAdapterDecorator {

    /**
     * Saved instance state key for the ViewAniamt
     * 保存动画状态的一个key值
     */
    private static final String SAVEDINSTANCESTATE_VIEWANIMATOR = "savedinstancestate_viewanimator";

    /**
     * Alpha property
     */
    private static final String ALPHA = "alpha";

    /**
     * The ViewAnimator responsible for animating the Views.
     * 用来给View设置动画的ViewAnimator
     */
    @Nullable
    private ViewAnimator mViewAnimator;

    /**
     * Whether this instance is the root AnimationAdapter. When this is set to false, animation is not applied to the views, since the
     * wrapper AnimationAdapter will take care of
     * that.
     * 是否是根AnimationAdapter。如果设置为false,views将不会应用动画，因为包裹的AnimationAdapter将会来做这个事
     */
    private boolean mIsRootAdapter;

    /**
     * If the AbsListView is an instance of GridView, this boolean indicates whether the GridView is possibly measuring the view.
     * 如果是GridView，这个布尔值用来标识是否可能正在测量view
     */
    private boolean mGridViewPossiblyMeasuring;

    /**
     * The position of the item that the GridView is possibly measuring.
     * GridView正在测量的位置
     */
    private int mGridViewMeasuringPosition;

    /**
     * Creates a new AnimationAdapter, wrapping given BaseAdapter.
     * 创建一个AnimationAdapter来包裹给定的BaseAdapter
     *
     * @param baseAdapter the BaseAdapter to wrap.
     */
    protected AnimationAdapter(@NonNull final BaseAdapter baseAdapter) {
        super(baseAdapter);

        mGridViewPossiblyMeasuring = true;
        mGridViewMeasuringPosition = -1;
        mIsRootAdapter = true;

        if (baseAdapter instanceof AnimationAdapter) {
            ((AnimationAdapter) baseAdapter).setIsWrapped();
        }
    }

    /**
     * 使用给定的ListViewWrapper来创建要给ViewAnimator
     */
    @Override
    public void setListViewWrapper(@NonNull final ListViewWrapper listViewWrapper) {
        super.setListViewWrapper(listViewWrapper);
        mViewAnimator = new ViewAnimator(listViewWrapper);
    }

    /**
     * Sets whether this instance is wrapped by another instance of AnimationAdapter. If called, this instance will not apply any
     * animations to the views, since the wrapper
     * AnimationAdapter handles that.
     * 设置当前实例是否被另一个AnimationAdapter包裹，如果调用了，这个实例不会应用任何动画，因为包裹的AnimationAdapter会做这个
     */
    private void setIsWrapped() {
        mIsRootAdapter = false;
    }

    /**
     * Call this method to reset animation status on all views. The next time {@link #notifyDataSetChanged()} is called on the base
     * adapter, all views will animate again.
     * 重设所有view的动画,当其base adapter调用notifyDataSetChanged时动画会被重新执行
     */
    public void reset() {
        if (getListViewWrapper() == null) {
            throw new IllegalStateException("Call setAbsListView() on this AnimationAdapter first!");
        }

        assert mViewAnimator != null;
        mViewAnimator.reset();

        mGridViewPossiblyMeasuring = true;
        mGridViewMeasuringPosition = -1;

        if (getDecoratedBaseAdapter() instanceof AnimationAdapter) {
            ((AnimationAdapter) getDecoratedBaseAdapter()).reset();
        }
    }

    /**
     * Returns the {@link com.nhaarman.listviewanimations.appearance.ViewAnimator} responsible for animating the Views in this adapter.
     * 返回一个为此view执行动画的ViewAnimator
     */
    @Nullable
    public ViewAnimator getViewAnimator() {
        return mViewAnimator;
    }

    /**
     * 获取item,需要的话执行动画
     */
    @NonNull
    @Override
    public final View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        if (mIsRootAdapter) {
            if (getListViewWrapper() == null) {
                throw new IllegalStateException("Call setAbsListView() on this AnimationAdapter first!");
            }

            assert mViewAnimator != null;
            if (convertView != null) {
                mViewAnimator.cancelExistingAnimation(convertView);
            }
        }

        View itemView = super.getView(position, convertView, parent);

        if (mIsRootAdapter) {
            animateViewIfNecessary(position, itemView, parent);
        }
        return itemView;
    }

    /**
     * Animates given View if necessary.
     * 如果需要，给制定view执行动画
     *
     * @param position the position of the item the View represents.
     * @param view     the View that should be animated.
     * @param parent   the parent the View is hosted in.
     */
    private void animateViewIfNecessary(final int position, @NonNull final View view, @NonNull final ViewGroup parent) {
        assert mViewAnimator != null;

        /* GridView measures the first View which is returned by getView(int, View, ViewGroup), but does not use that View.
           On KitKat, it does this actually multiple times.
           Therefore, we animate all these first Views, and reset the last animated position when we suspect GridView is measuring. */
        mGridViewPossiblyMeasuring = mGridViewPossiblyMeasuring && (mGridViewMeasuringPosition == -1 || mGridViewMeasuringPosition ==
                position);

        if (mGridViewPossiblyMeasuring) {
            mGridViewMeasuringPosition = position;
            mViewAnimator.setLastAnimatedPosition(-1);
        }

        Animator[] childAnimators;
        if (getDecoratedBaseAdapter() instanceof AnimationAdapter) {
            childAnimators = ((AnimationAdapter) getDecoratedBaseAdapter()).getAnimators(parent, view);
        } else {
            childAnimators = new Animator[0];
        }
        Animator[] animators = getAnimators(parent, view);
        Animator alphaAnimator = ObjectAnimator.ofFloat(view, ALPHA, 0, 1);

        Animator[] concatAnimators = AnimatorUtil.concatAnimators(childAnimators, animators, alphaAnimator);
        mViewAnimator.animateViewIfNecessary(position, view, concatAnimators);
    }

    /**
     * Returns the Animators to apply to the views. In addition to the returned Animators, an alpha transition will be applied to the view.
     * 返回应用给view的所有动画，除了返回的动画外，还有一个额外的alpha动画会被应用
     *
     * @param parent The parent of the view
     * @param view   The view that will be animated, as retrieved by getView().
     */
    @NonNull
    public abstract Animator[] getAnimators(@NonNull ViewGroup parent, @NonNull View view);

    /**
     * Returns a Parcelable object containing the AnimationAdapter's current dynamic state.
     * 返回一个Parcelable对象包含AnimationAdapter当前的动态状态
     */
    @NonNull
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();

        if (mViewAnimator != null) {
            bundle.putParcelable(SAVEDINSTANCESTATE_VIEWANIMATOR, mViewAnimator.onSaveInstanceState());
        }

        return bundle;
    }

    /**
     * Restores this AnimationAdapter's state.
     * 恢复AnimationAdapter的状态
     *
     * @param parcelable the Parcelable object previously returned by {@link #onSaveInstanceState()}.
     */
    public void onRestoreInstanceState(@Nullable final Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            if (mViewAnimator != null) {
                mViewAnimator.onRestoreInstanceState(bundle.getParcelable(SAVEDINSTANCESTATE_VIEWANIMATOR));
            }
        }
    }
}
