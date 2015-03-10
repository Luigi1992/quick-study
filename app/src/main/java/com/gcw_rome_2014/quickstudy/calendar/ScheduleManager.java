package com.gcw_rome_2014.quickstudy.calendar;

import android.content.ContentResolver;
import android.content.Context;

import com.gcw_rome_2014.quickstudy.calendar.provider.AndroidCalendarManager;
import com.gcw_rome_2014.quickstudy.calendar.provider.AndroidEventManager;
import com.gcw_rome_2014.quickstudy.calendar.provider.CalendarManager;
import com.gcw_rome_2014.quickstudy.model.events.Event;
import com.gcw_rome_2014.quickstudy.calendar.provider.EventManager;
import com.gcw_rome_2014.quickstudy.model.Exam;
import com.gcw_rome_2014.quickstudy.model.events.ExamEvent;
import com.gcw_rome_2014.quickstudy.model.events.StudySessionEvent;

import java.util.Calendar;

/**
 * Created by Luigi on 03/01/2015.
 * This class represents the user schedule.
 * It implements all the CRUD operation of the user schedule.
 */
public class ScheduleManager {
    //Manager variables
    private CalendarManager calendarManager;
    private EventManager eventManager;
    private Context context;

    public ScheduleManager(ContentResolver contentResolver, Context context) {
        this.calendarManager = new AndroidCalendarManager(contentResolver);
        this.eventManager = new AndroidEventManager(contentResolver);
        this.context = context;
    }

    /**
     * Add all the events
     * @param exam An object representing the exams.
     */
    public Event addExam(Exam exam) {
        //Event event = this.eventManager.addEvent(exam.getName() + " Exam", appName + "Automatic Planner", exam.getExamDate(), 0);
        Event event = new ExamEvent(exam);
        this.eventManager.addEvent(event);

        Calendar now = this.getCurrentDate();
        this.addStudySessionEvents(exam, now, exam.getDifficulty().getHoursOfStudy());

        return event;
    }

    public int updateExam(Exam exam) {
        //return this.eventManager.updateEvent();
        return 0;
    }

    public int deleteExam(Exam exam) {
        return this.eventManager.deleteEvent(exam.getId());
    }

    private void addStudySessionEvents(Exam exam, Calendar startDate, int hoursOfStudy) {
        System.out.println("START: " + startDate.getTime());
        System.out.println("END: " + exam.getLastStudyDate().getTime() + "\n");

        if(startDate.before(exam.getLastStudyDate())) {
            startDate.add(Calendar.DATE, 1);
            Event event = new StudySessionEvent(exam, startDate, hoursOfStudy);
            this.eventManager.addEvent(event);
            this.addStudySessionEvents(exam, startDate, hoursOfStudy);
        }

    }

    /**
     * Return the current date.
     * @return The current date.
     */
    private Calendar getCurrentDate() {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 9);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);

        return now;
    }
}
