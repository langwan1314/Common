package com.blue.leaves.common;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.blue.leaves.common.adapter.AdapterMainActivity;
import com.blue.leaves.common.textview.TextViewMainActivity;

public class MainActivity extends Activity {

    private ListView mListView = null;
    private String[] mListString = {
            "adapter", "textView"
    };
    private Class[] mListClass = {AdapterMainActivity.class, TextViewMainActivity.class};

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
                        intent.setClass(MainActivity.this,
                                mListClass[position]);
                        MainActivity.this
                                .startActivity(intent);
                    }
                });
    }

}
