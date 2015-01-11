package com.gcw_rome_2014.saveme.calendar;

import android.content.ContentResolver;

import com.gcw_rome_2014.saveme.calendar.provider.AndroidCalendarManager;
import com.gcw_rome_2014.saveme.calendar.provider.AndroidEventManager;
import com.gcw_rome_2014.saveme.calendar.provider.CalendarManager;
import com.gcw_rome_2014.saveme.calendar.provider.EventManager;

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
     * @param examName      The exam name.
     * @param dateAndTime   The date of the exam.
     * @param hoursOfStudy  An integer representing the number of hours the student want to study per day.
     */
    public long addExam(String examName, Calendar dateAndTime, int hoursOfStudy) {
        long eventID = this.eventManager.addEvent(examName + " Exam", "SAVE ME Automatic Planner", dateAndTime, 0);

        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 9);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);

        dateAndTime.add(Calendar.DATE, -2);
        this.addStudyEvents(examName, now, dateAndTime, hoursOfStudy);

        return eventID;
    }

    private void addStudyEvents(String examName, Calendar startDate, Calendar endDate, int hoursOfStudy) {
        System.out.println("START: " + startDate.getTime());
        System.out.println("END: " + endDate.getTime() + "\n");

        if(startDate.before(endDate)) {
            startDate.add(Calendar.DATE, 1);
            this.eventManager.addEvent("Study " + examName, "SAVE ME " + examName + " Study Session", startDate, hoursOfStudy);
            this.addStudyEvents(examName, startDate, endDate, hoursOfStudy);
        }

    }
}
