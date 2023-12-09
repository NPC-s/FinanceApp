package com.npcs.financehelper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class PieChart extends View {

    private Paint paint;
    private float[] values;
    private float maxValue;
    private String[] labels;
    private int[] colors;
    private ArrayList<PieEntry> entries;

    public PieChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        colors = new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};

        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        entries = new ArrayList<>();

        float curAngle = - 90;

        for (int i = 0; i < values.length; i++) {
            float startAngle = curAngle;
            float sweepAngle = (values[i] / maxValue) * 360;
            curAngle += sweepAngle;

            PieEntry entry = new PieEntry(values[i], labels[i]);
            entry.SetAngles(startAngle, sweepAngle);
            entries.add(entry);

            paint.setColor(colors[i]);
            canvas.drawArc(0, 0, getWidth(), getHeight(), startAngle, sweepAngle, true, paint);
        }

        paint.setColor(Color.BLACK);

        canvas.drawCircle( (getHeight()/2), getHeight()/2, (float) (getHeight() * 0.9 * 0.5), paint);

        curAngle = - 90;

        for (int i = 0; i < labels.length; i++) {
            float angle = values[i] / maxValue * 180 + curAngle;
            curAngle += values[i] / maxValue * 360;
            float x = (float) ((Math.cos(Math.toRadians(angle)) * getWidth() / 4) + getWidth() / 2 - labels[i].length() * 20 / 2);
            float y = (float) ((Math.sin(Math.toRadians(angle)) * (getHeight() / 4)) + getHeight() / 2);

            paint.setColor(Color.WHITE);
            paint.setTextSize(35);
            paint.setTypeface(Typeface.SANS_SERIF);
            paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            canvas.drawText(labels[i], x, y, paint);
        }
    }

    public void setValues(float[] floats) {
        values = floats;
    }

    public void setLabels(String[] strings) {
        labels = strings;
    }

    public void setMaxValue(float value){
        maxValue = value;
    }

    public void Touch(float x, float y){
        float dx = getWidth() / 2 - x;
        float dy = getHeight() / 2 - y;
    }
}
