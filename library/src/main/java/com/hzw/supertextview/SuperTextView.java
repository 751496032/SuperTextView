package com.hzw.supertextview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
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
        super(context);
        init(null);
    }

    public SuperTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SuperTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public SuperTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    private void init(@Nullable AttributeSet attrs) {
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
        if (mTextViewHelper!=null)
        mTextViewHelper.draw(canvas);
    }


    public GradientDrawable getBackgroundDrawable() {
        if (mTextViewHelper==null) return null;
        return mTextViewHelper.getBackgroundDrawable();
    }

    public void setBackgroundDrawable(GradientDrawable bgDrawable) {
        if (mTextViewHelper==null) return;
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
