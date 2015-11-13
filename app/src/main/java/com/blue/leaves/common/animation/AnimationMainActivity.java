package com.blue.leaves.common.animation;


import android.os.Bundle;

import com.blue.leaves.common.BaseMainActivity;
import com.blue.leaves.common.animation.flip.demo.FlipMainActivity;
import com.blue.leaves.common.textview.autofit.AutoFitActivity;
import com.blue.leaves.common.textview.autoscale.AutoScaleMainActivity;
import com.blue.leaves.common.textview.babushkatext.BabushkatextMainActivity;
import com.blue.leaves.common.textview.bubbletextview.BubbleTextViewMainActivity;
import com.blue.leaves.common.textview.bubbleview.TextViewBubbleMainActivity;
import com.blue.leaves.common.textview.chatmessageview.ChatMainActivity;
import com.blue.leaves.common.textview.codeinputlib.CodeInputMainActivity;
import com.blue.leaves.common.textview.expandabletextview.ExpandableTextviewDemoActivity;
import com.blue.leaves.common.textview.floatlabelededittext.FloatableEdittextMainActivity;
import com.blue.leaves.common.textview.jumpingbeans.JumpingBeanMainActivity;
import com.blue.leaves.common.textview.parkedtextview.ParkedMainActivity;
import com.blue.leaves.common.textview.richeditor.RichEditorMainActivity;
import com.blue.leaves.common.textview.shimmer.ShimmerMainActivity;
import com.blue.leaves.common.textview.sizeadjustingtextview.SizeAdjustingMainActivity;
import com.blue.leaves.common.textview.tagview.TagViewMainActivity;
import com.blue.leaves.common.textview.textdrawable.TextDrawableMainActivity;
import com.blue.leaves.common.textview.titanic.TitanicMainActivity;
import com.blue.leaves.common.textview.tokenautocomplete.TokenActivity;
import com.blue.leaves.common.textview.trianglerectanglelabelview.TriangleRectangleMainActivity;
import com.blue.leaves.widget.group.expandablelayout.ExpandableLayoutListView;

public class AnimationMainActivity extends BaseMainActivity {

    private ExpandableLayoutListView mListView = null;
    String[] data1 = {"flip", "111111",
            "222222", "333333333",
            "44444444444"};
    String[
            ] data2 =

            {
                    "一、flip：<br><br>https://github.com/openaphid/android-flip<br><br>" +
                            "Android 各种控件的翻书动画，通过FlipViewController控制要翻转的动画,为FlipViewController设置一个adapter，通过adapter的getView设置要翻转的View," +
                            "FlipViewController继承于AdapterView，一个Adapter对象作为一个AdapterView和View底层数据之间的桥，提供对data " +
                            "Items的存取，同时负责针对每个Data如何渲染到对应的View中。(参考http://blog.csdn.net/lizzywu/article/details/17612789)" +
                            "，里面还设计一些OpenGL ES中的GLSurfaceView的使用，基本原理是通过在翻转时保存获取获取下一个显示view的截屏，并通过open设置纹理坐标进行上下屏的渲染(这个就是翻转过程中的动画)" +
                            "，翻转完成之后把相应item设置为可显示<br>," +
                            "orientation控制横向还是纵向滑动"
            };
    private Class[] mListClass = {FlipMainActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter.setData(data1, data2, mListClass);
    }


}
