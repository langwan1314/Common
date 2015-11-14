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

package com.blue.leaves.animation.listview.stickylistheader.appearance;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.blue.leaves.animation.listview.core.BaseAdapterDecorator;
import com.blue.leaves.animation.listview.core.appearance.AnimationAdapter;
import com.blue.leaves.animation.listview.core.appearance.ViewAnimator;
import com.blue.leaves.animation.listview.core.util.AnimatorUtil;
import com.blue.leaves.animation.listview.core.util.ListViewWrapper;
import com.blue.leaves.animation.listview.stickylistheader.util.StickyListHeadersListViewWrapper;
import com.blue.leaves.nineoldandroids.animation.Animator;
import com.blue.leaves.nineoldandroids.animation.ObjectAnimator;
import com.blue.leaves.stickylistheaders.StickyListHeadersAdapter;
import com.blue.leaves.stickylistheaders.StickyListHeadersListView;


/**
 * A {@link com.blue.leaves.animation.listview.core.BaseAdapterDecorator} which can be used to animate header views provided by a
 * {@link com.blue.leaves.stickylistheaders.StickyListHeadersAdapter}.
 * 用来为StickyListHeadersAdapter设置一个头部的动画
 */
public class StickyListHeadersAdapterDecorator extends BaseAdapterDecorator implements StickyListHeadersAdapter {

    /**
     * Alpha property.
     */
    private static final String ALPHA = "alpha";

    /**
     * The decorated {@link com.blue.leaves.stickylistheaders.StickyListHeadersAdapter}.
     * 装饰的StickyListHeadersAdapter
     */
    @NonNull
    private final StickyListHeadersAdapter mStickyListHeadersAdapter;

    /**
     * The {@link com.blue.leaves.animation.listview.core.appearance.ViewAnimator} responsible for animating the Views.
     * 为View执行动画的ViewAnimator
     */
    @Nullable
    private ViewAnimator mViewAnimator;

    /**
     * Create a new {@code StickyListHeadersAdapterDecorator}, decorating given {@link android.widget.BaseAdapter}.
     *
     * @param baseAdapter the {@code BaseAdapter} to decorate. If this is a {@code BaseAdapterDecorator}, it should wrap an instance of
     *                    {@link com.blue.leaves.stickylistheaders.StickyListHeadersAdapter}.
     *                    创建要给StickyListHeadersAdapterDecorator，装饰指定的BaseAdapter
     */
    public StickyListHeadersAdapterDecorator(@NonNull final BaseAdapter baseAdapter) {
        super(baseAdapter);

        BaseAdapter adapter = baseAdapter;
        while (adapter instanceof BaseAdapterDecorator) {
            adapter = ((BaseAdapterDecorator) adapter).getDecoratedBaseAdapter();
        }

        if (!(adapter instanceof StickyListHeadersAdapter)) {
            throw new IllegalArgumentException(adapter.getClass().getCanonicalName() + " does not implement StickyListHeadersAdapter");
        }

        mStickyListHeadersAdapter = (StickyListHeadersAdapter) adapter;
    }

    /**
     * Sets the {@link com.blue.leaves.stickylistheaders.StickyListHeadersListView} that this adapter will be bound to.
     */
    public void setStickyListHeadersListView(@NonNull final StickyListHeadersListView listView) {
        ListViewWrapper stickyListHeadersListViewWrapper = new StickyListHeadersListViewWrapper(listView);
        setListViewWrapper(stickyListHeadersListViewWrapper);
    }

    /**
     * Returns the {@link com.blue.leaves.animation.listview.core.appearance.ViewAnimator} responsible for animating the header Views in this
     * adapter.
     */
    @Nullable
    public ViewAnimator getViewAnimator() {
        return mViewAnimator;
    }

    @Override
    public void setListViewWrapper(@NonNull final ListViewWrapper listViewWrapper) {
        super.setListViewWrapper(listViewWrapper);
        mViewAnimator = new ViewAnimator(listViewWrapper);
    }

    /**
     * 获取headerView,需要的画执行动画
     */
    @Override
    public View getHeaderView(final int position, final View convertView, final ViewGroup parent) {
        if (getListViewWrapper() == null) {
            throw new IllegalStateException("Call setStickyListHeadersListView() on this AnimationAdapter first!");
        }

        if (convertView != null) {
            assert mViewAnimator != null;
            mViewAnimator.cancelExistingAnimation(convertView);
        }

        View itemView = mStickyListHeadersAdapter.getHeaderView(position, convertView, parent);

        animateViewIfNecessary(position, itemView, parent);
        return itemView;
    }

    /**
     * Animates given View if necessary.
     *
     * @param position the position of the item the View represents.
     * @param view     the View that should be animated.
     * @param parent   the parent the View is hosted in.
     */
    private void animateViewIfNecessary(final int position, @NonNull final View view, @NonNull final ViewGroup parent) {
        Animator[] childAnimators;
        if (getDecoratedBaseAdapter() instanceof AnimationAdapter) {
            childAnimators = ((AnimationAdapter) getDecoratedBaseAdapter()).getAnimators(parent, view);
        } else {
            childAnimators = new Animator[0];
        }
        Animator alphaAnimator = ObjectAnimator.ofFloat(view, ALPHA, 0, 1);

        assert mViewAnimator != null;
        mViewAnimator.animateViewIfNecessary(position, view, AnimatorUtil.concatAnimators(childAnimators, new Animator[0], alphaAnimator));
    }

    @Override
    public long getHeaderId(final int position) {
        return mStickyListHeadersAdapter.getHeaderId(position);
    }
}
