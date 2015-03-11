package com.gcw_rome_2014.quickstudy.model.events;

import com.gcw_rome_2014.quickstudy.model.Exam;

import java.util.Calendar;

/**
 * Created by Luigi on 09/03/2015.
 */
public abstract class Event {
    private String name;
    private String description;
    private Calendar dateAndTime;
    private int duration;
    private Exam exam;

    public Event(Exam exam, String name, String description, Calendar dateAndTime, int duration) {
        this.exam = exam;
        this.name = name;
        this.description = description;
        this.dateAndTime = dateAndTime;
        this.duration = duration;
    }

    public Event(Exam exam, String name, String description, int duration) {
        this(exam, name, description, exam.getExamDate(), duration);
    }

    public long getId() {
        return exam.getId();
    }

    public void setId(long id) {
        this.exam.setId(id);
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
}
