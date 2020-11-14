package com.example.android2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ThermometerView extends View {

    private int thermometerColor = Color.BLACK;
    private int highColor = Color.RED;
    private int lowColor = Color.BLUE;
    private float value = 0.0f;

    private float width = 0f;
    private float height = 0f;

    float firstRadius;
    float centerXForFirstCircle;
    float centerYForFirstCircle;

    float paddingBetweenCenterAndSecondCircle;

    Paint firstCirclePaint, secondCirclePaint;
    Paint valueLinePaint;
    Paint thermometerPaint;
    RectF rectForSecondCircle = new RectF();
    RectF rectForHead = new RectF();

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
        value = checkValue(value);

        typedArray.recycle();
    }

    private void init() {
        firstCirclePaint = new Paint();
        firstCirclePaint.setColor((value > 0.0f) ? highColor : lowColor);
        firstCirclePaint.setStyle(Paint.Style.FILL);

        secondCirclePaint = new Paint();
        secondCirclePaint.setColor(thermometerColor);
        secondCirclePaint.setStyle(Paint.Style.STROKE);
        secondCirclePaint.setStrokeCap(Paint.Cap.ROUND);

        valueLinePaint = new Paint();
        valueLinePaint.setColor((value > 0.0f) ? highColor : lowColor);
        valueLinePaint.setStrokeCap(Paint.Cap.ROUND);

        thermometerPaint = new Paint();
        thermometerPaint.setColor(thermometerColor);
        thermometerPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w - getPaddingStart() - getPaddingEnd();
        height = h - getPaddingTop() - getPaddingBottom();

        centerXForFirstCircle = width / 2.0f;
        centerYForFirstCircle = 4 * height / 5.0f;
        firstRadius = width / 8f;
        paddingBetweenCenterAndSecondCircle = 2.9f * width / 10;

        valueLinePaint.setStrokeWidth(firstRadius);
        secondCirclePaint.setStrokeWidth(firstRadius / 3);

        rectForSecondCircle.set(
                centerXForFirstCircle - paddingBetweenCenterAndSecondCircle,
                centerYForFirstCircle - paddingBetweenCenterAndSecondCircle,
                centerXForFirstCircle + paddingBetweenCenterAndSecondCircle,
                centerYForFirstCircle + paddingBetweenCenterAndSecondCircle);

        rectForHead.set(
                centerXForFirstCircle - paddingBetweenCenterAndSecondCircle * 0.707f,
                centerYForFirstCircle - height / 1.6f - paddingBetweenCenterAndSecondCircle * 0.707f,
                centerXForFirstCircle + paddingBetweenCenterAndSecondCircle * 0.707f,
                centerYForFirstCircle - height / 1.6f + paddingBetweenCenterAndSecondCircle * 0.707f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawCircle(
                centerXForFirstCircle, centerYForFirstCircle,
                firstRadius,
                firstCirclePaint);
        canvas.drawLine(
                centerXForFirstCircle, centerYForFirstCircle,
                centerXForFirstCircle, centerYForFirstCircle - (height / 1.6f * (value + 50) / 100),
                valueLinePaint);
        canvas.drawArc(rectForSecondCircle, 315f, 270, false, secondCirclePaint);
        canvas.drawLine(
                centerXForFirstCircle - paddingBetweenCenterAndSecondCircle * 0.707f,
                centerYForFirstCircle - paddingBetweenCenterAndSecondCircle * 0.707f,
                centerXForFirstCircle - paddingBetweenCenterAndSecondCircle * 0.707f,
                centerYForFirstCircle - height / 1.6f,
                secondCirclePaint);
        canvas.drawLine(
                centerXForFirstCircle + paddingBetweenCenterAndSecondCircle * 0.707f,
                centerYForFirstCircle - paddingBetweenCenterAndSecondCircle * 0.707f,
                centerXForFirstCircle + paddingBetweenCenterAndSecondCircle * 0.707f,
                centerYForFirstCircle - height / 1.6f,
                secondCirclePaint);

        canvas.drawArc(rectForHead, 180f, 180f, false, secondCirclePaint);

        canvas.drawLine(
                centerXForFirstCircle + paddingBetweenCenterAndSecondCircle,
                centerYForFirstCircle - (height / 1.6f),
                centerXForFirstCircle + paddingBetweenCenterAndSecondCircle * 1.4f,
                centerYForFirstCircle - (height / 1.6f),
                secondCirclePaint);

        canvas.drawLine(
                centerXForFirstCircle + paddingBetweenCenterAndSecondCircle,
                centerYForFirstCircle - (height / 1.6f * 0.5f),
                centerXForFirstCircle + paddingBetweenCenterAndSecondCircle * 1.4f,
                centerYForFirstCircle - (height / 1.6f * 0.5f),
                secondCirclePaint);

        canvas.drawLine(
                centerXForFirstCircle + paddingBetweenCenterAndSecondCircle,
                centerYForFirstCircle - (height / 1.6f * 0.75f),
                centerXForFirstCircle + paddingBetweenCenterAndSecondCircle * 1.3f,
                centerYForFirstCircle - (height / 1.6f * 0.75f),
                secondCirclePaint);

        canvas.drawLine(
                centerXForFirstCircle + paddingBetweenCenterAndSecondCircle,
                centerYForFirstCircle - (height / 1.6f * 0.25f),
                centerXForFirstCircle + paddingBetweenCenterAndSecondCircle * 1.3f,
                centerYForFirstCircle - (height / 1.6f * 0.25f),
                secondCirclePaint);

        canvas.drawLine(
                centerXForFirstCircle + paddingBetweenCenterAndSecondCircle,
                centerYForFirstCircle - (height / 1.6f * 0.375f),
                centerXForFirstCircle + paddingBetweenCenterAndSecondCircle * 1.2f,
                centerYForFirstCircle - (height / 1.6f * 0.375f),
                secondCirclePaint);

        canvas.drawLine(
                centerXForFirstCircle + paddingBetweenCenterAndSecondCircle,
                centerYForFirstCircle - (height / 1.6f * 0.625f),
                centerXForFirstCircle + paddingBetweenCenterAndSecondCircle * 1.2f,
                centerYForFirstCircle - (height / 1.6f * 0.625f),
                secondCirclePaint);

        canvas.drawLine(
                centerXForFirstCircle + paddingBetweenCenterAndSecondCircle,
                centerYForFirstCircle - (height / 1.6f * 0.875f),
                centerXForFirstCircle + paddingBetweenCenterAndSecondCircle * 1.2f,
                centerYForFirstCircle - (height / 1.6f * 0.875f),
                secondCirclePaint);
    }

    public void setValue(float value) {
        this.value = checkValue(value);
        invalidate();
    }

    private float checkValue(float value) {
        if (value < -50) return  -50;
        if (value > 50) return 50;
        return value;
    }
}
