package com.hzw.supertextview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * author:HZWei
 * date: 2020/9/7
 * desc:
 */
@SuppressLint("AppCompatCustomView")
public class SuperTextView extends TextView {


    private SuperTextViewHelper mTextViewHelper;


    public SuperTextView(Context context) {
        this(context, null);
    }

    public SuperTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTextViewHelper = new SuperTextViewHelper(this, attrs);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTextViewHelper.updateIcon(this);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mTextViewHelper.draw(canvas);
    }


    public GradientDrawable getBackgroundDrawable() {
        return mTextViewHelper.getBackgroundDrawable();
    }

    public void setBackgroundDrawable(GradientDrawable bgDrawable) {
        mTextViewHelper.setBackgroundDrawable(bgDrawable);
        setBackground(bgDrawable);
    }

    public SuperTextViewHelper getTextViewHelper(){
        return mTextViewHelper;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mTextViewHelper=null;
    }
}
