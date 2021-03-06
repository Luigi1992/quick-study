package com.gcwrome2014.quickstudy.settings;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gcwrome2014.quickstudy.R;

/**
 * Created by Alessio on 16/03/2015.
 */
public class SetCustomDiffActivity extends PreferenceActivity {

    public static int DIFFICULTY_HOURS_EASY = 1;
    public static int DIFFICULTY_HOURS_MEDIUM = 2;
    public static int DIFFICULTY_HOURS_HARD = 4;

    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_custom_diff);

        final EditTextPreference preferenceEasy = (EditTextPreference) getPreferenceScreen().findPreference("preference_easy");
        final EditTextPreference preferenceMedium = (EditTextPreference) getPreferenceScreen().findPreference("preference_medium");
        final EditTextPreference preferenceHard = (EditTextPreference) getPreferenceScreen().findPreference("preference_hard");

        preferenceEasy.setSummary(preferenceEasy.getText() + " hours");
        preferenceMedium.setSummary(preferenceMedium.getText() + " hours");
        preferenceHard.setSummary(preferenceHard.getText() + " hours");

        setNumberOfHoursValidation(preferenceEasy);
        setNumberOfHoursValidation(preferenceMedium);
        setNumberOfHoursValidation(preferenceHard);

        LinearLayout root = (LinearLayout) findViewById(android.R.id.list).getParent().getParent().getParent();
        Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.settings_toolbar, root, false);
        root.addView(bar, 0); // insert at top
        bar.setBackgroundDrawable(new ColorDrawable(R.color.main_color));
        bar.setTitle(R.string.pref_hours_number_tittle);
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DIFFICULTY_HOURS_EASY = Integer.parseInt(preferenceEasy.getText());
                DIFFICULTY_HOURS_MEDIUM = Integer.parseInt(preferenceMedium.getText());
                DIFFICULTY_HOURS_HARD = Integer.parseInt(preferenceHard.getText());
                finish();
            }
        });
    }

    private void setNumberOfHoursValidation(EditTextPreference preference) {
        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                int numberOfHours = Integer.parseInt(newValue.toString());
                if (numberOfHours >= 1 && numberOfHours <= 12) {
                    // Updating summary
                    preference.setSummary(String.valueOf(numberOfHours)+" hours");
                    return true;
                } else {
                    // Invalid value
                    showErrorToast("Insert a value between 1 and 12");
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
