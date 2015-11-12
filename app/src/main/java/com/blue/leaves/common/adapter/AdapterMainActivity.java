package com.blue.leaves.common.adapter;


import android.os.Bundle;


import com.blue.leaves.common.BaseMainActivity;
import com.blue.leaves.common.adapter.common.CommonAdapterMainActivity;
import com.blue.leaves.common.adapter.drag.DragExampleActivity;
import com.blue.leaves.common.adapter.recyclerbindableadapter.RecyclerBinderMainActivity;
import com.blue.leaves.widget.group.expandablelayout.ExpandableLayoutListView;

public class AdapterMainActivity extends BaseMainActivity {

    private ExpandableLayoutListView mListView = null;
    String[] data1 = {"CommonAdapter", "DragSortAdapter", "RecyclerBindableAdapter"};
    String[] data2 = {"一、BubbleView：<br><br>https://github.com/tianzhijiexian/CommonAdapter<br><br>" +
            "1、包含两个通用的Adapter:<br>" +
            "a、CommonAdapter 一般listVew<br>" +
            "b、CommonRcvAdapter recyclerView的adapter<br>" +
            "2、都支持不同类型的item,两者都是通过调用((CommonAdapter<DemoModel>)listView.getAdapter()).updateData或者((CommonRcvAdapter<DemoModel>)" +
            "recyclerView.getAdapter()).updateData更新adapter数据，其中DemoModel为adapter的源数据类型。<br>" +
            "重写commonAdapter的getItemViewType来获取类型，通过getItemView来根据类型获取相应的view<br>" +
            "3、需要显示的item继承AdapterItem<DemoModel>，里面实现该类型的一些特殊看需求，如数据设置，监听click事件等<br>" +
            "4、在CommonAdapter的getView中分别调用getLayoutResId获取item的布局，findViews找到各个需要的控件，setViews设置各个item的控件<br>" +
            "5、继承自CommonAdapter，并实现里面的getItemViewType获取item的类型，getItemView获取类型对应的item  View",
            "二、DragSortAdapter：<br><br> https://github.com/vinc3m1/DragSortAdapter<br><br>" +
                    "一个实现了拖动功能的adapter ,针对RecyclerView<br>" +
                    "1、RecyclerView内置的布局管理器：<br>" +
                    "LinearLayoutManager 显示在垂直或水平滚动列表项。<br>" +
                    "GridLayoutManager 显示在网格中的项目。<br>" +
                    "StaggeredGridLayoutManager 显示了交错网格项目。<br>" +
                    "要创建自定义布局管理器，扩展RecyclerView.LayoutManager类<br>" +
                    "2、关于Drag(拖动)的一些操作处理," +
                    "支持按下效果等", "三、RecyclerBindableAdapter：<br><br>https://github.com/princeparadoxes/RecyclerBindableAdapter<br><br>" +
            "各种样式的recycleerView的adapter  使用了泛型<br>" +
            "The library contains five adapters:<br>" +
            "RecyclerBindableAdapter - basic abstract adapter;<br>" +
            "FilterBindableAdapter - abstract adapter extends RecyclerBindableAdapter, allows you to filter the items in the list;<br>" +
            "ParallaxBindableAdapter - abstract adapter extends RecyclerBindableAdapter, allows you to apply to the Parallax header and " +
            "footer;<br>" +
            "SimpleBindableAdapter and SimpleParallaxBindableA"};
    private Class[] mListClass = {CommonAdapterMainActivity.class, DragExampleActivity.class,
            RecyclerBinderMainActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter.setData(data1, data2, mListClass);
    }


}