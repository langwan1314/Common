package com.blue.leaves.common.animation.splash;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.blue.leaves.common.R;
import com.blue.leaves.common.animation.splash.fade.Fade2MainActivity;
import com.blue.leaves.common.animation.splash.fade.FadeSplashScreenActivity;
import com.blue.leaves.common.animation.splash.splitActivity.Activity1;

/**
 * Splash主页面
 * 继承BaseActivity获取ActionBar
 *
 * @author duguang 博客地址:http://blog.csdn.net/duguang77
 */
public class SplashMainActivity extends BaseActivity implements OnItemClickListener {
    String[] splashName = {"Zaker风格", "镜头风格_由远至近", "硬币翻转风格", "3D翻转效果", "Viewpager引导风格", "中心打开式",
            "ShowTime2秒式", "ShowTime2秒式(第二种实现方法)"};
    private AnimationAdapter adapter;
    private ListView listView_anim_complex;

    @Override
    public void setView() {
        setContentView(R.layout.activity_anim_complex);

    }

    @Override
    public void initView() {
        listView_anim_complex = (ListView) findViewById(R.id.listView_anim_complex);
        adapter = new AnimationAdapter(this, splashName);

    }

    @Override
    public void setListener() {
        listView_anim_complex.setAdapter(adapter);
        listView_anim_complex.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        switch (position) {
            case 0:
                startIntent(ZakerActivity.class);
                break;
            case 1:
                startIntent(LensFocusActivity.class);
                break;
            case 2:
                startIntent(RotateActivity.class);
                break;
            case 3:
                startIntent(Rotate3DActivity.class);
                break;
            case 4:
                startIntent(ViewPagerActivity.class);
                break;
            case 5:
                startIntent(Activity1.class);
                break;
            case 6:
                startIntent(FadeSplashScreenActivity.class);
                break;
            case 7:
                startIntent(Fade2MainActivity.class);
                break;

            default:
                break;
        }

    }

    /**
     * 切换Activity
     *
     * @param class1
     */
    public void startIntent(Class class1) {
        Intent intent = new Intent(SplashMainActivity.this, class1);
        startActivity(intent);
    }

}
