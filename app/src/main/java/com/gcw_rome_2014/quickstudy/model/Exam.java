package com.gcw_rome_2014.quickstudy.model;

import com.gcw_rome_2014.quickstudy.model.difficulties.Difficulty;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Luigi on 18/01/2015.
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
    private Calendar examDate;

    // A flag that it's used to check if the user remembered to reserve for the exam test.
    private boolean isRegistered;

    private Set<Long> studySessionIds;

    public Exam(String name, Difficulty difficulty, Calendar examDate) {
        this.id = DEFAULT_ID;
        this.name = name;
        this.difficulty = difficulty;
        this.examDate = examDate;
        this.isRegistered = false;
        this.studySessionIds = new HashSet<>();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Exam)) return false;

        Exam exam = (Exam) o;

        if (id != exam.id) return false;
        if (isRegistered != exam.isRegistered) return false;
        if (!examDate.equals(exam.examDate)) return false;
        if (!name.equals(exam.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        return result;
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
}
