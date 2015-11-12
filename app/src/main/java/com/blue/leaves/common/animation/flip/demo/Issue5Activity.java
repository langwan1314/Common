package com.blue.leaves.common.animation.flip.demo;

import android.app.Activity;
import android.os.Bundle;

import com.blue.leaves.animation.flip.FlipViewController;
import com.blue.leaves.common.R;
import com.blue.leaves.common.animation.flip.demo.issue5.GalleryFlipAdapter;

public class Issue5Activity extends Activity {

  private FlipViewController flipView;

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setTitle(R.string.activity_title);

    flipView = new FlipViewController(this);

    flipView.setAdapter(new GalleryFlipAdapter(this, flipView));

    setContentView(flipView);
  }

  @Override
  protected void onResume() {
    super.onResume();
    flipView.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    flipView.onPause();
  }
}