package com.pyz.event;

public class Activity {

    public static void main(String[] args) {
        MotionEvent motionEvent = new MotionEvent(100,100);
        motionEvent.setActionMasked(MotionEvent.ACTION_DOWN);
    }
}
