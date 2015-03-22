package com.gcw_rome_2014.quickstudy.database.selectors;

import com.gcw_rome_2014.quickstudy.database.QuickStudyReaderContract;

/**
 * Created by Luigi on 22/03/2015.
 */
public class OldExamsSelectors implements Selector {
    public OldExamsSelectors() {}

    @Override
    public String getSelection() {
        return QuickStudyReaderContract.ExamEntry.COLUMN_NAME_DATE + " < date('now')";
    }

    @Override
    public String[] getSelectionArgs() {
        return null;
    }
}
