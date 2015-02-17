package com.gcw_rome_2014.quickstudy.calendar;

import android.content.ContentResolver;
import android.content.Context;

import com.gcw_rome_2014.quickstudy.calendar.provider.AndroidCalendarManager;
import com.gcw_rome_2014.quickstudy.calendar.provider.AndroidEventManager;
import com.gcw_rome_2014.quickstudy.calendar.provider.CalendarManager;
import com.gcw_rome_2014.quickstudy.calendar.provider.EventManager;
import com.gcw_rome_2014.quickstudy.database.QuickStudyDatabase;
import com.gcw_rome_2014.quickstudy.model.Exam;
import com.gcw_rome_2014.quickstudy.model.QuickStudy;

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
    private static final String appName = "Quick Study ";

    public ScheduleManager(ContentResolver contentResolver, Context context) {
        this.calendarManager = new AndroidCalendarManager(contentResolver);
        this.eventManager = new AndroidEventManager(contentResolver);
        this.context = context;
    }

    /**
     * Add all the events
     * @param exam An object representing the exams.
     */
    public long addExam(Exam exam) {
        long eventID = this.eventManager.addEvent(exam.getName() + " Exam", appName + "Automatic Planner", exam.getExamDate(), 0);
        exam.setId(eventID);    // The Exam id is the Calendar Id

        Calendar now = this.getCurrentDate();
        this.addStudyEvents(exam, now, exam.getDifficulty().getHoursOfStudy());

        return eventID;
    }

    private void addStudyEvents(Exam exam, Calendar startDate, int hoursOfStudy) {
        System.out.println("START: " + startDate.getTime());
        System.out.println("END: " + exam.getLastStudyDate().getTime() + "\n");

        if(startDate.before(exam.getLastStudyDate())) {
            startDate.add(Calendar.DATE, 1);
            this.eventManager.addEvent("Study " + exam.getName(),
                    appName + exam.getName() + " Study Session",
                    startDate,
                    hoursOfStudy);
            this.addStudyEvents(exam, startDate, hoursOfStudy);
        }

    }

    public void deleteExam(Exam exam) {
        this.eventManager.deleteEvent(exam.getId());
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
