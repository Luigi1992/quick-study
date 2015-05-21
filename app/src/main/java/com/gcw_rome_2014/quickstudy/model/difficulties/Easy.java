package com.gcw_rome_2014.quickstudy.model.difficulties;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.gcw_rome_2014.quickstudy.R;

/**
 * Created by Luigi on 13/02/2015.
 */
public class Easy extends Difficulty {

    public Easy(Context context) {
        super("Easy", R.drawable.icon_easy_test, context);
    }

    @Override
    public int getHoursOfStudy() {
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this.getContext());
        return Integer.parseInt(prefs.getString("preference_easy", "1"));
    }
}