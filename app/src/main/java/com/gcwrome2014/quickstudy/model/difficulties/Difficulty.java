package com.gcwrome2014.quickstudy.model.difficulties;

import java.io.Serializable;

/**
 * Created by Luigi on 13/02/2015.
 */
public abstract class Difficulty implements Serializable{

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Difficulty)) return false;

        Difficulty that = (Difficulty) o;

        if (imageValue != that.imageValue) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}