package com.blue.leaves.common;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.blue.leaves.common.adapter.AdapterMainActivity;
import com.blue.leaves.common.textview.TextViewMainActivity;
import com.blue.leaves.widget.group.expandablelayout.ExpandableLayoutItem;
import com.blue.leaves.widget.group.expandablelayout.ExpandableLayoutListView;

public class BaseMainActivity extends Activity {

    protected ExpandableLayoutListView mListView = null;
    protected AddressAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_textview);
        mListView = (ExpandableLayoutListView) findViewById(R.id.listview);
        mAdapter = new AddressAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView
                .setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(
                            AdapterView<?> parent,
                            View view, int position, long id) {
                    }
                });
    }

    public class AddressAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private Activity mActivity;
        private Class[] mListClass = null;
        String[] data1 = null;
        String[] data2 = null;
        int mSelectIndex = 0;

        public AddressAdapter(Activity context) {
            mActivity = context;
            mInflater = LayoutInflater.from(mActivity);
        }

        public void setData(String[] list1, String[] list2, Class[] c) {
            data1 = list1;
            data2 = list2;
            mListClass = c;
        }

        @Override
        public int getCount() {
            if (data1 != null)
                return data1.length;
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (data1 != null)
                return data1[position];
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            ViewHolder viewHolder = null;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.activity_second_main_listview_item,
                        null);

                viewHolder = new ViewHolder();
                viewHolder.row = (ExpandableLayoutItem) convertView
                        .findViewById(R.id.row);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //viewHolder.text.setText(mData[position]);
            TextView tv1 = (TextView) viewHolder.row.getContentLayout().findViewById(R.id.content_text);
            tv1.setText(Html.fromHtml(data2[position]));
            Button btn = (Button) viewHolder.row.getContentLayout().findViewById(R.id.btn_go);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(BaseMainActivity.this,
                            mListClass[position]);
                    BaseMainActivity.this
                            .startActivity(intent);
                }
            });
            TextView tv2 = (TextView) viewHolder.row.getHeaderLayout().findViewById(R.id.header_text);
            tv2.setText(data1[position]);
            return convertView;
        }
    }

    static class ViewHolder {
        public ExpandableLayoutItem row;
    }

}
