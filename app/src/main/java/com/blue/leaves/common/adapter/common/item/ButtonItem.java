package com.blue.leaves.common.adapter.common.item;

import android.view.View;
import android.widget.Button;

import com.blue.leaves.adapter.common.AdapterItem;
import com.blue.leaves.common.R;
import com.blue.leaves.common.adapter.common.model.DemoModel;

/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public class ButtonItem implements AdapterItem<DemoModel> {

    Button btn;

    @Override
    public int getLayoutResId() {
        return R.layout.demo_item_button;
    }

    @Override
    public void findViews(View root) {
        btn = (Button) root.findViewById(R.id.button);
    }


    @Override
    public void setViews(DemoModel model, int position) {
        btn.setText(model.content);
    }

}
