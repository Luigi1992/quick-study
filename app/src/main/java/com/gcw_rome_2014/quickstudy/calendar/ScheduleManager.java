package com.gcw_rome_2014.quickstudy.calendar;

import android.content.ContentResolver;

import com.gcw_rome_2014.quickstudy.calendar.provider.AndroidCalendarManager;
import com.gcw_rome_2014.quickstudy.calendar.provider.AndroidEventManager;
import com.gcw_rome_2014.quickstudy.calendar.provider.CalendarManager;
import com.gcw_rome_2014.quickstudy.calendar.provider.EventManager;
import com.gcw_rome_2014.quickstudy.model.Exam;

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

    public ScheduleManager(ContentResolver contentResolver) {
        this.calendarManager = new AndroidCalendarManager(contentResolver);
        this.eventManager = new AndroidEventManager(contentResolver);
    }

    /**
     * Add all the events
     * @param exam An object representing the exams.
     * @param hoursOfStudy  An integer representing the number of hours the student want to study per day.
     */
    public long addExam(Exam exam, int hoursOfStudy) {
        long eventID = this.eventManager.addEvent(exam.getName() + " Exam", "SAVE ME Automatic Planner", exam.getExamDate(), 0);

        Calendar now = this.getCurrentDate();
        this.addStudyEvents(exam, now, hoursOfStudy);

        return eventID;
    }

    private void addStudyEvents(Exam exam, Calendar startDate, int hoursOfStudy) {
        System.out.println("START: " + startDate.getTime());
        System.out.println("END: " + exam.getLastStudyDate().getTime() + "\n");

        if(startDate.before(exam.getLastStudyDate())) {
            startDate.add(Calendar.DATE, 1);
            this.eventManager.addEvent("Study " + exam.getName(),
                    "SAVE ME " + exam.getName() + " Study Session",
                    startDate,
                    hoursOfStudy);
            this.addStudyEvents(exam, startDate, hoursOfStudy);
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
