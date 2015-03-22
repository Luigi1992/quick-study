package com.gcw_rome_2014.quickstudy.database.selectors;

import com.gcw_rome_2014.quickstudy.database.QuickStudyReaderContract;

/**
 * Created by Luigi on 22/03/2015.
 */
public class IncomingExamsSelector implements Selector {
    public IncomingExamsSelector() {}

    /**
     * The columns for the WHERE clause
      */
    @Override
    public String getSelection() {
        return QuickStudyReaderContract.ExamEntry.COLUMN_NAME_DATE + " < date('now', '+1 month') AND " +
                QuickStudyReaderContract.ExamEntry.COLUMN_NAME_DATE + " > datetime('now')";
    }

    /**
     * The values for the WHERE clause
      */
    @Override
    public String[] getSelectionArgs() {
        return null;
    }
}