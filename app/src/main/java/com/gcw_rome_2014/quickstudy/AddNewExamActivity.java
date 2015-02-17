package com.gcw_rome_2014.quickstudy;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Button;

import com.gcw_rome_2014.quickstudy.calendar.ScheduleManager;
import com.gcw_rome_2014.quickstudy.model.Exam;
import com.gcw_rome_2014.quickstudy.model.difficulties.Difficulty;
import com.gcw_rome_2014.quickstudy.model.difficulties.Easy;
import com.gcw_rome_2014.quickstudy.model.difficulties.Hard;
import com.gcw_rome_2014.quickstudy.model.difficulties.Medium;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class AddNewExamActivity extends ActionBarActivity {

    EditText examNameEditText;
    EditText dateOfExamEditText;
    EditText hourOfExamEditText;
    EditText numberOfHoursEditText;
    Spinner examDifficultySpinner;
    TextView saveNewExamButton;


    private static final int RESULT_SETTINGS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exam);

        //Show icon in the Action Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);

        //Set default values for settings
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        examNameEditText = (EditText) findViewById(R.id.examNameEditText);
        dateOfExamEditText = (EditText) findViewById(R.id.dateOfExamEditText);
        hourOfExamEditText = (EditText) findViewById(R.id.hourOfExamEditText);
        numberOfHoursEditText = (EditText) findViewById(R.id.numberOfHoursEditText);
        examDifficultySpinner = (Spinner) findViewById(R.id.exam_difficulty_spinner);
        saveNewExamButton = (TextView) findViewById(R.id.saveMeTextView);

        this.clearAllFields();

        // To prevent opening keyboard before date/time dialog
        dateOfExamEditText.setInputType(InputType.TYPE_NULL);
        dateOfExamEditText.setFocusable(false);
        hourOfExamEditText.setInputType(InputType.TYPE_NULL);
        hourOfExamEditText.setFocusable(false);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.difficulty_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        examDifficultySpinner.setAdapter(adapter);

        setDatePicker();
        setHourPicker();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        // Validation of fields and saving exam in new thread.
        saveNewExamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (!isExamValid(v)) {
                    showErrorToast();
                } else {
                    final Exam newExam = parseExam(v);
                    new Thread(new Runnable() {
                        public void run() {

                            saveNewExamEvent(newExam);
                        }
                    }).start();
                }
            }

        });
    }

    private boolean isExamValid(View v) {
        String examName = examNameEditText.getText().toString();
        String numberOfHoursString = numberOfHoursEditText.getText().toString();

        if (examName.isEmpty() || numberOfHoursString.isEmpty())
            return false;

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy-HH:mm", Locale.getDefault());
        int hoursOfStudy = Integer.valueOf(numberOfHoursString);
        String dateString = dateOfExamEditText.getText().toString() + "-" + hourOfExamEditText.getText().toString();
        try {
            dateFormat.parse(dateString);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private Exam parseExam(View v) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy-HH:mm", Locale.getDefault());
        String examName = examNameEditText.getText().toString();
        String numberOfHoursString = numberOfHoursEditText.getText().toString();

        int hoursOfStudy = Integer.valueOf(numberOfHoursString);
        String dateString = dateOfExamEditText.getText().toString() + "-" + hourOfExamEditText.getText().toString();
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (Exception e) {
            // The error here would be caught in isExamValid method.
        }

        //Set calendar for an easy management of time.
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        String examDifficultyString = examDifficultySpinner.getSelectedItem().toString();

        Difficulty difficulty;
        switch (examDifficultyString) {
            case "Easy":
                difficulty = new Easy();
                break;
            case "Medium":
                difficulty = new Medium();
                break;
            case "Hard":
                difficulty = new Hard();
                break;
            default:
                difficulty = new Medium();
        }

        return new Exam(examName, difficulty, calendar);
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
                DatePickerDialog datePicker = new DatePickerDialog(AddNewExamActivity.this, date,
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddNewExamActivity.this, time,
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
     * @param exam Exam to be saved.
     */
    public void saveNewExamEvent(Exam exam) {
        ScheduleManager scheduleManager = new ScheduleManager(getContentResolver(), getApplicationContext());

        long eventID = scheduleManager.addExam(exam, exam.getDifficulty().getHoursOfStudy());

        Intent i = new Intent(getApplicationContext(), ExamsActivity.class);
        startActivity(i);

        /*//Open calendarProvider calender with an intent to show the inserted event.
        Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
        Intent intent = new Intent(Intent.ACTION_VIEW)
                .setData(uri);
        startActivity(intent);*/
    }

    private void showErrorToast() {
        Context context = getApplicationContext();
        CharSequence text = "All fields are required";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
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
