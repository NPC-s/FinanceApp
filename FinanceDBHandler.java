package com.npcs.financeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
        if (isAdd){
            val = "1";
        }

        values.put(FinancesDBSchemes.TransactionsHistory.COLUMN_NAME_VALUE, value);
        values.put(FinancesDBSchemes.TransactionsHistory.COLUMN_NAME_TYPE, val);
        values.put(FinancesDBSchemes.TransactionsHistory.COLUMN_NAME_CATEGORY, category);

        db.insert(FinancesDBSchemes.TransactionsHistory.TABLE_NAME, null, values);
        db.close();
    }

    private static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + FinancesDBSchemes.TransactionsHistory.TABLE_NAME + " (" +
            FinancesDBSchemes.TransactionsHistory._ID + " INTEGER PRIMARY KEY," +
            FinancesDBSchemes.TransactionsHistory.COLUMN_NAME_VALUE + " TEXT," +
            FinancesDBSchemes.TransactionsHistory.COLUMN_NAME_TYPE + " TEXT," +
            FinancesDBSchemes.TransactionsHistory.COLUMN_NAME_CATEGORY + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS " + FinancesDBSchemes.TransactionsHistory.TABLE_NAME;

    public int getTransactionSum() {
        int sum = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" +
                FinancesDBSchemes.TransactionsHistory.COLUMN_NAME_VALUE + ") FROM " +
                FinancesDBSchemes.TransactionsHistory.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            sum = cursor.getInt(0);
        }

        cursor.close();
        return sum;
    }
}
