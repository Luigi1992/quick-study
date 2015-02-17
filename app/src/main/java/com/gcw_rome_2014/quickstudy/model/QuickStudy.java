package com.gcw_rome_2014.quickstudy.model;

import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;

import com.gcw_rome_2014.quickstudy.calendar.ScheduleManager;
import com.gcw_rome_2014.quickstudy.database.QuickStudyDatabase;

import java.util.Map;

/**
 * Created by Luigi on 15/02/2015.
 * This class represent the entry point of the application.
 * It also use the Lazy approach to load data into exams variable.
 *
 * NOTE: Before calling any methods, you MUST call the init() method first!!
 */
public class QuickStudy {
    private static QuickStudy instance = new QuickStudy();
    private QuickStudyDatabase database = null;
    ScheduleManager scheduleManager = null;
    private Map<Long, Exam> exams = null;

    public static QuickStudy getInstance() {
        return instance;
    }

    private QuickStudy() {

    }

    public void init(Context context, ContentResolver contentResolver) {
        this.database = new QuickStudyDatabase(context);
        this.scheduleManager = new ScheduleManager(contentResolver, context);
    }

    public Exam getExam(Long id) {
        if(this.database == null)
            return null;

        lazyLoad();
        return this.exams.get(id);
    }

    public void putExam(Exam exam) {
        if(this.database == null || this.scheduleManager == null)
            return;

        lazyLoad();
        this.scheduleManager.addExam(exam);     //Into Calendar
        this.database.putExam(exam);            //Into Database
        this.exams.put(exam.getId(), exam);     //Into List

        Log.i("QuickStudy", "Exam ID: " + exam.getId());
    }

    public boolean deleteExam(Exam exam) {
        if(this.database == null || this.scheduleManager == null)
            return false;

        lazyLoad();

        boolean calendar = this.scheduleManager.deleteExam(exam) > 0;       //From Calendar
        boolean database = this.database.deleteExam(exam.getId()) > 0;      //From Database
        boolean list = this.exams.remove(exam.getId()) != null;             //From list

        return calendar && database && list;
    }

    public Map<Long, Exam> getExams() {
        if(this.database == null)
            return null;

        lazyLoad();
        return exams;
    }

    public void setExams(Map<Long, Exam> exams) {
        this.exams = exams;
    }

    public Exam[] getArrayOfExams() {
        if(this.database == null)
            return null;

        lazyLoad();

        Exam[] examsArray = new Exam[this.exams.values().size()];
        this.exams.values().toArray(examsArray);

        return examsArray;
    }

    /**
     * Method for the lazy load.
     */
    private void lazyLoad() {
        if(this.exams == null)
            this.exams = this.database.readAllExams();
    }
}
