package com.gcw_rome_2014.quickstudy;

import android.content.Context;
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
    private RadioButton registered_details_yes;
    private RadioButton registered_details_no;
    private Exam exam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exam);

        this.exam = (Exam) getIntent().getSerializableExtra("exam");

        name_details = (TextView) findViewById(R.id.details_name);
        date_details = (TextView) findViewById(R.id.details_date);
        difficulty_details = (TextView) findViewById(R.id.details_difficulty);
        registered_details_yes = (RadioButton) findViewById(R.id.details_registered_yes);
        registered_details_no = (RadioButton) findViewById(R.id.details_registered_no);

        name_details.setText(exam.getName());
        date_details.setText(new SimpleDateFormat("EEE d MMM yyyy, HH:mm", Locale.ITALIAN).format(exam.getExamDate().getTime()));
        difficulty_details.setText(exam.getDifficulty().getName());
        if (exam.isRegistered())
            registered_details_yes.toggle();
        else
            registered_details_no.toggle();

        //Show icon in the Action Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
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
                if(done)
                    showToastMessage("Success! The Exam has been deleted");
                else
                    showToastMessage("There was an error during deleting the exam");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.details_registered_yes:
                if (checked)
                    // TODO
                    break;
            case R.id.details_registered_no:
                if (checked)
                    // TODO
                    break;
        }
    }

    private void showToastMessage(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

}
