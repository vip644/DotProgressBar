package com.threedots.progress.dotprogress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class DotProgressBar extends View {

    private float radius;
    private Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Handler handler = new Handler();
    private int index = 0;
    private int widthSize, heightSize;
    private int margin = 4;
    private int dotCount = 3;

    public DotProgressBar(Context context) {
        super(context);
        init(context);
    }

    public DotProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DotProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        radius = context.getResources().getDimension(R.dimen.circle_indicator_radius);

        //dot fill color
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(Color.CYAN);

        //dot background color
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0x33000000);
        start();

    }

    public void setDotCount(int count){
        dotCount = count;
    }

    public void start(){
        index = -1;
        handler.removeCallbacks(runnable);
        handler.post(runnable);
    }

    public void stop(){
        handler.removeCallbacks(runnable);
    }

    private int step = 1;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            index += step;
            if(index<0){
                index = 1;
                step = 1;
            }
            else if (index > (dotCount-1)){
                if((dotCount-1)>=0){
                    index = dotCount-2;
                    step = -1;
                }else {
                    index = 0;
                    step=1;
                }
            }
            invalidate();
            handler.postDelayed(runnable, 300);

        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        widthSize = MeasureSpec.getSize(widthMeasureSpec);
        heightSize = (int)radius*2 + getPaddingBottom()+getPaddingTop();
        setMeasuredDimension(widthSize,heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float dX = (widthSize - dotCount * radius * 2 - (dotCount - 1) * margin) / 2.0f;
        float dY = heightSize / 2;
        for (int i = 0; i < dotCount; i++) {
            if (i == index) {
                canvas.drawCircle(dX, dY, radius, fillPaint);
            } else {
                canvas.drawCircle(dX, dY, radius, paint);
            }

            dX += (2 * radius + margin);
        }
    }
}