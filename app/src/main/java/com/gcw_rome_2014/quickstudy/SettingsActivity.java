package com.gcw_rome_2014.quickstudy;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Alessio on 05/01/2015.
 */
public class SettingsActivity extends PreferenceActivity {

    public static int DIFFICULTY_HOURS_EASY = 1;
    public static int DIFFICULTY_HOURS_MEDIUM = 3;
    public static int DIFFICULTY_HOURS_HARD = 6;



    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
        final EditTextPreference preferenceEasy = (EditTextPreference)getPreferenceScreen().findPreference("preference_easy");
        final EditTextPreference preferenceMedium = (EditTextPreference)getPreferenceScreen().findPreference("preference_medium");
        final EditTextPreference preferenceHard = (EditTextPreference)getPreferenceScreen().findPreference("preference_hard");

        LinearLayout root = (LinearLayout)findViewById(android.R.id.list).getParent().getParent().getParent();
        Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.settings_toolbar, root, false);
        root.addView(bar, 0); // insert at top
        bar.setBackgroundDrawable(new ColorDrawable(R.color.main_color));
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                DIFFICULTY_HOURS_EASY = Integer.parseInt(preferenceEasy.getText());
                DIFFICULTY_HOURS_MEDIUM = Integer.parseInt(preferenceMedium.getText());
                DIFFICULTY_HOURS_HARD = Integer.parseInt(preferenceHard.getText());
            }
        });

        //This method is deprecated, but allows to view the toolbar on the settings screen

        /*// Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();*/

    }

}
