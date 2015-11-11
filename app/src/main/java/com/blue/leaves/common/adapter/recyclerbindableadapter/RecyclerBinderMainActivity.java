package com.blue.leaves.common.adapter.recyclerbindableadapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.blue.leaves.common.R;
import com.blue.leaves.common.adapter.recyclerbindableadapter.filter.FilterExampleActivity;
import com.blue.leaves.common.adapter.recyclerbindableadapter.grid.GridExampleActivity;
import com.blue.leaves.common.adapter.recyclerbindableadapter.linear.LinearExampleActivity;
import com.blue.leaves.common.adapter.recyclerbindableadapter.parallax.ParallaxExampleActivity;
import com.blue.leaves.common.adapter.recyclerbindableadapter.simple.SimpleExampleActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecyclerBinderMainActivity extends AppCompatActivity {

    @OnClick(R.id.main_activity_linear_example_button)
    protected void onLinearButtonClick() {
        this.startActivity(new Intent(this, LinearExampleActivity.class));
    }

    @OnClick(R.id.main_activity_grid_example_button)
    protected void onGridButtonClick() {
        this.startActivity(new Intent(this, GridExampleActivity.class));
    }

    @OnClick(R.id.main_activity_simple_example_button)
    protected void onSimpleButtonClick() {
        this.startActivity(new Intent(this, SimpleExampleActivity.class));
    }

    @OnClick(R.id.main_activity_parallax_example_button)
    protected void onParallaxButtonClick() {
        this.startActivity(new Intent(this, ParallaxExampleActivity.class));
    }

    @OnClick(R.id.main_activity_filter_example_button)
    protected void onFilterButtonClick() {
        this.startActivity(new Intent(this, FilterExampleActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_recyclerbindableadapter);
        ButterKnife.bind(this);
    }
}
