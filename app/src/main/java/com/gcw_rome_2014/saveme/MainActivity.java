package com.gcw_rome_2014.saveme;

import android.app.*;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.gcw_rome_2014.saveme.calendar.ScheduleManager;
import com.gcw_rome_2014.saveme.model.Exam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    EditText examNameEditText;
    EditText dateOfExamEditText;
    EditText hourOfExamEditText;
    EditText numberOfHoursEditText;

    private static final int RESULT_SETTINGS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Show icon in the Action Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);

        //Set default values for settings
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        examNameEditText = (EditText) findViewById(R.id.examNameEditText);
        dateOfExamEditText = (EditText) findViewById(R.id.dateOfExamEditText);
        hourOfExamEditText = (EditText) findViewById(R.id.hourOfExamEditText);
        numberOfHoursEditText = (EditText) findViewById(R.id.numberOfHoursEditText);

        // To prevent opening keyboard before date/time dialog
        dateOfExamEditText.setInputType(InputType.TYPE_NULL);
        dateOfExamEditText.setFocusable(false);
        hourOfExamEditText.setInputType(InputType.TYPE_NULL);
        hourOfExamEditText.setFocusable(false);

        setDatePicker();
        setHourPicker();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivityForResult(i, RESULT_SETTINGS);
                break;
        }
        return true;
    }

    public void setDatePicker() {
        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel(myCalendar);
            }
        };

        dateOfExamEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker = new DatePickerDialog(MainActivity.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePicker.show();
            }
        });
    }

    private void setHourPicker() {

        final Calendar myCalendar = Calendar.getInstance();

        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                updateTimeLabel(myCalendar);
            }
        };

        hourOfExamEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, time,
                        myCalendar.get(Calendar.HOUR_OF_DAY),
                        myCalendar.get(Calendar.MINUTE), true);
                hourOfExamEditText.setInputType(InputType.TYPE_NULL);

                timePickerDialog.show();
            }
        });
    }

    private void updateTimeLabel(Calendar myCalendar) {
        String myFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALIAN);

        hourOfExamEditText.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateDateLabel(Calendar myCalendar) {

        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALIAN);

        dateOfExamEditText.setText(sdf.format(myCalendar.getTime()));
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    /**
     * This function is called when the SAVE ME Button is tapped.
     *
     * @param view Default param.
     */
    public void addEvent(View view) {
        ScheduleManager scheduleManager = new ScheduleManager(getContentResolver());

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy-HH:mm", Locale.getDefault());
        try {
            String examName = examNameEditText.getText().toString();
            String numberOfHoursString = numberOfHoursEditText.getText().toString();

            if(examName.isEmpty() || numberOfHoursString.isEmpty())
                throw new Exception();

            int hoursOfStudy = Integer.valueOf(numberOfHoursString);
            String dateString = dateOfExamEditText.getText().toString() + "-" + hourOfExamEditText.getText().toString();
            Date date = dateFormat.parse(dateString);

            //Set calendar for an easy management of time.
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);

            long eventID = scheduleManager.addExam(examName, calendar, hoursOfStudy);

            Intent i = new Intent(getApplicationContext(), ExamsActivity.class);
            startActivity(i);


            //Clear all fields
            this.clearAllFields();

            /*//Open calendarProvider calender with an intent to show the inserted event.
            Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
            Intent intent = new Intent(Intent.ACTION_VIEW)
                    .setData(uri);
            startActivity(intent);*/


        } catch (Exception e) {
            Context context = getApplicationContext();
            CharSequence text = "All fields are required";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        }

    }

    /**
     * Clear all the fields.
     */
    private void clearAllFields() {
        examNameEditText.setText("");
        dateOfExamEditText.setText("");
        hourOfExamEditText.setText("");
        numberOfHoursEditText.setText("");
    }
}
