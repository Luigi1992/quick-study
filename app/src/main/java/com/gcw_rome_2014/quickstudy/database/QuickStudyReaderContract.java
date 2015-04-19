package com.gcw_rome_2014.quickstudy.database;

import android.provider.BaseColumns;

/**
 * Created by Luigi on 15/02/2015.
 */
public final class QuickStudyReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public QuickStudyReaderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class ExamEntry implements BaseColumns {
        public static final String TABLE_NAME = "exams";
        public static final String COLUMN_NAME_ENTRY_ID = "exam_id";
        public static final String COLUMN_NAME_NAME = "exam_name";
        public static final String COLUMN_NAME_DIFFICULTY = "exam_difficulty";
        public static final String COLUMN_NAME_DATE = "exam_date";
        public static final String COLUMN_NAME_REGISTERED = "exam_registered";
    }

    /* Inner class that defines the table contents */
    public static abstract class StudySessionEntry implements BaseColumns {
        public static final String TABLE_NAME = "study_sessions";
        public static final String COLUMN_NAME_SESSION_ID = "session_id";
        public static final String COLUMN_NAME_EXAM_ID = "exam_id";
    }

}