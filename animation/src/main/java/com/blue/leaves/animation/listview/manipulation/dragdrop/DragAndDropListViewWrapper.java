package com.blue.leaves.animation.listview.manipulation.dragdrop;

import android.widget.AbsListView;

import com.blue.leaves.animation.listview.core.util.ListViewWrapper;

public interface DragAndDropListViewWrapper extends ListViewWrapper {

    void setOnScrollListener(AbsListView.OnScrollListener onScrollListener);

    int pointToPosition(int x, int y);

    int computeVerticalScrollOffset();

    int computeVerticalScrollExtent();

    int computeVerticalScrollRange();
}
