package com.example.exp12;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ColorWheelView extends View {

    public interface OnColorChangeListener {
        void onColorChange(float hue, float saturation);
    }

    private Paint paint;
    private float radius;
    private OnColorChangeListener listener;

    public ColorWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Shader shader = new SweepGradient(0, 0,
                new int[] { 0xFFFF0000, 0xFFFFFF00, 0xFF00FF00, 0xFF00FFFF,
                        0xFF0000FF, 0xFFFF00FF, 0xFFFF0000 },
                null);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(shader);
    }

    public void setOnColorChangeListener(OnColorChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float cx = getWidth() / 2f;
        float cy = getHeight() / 2f;
        radius = Math.min(cx, cy) - 10;
        canvas.translate(cx, cy);
        canvas.drawCircle(0, 0, radius, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX() - getWidth() / 2f;
        float y = event.getY() - getHeight() / 2f;
        float r = (float) Math.sqrt(x * x + y * y);
        float angle = (float) Math.toDegrees(Math.atan2(y, x));
        if (angle < 0) angle += 360;

        if (r <= radius && listener != null) {
            float hue = angle;
            float saturation = Math.min(1f, r / radius);
            listener.onColorChange(hue, saturation);
            return true;
        }
        return false;
    }
}