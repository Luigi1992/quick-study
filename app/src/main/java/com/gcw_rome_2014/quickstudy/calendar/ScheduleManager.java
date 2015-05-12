package com.gcw_rome_2014.quickstudy.calendar;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.gcw_rome_2014.quickstudy.R;
import com.gcw_rome_2014.quickstudy.calendar.provider.AndroidCalendarManager;
import com.gcw_rome_2014.quickstudy.calendar.provider.AndroidEventManager;
import com.gcw_rome_2014.quickstudy.calendar.provider.CalendarManager;
import com.gcw_rome_2014.quickstudy.calendar.provider.EventManager;
import com.gcw_rome_2014.quickstudy.model.Exam;
import com.gcw_rome_2014.quickstudy.model.events.Event;
import com.gcw_rome_2014.quickstudy.model.events.ExamEvent;
import com.gcw_rome_2014.quickstudy.model.events.StudySessionEvent;
import com.gcw_rome_2014.quickstudy.settings.SetWeekActivity;

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
    private SharedPreferences prefs;

    public ScheduleManager(ContentResolver contentResolver, Context context) {
        this.calendarManager = new AndroidCalendarManager(contentResolver);
        this.eventManager = new AndroidEventManager(contentResolver);
        this.context = context;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Add all the events
     * @param exam An object representing the exams.
     */
    public Event addExam(Exam exam) {
        Event event = new ExamEvent(exam);
        Long eventID = this.eventManager.addEvent(event);
        exam.setId(eventID); //Important! Without this the exam has no ID!

        Calendar now = this.getCurrentDate();
        this.addStudySessionEvents(exam, now, exam.getDifficulty().getHoursOfStudy());

        return event;
    }

    public int updateExam(Exam exam) {
        int row = this.eventManager.updateEvent(new ExamEvent(exam));

        for (long sessionID : exam.getStudySessionIds())
            this.eventManager.updateEventName(sessionID, exam.getName());

        return row;
    }

    public int deleteExam(Exam exam) {
        int row = this.eventManager.deleteEvent(exam.getId());

        for(long id : exam.getStudySessionIds())
            row += this.eventManager.deleteEvent(id);

        return row;
    }

    private void addStudySessionEvents(Exam exam, Calendar startDate, int hoursOfStudy) {
        System.out.println("START: " + startDate.getTime());
        System.out.println("END: " + exam.getLastStudyDate().getTime() + "\n");
        System.out.println(hoursOfStudy);

        if(startDate.before(exam.getLastStudyDate())) {
            startDate.add(Calendar.DATE, 1);
            switch (startDate.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.MONDAY:
                    if (prefs.getBoolean("preference_monday", true))
                        setStudySession(startDate, "monday", exam, hoursOfStudy, 6);
                    break;
                case Calendar.TUESDAY:
                    if (prefs.getBoolean("preference_tuesday", true))
                        setStudySession(startDate, "tuesday", exam, hoursOfStudy, 6);
                    break;
                case Calendar.WEDNESDAY:
                    if (prefs.getBoolean("preference_wednesday", true))
                        setStudySession(startDate, "wednesday", exam, hoursOfStudy, 6);
                    break;
                case Calendar.THURSDAY:
                    if (prefs.getBoolean("preference_thursday", true))
                        setStudySession(startDate, "thursday", exam, hoursOfStudy, 6);
                    break;
                case Calendar.FRIDAY:
                    if (prefs.getBoolean("preference_friday", true))
                        setStudySession(startDate, "friday", exam, hoursOfStudy, 6);
                    break;
                case Calendar.SATURDAY:
                    if (prefs.getBoolean("preference_saturday", true))
                        setStudySession(startDate, "saturday", exam, hoursOfStudy, 6);
                    break;
                case Calendar.SUNDAY:
                    if (prefs.getBoolean("preference_sunday", true))
                        setStudySession(startDate, "sunday", exam, hoursOfStudy, 6);
                    break;
            }

            this.addStudySessionEvents(exam, startDate, hoursOfStudy);

        }

    }

    public void setStudySession(Calendar session, String day, Exam exam, int hoursOfStudy, int startSession) {

        if (prefs.getBoolean(day + "_6_8", false) && 6>=startSession) {
            if (hoursOfStudy>2)
                setStudySession(session, day, exam, hoursOfStudy-2, 8);
            session.set(Calendar.HOUR_OF_DAY, 6);} else

        if (prefs.getBoolean(day + "_8_10", true) && 8>=startSession) {
            if (hoursOfStudy>2)
                setStudySession(session, day, exam, hoursOfStudy-2, 10);
            session.set(Calendar.HOUR_OF_DAY, 8);} else

        if (prefs.getBoolean(day + "_10_12", true) && 10>=startSession) {
            if (hoursOfStudy>2)
                setStudySession(session, day, exam, hoursOfStudy-2, 12);
            session.set(Calendar.HOUR_OF_DAY, 10);} else

        if (prefs.getBoolean(day + "_12_14", true) && 12>=startSession) {
            if (hoursOfStudy>2)
                setStudySession(session, day, exam, hoursOfStudy-2, 14);
            session.set(Calendar.HOUR_OF_DAY, 12);} else

        if (prefs.getBoolean(day + "_14_16", true) && 14>=startSession) {
            if (hoursOfStudy>2)
                setStudySession(session, day, exam, hoursOfStudy-2, 16);
            session.set(Calendar.HOUR_OF_DAY, 14);} else

        if (prefs.getBoolean(day + "_16_18", true) && 16>=startSession) {
            if (hoursOfStudy>2)
                setStudySession(session, day, exam, hoursOfStudy-2, 18);
            session.set(Calendar.HOUR_OF_DAY, 16);} else

        if (prefs.getBoolean(day + "_18_20", true) && 18>=startSession) {
            if (hoursOfStudy>2)
                setStudySession(session, day, exam, hoursOfStudy-2, 20);
            session.set(Calendar.HOUR_OF_DAY, 18);} else

        if (prefs.getBoolean(day + "_20_22", true) && 20>=startSession) {
            if (hoursOfStudy>2)
                setStudySession(session, day, exam, hoursOfStudy-2, 22);
            session.set(Calendar.HOUR_OF_DAY, 20);} else

        if (prefs.getBoolean(day + "_22_00", false) && 22>=startSession)
            session.set(Calendar.HOUR_OF_DAY, 22);

        int duration = 2;
        if (hoursOfStudy==1)
            duration = 1;
        Event event = new StudySessionEvent(exam, session, duration);
        long sessionId = this.eventManager.addEvent(event);
        exam.addSessionId(sessionId);

    }

    /**
     * Return the current date.
     * @return The current date.
     */
    private Calendar getCurrentDate() {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 8);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);

        return now;
    }
}
