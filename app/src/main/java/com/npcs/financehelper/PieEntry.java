package com.npcs.financehelper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;

public class PieEntry {
    float value;
    String category;
    private float startAngle;
    private float sweepAngle;

    public PieEntry(float value, String category){
        value = value;
        category = category;
    }

    public void SetAngles(float startAngle, float sweepAngle){
        startAngle = startAngle;
        sweepAngle = sweepAngle;
    }
}
