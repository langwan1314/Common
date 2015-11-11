package com.blue.leaves.common.adapter.common.item;


import android.view.View;
import android.widget.TextView;

import com.blue.leaves.adapter.common.AdapterItem;
import com.blue.leaves.common.R;
import com.blue.leaves.common.adapter.common.model.DemoModel;

/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public class TextItem implements AdapterItem<DemoModel>{

    @Override
    public int getLayoutResId() {
        return R.layout.demo_item_text;
    }

    TextView textView;

    @Override
    public void findViews(View root) {
        textView = (TextView) root.findViewById(R.id.textView);
    }

    @Override
    public void setViews(DemoModel model, int position) {
        textView.setText(model.content);
    }

}

