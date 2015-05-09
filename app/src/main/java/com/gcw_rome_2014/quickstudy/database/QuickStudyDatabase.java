package com.gcw_rome_2014.quickstudy.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gcw_rome_2014.quickstudy.database.selectors.AllExamsSelector;
import com.gcw_rome_2014.quickstudy.database.selectors.IncomingExamsSelector;
import com.gcw_rome_2014.quickstudy.database.selectors.Selector;
import com.gcw_rome_2014.quickstudy.model.Exam;
import com.gcw_rome_2014.quickstudy.model.difficulties.Difficulty;
import com.gcw_rome_2014.quickstudy.model.difficulties.Easy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
        ContentValues examValues = this.examToContentValues(exam);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                QuickStudyReaderContract.ExamEntry.TABLE_NAME,
                "null",
                examValues);

        for(Long id : exam.getStudySessionIds()) {
            // Create a new map of values for the sessions, where column names are the keys
            ContentValues studySessionValues = this.sessionToContentValues(exam.getId(), id);

            // Insert the new row, returning the primary key value of the new row
            newRowId += db.insert(
                    QuickStudyReaderContract.StudySessionEntry.TABLE_NAME,
                    "null",
                    studySessionValues);
        }

        return newRowId;
    }

    /**
     * Delete an exam from table providing his id.
     * @param id The exam id
     */
    public int deleteExam(long id) {
        // Gets the data repository in write mode
        this.db = mDbHelper.getWritableDatabase();

        // Define 'where' part of query.
        String selection = QuickStudyReaderContract.ExamEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(id)};
        // Issue SQL statement.
        return db.delete(QuickStudyReaderContract.ExamEntry.TABLE_NAME,
                selection,
                selectionArgs);
    }

    /**
     * This is fake edid exam function. It deletes the exam from database and inserts it again.
     * @param exam The exam to be updated
     */
    public long updateExam(Exam exam) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // New value for one column
        ContentValues values = this.examToContentValues(exam);

        // Which row to update, based on the ID
        String selection = QuickStudyReaderContract.ExamEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(exam.getId()) };

        return db.update(
                QuickStudyReaderContract.ExamEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
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
                String.valueOf(id)
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
        return processExam(cursor);
    }

    /**
     * This function return the Exam object found in database with the specified id.
     * @return Return the instantiated Exam object.
     */
    public Map<Long, Exam> readAllExams() {
        return readAllExams(new AllExamsSelector());
    }

    /**
     * This function return the Exam object found in database with the specified id.
     * @return Return the instantiated Exam object.
     */
    public Map<Long, Exam> readAllExams(Selector selector) {
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
        String selection = selector.getSelection();

        // The values for the WHERE clause
        String[] selectionArgs = selector.getSelectionArgs();

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                QuickStudyReaderContract.ExamEntry.COLUMN_NAME_DATE + " DESC";

        Cursor cursor = db.query(
                QuickStudyReaderContract.ExamEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        Map<Long, Exam> exams = new HashMap<>();

        while(cursor.moveToNext()) {
            Exam exam = processExam(cursor);
            Set<Long> sessionIds = this.readSessionsOfExam(exam.getId());
            exam.setStudySessionIds(sessionIds);
            exams.put(exam.getId(), exam);
        }

        return exams;
    }

    /**
     * This function return the Exam object found in database with the specified id.
     * @return Return the instantiated Exam object.
     */
    public Set<Long> readSessionsOfExam(long examID) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                QuickStudyReaderContract.StudySessionEntry._ID,
                QuickStudyReaderContract.StudySessionEntry.COLUMN_NAME_EXAM_ID,
                QuickStudyReaderContract.StudySessionEntry.COLUMN_NAME_SESSION_ID,
        };

        // The columns for the WHERE clause
        String selection = QuickStudyReaderContract.StudySessionEntry.COLUMN_NAME_EXAM_ID + " = ?";

        // The values for the WHERE clause
        String[] selectionArgs = {
                String.valueOf(examID)
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                QuickStudyReaderContract.StudySessionEntry.COLUMN_NAME_EXAM_ID + " DESC";

        Cursor cursor = db.query(
                QuickStudyReaderContract.StudySessionEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        Set<Long> sessionIDs = new HashSet<>();

        while(cursor.moveToNext()) {
            long sessionId = processSession(cursor);
            sessionIDs.add(sessionId);
        }

        return sessionIDs;
    }

    /**
     * Process an exam to return its content values.
     * @param exam The exam to process
     * @return The correspondent content value
     */
    private ContentValues examToContentValues(Exam exam) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(QuickStudyReaderContract.ExamEntry.COLUMN_NAME_ENTRY_ID, exam.getId());
        values.put(QuickStudyReaderContract.ExamEntry.COLUMN_NAME_NAME, exam.getName());
        values.put(QuickStudyReaderContract.ExamEntry.COLUMN_NAME_DIFFICULTY, exam.getDifficulty().getName());

        // Converting Date to string for SQLite format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String dateString = sdf.format(exam.getDate().getTime());

        values.put(QuickStudyReaderContract.ExamEntry.COLUMN_NAME_DATE, dateString);
        values.put(QuickStudyReaderContract.ExamEntry.COLUMN_NAME_REGISTERED, exam.isRegistered());

        return values;
    }

    private ContentValues sessionToContentValues(long examId, long sessionId) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(QuickStudyReaderContract.StudySessionEntry.COLUMN_NAME_EXAM_ID, examId);
        values.put(QuickStudyReaderContract.StudySessionEntry.COLUMN_NAME_SESSION_ID, sessionId);

        return values;
    }

    private Exam processExam(Cursor cursor) {
        long examId = cursor.getLong(
                cursor.getColumnIndexOrThrow(QuickStudyReaderContract.ExamEntry.COLUMN_NAME_ENTRY_ID)
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

        Boolean isRegistered = (cursor.getInt(
                cursor.getColumnIndexOrThrow(QuickStudyReaderContract.ExamEntry.COLUMN_NAME_REGISTERED)
        )) == 1;

        Calendar examDate = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
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

        return new Exam(examId, examName, difficulty, examDate, isRegistered);
    }

    private Long processSession(Cursor cursor) {
        return cursor.getLong(
                cursor.getColumnIndexOrThrow(QuickStudyReaderContract.StudySessionEntry.COLUMN_NAME_SESSION_ID)
        );
    }

}
