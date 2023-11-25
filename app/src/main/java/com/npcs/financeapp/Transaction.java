package com.npcs.financeapp;
public class Transaction {
    public String value;
    public String category;
    public boolean isAdd;

    public Transaction(String value, String category, String isAdd){
        this.value = value;
        this.category = category;
        this.isAdd = isAdd == "0";
    }
}
