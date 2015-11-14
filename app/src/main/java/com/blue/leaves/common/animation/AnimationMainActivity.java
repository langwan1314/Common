package com.blue.leaves.common.animation;


import android.os.Bundle;

import com.blue.leaves.common.BaseMainActivity;
import com.blue.leaves.common.animation.base1.Base1AnimationActivity;
import com.blue.leaves.common.animation.base2.BaseAnimation2Activity;
import com.blue.leaves.common.animation.flip.demo.FlipMainActivity;
import com.blue.leaves.common.animation.listviewanimations.ListviewMainActivity;
import com.blue.leaves.common.animation.splash.SplashMainActivity;
import com.blue.leaves.common.animation.viewpager.ViewPagerMainActivity;
import com.blue.leaves.widget.group.expandablelayout.ExpandableLayoutListView;

public class AnimationMainActivity extends BaseMainActivity {

    private ExpandableLayoutListView mListView = null;
    String[] data1 = {"flip", "listviewanimations", "splash", "base1", "base2", "viewpager"};
    String[
            ] data2 =

            {
                    "一、flip：<br><br>https://github.com/openaphid/android-flip<br><br>" +
                            "Android 各种控件的翻书动画，通过FlipViewController控制要翻转的动画," +
                            "为FlipViewController设置一个adapter，通过adapter的getView设置要翻转的View," +
                            "FlipViewController继承于AdapterView，一个Adapter对象作为一个AdapterView和View底层数据之间的桥，提供对data " +
                            "Items的存取，同时负责针对每个Data如何渲染到对应的View中。(参考http://blog.csdn" +
                            ".net/lizzywu/article/details/17612789)" +
                            "，里面还设计一些OpenGL ES中的GLSurfaceView的使用，基本原理是通过在翻转时保存获取获取下一个显示view的截屏，并通过open设置纹理坐标进行上下屏的渲染" +
                            "(这个就是翻转过程中的动画)" +
                            "，翻转完成之后把相应item设置为可显示<br>," +
                            "orientation控制横向还是纵向滑动"
                    , "二、listview：<br><br>https://github.com/nhaarman/ListViewAnimations<br><br>" +
                    "为ListView等类型的View设置动画，主要是为其item。"
                    , "三、splash：<br><br><br><br>" +
                    "启动也动画。"
                    , "四、base1：<br><br><br><br>" +
                    "基础动画。"
                    , "五、base2：<br><br><br><br>" +
                    "基础动画，各种曲线动画。",
                    "五、viewpager：<br><br>https://github.com/TakeoffAndroid/AppIntroAnimation<br><br>" +
                            "ViewPager式颜色切换动画,也可以用作启动页动画。"
            };
    private Class[] mListClass = {FlipMainActivity.class, ListviewMainActivity.class, SplashMainActivity.class,
            Base1AnimationActivity
                    .class, BaseAnimation2Activity
            .class, ViewPagerMainActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter.setData(data1, data2, mListClass);
    }


}
