package com.codestringz.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.FrameLayout;

public class RoundCornerLayout extends FrameLayout
{
    private float CORNER_RADIUS = 50.f;
    private float mCornerRadius;
    private Paint mPaint;
    private Paint mMaskPaint;
    private DisplayMetrics mMetrics;

    public RoundCornerLayout(@NonNull Context context)
    {
        super(context);
        init(context);
    }

    public RoundCornerLayout(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs, 0);

        init(context);
    }

    public RoundCornerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context)
    {
        mMetrics = context.getResources().getDisplayMetrics();

        mCornerRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, CORNER_RADIUS, mMetrics);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mMaskPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        mMaskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        setWillNotDraw(false);
    }

    @Override
    public void draw(Canvas canvas)
    {
        if (isInEditMode())
        {
            /*
              If you don't use this if clause,
              Android Studio's layout preview window will not showing anything!!!
             */
            super.draw(canvas);
        }

        Bitmap offscreenBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);

        Canvas offscreenCanvas = new Canvas(offscreenBitmap);

        super.draw(offscreenCanvas);

        Bitmap maskBitmap = createMask(getWidth(), getHeight());
        if (maskBitmap != null)
        {
            offscreenCanvas.drawBitmap(maskBitmap, 0f, 0f, mMaskPaint);
            canvas.drawBitmap(offscreenBitmap, 0f, 0f, mPaint);
        }

    }

    private Bitmap createMask(int width, int height)
    {
        Bitmap mask = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);
        Canvas tempCanvas = new Canvas(mask);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);

        tempCanvas.drawRect(0, 0, width, height, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        tempCanvas.drawRoundRect(new RectF(0, 0, width, height), mCornerRadius, mCornerRadius, paint);
        return mask;
    }

    public void setCornerRadius(float myCornerRadius)
    {
        CORNER_RADIUS = myCornerRadius;
        mCornerRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, CORNER_RADIUS, mMetrics);
        this.invalidate();
    }
}
