package com.gcw_rome_2014.quickstudy.model;

/**
 * Created by Luigi on 15/02/2015.
 * This class represent the entry point of the application.
 */
public class QuickStudy {
    private static QuickStudy instance = new QuickStudy();

    public static QuickStudy getInstance() {
        return instance;
    }

    private QuickStudy() {

    }

}
