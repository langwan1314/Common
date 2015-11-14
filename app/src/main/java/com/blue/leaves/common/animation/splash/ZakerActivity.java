package com.blue.leaves.common.animation.splash;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blue.leaves.common.R;


public class ZakerActivity extends BaseActivity implements OnClickListener {

    private Button btnBelow, btnAbove;
    private TextView tvHint;
    private int lastDownY = 0;

    @Override
    public void setView() {
        setContentView(R.layout.activity_splash_zaker);
    }

    @Override
    public void initView() {
        btnBelow = (Button) this.findViewById(R.id.btn_Below);
        btnAbove = (Button) this.findViewById(R.id.btn_above);
        tvHint = (TextView) this.findViewById(R.id.tv_hint);

        Animation ani = new AlphaAnimation(0f, 1f);
        ani.setDuration(1500);
        ani.setRepeatMode(Animation.REVERSE);
        ani.setRepeatCount(Animation.INFINITE);
        tvHint.startAnimation(ani);

    }

    @Override
    public void setListener() {
        btnBelow.setOnClickListener(this);
        btnAbove.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_Below) {
            Toast.makeText(ZakerActivity.this, "这是下面一层按钮", 1000).show();
        }
        if (v.getId() == R.id.btn_above) {
            Toast.makeText(ZakerActivity.this, "这是上面一层按钮", 1000).show();
        }

    }

}
