package com.gcw_rome_2014.quickstudy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        date_details.setText(new SimpleDateFormat("EEE, d MMMM yyyy, HH:mm", Locale.ITALIAN).format(exam.getDate().getTime()));
        difficulty_details.setText(exam.getDifficulty().getName());
        if (exam.isRegistered())
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
                Intent i = new Intent(this, EditExamActivity.class);
                i.putExtra("exam", this.exam);
                startActivity(i);
                return true;
            case R.id.action_delete:
                new AlertDialog.Builder(this)
                        .setTitle("Delete exam")
                        .setMessage("Are you sure you want to delete this exam?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Boolean done = QuickStudy.getInstance().deleteExam(ViewExamActivity.this.exam);
                                if(done) {
                                    showToastMessage("The exam has been deleted");
                                    startActivity(new Intent(getApplicationContext(), ExamsActivity.class));
                                } else
                                    showToastMessage("An error occurred while deleting the exam");
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert).show();
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
