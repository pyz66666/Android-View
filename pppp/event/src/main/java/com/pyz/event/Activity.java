package com.pyz.event;

public class Activity {

    public static void main(String[] args) {
        ViewGroup viewGroup = new ViewGroup(0,0,1080,1920);
        MotionEvent motionEvent = new MotionEvent(100,100);
        motionEvent.setActionMasked(MotionEvent.ACTION_DOWN);
    }
}
