package com.gcwrome2014.quickstudy.model;

import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;

import com.gcwrome2014.quickstudy.calendar.ScheduleManager;
import com.gcwrome2014.quickstudy.database.QuickStudyDatabase;
import com.gcwrome2014.quickstudy.database.selectors.AllExamsSelector;
import com.gcwrome2014.quickstudy.database.selectors.Selector;

import java.util.ArrayList;
import java.util.List;
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
    private ScheduleManager scheduleManager = null;
    private Map<Long, Exam> exams = null;
    private Selector selector = null;

    private static final String appName = "Quick Study";

    public static QuickStudy getInstance() {
        return instance;
    }

    private QuickStudy() {
        this.selector = new AllExamsSelector();
    }

    public void init(Context context, ContentResolver contentResolver) {
        this.database = new QuickStudyDatabase(context);
        this.scheduleManager = new ScheduleManager(contentResolver, context);
    }

    /**
     * Return The exam.     * @param id The Exam id
     * @return The Exam. May return null if the class is not initialized.
     */
    public Exam getExam(Long id) {
        if (!isInitialized()) return null;

        load();
        return this.exams.get(id);
    }

    /**
     * This method add an Exam to database, calendar and the temp list
     * @param exam The exam to add.
     */
    public void putExam(Exam exam) {
        if (!isInitialized()) return;

        load();
        this.scheduleManager.addExam(exam);     //Into Calendar
        this.database.putExam(exam);            //Into Database
        this.exams.put(exam.getId(), exam);     //Into List

        Log.i("QuickStudy", "Exam ID: " + exam.getId());
    }


    public void updateExam(Exam exam) {
        if (!isInitialized()) return;

        load();
        this.exams.put(exam.getId(), exam);        //Into List
        this.scheduleManager.updateExam(exam);     //Into Calendar
        this.database.updateExam(exam);            //Into Database

    }

    public boolean deleteExam(Exam exam) {
        if (!isInitialized()) return false;

        load();
        boolean list = this.exams.remove(exam.getId()) != null;             //From list
        boolean calendar = this.scheduleManager.deleteExam(exam) > 0;       //From Calendar
        boolean database = this.database.deleteExam(exam.getId()) > 0;      //From Database

        return calendar && database && list;
    }

    /**
     * Check if the init function was called at least once.
     * @return Initialized
     */
    private boolean isInitialized() {
        return (this.database != null && this.scheduleManager != null);
    }

    public Map<Long, Exam> getExams() {
        if(this.database == null)
            return null;

        load();
        return exams;
    }

    public void setExams(Map<Long, Exam> exams) {
        this.exams = exams;
    }

    public void setSelector(Selector selector) {
        this.selector = selector;
    }

    public static String getAppName() {
        return appName;
    }

    public Exam[] getArrayOfExams() {
        if(this.database == null)
            return null;

        load();

        Exam[] examsArray = new Exam[this.exams.values().size()];
        this.exams.values().toArray(examsArray);

        return examsArray;
    }

    public List<Exam> getListOfExams() {
        if(this.database == null)
            return null;

        load();
        return new ArrayList<>(this.exams.values());
    }

    /**
     * Method for the lazy load.
     */
    private void load() {
        this.exams = this.database.readAllExams(this.selector);
    }

    /**
     * Load exams with a selector.
     */
    private void selectorLoad(Selector selector) {
        this.exams = this.database.readAllExams(selector);
    }
}
