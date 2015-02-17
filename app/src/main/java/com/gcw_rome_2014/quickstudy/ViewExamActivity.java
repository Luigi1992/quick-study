package com.gcw_rome_2014.quickstudy;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.gcw_rome_2014.quickstudy.model.Exam;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exam);

        Exam exam = (Exam) getIntent().getSerializableExtra("exam");

        name_details = (TextView) findViewById(R.id.details_name);
        date_details = (TextView) findViewById(R.id.details_date);
        difficulty_details = (TextView) findViewById(R.id.details_difficulty);
        registered_details = (TextView) findViewById(R.id.details_registered);

        name_details.setText(exam.getName());
        date_details.setText(new SimpleDateFormat("EEE d MMM yyyy, HH:mm", Locale.ITALIAN).format(exam.getExamDate().getTime()));
        difficulty_details.setText(exam.getDifficulty().getName());
        if (exam.isRegistered())
            registered_details.setText("Yes");
        else
            registered_details.setText("No");

        //Show icon in the Action Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
    }

}
