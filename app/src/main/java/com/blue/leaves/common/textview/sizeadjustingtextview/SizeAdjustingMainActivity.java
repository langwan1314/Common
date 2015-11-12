package com.blue.leaves.common.textview.sizeadjustingtextview;


import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.blue.leaves.common.R;
import com.blue.leaves.textview.sizeadjustingtextview.SizeAdjustingTextView;


public class SizeAdjustingMainActivity extends Activity {

    private EditText mMessageEditText;
    private SizeAdjustingTextView mTopMessageBox,
            mMiddleLeftBox, mMiddleRightBox, mBottomLeftBox,
            mBottomMiddleBox, mBottomRightBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size_adjust_main);

        mMessageEditText = (EditText)findViewById(R.id.text_input);
        mMessageEditText.setOnEditorActionListener(actionListener);
        mTopMessageBox = (SizeAdjustingTextView)findViewById(R.id.topBox);

        mMiddleLeftBox = (SizeAdjustingTextView)findViewById(R.id.middleLeftBox);
        mMiddleRightBox = (SizeAdjustingTextView)findViewById(R.id.middleRightBox);

        mBottomLeftBox = (SizeAdjustingTextView)findViewById(R.id.bottomLeftBox);
        mBottomMiddleBox = (SizeAdjustingTextView)findViewById(R.id.bottomMiddleBox);
        mBottomRightBox = (SizeAdjustingTextView)findViewById(R.id.bottomRightBox);
    }

    OnEditorActionListener actionListener = new OnEditorActionListener() {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            boolean handled = false;
            String incomingText = mMessageEditText.getText().toString();
            if(actionId==EditorInfo.IME_ACTION_SEND && !incomingText.isEmpty()){
                handled = true;
                moveText();
                clearEditText();
            }
            return handled;
        }
    };

    private void moveText() {

        TextMover.moveTextFromCellToCell(mBottomMiddleBox, mBottomRightBox);
        TextMover.moveTextFromCellToCell(mBottomLeftBox, mBottomMiddleBox);
        TextMover.moveTextFromCellToCell(mMiddleRightBox, mBottomLeftBox);
        TextMover.moveTextFromCellToCell(mMiddleLeftBox, mMiddleRightBox);
        TextMover.moveTextFromCellToCell(mTopMessageBox, mMiddleLeftBox);
        String incomingText = mMessageEditText.getText().toString();
        TextMover.moveNewTextIntoCell(mTopMessageBox, incomingText);

    }

    private void clearEditText(){
        mMessageEditText.setText("");
    }

}