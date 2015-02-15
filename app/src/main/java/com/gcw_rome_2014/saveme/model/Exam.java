package com.gcw_rome_2014.saveme.model;

import com.gcw_rome_2014.saveme.R;
import com.gcw_rome_2014.saveme.model.difficulties.Difficulty;

import java.util.Calendar;

/**
 * Created by Luigi on 18/01/2015.
 */
public class Exam {

    //The Exam name
    private String name;

    //The exam difficulty. It is a integer representing an image.
    private Difficulty difficulty;

    //The exam date
    private Calendar examDate;

    //A flag that it is used to check if the user remembered to reserve for the exam test.
    private boolean isRegistered;

    public Exam(String name, Difficulty difficulty, Calendar examDate) {
        this.name = name;
        this.difficulty = difficulty;
        this.examDate = examDate;
        this.isRegistered = false;
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
