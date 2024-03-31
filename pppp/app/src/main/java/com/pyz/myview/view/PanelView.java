package com.pyz.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.pyz.myview.Utils;

public class PanelView extends View {

    private Paint paint;
    private final int Radius  = 250;
    private Path scalePath , path;
    private float v;

    public PanelView(Context context) {
        this(context,null);
    }

    public PanelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PanelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public PanelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        paint = new Paint();
        path = new Path();
        paint.setColor(Color.parseColor("#00BFFF"));
        paint.setAntiAlias(true);
        paint.setStrokeWidth(6);
        paint.setStyle(Paint.Style.STROKE);
        scalePath = new Path();
        scalePath.addRect(0,0, Utils.dip2px(2),Utils.dip2px(10),Path.Direction.CCW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画弧形
        canvas.drawPath(path,paint);
        paint.setPathEffect(new PathDashPathEffect(scalePath,v,0,PathDashPathEffect.Style.ROTATE));
        //画刻度
        canvas.drawPath(path,paint);
        paint.setPathEffect(null);
        //这里线的计算是难点最好自己画个图来算
        canvas.drawLine(getWidth()/2, getHeight()/2, (float) (getWidth()/2+(Radius-60)*Math.cos(Math.toRadians(90+45+(360-90)/20f*5))),
                (float) (getHeight()/2+(Radius-60)*Math.sin(Math.toRadians(90+45+(360-90)/20f*5))), paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        path.reset();
        path.addArc(getMeasuredWidth()/2-Radius,getMeasuredHeight()/2-Radius,getMeasuredWidth()/2+Radius,getMeasuredHeight()/2+Radius
        ,135,270);
        PathMeasure pathMeasure = new PathMeasure(path,false);
        //刻度的计算
        v = ((pathMeasure.getLength() -Utils.dip2px(2))/20);
    }
}
