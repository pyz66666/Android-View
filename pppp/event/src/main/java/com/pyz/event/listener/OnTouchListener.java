package com.pyz.event.listener;

import com.pyz.event.MotionEvent;
import com.pyz.event.View;

public interface OnTouchListener {

    boolean onTouch(View view, MotionEvent event);
}
