package com.npcs.financehelper;

import android.util.Log;

public class Goal {
    public String name;
    public int value;
    public int maxValue;

    public Goal(String n, int val, int mv){
        name = n;
        value = Math.min(val, mv);
        maxValue = mv;
    }
}
