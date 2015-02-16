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
    private static final String UNIQUE_FIELD = " UNIQUE";
    private static final String NOT_NULL_FIELD = " NOT NULL";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + QuickStudyReaderContract.ExamEntry.TABLE_NAME + " (" +
                    QuickStudyReaderContract.ExamEntry._ID + " INTEGER PRIMARY KEY," +
                    QuickStudyReaderContract.ExamEntry.COLUMN_NAME_ENTRY_ID + INTEGER_TYPE + UNIQUE_FIELD + NOT_NULL_FIELD + COMMA_SEP +
                    QuickStudyReaderContract.ExamEntry.COLUMN_NAME_NAME + TEXT_TYPE + NOT_NULL_FIELD + COMMA_SEP +
                    QuickStudyReaderContract.ExamEntry.COLUMN_NAME_DIFFICULTY + TEXT_TYPE + NOT_NULL_FIELD + COMMA_SEP +
                    QuickStudyReaderContract.ExamEntry.COLUMN_NAME_DATE + TEXT_TYPE + NOT_NULL_FIELD + COMMA_SEP +
                    QuickStudyReaderContract.ExamEntry.COLUMN_NAME_REGISTERED + INTEGER_TYPE + NOT_NULL_FIELD + COMMA_SEP +
                    // Any other options for the CREATE command
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + QuickStudyReaderContract.ExamEntry.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "QuickStudyReader.db";

    public QuickStudyReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}