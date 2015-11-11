package com.blue.leaves.common.textview;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.blue.leaves.common.R;
import com.blue.leaves.common.adapter.AdapterMainActivity;
import com.blue.leaves.widget.group.expandablelayout.ExpandableLayoutItem;
import com.blue.leaves.widget.group.expandablelayout.ExpandableLayoutListView;

public class TextViewMainActivity extends Activity {

    private ExpandableLayoutListView mListView = null;
    private String[] mListString = {
            "adapter"
    };
    private Class[] mListClass = {AdapterMainActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_textview);
        mListView = (ExpandableLayoutListView) findViewById(R.id.listview);
        mListView.setAdapter(new AddressAdapter(this));
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
        String[] data1 = {"bubbleview", "chatmessageview", "trianglerectanglelabelview"};
        String[] data2;
        int mSelectIndex = 0;

        public AddressAdapter(Activity context) {
            mActivity = context;
            mInflater = LayoutInflater.from(mActivity);
        }

        public void setData(String[] list) {
            data1 = list;
        }

        public void setSelectAddress(int index) {
            mSelectIndex = index;
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
            tv1.setText(data1[position]);
            TextView tv2 = (TextView) viewHolder.row.getHeaderLayout().findViewById(R.id.header_text);
            tv2.setText(data1[position]);
            return convertView;
        }
    }

    static class ViewHolder {
        public ExpandableLayoutItem row;
    }

}
