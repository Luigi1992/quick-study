package com.gcw_rome_2014.saveme;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    EditText examNameEditText;
    EditText dateOfExamEditText;
    EditText hourOfExamEditText;
    EditText numberOfHoursEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        examNameEditText = (EditText) findViewById(R.id.examNameEditText);
        dateOfExamEditText = (EditText) findViewById(R.id.dateOfExamEditText);
        hourOfExamEditText= (EditText) findViewById(R.id.hourOfExamEditText);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                        myCalendar.get(Calendar.HOUR),
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
}
