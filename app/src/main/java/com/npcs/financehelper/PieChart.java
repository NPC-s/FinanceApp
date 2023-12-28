package com.npcs.financehelper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.compose.material3.MaterialTheme;

import com.google.android.material.color.DynamicColors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(30f);
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
            canvas.drawArc(paint.getStrokeWidth(), paint.getStrokeWidth(), getWidth()-paint.getStrokeWidth(), getHeight() - paint.getStrokeWidth(), startAngle, sweepAngle, false, paint);
        }

        paint.setStyle(Paint.Style.FILL);

        curAngle = - 90;

        for (int i = 0; i < labels.length; i++) {
            float angle = values[i] / maxValue * 180 + curAngle;
            curAngle += values[i] / maxValue * 360;
            float x = (float) ((Math.cos(Math.toRadians(angle)) * getWidth() / 4) + getWidth() / 2 - labels[i].length() * 20 / 2);
            float y = (float) ((Math.sin(Math.toRadians(angle)) * (getHeight() / 4)) + getHeight() / 2);

            paint.setColor(Color.GRAY);
            paint.setTextSize(35);
            paint.setTypeface(Typeface.SANS_SERIF);
            paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            canvas.drawText(labels[i], x, y, paint);
        }
    }

    public void setValues(float[] floats) {
        values = floats;
    }

    public void setLabels(String[] strings, FinanceDBHandler db) {
        List<String> cats = new ArrayList<String>(Arrays.asList(strings));
        if (cats.contains("Пополнения"))
            cats.remove("Пополнения");

        labels = cats.toArray(new String[0]);
        values = new float[labels.length];

        for (int i = 0; i < labels.length; i++)
            values[i] = db.getSum(labels[i]);

        maxValue = db.getSum();
    }

    public void setMaxValue(float value){
        maxValue = value;
    }

    public void Touch(float x, float y){
        float dx = getWidth() / 2 - x;
        float dy = getHeight() / 2 - y;
    }
}
