package com.gcw_rome_2014.quickstudy.model;

import android.content.Context;

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
    private Map<Long, Exam> exams = null;

    public static QuickStudy getInstance() {
        return instance;
    }

    private QuickStudy() {

    }

    public void init(Context context) {
        this.database = new QuickStudyDatabase(context);
    }

    public Exam getExam(Long id) {
        if(this.database == null)
            return null;

        lazyLoad();
        return this.exams.get(id);
    }

    public void putExam(Exam exam) {
        if(this.database == null)
            return;

        lazyLoad();
        this.database.putExam(exam);
        this.exams.put(exam.getId(), exam);
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
