package com.gcw_rome_2014.quickstudy.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Luigi on 15/02/2015.
 * This class is an helper to manage a database.
 * ** Guidelines **
 * - To access your database
 * QuickStudyReaderDbHelper mDbHelper = new QuickStudyReaderDbHelper(getContext());
 */
public class QuickStudyReaderDbHelper extends SQLiteOpenHelper {
    //Constants to create and delete tables
    private static final String UNIQUE_FIELD = " UNIQUE ";
    private static final String ON_CONFLICT_REPLACE = " ON CONFLICT REPLACE ";
    private static final String ON_DELETE_CASCADE = " ON DELETE CASCADE ";
    private static final String FOREIGN_KEY = " FOREIGN KEY ";
    private static final String REFERENCES = " REFERENCES ";
    private static final String NOT_NULL_FIELD = " NOT NULL ";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_EXAMS_ENTRIES =
            "CREATE TABLE " + QuickStudyReaderContract.ExamEntry.TABLE_NAME + " (" +
                    QuickStudyReaderContract.ExamEntry._ID + " INTEGER PRIMARY KEY," +
                    QuickStudyReaderContract.ExamEntry.COLUMN_NAME_ENTRY_ID + INTEGER_TYPE + UNIQUE_FIELD + NOT_NULL_FIELD + COMMA_SEP +
                    QuickStudyReaderContract.ExamEntry.COLUMN_NAME_NAME + TEXT_TYPE + NOT_NULL_FIELD + COMMA_SEP +
                    QuickStudyReaderContract.ExamEntry.COLUMN_NAME_DIFFICULTY + TEXT_TYPE + NOT_NULL_FIELD + COMMA_SEP +
                    QuickStudyReaderContract.ExamEntry.COLUMN_NAME_DATE + TEXT_TYPE + NOT_NULL_FIELD + COMMA_SEP +
                    QuickStudyReaderContract.ExamEntry.COLUMN_NAME_REGISTERED + INTEGER_TYPE + NOT_NULL_FIELD +
                    // Any other options for the CREATE command
                    " ); ";
    private static final String SQL_CREATE_SESSIONS_ENTRIES =
            "CREATE TABLE " + QuickStudyReaderContract.StudySessionEntry.TABLE_NAME + " (" +
                    QuickStudyReaderContract.StudySessionEntry._ID + " INTEGER PRIMARY KEY," +
                    QuickStudyReaderContract.StudySessionEntry.COLUMN_NAME_SESSION_ID + INTEGER_TYPE + NOT_NULL_FIELD + COMMA_SEP +
                    QuickStudyReaderContract.StudySessionEntry.COLUMN_NAME_EXAM_ID + INTEGER_TYPE + REFERENCES +
                        QuickStudyReaderContract.ExamEntry.TABLE_NAME + "(" + QuickStudyReaderContract.ExamEntry.COLUMN_NAME_ENTRY_ID + ")" +
                        ON_DELETE_CASCADE + COMMA_SEP +
                    UNIQUE_FIELD + "(" + QuickStudyReaderContract.StudySessionEntry.COLUMN_NAME_SESSION_ID + COMMA_SEP +
                        QuickStudyReaderContract.StudySessionEntry.COLUMN_NAME_EXAM_ID + ") " + ON_CONFLICT_REPLACE +
                    " )";

    private static final String SQL_DELETE_EXAMS_ENTRIES =
            "DROP TABLE IF EXISTS " + QuickStudyReaderContract.ExamEntry.TABLE_NAME;
    private static final String SQL_DELETE_SESSIONS_ENTRIES =
            "DROP TABLE IF EXISTS " + QuickStudyReaderContract.StudySessionEntry.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "QuickStudyReader.db";

    public QuickStudyReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_EXAMS_ENTRIES);
        db.execSQL(SQL_CREATE_SESSIONS_ENTRIES);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_EXAMS_ENTRIES);
        db.execSQL(SQL_DELETE_SESSIONS_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}