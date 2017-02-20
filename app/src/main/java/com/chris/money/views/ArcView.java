package com.chris.money.views;
/**
 * User: xiaoming
 * Date: 2017-02-18
 * Time: 12:09
 * 描述一下这个类吧
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by apple on 2017/2/18.
 */
public class ArcView extends View {
    private Paint mPaint;

    public ArcView(Context context) {
        super(context);
        init();
    }

    public ArcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        mPaint = new Paint();
        mPaint.setColor(0xFFFFFF);
        mPaint.setStrokeWidth(4);
    }

    @Override
    public void draw(Canvas canvas) {
        //画弧线
        mPaint.setStyle(Paint.Style.STROKE);
        RectF oval1 = new RectF(150, 20, 280, 80);
        canvas.drawArc(oval1, 90, 180, true, mPaint);
        super.draw(canvas);
    }
}
