package com.blue.leaves.common.adapter.recyclerbindableadapter.filter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.blue.leaves.common.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Danil on 07.10.2015.
 */
public class FilterViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.filter_example_item_text)
    TextView text;


    public FilterViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindView(String item) {
        text.setText(item);
    }
}
