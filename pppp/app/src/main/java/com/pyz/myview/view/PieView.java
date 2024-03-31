package com.pyz.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class PieView extends View {

    private Paint mPaint;
    private final int Radius  = 250;
    private String[] colorList = {"#9400D3","#4B0082","#4169E1","#1E90FF","#00FFFF"};
    private int[] radianList = {50,80,60,100,70};
    public PieView(Context context) {
        this(context,null);
    }
    public PieView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public PieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        int anagle = 0;
        for (int i = 0; i <5 ;i++){
            mPaint.setColor(Color.parseColor(colorList[i]));
            if(i == 3){
                canvas.save();
                //计算偏移量的时候要用三角函数计算
                canvas.translate((float) (30*Math.cos(Math.toRadians(anagle+radianList[i]/2))),(float) (30*Math.sin(Math.toRadians(anagle+radianList[i]/2))));
                canvas.drawArc(getWidth()/2-Radius, 50, getWidth()/2+Radius, Radius*2+50,
                        anagle, radianList[i],true, mPaint);
                anagle = anagle+radianList[i];
                canvas.restore();
            }else {
                canvas.drawArc(getWidth()/2-Radius, 50, getWidth()/2+Radius, Radius*2+50,
                        anagle, radianList[i],true, mPaint);
                anagle = anagle+radianList[i];
            }


        }
    }
}
