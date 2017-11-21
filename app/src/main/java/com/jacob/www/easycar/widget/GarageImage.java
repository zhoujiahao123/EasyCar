package com.jacob.www.easycar.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.jacob.www.easycar.R;

/**
 * @author 张兴锐
 * @date 2017/11/20
 */

public class GarageImage extends View {

    private Paint mPaint;
    private int wordColor;
    private int fillColor;
    private int radius = 20;
    private String text;
    private int textSize;

    public int getWordColor() {
        return wordColor;
    }

    public int getFillColor() {
        return fillColor;
    }

  

    public int getTextSize() {
        return textSize;
    }

    public void setWordColor(int wordColor) {
        this.wordColor = wordColor;
        invalidate();
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
        invalidate();
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        invalidate();
    }

    public GarageImage(Context context) {
        this(context, null);
    }

    public GarageImage(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GarageImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.GarageImage);
        wordColor = ta.getColor(R.styleable.GarageImage_word_color, Color.WHITE);
        fillColor = ta.getColor(R.styleable.GarageImage_fill_color, Color.BLACK);
        text = ta.getString(R.styleable.GarageImage_text);
        textSize = ta.getDimensionPixelSize(R.styleable.GarageImage_textSize, 10);
        ta.recycle();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(fillColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        Log.i(this.getClass().getSimpleName(),width+" "+height);
        radius = Math.min(width, height) / 2;
        //先绘制底层
        mPaint.setColor(fillColor);
        canvas.drawCircle(width / 2, height / 2, radius, mPaint);
        //再绘制文字
        mPaint.setColor(wordColor);
        mPaint.setTextSize(textSize);
        //x轴居中
        float stringWidth = mPaint.measureText(text);
        float x = (width - stringWidth) / 2;
        //y轴居中
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float y = height / 2 + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;
        canvas.drawText(text, x, y, mPaint);
    }
}
