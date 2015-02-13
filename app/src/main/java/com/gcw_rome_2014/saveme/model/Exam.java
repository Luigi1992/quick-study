package com.gcw_rome_2014.saveme.model;

import com.gcw_rome_2014.saveme.R;
import com.gcw_rome_2014.saveme.model.difficulties.Difficulty;

/**
 * Created by Luigi on 18/01/2015.
 */
public class Exam {

    //The Exam name
    String name;

    //The exam difficulty. It is a integer representing an image.
    Difficulty difficulty;

    public Exam(String name, Difficulty difficulty) {
        this.name = name;
        this.difficulty = difficulty;
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
}
