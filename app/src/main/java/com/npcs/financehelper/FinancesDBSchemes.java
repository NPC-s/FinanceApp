package com.npcs.financehelper;

import android.provider.BaseColumns;

public class FinancesDBSchemes {

    private FinancesDBSchemes() {}
    public static class TransactionsHistory implements BaseColumns{
        public static final String TABLE_NAME = "transactions";
        public static final String COLUMN_NAME_VALUE = "value";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_CATEGORY = "category";
    }

}
