package com.gcw_rome_2014.quickstudy.model.difficulties;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.gcw_rome_2014.quickstudy.R;
import com.gcw_rome_2014.quickstudy.settings.SetCustomDiffActivity;

/**
 * Created by Luigi on 13/02/2015.
 */
public class Hard extends Difficulty {

    public Hard(Context context) {
        super("Hard", R.drawable.icon_hard_test, context);
    }

    @Override
    public int getHoursOfStudy() {
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this.getContext());
        return Integer.parseInt(prefs.getString("preference_hard", "4"));
    }
}
