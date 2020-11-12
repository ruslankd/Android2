package com.example.android2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ThermometerView extends View {

    private int thermometerColor = Color.BLACK;
    private int highColor = Color.RED;
    private int lowColor = Color.BLUE;
    private float value = 30.0f;

    private int width = 0;
    private int height = 0;

    Rect firstCircle = new Rect();
    Paint firstCirclePaint;
    Paint valueLinePaint;

    public ThermometerView(Context context) {
        super(context);
        init();
    }

    public ThermometerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
        init();
    }

    public ThermometerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        init();
    }

    public ThermometerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttr(context, attrs);
        init();
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray =
                context.obtainStyledAttributes(
                        attrs,
                        R.styleable.ThermometerView,
                        0,
                        0
                );

        thermometerColor =
                typedArray.getColor(
                        R.styleable.ThermometerView_thermometer_color,
                        Color.BLACK
                );

        highColor =
                typedArray.getColor(
                        R.styleable.ThermometerView_high_color,
                        Color.RED
                );

        lowColor =
                typedArray.getColor(
                        R.styleable.ThermometerView_low_color,
                        Color.BLUE
                );

        value =
                typedArray.getFloat(
                        R.styleable.ThermometerView_value,
                        0.0f
                );
        typedArray.recycle();
    }

    private void init() {
        firstCirclePaint = new Paint();
        firstCirclePaint.setColor((value > 0.0f) ? highColor : lowColor);
        firstCirclePaint.setStyle(Paint.Style.FILL);

        valueLinePaint = new Paint();
        valueLinePaint.setColor((value > 0.0f) ? highColor : lowColor);
        valueLinePaint.setStyle(Paint.Style.STROKE);
//        valueLinePaint.setStrokeWidth(10f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w - getPaddingStart() - getPaddingEnd();
        height = h - getPaddingTop() - getPaddingBottom();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(width / 2, 3 * height / 4, 60, firstCirclePaint);

        Path path = new Path();
        path.moveTo(width / 2, 3 * height / 4);
        path.lineTo(width / 2, 3 * height / 4 + 10 * value);

        canvas.drawPath(path, valueLinePaint);
    }
}
