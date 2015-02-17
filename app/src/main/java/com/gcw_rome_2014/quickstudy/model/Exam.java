package com.gcw_rome_2014.quickstudy.model;

import com.gcw_rome_2014.quickstudy.model.difficulties.Difficulty;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Luigi on 18/01/2015.
 */
public class Exam implements Serializable {
    private static final long DEFAULT_ID = -1;

    // Exam Calendar Id
    long id;

    // The Exam name
    private String name;

    // The exam difficulty. It is a integer representing an image.
    private Difficulty difficulty;

    // The exam date
    private Calendar examDate;

    // A flag that it is used to check if the user remembered to reserve for the exam test.
    private boolean isRegistered;

    public Exam(String name, Difficulty difficulty, Calendar examDate) {
        this.id = DEFAULT_ID;
        this.name = name;
        this.difficulty = difficulty;
        this.examDate = examDate;
        this.isRegistered = false;
    }

    public Exam(long id, String name, Difficulty difficulty, Calendar examDate, boolean isRegistered) {
        this(name, difficulty, examDate);
        this.id = id;
        this.isRegistered = isRegistered;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isDatabaseRecorded() {
        return this.getId() != DEFAULT_ID;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getExamDate() {
        return examDate;
    }

    public Calendar getLastStudyDate() {
        Calendar lastDay = (Calendar) this.examDate.clone();
        lastDay.add(Calendar.DATE, -2);
        return lastDay;
    }

    public void setExamDate(Calendar examDate) {
        this.examDate = examDate;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }
}
