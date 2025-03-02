package com.pyz.event;

import com.pyz.event.listener.OnClickListener;
import com.pyz.event.listener.OnTouchListener;

public class View {

    private int left;
    private int top;
    private int right;
    private int bottom;

    private OnTouchListener mOnTouchListener;
    private OnClickListener mOnClickListener;

    public View() {

    }

    public View(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public OnTouchListener getmOnTouchListener() {
        return mOnTouchListener;
    }

    public void setOnTouchListener(OnTouchListener mOnTouchListener) {
        this.mOnTouchListener = mOnTouchListener;
    }

    public OnClickListener getmOnClickListener() {
        return mOnClickListener;
    }

    public void setOnClickListener(OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public boolean isContainer(int x,int y){
        return x >= left && x < right && y >= top && y < bottom;
    }

    public boolean dispatchTouchEvent(MotionEvent event){
        //分发给子控件处理
        //要不要去消费？
        boolean result = false;
        if (mOnTouchListener != null && mOnTouchListener.onTouch(this,event)){
            result = true;
        }
        //如果onTouchListener没有消费，则会走到onClick
        if (!result && onTouchEvent(event)){
            result = true;
        }
        return result;

    }

    private boolean onTouchEvent(MotionEvent event){
        if (mOnClickListener != null){
            mOnClickListener.onClick(this);
            return true;
        }
        return false;
    }
}