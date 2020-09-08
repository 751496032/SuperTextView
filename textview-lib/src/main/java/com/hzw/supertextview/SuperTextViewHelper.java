package com.hzw.supertextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.Px;


import static android.graphics.drawable.GradientDrawable.LINEAR_GRADIENT;

/**
 * author:HZWei
 * date:  2020/9/7
 * desc:
 */
public class SuperTextViewHelper {

    private Context mContext;
    private int mCanvasTranslationX;

    private int mDrawablePadding;
    private Drawable mIconLeft;
    private Drawable mIconRight;
    private Drawable mIconTop;
    private Drawable mIconBottom;
    private int mTextWidth;
    private int mTextViewWidth;
    private int mPaddingStart;
    private int mPaddingEnd;

    private boolean mIsOpenResetSwitch;
    private GradientDrawable mNormalBGDrawable;
    private GradientDrawable mDisableDrawable;
    private GradientDrawable mPressedDrawable;
    private  int mStrokeColor;
    private  int mStrokeWidth;


    public enum GradientOrientation {
        LEFT_RIGHT(0),
        RIGHT_LEFT(1),
        TOP_BOTTOM(2),
        BOTTOM_TOP(3);

        int value;

        GradientOrientation(int value) {
            this.value = value;
        }

        public static GradientOrientation getOrientation(int val) {
            for (GradientOrientation orientation : values()) {
                if (orientation.value == val) return orientation;
            }
            return GradientOrientation.LEFT_RIGHT;
        }
    }

    public SuperTextViewHelper(TextView view, AttributeSet attrs) {
        mContext = view.getContext();
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.SuperTextView);
        mIsOpenResetSwitch = typedArray.getBoolean(R.styleable.SuperTextView_open_reset_switch, true);
        int gradientOrientation = typedArray.getInt(R.styleable.SuperTextView_hzw_bg_gradient_orientation, 0);
        int startColor = typedArray.getColor(R.styleable.SuperTextView_hzw_bg_gradient_start_color, 0);
        int centerColor = typedArray.getColor(R.styleable.SuperTextView_hzw_bg_gradient_center_color, 0);
        int endColor = typedArray.getColor(R.styleable.SuperTextView_hzw_bg_gradient_end_color, 0);
        int normalColor = typedArray.getColor(R.styleable.SuperTextView_hzw_normal_fill_color, 0);
        int disableColor = typedArray.getColor(R.styleable.SuperTextView_hzw_disable_color, 0);
        int pressedColor = typedArray.getColor(R.styleable.SuperTextView_hzw_pressed_color, 0);
        mStrokeColor = typedArray.getColor(R.styleable.SuperTextView_hzw_stroke_color, 0);
        mStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_hzw_stroke_width, 0);
        int radius = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_hzw_radius, 0);
        int radiusLT = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_hzw_radius_left_top, 0);
        int radiusRT = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_hzw_radius_right_top, 0);
        int radiusRB = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_hzw_radius_right_bottom, 0);
        int radiusLB = typedArray.getDimensionPixelSize(R.styleable.SuperTextView_hzw_radius_left_bottom, 0);
        typedArray.recycle();

        int[] fillColors = centerColor != 0 ? new int[]{startColor, centerColor, endColor} : new int[]{startColor, endColor};
        float[] cornerRadius = {radiusLT, radiusLT, radiusRT, radiusRT, radiusRB, radiusRB, radiusLB, radiusLB};

        mNormalBGDrawable = new GradientDrawable();
        mNormalBGDrawable.setGradientType(LINEAR_GRADIENT);
        mNormalBGDrawable.setStroke(mStrokeWidth, mStrokeColor);
        setCornerRadius(mNormalBGDrawable, radius, cornerRadius);
        setFillColor(mNormalBGDrawable, normalColor, fillColors);

        switch (GradientOrientation.getOrientation(gradientOrientation)) {
            case LEFT_RIGHT:
                mNormalBGDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
                break;
            case RIGHT_LEFT:
                mNormalBGDrawable.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
                break;
            case TOP_BOTTOM:
                mNormalBGDrawable.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
                break;
            case BOTTOM_TOP:
                mNormalBGDrawable.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
                break;
        }
        createDisableDrawable(disableColor, radius, cornerRadius);
        createPressedDrawable(pressedColor, radius, cornerRadius);

        int pressed = android.R.attr.state_pressed;
        int enabled = android.R.attr.state_enabled;
        int focused = android.R.attr.state_focused;


        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{pressed}, mPressedDrawable); //按下状态
        stateListDrawable.addState(new int[]{enabled}, mNormalBGDrawable);//恢复正常状态
        stateListDrawable.addState(new int[]{-enabled}, mDisableDrawable);//禁止状态
        stateListDrawable.addState(new int[]{}, mNormalBGDrawable); //正常状态
        view.setBackground(stateListDrawable);


    }

    private void createDisableDrawable(int disableColor, int radius, float[] cornerRadius) {
        //禁止状态
        if (mDisableDrawable == null) {
            mDisableDrawable = new GradientDrawable();
        }
        setCornerRadius(mDisableDrawable, radius, cornerRadius);
        setFillColor(mDisableDrawable, disableColor, null);
    }

    private void createPressedDrawable(int pressedColor, int radius, float[] cornerRadius) {
        //手指按下的状态
        if (mPressedDrawable == null) {
            mPressedDrawable = new GradientDrawable();
        }
        setCornerRadius(mPressedDrawable, radius, cornerRadius);
        setFillColor(mPressedDrawable, pressedColor, null);
    }


    private void setFillColor(GradientDrawable drawable, int solidColor, int[] fillColors) {
        if (fillColors != null) drawable.setColors(fillColors);
        if (solidColor != 0) drawable.setColor(solidColor);
    }

    private void setCornerRadius(GradientDrawable drawable, int radius, float[] cornerRadius) {
        if (cornerRadius != null) drawable.setCornerRadii(cornerRadius);
        if (radius != 0) drawable.setCornerRadius(radius);
    }

    public void setCornerRadius(@Px int radius){
        if (mNormalBGDrawable!=null){
            mNormalBGDrawable.setCornerRadius(radius);
        }
        if (mPressedDrawable!=null){
            mPressedDrawable.setCornerRadius(radius);
        }
        if (mDisableDrawable!=null){
            mDisableDrawable.setCornerRadius(radius);

        }
    }

    /**
     *
     * @param ltRadius 左上圆角
     * @param rtRadius 右上圆角
     * @param rbRadius 右下圆角
     * @param lbRadius 左下圆角
     */
    public void setCornerRadius(int ltRadius,int rtRadius,int rbRadius,int lbRadius){
        float[]  radius={ltRadius,ltRadius,rtRadius,rtRadius,rbRadius,rbRadius,lbRadius,lbRadius};
        if (mNormalBGDrawable!=null){
            mNormalBGDrawable.setCornerRadii(radius);
        }
        if (mPressedDrawable!=null){
            mPressedDrawable.setCornerRadii(radius);
        }
        if (mDisableDrawable!=null){
            mDisableDrawable.setCornerRadii(radius);
        }
    }


    public void setFillColor(@ColorRes int color){
        mNormalBGDrawable.setColor(mContext.getResources().getColor(color));
    }

    public void setFillColor(int... colors){
        if (colors.length==1){
            mNormalBGDrawable.setColor(mContext.getResources().getColor(colors[0]));
        }
        if (colors.length==2 || colors.length==3 ){
            mNormalBGDrawable.setColors(colors);

        }
    }

    public void setStrokeWidth(int width){
        mStrokeWidth=dp2px(width);
        mNormalBGDrawable.setStroke(mStrokeWidth,mStrokeColor);
    }

    public void setStrokeColor(@ColorRes int color){
        mStrokeColor=mContext.getResources().getColor(color);
        mNormalBGDrawable.setStroke(mStrokeWidth,mStrokeColor);
    }

   protected void updateIcon(TextView view) {
        if (!mIsOpenResetSwitch) return;
        String text = view.getText().toString();
        if (TextUtils.isEmpty(text)) return;
        mCanvasTranslationX = 0;

        mDrawablePadding = view.getCompoundDrawablePadding();
        Drawable[] drawables = view.getCompoundDrawablesRelative();
        mIconLeft = drawables[0];
        mIconRight = drawables[2];
        mIconTop = drawables[1];
        mIconBottom = drawables[3];

        mTextWidth = (int) view.getPaint().measureText(text);
        mTextViewWidth = view.getMeasuredWidth();

        mPaddingStart = view.getPaddingStart();
        mPaddingEnd = view.getPaddingEnd();

        int gravity = view.getGravity();

        switch (gravity) {
            case Gravity.CENTER:
            case Gravity.CENTER_HORIZONTAL:
                //文本在中间
                setLeftAndRightIconRectBoundsForMiddle();
                break;
            case (Gravity.START | Gravity.CENTER_VERTICAL):
            case (Gravity.START | Gravity.TOP):
                //文本在左边
                setLeftAndRightIconRectBoundsForSides(true);
                setTopAndBottomIconRectBounds(true);
                break;
            case (Gravity.END | Gravity.CENTER_VERTICAL):
            case (Gravity.END | Gravity.TOP):
                //文本在右边
                setLeftAndRightIconRectBoundsForSides(false);
                setTopAndBottomIconRectBounds(false);
                break;

        }



    }


    private void setLeftAndRightIconRectBoundsForMiddle() {

        if (mIconLeft != null) {
            Rect bounds = mIconLeft.getBounds();
            int iconWidth = mIconLeft.getIntrinsicWidth();
            int distance = (mTextViewWidth - mTextWidth - iconWidth - mPaddingStart - mPaddingEnd) / 2 - mDrawablePadding;

            if (mIconRight != null) {
                distance -= iconWidth / 2;
                mCanvasTranslationX = 0;
            } else {
                mCanvasTranslationX = iconWidth;
            }
            bounds.set(bounds.left + distance, bounds.top, bounds.right + distance, bounds.bottom);
        }
        if (mIconRight != null) {
            Rect bounds = mIconRight.getBounds();
            int iconWidth = mIconRight.getIntrinsicWidth();
            int distance = (mTextViewWidth - mTextWidth - iconWidth - mPaddingStart - mPaddingEnd) / 2 - mDrawablePadding;

            if (mIconLeft != null) {
                distance -= iconWidth / 2;
                mCanvasTranslationX = 0;
            } else {
                mCanvasTranslationX = -iconWidth;
            }
            bounds.set(bounds.left - distance, bounds.top, bounds.right - distance, bounds.bottom);
        }

    }


    private void setLeftAndRightIconRectBoundsForSides(boolean isLeftPosition) {
        if (isLeftPosition) {
            if (mIconRight != null) {
                Rect bounds = mIconRight.getBounds();
                int iconWidth = mIconRight.getIntrinsicWidth();
                int iconLeftWidth = mIconLeft != null ? mIconLeft.getIntrinsicWidth() : 0;
                int distance = mTextViewWidth - iconLeftWidth - mTextWidth - iconWidth - mPaddingStart
                        - mPaddingEnd - (mIconLeft != null ? mDrawablePadding * 2 : mDrawablePadding);

                bounds.set(bounds.left - distance, bounds.top, bounds.right - distance, bounds.bottom);

            }
        } else {
            if (mIconLeft != null) {
                Rect bounds = mIconLeft.getBounds();
                int iconWidth = mIconLeft.getIntrinsicWidth();
                int iconRightWidth = mIconRight != null ? mIconRight.getIntrinsicWidth() : 0;
                int distance = mTextViewWidth - iconRightWidth - mTextWidth - iconWidth - mPaddingStart
                        - mPaddingEnd - (mIconRight != null ? mDrawablePadding * 2 : mDrawablePadding);

                bounds.set(bounds.left + distance, bounds.top, bounds.right + distance, bounds.bottom);
            }
        }
    }


    private void setTopAndBottomIconRectBounds(boolean isLeftPosition) {
        if (mIconTop != null || mIconBottom != null) {
            int iconLeftWidth = mIconLeft != null ? mIconLeft.getIntrinsicWidth() : 0;
            int iconRightWidth = mIconRight != null ? mIconRight.getIntrinsicHeight() : 0;
            int totalContentWidth = mPaddingStart + iconLeftWidth + mDrawablePadding + mTextWidth + mDrawablePadding + iconRightWidth + mPaddingEnd;
            int distance = (mTextViewWidth - totalContentWidth) / 2;
            if (isLeftPosition) distance = -distance;
            if (mIconTop != null) {
                Rect bounds = mIconTop.getBounds();
                bounds.set(bounds.left + distance, bounds.top, bounds.right + distance, bounds.bottom);
            }
            if (mIconBottom != null) {
                Rect bounds = mIconBottom.getBounds();
                bounds.set(bounds.left + distance, bounds.top, bounds.right + distance, bounds.bottom);
            }

        }
    }

    protected void draw(Canvas canvas) {
        if (mCanvasTranslationX != 0) {
            canvas.save();
            canvas.translate(mCanvasTranslationX, 0);
            canvas.restore();
        }
    }

    public GradientDrawable getBackgroundDrawable() {
        return mNormalBGDrawable;
    }


    public void setBackgroundDrawable(GradientDrawable drawable) {
        this.mNormalBGDrawable = drawable;
    }


    private int dp2px(int dp) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return (int) (density * dp + 0.5f);
    }
}
