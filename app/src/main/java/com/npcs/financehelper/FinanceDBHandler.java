package com.npcs.financehelper;

import static com.npcs.financehelper.FinancesDBSchemes.TransactionsHistory.COLUMN_NAME_VALUE;
import static com.npcs.financehelper.FinancesDBSchemes.TransactionsHistory.COLUMN_NAME_TYPE;
import static com.npcs.financehelper.FinancesDBSchemes.TransactionsHistory.COLUMN_NAME_CATEGORY;
import static com.npcs.financehelper.FinancesDBSchemes.TransactionsHistory.TABLE_NAME;
//import static com.npcs.financehelper.FinancesDBSchemes.TransactionsHistory.COLUMN_NAME_VALUE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class FinanceDBHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "finances.db";

    public FinanceDBHandler(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void AddTransactions(String value, boolean isAdd, String category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String val = "0";
        if (isAdd)
            val = "1";

        values.put(COLUMN_NAME_VALUE, value);
        values.put(COLUMN_NAME_TYPE, val);
        values.put(COLUMN_NAME_CATEGORY, category);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<Transaction> getTransactions(){

        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Transaction> transactionsList = new ArrayList<>();

        Cursor cursorTransactions = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursorTransactions.moveToFirst()) {
            do {
                transactionsList.add(new Transaction(cursorTransactions.getString(1),
                        cursorTransactions.getString(3),
                        cursorTransactions.getString(2)));
            } while (cursorTransactions.moveToNext());
        }
        cursorTransactions.close();
        return transactionsList;
    }

    public ArrayList<Transaction> getTransactions(String[] categories){

        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Transaction> transactionsList = new ArrayList<>();

        Cursor cursorTransactions = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
                COLUMN_NAME_CATEGORY + " IN ('" + String.join("', '", categories) + "') AND "+
                COLUMN_NAME_TYPE + " = '0'", null);

        if (cursorTransactions.moveToFirst()) {
            do {
                transactionsList.add(new Transaction(cursorTransactions.getString(1),
                        cursorTransactions.getString(3),
                        cursorTransactions.getString(2)));
            } while (cursorTransactions.moveToNext());
        }
        cursorTransactions.close();
        return transactionsList;
    }

    public List<String> getCategories(){
        List<String> results = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorCategories = db.rawQuery(SQL_GET_CATEGORIES, null);

        if (cursorCategories.moveToFirst()) {
            do {
                String cat = cursorCategories.getString(0);
                if (!results.contains(cat))
                    results.add(cat);
            } while (cursorCategories.moveToNext());
        }
        cursorCategories.close();

        return results;
    }

    public List<String> getCategories(String[] allowedCategories){
        List<String> results = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursorCategories = db.rawQuery(SQL_GET_CATEGORIES +
                " AND " + COLUMN_NAME_CATEGORY +" IN (" + String.join(", ", allowedCategories),
            null);

        if (cursorCategories.moveToFirst()) {
            do {
                String cat = cursorCategories.getString(0);
                if (!results.contains(cat))
                    results.add(cat);
            } while (cursorCategories.moveToNext());
        }
        cursorCategories.close();

        return results;
    }

    public float getSum(String category){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorSum = db.rawQuery("SELECT SUM(" + COLUMN_NAME_VALUE + ") FROM transactions " +
                        "WHERE " + COLUMN_NAME_TYPE + " = '0' AND "
                        + COLUMN_NAME_CATEGORY + " = '" + category + "'",
                null);

        float res = 0;
        if (cursorSum.moveToFirst()){
            do {
                res = cursorSum.getFloat(0);
            } while (cursorSum.moveToNext());
        }

        cursorSum.close();

        return res;
    }

    public float getSum(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorSum = db.rawQuery("SELECT SUM(" + COLUMN_NAME_VALUE + ") FROM transactions " +
                        "WHERE " + COLUMN_NAME_TYPE + " = '0'",
                null);

        float res = 0;
        if (cursorSum.moveToFirst()){
            do{
                res = cursorSum.getFloat(0);
            } while (cursorSum.moveToNext());
        }

        cursorSum.close();

        return res;
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    FinancesDBSchemes.TransactionsHistory._ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_VALUE + " TEXT," +
                    COLUMN_NAME_TYPE + " TEXT," +
                    COLUMN_NAME_CATEGORY + " TEXT)";

    private static final String SQL_GET_CATEGORIES = "SELECT " + COLUMN_NAME_CATEGORY +
            " FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME_TYPE +
            " = '0'";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
