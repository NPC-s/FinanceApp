package com.npcs.financehelper;

public class Transaction {
    public int value;
    public String category;
    public boolean isAdd = false;

    public Transaction(int value, String category, boolean isAdd){
        this.value = value;
        this.category = category;
        if (isAdd)
            this.category = "Пополнение";
        this.isAdd = isAdd;
    }
}
