package com.npcs.financehelper;

import android.app.Application;

import com.google.android.material.color.DynamicColors;

public class FinanceHelper extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DynamicColors.applyToActivitiesIfAvailable(this);
    }
}
