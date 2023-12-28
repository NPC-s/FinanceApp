package com.npcs.financehelper;

import static com.npcs.financehelper.FinancesDBSchemes.TransactionsHistory.TRANS_COLUMN_NAME_VALUE;
import static com.npcs.financehelper.FinancesDBSchemes.TransactionsHistory.TRANS_COLUMN_NAME_TYPE;
import static com.npcs.financehelper.FinancesDBSchemes.TransactionsHistory.TRANS_COLUMN_NAME_CATEGORY;
import static com.npcs.financehelper.FinancesDBSchemes.TransactionsHistory.TRANS_TABLE_NAME;
import static com.npcs.financehelper.FinancesDBSchemes.Goals.GOALS_COLUMN_NAME_GOAL;
import static com.npcs.financehelper.FinancesDBSchemes.Goals.GOALS_COLUMN_NAME_DATE;
import static com.npcs.financehelper.FinancesDBSchemes.Goals.GOALS_COLUMN_NAME_CUR_COUNT;
import static com.npcs.financehelper.FinancesDBSchemes.Goals.GOALS_COLUMN_NAME_MAX_COUNT;
import static com.npcs.financehelper.FinancesDBSchemes.Goals.GOALS_TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
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
        db.execSQL(SQL_CREATE_ENTRIES_GOALS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void AddTransactions(String value, boolean isAdd, String category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TRANS_COLUMN_NAME_VALUE, value);
        values.put(TRANS_COLUMN_NAME_TYPE, isAdd);
        values.put(TRANS_COLUMN_NAME_CATEGORY, category);

        db.insert(TRANS_TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<Transaction> getTransactions(){

        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Transaction> transactionsList = new ArrayList<>();

        Cursor cursorTransactions = db.rawQuery("SELECT * FROM " + TRANS_TABLE_NAME, null);

        if (cursorTransactions.moveToFirst()) {
            do {
                transactionsList.add(new Transaction(cursorTransactions.getInt(1),
                        cursorTransactions.getString(3),
                        (cursorTransactions.getInt(2) == 1)));
            } while (cursorTransactions.moveToNext());
        }
        cursorTransactions.close();
        return transactionsList;
    }

    public ArrayList<Transaction> getTransactions(String[] categories, boolean is_with_adds){

        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Transaction> transactionsList = new ArrayList<>();

        String query = "SELECT * FROM " + TRANS_TABLE_NAME + " WHERE " +
                TRANS_COLUMN_NAME_CATEGORY + " IN ('" + String.join("', '", categories) + "')" +
                " AND "+ TRANS_COLUMN_NAME_TYPE + " = 0 ";

        if (is_with_adds)
            query += "OR " + TRANS_COLUMN_NAME_TYPE + " = 1";

        Cursor cursorTransactions = db.rawQuery(query, null);

        if (cursorTransactions.moveToFirst()) {
            do {
                transactionsList.add(new Transaction(cursorTransactions.getInt(1),
                        cursorTransactions.getString(3),
                        (cursorTransactions.getInt(2) == 1)));
            } while (cursorTransactions.moveToNext());
        }
        cursorTransactions.close();
        return transactionsList;
    }

    public List<String> getCategories(boolean isAdd){
        List<String> results = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorIsAdd = db.rawQuery(SQL_GET_IS_ADD_IN_TRANS, null);

        if (isAdd & cursorIsAdd.moveToFirst()) {
            results.add("Пополнения");
        }
        cursorIsAdd.close();

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
                " AND " + TRANS_COLUMN_NAME_CATEGORY +" IN (" + String.join(", ", allowedCategories),
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

        Cursor cursorSum = db.rawQuery("SELECT SUM(" + TRANS_COLUMN_NAME_VALUE + ") FROM transactions " +
                        "WHERE " + TRANS_COLUMN_NAME_TYPE + " = '0' AND "
                        + TRANS_COLUMN_NAME_CATEGORY + " = '" + category + "'",
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

        Cursor cursorSum = db.rawQuery("SELECT SUM(" + TRANS_COLUMN_NAME_VALUE + ") FROM transactions " +
                        "WHERE " + TRANS_COLUMN_NAME_TYPE + " = '0'",
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

    public ArrayList<Goal> getGoals(){
        ArrayList<Goal> results = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorGoals = db.rawQuery("SELECT * FROM " + GOALS_TABLE_NAME,
                null);

        if (cursorGoals.moveToFirst()) {
            do {
                results.add(new Goal(cursorGoals.getString(1),
                        cursorGoals.getInt(2),
                        cursorGoals.getInt(3)));
            } while (cursorGoals.moveToNext());
        }
        cursorGoals.close();

        return results;
    }

    public void addGoal(Goal g){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(GOALS_COLUMN_NAME_GOAL, g.name);
        values.put(GOALS_COLUMN_NAME_CUR_COUNT, g.value);
        values.put(GOALS_COLUMN_NAME_MAX_COUNT, g.maxValue);

        db.insert(GOALS_TABLE_NAME, null, values);
        db.close();
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TRANS_TABLE_NAME + " (" +
                    FinancesDBSchemes.TransactionsHistory._ID + " INTEGER PRIMARY KEY," +
                    TRANS_COLUMN_NAME_VALUE + " INTEGER," +
                    TRANS_COLUMN_NAME_TYPE + " BOOLEAN," +
                    TRANS_COLUMN_NAME_CATEGORY + " TEXT)";

    private static final String SQL_CREATE_ENTRIES_GOALS =
            "CREATE TABLE " + GOALS_TABLE_NAME + " (" +
                    FinancesDBSchemes.Goals._ID + " INTEGER PRIMARY KEY," +
                    GOALS_COLUMN_NAME_GOAL + " TEXT," +
                    GOALS_COLUMN_NAME_CUR_COUNT + " INTEGER," +
                    GOALS_COLUMN_NAME_MAX_COUNT + " INTEGER" + ")";

    private static final String SQL_GET_CATEGORIES = "SELECT " + TRANS_COLUMN_NAME_CATEGORY +
            " FROM " + TRANS_TABLE_NAME + " WHERE " + TRANS_COLUMN_NAME_TYPE +
            " = 0";

    private static final String SQL_GET_IS_ADD_IN_TRANS = "SELECT " + TRANS_COLUMN_NAME_CATEGORY +
            " FROM " + TRANS_TABLE_NAME + " WHERE " + TRANS_COLUMN_NAME_TYPE +
            " = 1";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TRANS_TABLE_NAME;
}
