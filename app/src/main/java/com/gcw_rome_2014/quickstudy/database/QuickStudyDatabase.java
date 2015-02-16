package com.gcw_rome_2014.quickstudy.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gcw_rome_2014.quickstudy.model.Exam;
import com.gcw_rome_2014.quickstudy.model.difficulties.Difficulty;
import com.gcw_rome_2014.quickstudy.model.difficulties.Easy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Luigi on 16/02/2015.
 * This class act as a Facade in order to decrease coupling in the application code.
 *
 * Note: Because they can be long-running, be sure that you call getWritableDatabase()
 * or getReadableDatabase() in a background thread, such as with AsyncTask or IntentService.
 */
public class QuickStudyDatabase {
    private QuickStudyReaderDbHelper mDbHelper;
    private SQLiteDatabase db;

    public QuickStudyDatabase (Context context) {
        this.mDbHelper = new QuickStudyReaderDbHelper(context);
    }

    public long putExam(Exam exam) {
        // Gets the data repository in write mode
        this.db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(QuickStudyReaderContract.ExamEntry.COLUMN_NAME_ENTRY_ID, exam.getId());
        values.put(QuickStudyReaderContract.ExamEntry.COLUMN_NAME_NAME, exam.getName());
        values.put(QuickStudyReaderContract.ExamEntry.COLUMN_NAME_DIFFICULTY, exam.getDifficulty().getName());
        values.put(QuickStudyReaderContract.ExamEntry.COLUMN_NAME_DATE, exam.getExamDate().toString());
        values.put(QuickStudyReaderContract.ExamEntry.COLUMN_NAME_REGISTERED, exam.isRegistered());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                QuickStudyReaderContract.ExamEntry.TABLE_NAME,
                "null",
                values);

        return newRowId;
    }

    /**
     * This function return the Exam object found in database with the specified id.
     * @param id The exam id
     * @return Return the instantiated Exam object.
     */
    public Exam readExam(long id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                QuickStudyReaderContract.ExamEntry._ID,
                QuickStudyReaderContract.ExamEntry.COLUMN_NAME_ENTRY_ID,
                QuickStudyReaderContract.ExamEntry.COLUMN_NAME_NAME,
                QuickStudyReaderContract.ExamEntry.COLUMN_NAME_DIFFICULTY,
                QuickStudyReaderContract.ExamEntry.COLUMN_NAME_DATE,
                QuickStudyReaderContract.ExamEntry.COLUMN_NAME_REGISTERED
        };

        // The columns for the WHERE clause
        String selection = QuickStudyReaderContract.ExamEntry.COLUMN_NAME_ENTRY_ID + " = ?";

        // The values for the WHERE clause
        String[] selectionArgs = {
                QuickStudyReaderContract.ExamEntry.COLUMN_NAME_ENTRY_ID
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                QuickStudyReaderContract.ExamEntry.COLUMN_NAME_ENTRY_ID + " DESC";

        Cursor cursor = db.query(
                QuickStudyReaderContract.ExamEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        cursor.moveToFirst();
        long examId = cursor.getLong(
                cursor.getColumnIndexOrThrow(QuickStudyReaderContract.ExamEntry._ID)
        );

        String examName = cursor.getString(
                cursor.getColumnIndexOrThrow(QuickStudyReaderContract.ExamEntry.COLUMN_NAME_NAME)
        );

        String difficultyName = cursor.getString(
                cursor.getColumnIndexOrThrow(QuickStudyReaderContract.ExamEntry.COLUMN_NAME_DIFFICULTY)
        );

        String examDateString = cursor.getString(
                cursor.getColumnIndexOrThrow(QuickStudyReaderContract.ExamEntry.COLUMN_NAME_DATE)
        );

        Boolean isRegistered = Boolean.parseBoolean( cursor.getString(
                cursor.getColumnIndexOrThrow(QuickStudyReaderContract.ExamEntry.COLUMN_NAME_REGISTERED)
        ));

        Calendar examDate = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS.SSS", Locale.getDefault());
        try {
            examDate.setTime(sdf.parse(examDateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Difficulty difficulty;
        // Try to instantiate Difficulty command
        try {
            String className = "com.gcw_rome_2014.quickstudy.model.difficulties.";
            className += difficultyName;
            difficulty = (Difficulty) Class.forName(className).newInstance();
        } catch (Exception e) {
            difficulty = new Easy();
        }

        Exam exam = new Exam(examId, examName, difficulty, examDate, isRegistered);

        return exam;
    }


}
