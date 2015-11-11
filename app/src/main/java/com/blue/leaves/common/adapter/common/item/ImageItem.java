package com.blue.leaves.common.adapter.common.item;

import android.view.View;
import android.widget.ImageView;

import com.blue.leaves.adapter.common.AdapterItem;
import com.blue.leaves.common.R;
import com.blue.leaves.common.adapter.common.model.DemoModel;

/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public class ImageItem implements AdapterItem<DemoModel> {

    ImageView imageView;

    @Override
    public int getLayoutResId() {
        return R.layout.demo_item_image;
    }

    @Override
    public void findViews(View root) {
        imageView = (ImageView) root.findViewById(R.id.imageView);
    }

    @Override
    public void setViews(DemoModel model, int position) {
        imageView.setImageResource(Integer.parseInt(model.content));
    }

}
