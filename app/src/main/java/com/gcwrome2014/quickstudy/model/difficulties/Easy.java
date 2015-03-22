package com.gcwrome2014.quickstudy.model.difficulties;

import com.gcwrome2014.quickstudy.R;
import com.gcwrome2014.quickstudy.settings.SetCustomDiffActivity;

/**
 * Created by Luigi on 13/02/2015.
 */
public class Easy extends Difficulty {

    public Easy() {
        super("Easy", R.drawable.icon_easy_test);
    }

    @Override
    public int getHoursOfStudy() {
        return SetCustomDiffActivity.DIFFICULTY_HOURS_EASY;
    }
}