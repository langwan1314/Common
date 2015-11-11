package com.blue.leaves.common.adapter;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.blue.leaves.common.R;

public class AdapterMainActivity extends Activity {

    private ListView mListView = null;
    private String[] mListString = {
            "common",
            "drag",
            "recyclerbindableadapter"
    };
    private Class[] mListClass = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.lv_ll);
        mListView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.activity_main_listview_item,
                mListString));
        mListView
                .setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(
                            AdapterView<?> parent,
                            View view, int position, long id) {
                        Intent intent = new Intent();
                        intent.setClass(AdapterMainActivity.this,
                                mListClass[position]);
                        AdapterMainActivity.this
                                .startActivity(intent);
                    }
                });
    }

}
