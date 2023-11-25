package com.npcs.financeapp;

import android.util.Log;

public class Transaction {
    public String value;
    public String category;
    public boolean isAdd = false;

    public Transaction(String value, String category, String isAddstr){
        this.value = value;
        this.category = category;
        this.isAdd = isAddstr.equals("1");
    }
}
