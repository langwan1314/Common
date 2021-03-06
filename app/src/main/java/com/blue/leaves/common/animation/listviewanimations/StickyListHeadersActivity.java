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

package com.blue.leaves.common.animation.listviewanimations;

import android.os.Bundle;

import com.blue.leaves.animation.listview.core.appearance.simple.AlphaInAnimationAdapter;
import com.blue.leaves.animation.listview.stickylistheader.appearance.StickyListHeadersAdapterDecorator;
import com.blue.leaves.animation.listview.stickylistheader.util.StickyListHeadersListViewWrapper;
import com.blue.leaves.common.R;
import com.blue.leaves.stickylistheaders.StickyListHeadersListView;


public class StickyListHeadersActivity extends BaseActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animations_listview_stickylistheaders);

        StickyListHeadersListView listView = (StickyListHeadersListView) findViewById(R.id.activity_stickylistheaders_listview);
        listView.setFitsSystemWindows(true);

        MyListAdapter adapter = new MyListAdapter(this);
        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(adapter);
        StickyListHeadersAdapterDecorator stickyListHeadersAdapterDecorator = new StickyListHeadersAdapterDecorator(animationAdapter);
        stickyListHeadersAdapterDecorator.setListViewWrapper(new StickyListHeadersListViewWrapper(listView));

        assert animationAdapter.getViewAnimator() != null;
        animationAdapter.getViewAnimator().setInitialDelayMillis(500);

        assert stickyListHeadersAdapterDecorator.getViewAnimator() != null;
        stickyListHeadersAdapterDecorator.getViewAnimator().setInitialDelayMillis(500);

        listView.setAdapter(stickyListHeadersAdapterDecorator);
    }
}
