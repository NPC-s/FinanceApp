package com.npcs.financehelper;

import android.provider.BaseColumns;

public class FinancesDBSchemes {

    private FinancesDBSchemes() {}
    public static class TransactionsHistory implements BaseColumns{
        public static final String TRANS_TABLE_NAME = "transactions";
        public static final String TRANS_COLUMN_NAME_VALUE = "value";
        public static final String TRANS_COLUMN_NAME_TYPE = "type";
        public static final String TRANS_COLUMN_NAME_CATEGORY = "category";
        public static final String TRANS_COLUMN_NAME_DATE = "date";
    }

    public static class Goals implements BaseColumns{
        public static final String GOALS_TABLE_NAME = "goals";
        public static final String GOALS_COLUMN_NAME_GOAL = "goal";
        public static final String GOALS_COLUMN_NAME_MAX_COUNT = "max_count";
        public static final String GOALS_COLUMN_NAME_CUR_COUNT = "cur_count";
        public static final String GOALS_COLUMN_NAME_DATE = "date";
    }
}
