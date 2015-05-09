package com.gcw_rome_2014.quickstudy.model.events;

import android.content.Context;

import com.gcw_rome_2014.quickstudy.model.Exam;

import java.util.Calendar;

/**
 * Created by Luigi on 09/03/2015.
 * Event class.
 */
public abstract class Event {
    private String name;
    private String description;
    private Calendar dateAndTime;
    private int duration;
    private Exam exam;
    private static Context context;

    public Event(Exam exam, String name, String description, Calendar dateAndTime, int duration) {
        this.exam = exam;
        this.name = name;
        this.description = description;
        this.dateAndTime = dateAndTime;
        this.duration = duration;
    }

    public Event(Exam exam, String name, String description, int duration) {
        this(exam, name, description, exam.getDate(), duration);
    }

    public long getId() {
        return exam.getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Calendar dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public static Context getContext() { return context; }

    public static void setContext(Context mcontext) {
        if (context == null)
            context = mcontext;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;

        Event event = (Event) o;

        return exam.equals(event.exam);
    }

    @Override
    public int hashCode() {
        return exam.hashCode();
    }
}
