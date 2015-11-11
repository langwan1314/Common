package com.blue.leaves.common.adapter.recyclerbindableadapter.parallax;

import android.view.View;
import android.widget.TextView;


import com.blue.leaves.adapter.recyclerbindableadapter.view.BindableViewHolder;
import com.blue.leaves.common.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Danil on 07.10.2015.
 */
public class ParallaxViewHolder extends BindableViewHolder<String> {
    @Bind(R.id.parallax_example_item_text)
    TextView text;

    public ParallaxViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindView(int position, String item, ActionListener actionListener) {
        super.bindView(position, item, actionListener);
        text.setText(item);
    }
}
