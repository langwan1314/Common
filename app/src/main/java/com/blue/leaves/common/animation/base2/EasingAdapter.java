package com.blue.leaves.common.animation.base2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blue.leaves.animation.easing.BaseEasingMethod;
import com.blue.leaves.animation.easing.Skill;
import com.blue.leaves.common.R;


public class EasingAdapter extends BaseAdapter {

    private Context mContext;
    public EasingAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return Skill.values().length;
    }

    @Override
    public Object getItem(int i) {
        return Skill.values()[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Object o = getItem(i);
        BaseEasingMethod b = ((Skill)o).getMethod(1000);
        int start = b.getClass().getName().lastIndexOf(".") + 1;
        String name = b.getClass().getName().substring(start);
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_animation2_base,null);
        TextView tv = (TextView)v.findViewById(R.id.list_item_text);
        tv.setText(name);
        v.setTag(o);
        return v;
    }
}
