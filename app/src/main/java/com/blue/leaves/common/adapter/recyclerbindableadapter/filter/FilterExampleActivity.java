package com.blue.leaves.common.adapter.recyclerbindableadapter.filter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Filter;


import com.blue.leaves.common.R;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FilterExampleActivity extends AppCompatActivity implements TextWatcher,
        Filter.FilterListener {

    @Bind(R.id.filter_example_recycle)
    protected RecyclerView filterExampleRecycler;

    @Bind(R.id.filter_example_edit_text)
    protected EditText filterEditText;

    @Bind(R.id.filter_example_no_result)
    protected View noResultView;

    private FilterExampleAdapter filterExampleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_example);
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        filterExampleRecycler.setLayoutManager(layoutManager);
        filterExampleRecycler.setItemAnimator(new DefaultItemAnimator());

        filterExampleAdapter = new FilterExampleAdapter();
        filterExampleRecycler.setAdapter(filterExampleAdapter);

        filterEditText.addTextChangedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<String> list = Arrays.asList(getResources().getStringArray(R.array.names));
        filterExampleAdapter.clear();
        filterExampleAdapter.addAll(list);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        filterExampleAdapter.getFilter().filter(s, FilterExampleActivity.this);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void onFilterComplete(int count) {
        noResultView.setVisibility(count > 0 ? View.GONE : View.VISIBLE);
    }
}
