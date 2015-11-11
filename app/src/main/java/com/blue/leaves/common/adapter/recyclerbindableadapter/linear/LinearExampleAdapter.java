package com.blue.leaves.common.adapter.recyclerbindableadapter.linear;

import android.view.View;

import com.blue.leaves.adapter.recyclerbindableadapter.RecyclerBindableAdapter;
import com.blue.leaves.common.R;


/**
 * Created by Danil on 02.10.2015.
 */
public class LinearExampleAdapter extends RecyclerBindableAdapter<Integer, LinearViewHolder> {
    private LinearViewHolder.ActionListener actionListener;

    @Override
    protected int layoutId(int type) {
        return R.layout.linear_example_item;
    }

    @Override
    protected LinearViewHolder viewHolder(View view, int type) {
        return new LinearViewHolder(view);
    }

    @Override
    protected void onBindItemViewHolder(LinearViewHolder viewHolder, final int position, int type) {
        viewHolder.bindView(getItem(position), position, actionListener);
    }

    public void setActionListener(LinearViewHolder.ActionListener actionListener) {
        this.actionListener = actionListener;
    }
}