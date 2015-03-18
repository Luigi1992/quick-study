package com.gcw_rome_2014.quickstudy.model.difficulties;

import com.gcw_rome_2014.quickstudy.R;
import com.gcw_rome_2014.quickstudy.settings.SetCustomDiffActivity;

/**
 * Created by Luigi on 13/02/2015.
 */
public class Medium extends Difficulty {

    public Medium() {
        super("Medium", R.drawable.icon_medium_test);
    }

    @Override
    public int getHoursOfStudy() {
        return SetCustomDiffActivity.DIFFICULTY_HOURS_MEDIUM;
    }
}
