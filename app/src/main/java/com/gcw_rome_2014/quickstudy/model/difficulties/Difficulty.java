package com.gcw_rome_2014.quickstudy.model.difficulties;

/**
 * Created by Luigi on 13/02/2015.
 */
public abstract class Difficulty {

    private String name;
    private int imageValue;

    public Difficulty(String name, int value) {
        this.name = name;
        this.imageValue = value;
    }

    public int getImageValue() {
        return imageValue;
    }

    public void setImageValue(int value) {
        this.imageValue = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract int getHoursOfStudy();
}