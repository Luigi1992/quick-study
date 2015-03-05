package com.gcw_rome_2014.quickstudy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gcw_rome_2014.quickstudy.model.Exam;
import com.gcw_rome_2014.quickstudy.model.QuickStudy;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Alessio on 16/02/2015.
 */
public class ViewExamActivity extends ActionBarActivity {
    private TextView name_details;
    private TextView date_details;
    private TextView difficulty_details;
    private TextView registered_details;
    private Exam exam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exam);

        this.exam = (Exam) getIntent().getSerializableExtra("exam");

        name_details = (TextView) findViewById(R.id.details_name);
        date_details = (TextView) findViewById(R.id.details_date);
        difficulty_details = (TextView) findViewById(R.id.details_difficulty);
        registered_details = (TextView) findViewById(R.id.details_registered);

        name_details.setText(exam.getName());
        date_details.setText(new SimpleDateFormat("EEE, d MMMM yyyy, HH:mm", Locale.ITALIAN).format(exam.getExamDate().getTime()));
        difficulty_details.setText(exam.getDifficulty().getName());
        if (exam.isRegistered()==true)
            registered_details.setText("You've already signed up for this exam");
        else
            registered_details.setText("You haven't signed up for this exam yet");

        //Show icon in the Action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_exam, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_edit:
                //TODO
                return true;
            case R.id.action_delete:
                Boolean done = QuickStudy.getInstance().deleteExam(this.exam);
                if(done) {
                    showToastMessage("Success! The exam has been deleted");
                    Intent i = new Intent(this, ExamsActivity.class);
                    startActivity(i);
                } else
                    showToastMessage("There was an error while deleting the exam");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showToastMessage(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

}
