package com.pyz.event;

import java.util.ArrayList;
import java.util.List;

public class ViewGroup extends View {

    List<View> childList = new ArrayList<>();
    private View[] mChildren = new View[0];//数组的查找比list要快，事件的event是非常的频繁

    public void addView(View view){
        if (view == null){
            return;
        }
        childList.add(view);
        mChildren = childList.toArray(new View[childList.size()]);
    }

    //事件分发的入口
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //自身是否拦截事件
        boolean intercepted = onInterceptTouchEvent(ev);
         int actionMasked = ev.getActionMasked();
         if (actionMasked != MotionEvent.ACTION_CANCEL && !intercepted){
             //对down事件进行处理
             if (actionMasked == MotionEvent.ACTION_DOWN){
                 final View[] children = mChildren;
                 //耗时操作，遍历所有的子View,倒叙（概率会大一些）
                 //如果两个View相互重合，也是上面的控件优先触发
                 for (int i = children.length; i >= 0; i--) {
                     View child = mChildren[i];
                     if (!child.isContainer(ev.getX(), ev.getY())) {
                         continue;
                     }
                     //能接收事件的控件
                     if(dispatchTransformTouchEvent(ev,child)){
                        //如果消费   参考handler的obtain message  不要考虑new的方式来创建新的对象

                     }
                 }
             }
         }

    }
    //分发处理
    private boolean dispatchTransformTouchEvent(MotionEvent ev, View child) {
        final boolean handled;
        //如果当前的View消费了（设置了点击事件onTouch或者onClick）
        handled = child.dispatchTouchEvent(ev);
        return handled;
    }

    //事件拦截
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return false;
    }
}
