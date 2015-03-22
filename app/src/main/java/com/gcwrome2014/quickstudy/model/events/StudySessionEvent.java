package com.gcwrome2014.quickstudy.model.events;

import com.gcwrome2014.quickstudy.model.Exam;
import com.gcwrome2014.quickstudy.model.QuickStudy;

import java.util.Calendar;

/**
 * Created by Luigi on 09/03/2015.
 * Class that represent a study session on Calendar.
 */
public class StudySessionEvent extends Event {
    public StudySessionEvent(Exam exam, Calendar dateAndTime, int duration) {
        super(exam,
                "Study Session for " + exam.getName(),
                QuickStudy.getAppName() + " " + exam.getName() + " Study Session",
                dateAndTime,
                duration);
    }

}
