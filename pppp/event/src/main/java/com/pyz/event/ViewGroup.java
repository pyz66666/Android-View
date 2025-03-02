package com.pyz.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewGroup extends View {

    List<View> childList = new ArrayList<>();
    private View[] mChildren = new View[0];//数组的查找比list要快，事件的event是非常的频繁
    private TouchTarget mFirstTouchTarget;//只给actiondown

    public ViewGroup(int i, int i1, int i2, int i3) {
        super(i,i1,i2,i3);
    }

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
        TouchTarget touchTarget = null;
        boolean handled = false;
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
                         handled = true;
                        touchTarget = addTouchTarget(child);
                        break;
                     }
                 }
             }
             if (mFirstTouchTarget == null){
                 //没有人接收当前事件，调用自己
                handled =  dispatchTransformTouchEvent(ev, null);

             }
         }
         return handled;

    }

    private TouchTarget addTouchTarget(View child) {
        final  TouchTarget touchTarget = TouchTarget.obtain(child);
        touchTarget.next = mFirstTouchTarget;
        mFirstTouchTarget = touchTarget;
        return touchTarget;
    }

    //分发处理
    private boolean dispatchTransformTouchEvent(MotionEvent ev, View child) {
        final boolean handled;
        //如果当前的View消费了（设置了点击事件onTouch或者onClick）
        if (child != null){
            handled = child.dispatchTouchEvent(ev);
        }else {
            handled = super.dispatchTouchEvent(ev);
        }
        return handled;
    }

    //事件拦截
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return false;
    }

    /**
     * 用链表来实现回收池
     */
    private static final class TouchTarget{
        public View child;
        //回收池
        public static TouchTarget sRecyclerBin;
        public TouchTarget next;
        public static int sRecycledCount;
        private static final Object sRecyclerLock = new Object();
        public static TouchTarget obtain(View child){
            TouchTarget target;
            synchronized (sRecyclerLock){
                if (sRecyclerBin == null){
                    target = new TouchTarget();
                }else {
                    target = sRecyclerBin;
                }
                sRecyclerBin = target.next;
                sRecycledCount --;
                target.next = null;
            }
            target.child = child;
            return target;
        }

        public void recycle(){
            synchronized (sRecyclerLock){
                if (sRecycledCount < 32){
                    next = sRecyclerBin;
                    sRecyclerBin = this;
                    sRecycledCount = 1;
                }
            }
        }


    }
}
