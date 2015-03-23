package com.gcwrome2014.quickstudy.model;

import com.gcwrome2014.quickstudy.model.difficulties.Difficulty;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Luigi on 18/01/2015.
 * This class represent the exam that the user has to study for the day of the test.
 */
public class Exam implements Serializable {
    private static final long DEFAULT_ID = -1;

    // Exam Calendar Id
    long id;

    // The Exam name
    private String name;

    // The exam difficulty. It's a integer representing an image.
    private Difficulty difficulty;

    // The exam date
    private Calendar date;

    // A flag that it's used to check if the user remembered to reserve for the exam test.
    private boolean isRegistered;

    //Store the exam's study sessions
    private Set<Long> studySessionIds;

    public Exam(String name, Difficulty difficulty, Calendar date) {
        this.id = DEFAULT_ID;
        this.name = name;
        this.difficulty = difficulty;
        this.date = date;
        this.isRegistered = false;
        this.studySessionIds = new HashSet<>();
    }

    public Exam(long id, String name, Difficulty difficulty, Calendar date, boolean isRegistered) {
        this(name, difficulty, date);
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

    public Calendar getDate() {
        return date;
    }

    public Calendar getLastStudyDate() {
        Calendar lastDay = (Calendar) this.date.clone();
        lastDay.add(Calendar.DATE, -1);
        return lastDay;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }

    public void addSessionId(Long id) {
        this.studySessionIds.add(id);
    }

    public Set<Long> getStudySessionIds() {
        return studySessionIds;
    }

    public void setStudySessionIds(Set<Long> studySessionIds) {
        this.studySessionIds = studySessionIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Exam)) return false;

        Exam exam = (Exam) o;

        if (id != exam.id) return false;
        if (isRegistered != exam.isRegistered) return false;
        if (!date.equals(exam.date)) return false;
        if (!name.equals(exam.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        return result;
    }

    public boolean isOld() {
        Date currentDate = new Date();
        return this.date.getTime().compareTo(currentDate) < 0;
    }
}
