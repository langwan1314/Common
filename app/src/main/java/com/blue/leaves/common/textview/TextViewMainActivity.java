package com.blue.leaves.common.textview;


import android.os.Bundle;

import com.blue.leaves.common.BaseMainActivity;
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

public class TextViewMainActivity extends BaseMainActivity {

    private ExpandableLayoutListView mListView = null;
    String[] data1 = {"bubbleview", "chatmessageview", "TagView", "trianglerectanglelabelview", "autofittextview", "AutosizeEditText",
            "BabushkaText", "BubbleTextView", "ExpandableTextView", "floatlabelededittext", "JumpingBeans", "material-code-input",
            "ParkedTextView", "richeditor", "Shimmer", "SizeAdjustingTextView", "TextDrawable", "Titanic", "TokenAutoComplete", "111111",
            "222222", "333333333",
            "44444444444"};
    String[
            ] data2 =

            {
                    "一、BubbleView：<br><br>https://github.com/lguipeng/BubbleView<br><br>" +
                            "定义了BubbleTextVew、BubbleImageView、BubbleLinearLayout可以实现各种Bubble View",
                    "二、ChatMessageView：<br><br> https://github.com/himanshu-soni/ChatMessageView<br><br>" +
                            "定义了一个聊天的消息View ChatMessageView," +
                            "可以通过cmv_arrowGravity、cmv_arrowMargin、cmv_showArrow、cmv_backgroundColor等设置它的相关属性," +
                            "支持按下效果等", "三、TagView：<br><br>https://github.com/Cutta/TagView<br><br>" +
                    "一个TagView布局，搜索相应的字母时会自动显示该字母相关的TagView,点击可以删除", "四、TriangleRectangleLabelView：<br><br>https://github" +
                    ".com/Frank-Zhu/TriangleRectangleLabelView<br><br>" +
                    "带三角形的标签，可以用来描述一个时间轴", "五、android-autofittextview：<br><br>https://github" +
                    ".com/grantland/android-autofittextview<br><br>" +
                    "字体大小自动随内容输入自动改变的textView", "六、AutosizeEditText：<br><br>https://github.com/txusballesteros/AutosizeEditText<br><br>" +
                    "字体大小自动随内容输入自动改变的textView", "七、BabushkaText：<br><br>https://github.com/quiqueqs/BabushkaText<br><br>" +
                    "一个自定义的TextView，可以设置里面Text显示各种样式", "八、BubbleTextView：<br><br>https://github.com/dupengtao/BubbleTextView<br><br>" +
                    "各种方向带箭头气泡风格的textView", "九、ExpandableTextView：<br><br>https://github.com/Manabu-GT/ExpandableTextView<br><br>" +
                    "可扩展的textView", "十、floatlabelededittext：<br><br>https://github.com/wrapp/floatlabelededittext<br><br>" +
                    "一个可浮动的textView,一般用于登录等时候的提示，类似于materia里面的新的一个textView风格。", "十一、JumpingBeans：<br><br>https://github" +
                    ".com/frakbot/JumpingBeans<br><br>" + "字迹跳动风格。", "十二、material-code-input：<br><br>https://github" +
                    ".com/glomadrian/material-code-input<br><br>" + "material 输入风格，适合用来输入密码什么的。",
                    "十三、ParkedTextView：<br><br>https://github" +
                            ".com/gotokatsuya/ParkedTextView<br><br>" + "在输入框的末尾带一些固定文字。",
                    "十四、richeditor-android-master：<br><br>https://github" +
                            ".com/wasabeef/richeditor-android<br><br>" + "富文本，可以设置输入文字的颜色，大小等，可以输入图片。",
                    "十五、Shimmer：<br><br>https://github" +
                            ".com/RomainPiel/Shimmer-android<br><br>" + "像Android开机动画一样闪烁的字迹。",
                    "十六、SizeAdjustingTextView：<br><br>https://github.com/erchenger/SizeAdjustingTextView<br><br>" +
                            "字迹会随着输入大小改变,后面的灰覆盖前面的。", "十七、TextDrawable：<br><br>https://github.com/amulyakhare/TextDrawable<br><br>" +
                    "用一个各种形状如圆圈等包含文字，文字及圆圈颜色可以设置。", "十八、Titanic：<br><br>https://github.com/RomainPiel/Titanic<br><br>" +
                    "文字加载水波纹效果。", "十九、TokenAutoComplete：<br><br>https://github.com/edmodo/TokenAutoComplete" + "自动完成Text输入。", "", "", "",
                    "", ""
            };
    private Class[] mListClass = {TextViewBubbleMainActivity.class, ChatMainActivity.class,
            TagViewMainActivity.class, TriangleRectangleMainActivity.class, AutoFitActivity.class, AutoScaleMainActivity.class,
            BabushkatextMainActivity.class, BubbleTextViewMainActivity.class, ExpandableTextviewDemoActivity.class,
            FloatableEdittextMainActivity.class, JumpingBeanMainActivity.class, CodeInputMainActivity.class, ParkedMainActivity.class,
            RichEditorMainActivity.class, ShimmerMainActivity.class, SizeAdjustingMainActivity.class, TextDrawableMainActivity.class,
            TitanicMainActivity.class, TokenActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter.setData(data1, data2, mListClass);
    }


}
