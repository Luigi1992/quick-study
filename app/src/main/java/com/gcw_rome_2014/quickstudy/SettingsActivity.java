package com.gcw_rome_2014.quickstudy;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

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

        setNumberOfHoursValidation(preferenceEasy);
        setNumberOfHoursValidation(preferenceMedium);
        setNumberOfHoursValidation(preferenceHard);

        LinearLayout root = (LinearLayout)findViewById(android.R.id.list).getParent().getParent().getParent();
        Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.settings_toolbar, root, false);
        root.addView(bar, 0); // insert at top
        bar.setBackgroundDrawable(new ColorDrawable(R.color.main_color));
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DIFFICULTY_HOURS_EASY = Integer.parseInt(preferenceEasy.getText());
                DIFFICULTY_HOURS_MEDIUM = Integer.parseInt(preferenceMedium.getText());
                DIFFICULTY_HOURS_HARD = Integer.parseInt(preferenceHard.getText());
                finish();
            }
        });

        //This method is deprecated, but allows to view the toolbar on the settings screen

        /*// Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();*/

    }

    private void setNumberOfHoursValidation(EditTextPreference preference) {
        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                int numberOfHours = Integer.parseInt(newValue.toString());
                if (numberOfHours >= 1 && numberOfHours <= 12) {
                    // Updating summary
                    preference.setSummary(String.valueOf(numberOfHours));
                    return true;
                } else {
                    // Invalid value
                    showErrorToast("The value should be between 1 and 12");
                    return false;
                }
            }
        });
    }

    private void showErrorToast(String message) {
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
